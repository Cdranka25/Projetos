import time
import threading


class Cronometro:
    def __init__(self):
        self.tempo = 0.0
        self.rodando = False
        self._thread = None
        self._callback = None


    def iniciar_cronometro(self, callback=None):
        if not self.rodando:
            self.rodando = True
            self._callback = callback
            self._thread = threading.Thread(target=self.contar_cronometro)
            self._thread.daemon = True
            self._thread.start()

    def contar_cronometro(self):
        ultimo_tempo = time.perf_counter()
        while self.rodando:
            time.sleep(0.005)
            agora = time.perf_counter()
            delta = agora - ultimo_tempo
            self.tempo += delta
            ultimo_tempo = agora
            if self._callback:
                self._callback(self.tempo)

    def parar_cronometro(self):
        self.rodando = False
        if self._thread:
            self._thread.join(timeout=0.1)

    def resetar_cronometro(self):
        self.parar_cronometro()
        self.tempo = 0.0
