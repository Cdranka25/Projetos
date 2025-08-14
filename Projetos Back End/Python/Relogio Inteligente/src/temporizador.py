import threading
import time
import pygame

class Temporizador:
    def __init__(self):
        self.total_ms = 0.0
        self.tempo_restante = 0.0
        self.rodando = False
        self._thread = None
        self._callback = None
        self._lock = threading.Lock()
        self._alarme_tocando = False
        self._alarme_thread = None

    def iniciar_temporizador(self, hora, minuto, segundo, callback=None):
        with self._lock:

            if self.total_ms == 0 and self.tempo_restante == 0:
                if not (0 <= hora <= 23 and 0 <= minuto <= 59 and 0 <= segundo <= 59):
                    raise ValueError("Valores de tempo inválidos")
                self.total_ms = (hora * 3600 + minuto * 60 + segundo) * 1000
                self.tempo_restante = self.total_ms

            if callback:
                self._callback = callback

            if not self.rodando and self.tempo_restante > 0:
                self.rodando = True
                self._thread = threading.Thread(target=self.contar_temporizador)
                self._thread.start()

    def contar_temporizador(self):
        ultimo_tempo = time.perf_counter()

        while True:
            with self._lock:
                if not self.rodando or self.tempo_restante <= 0:
                    self.rodando = False
                    if self.tempo_restante <= 0 and self._callback:
                        self._callback(0)
                    break

            time.sleep(0.1)
            agora = time.perf_counter()
            delta = (agora - ultimo_tempo) * 1000
            ultimo_tempo = agora

            with self._lock:
                if self.rodando:
                    self.tempo_restante -= delta
                    if self.tempo_restante <= 0:
                        self.tempo_restante = 0
                        self.rodando = False

                    if self._callback:
                        self._callback(self.tempo_restante)


    def para_temporizador(self):
        with self._lock:
            self.rodando = False

    def reseta_temporizador(self):
        with self._lock:
            self.rodando = False
            self.tempo_restante = 0.0
            self.total_ms = 0.0
            if self._callback:
                self._callback(0)

    def definir_tempo(self, tempo_ms):
        with self._lock:
            self.tempo_restante = tempo_ms