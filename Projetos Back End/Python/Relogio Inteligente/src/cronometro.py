# cronometro.py
import time
import threading

class Cronometro:
    def __init__(self):
        self.tempo = 0.0
        self.rodando = False
        self._thread = None
        self._callback = None
        self._stop_event = threading.Event()

    def iniciar_cronometro(self, callback=None):
        if self.rodando:
            return
        
        self._stop_event.clear()
        self.rodando = True
        self._callback = callback
        self._thread = threading.Thread(target=self._contar, daemon=True)
        self._thread.start()

    def _contar(self):
        ultimo_tempo = time.perf_counter()
        
        while not self._stop_event.is_set():
            agora = time.perf_counter()
            delta = agora - ultimo_tempo
            self.tempo += delta
            ultimo_tempo = agora
            
            if self._callback:
                self._callback(self.tempo * 1000)
            
            time.sleep(0.045)  

    def parar_cronometro(self):
        if self.rodando:
            self._stop_event.set()
            self._thread.join(timeout=0.1)
            self.rodando = False

    def resetar_cronometro(self):
        self.parar_cronometro()  
        self.tempo = 0.0
        if self._callback:
            self._callback(0)  