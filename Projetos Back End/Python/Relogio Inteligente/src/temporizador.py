# temporizador.py
import threading
import time

class Temporizador:
    def __init__(self):
        self.total_ms = 0.0
        self.tempo_restante = 0.0
        self._lock = threading.Lock()

        self._stop_event = threading.Event()
        self._thread = None
        self._callback = None 


    def set_total_ms(self, tempo_ms: int):
        with self._lock:
            self.total_ms = float(tempo_ms)
            self.tempo_restante = float(tempo_ms)

    def get_remaining_ms(self) -> int:
        with self._lock:
            return int(max(0, self.tempo_restante))

    def is_running(self) -> bool:
        return self._thread is not None and self._thread.is_alive()

    def start_temporizador(self, callback=None):

        with self._lock:
            if callback:
                self._callback = callback

            if self.tempo_restante <= 0:
                return  

            if self._thread and self._thread.is_alive():
                return  

            self._stop_event.clear()
            self._thread = threading.Thread(target=self._run_loop, daemon=True)
            self._thread.start()

    def _run_loop(self):
        last = time.perf_counter()
        if self._callback:
            try:
                self._callback(self.get_remaining_ms())
            except Exception:
                pass

        while not self._stop_event.is_set():
            now = time.perf_counter()
            delta_ms = (now - last) * 1000.0
            last = now

            with self._lock:
                self.tempo_restante -= delta_ms
                if self.tempo_restante <= 0:
                    self.tempo_restante = 0
                    cb = self._callback

                else:
                    cb = self._callback

            if cb:
                try:
                    cb(int(max(0, self.tempo_restante)))
                except Exception:
                    pass

            if self.tempo_restante <= 0:
                break

            time.sleep(0.05)  

        if self._callback:
            try:
                self._callback(0)
            except Exception:
                pass

    def para_temporizador(self):
        self._stop_event.set()

        if self._thread:
            self._thread.join(timeout=0.2)
        self._thread = None

    def reseta_temporizador(self):
        self.para_temporizador()
        with self._lock:
            self.total_ms = 0.0
            self.tempo_restante = 0.0

        if self._callback:
            try:
                self._callback(0)
            except Exception:
                pass
