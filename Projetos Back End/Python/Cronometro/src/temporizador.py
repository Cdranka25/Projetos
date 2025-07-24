import threading
import time
from formatador_tempo import formatar_tempo


class Temporizador:

    def __init__(self):
        self.total_ms = 0.0
        self.tempo_restante = 0.0
        self.rodando = False
        self._thread = None
        self._callback = None

    def iniciar_Temporizador(self, hora, minuto, segundo, callback=None):
        self.total_ms = (hora * 3600000) + (minuto * 60000) + (segundo * 1000)
        self.tempo_restante = self.total_ms
        self._callback = callback

        if not self.rodando and self.total_ms > 0:
            self.rodando = True
            self._thread = threading.Thread(target=self.contar_temporizador)
            self._thread.daemon = True
            self._thread.start()

    def contar_temporizador(self):
        ultimo_tempo = time.perf_counter()

        while self.rodando and self.tempo_restante > 0:
            time.sleep(0.005)
            agora = time.perf_counter()

            delta = (agora - ultimo_tempo) * 1000
            ultimo_tempo = agora

            self.tempo_restante -= delta
            if self.tempo_restante <= 0:
                self.tempo_restante = 0

            if self._callback:
                self._callback(self.tempo_restante)

        self.rodando = False

    def parar_temporizador(self):
        self.rodando = False
        if self._thread:
            self._thread.join(timeout=0.1)


def resetar_temporizador(self):
    self.temporizador.resetar_temporizador()
    self.label_temporizador.config(text="00:00:00:000")


def definir_tempo(self, tempo_ms):
    self.tempo_restante = tempo_ms
