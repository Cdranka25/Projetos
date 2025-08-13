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
            if not (0 <= hora <= 23 and 0 <= minuto <= 59 and 0 <= segundo <= 59):
                raise ValueError("Valores de tempo inválidos")

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

        while True:
            with self._lock:
                if not self.rodando or self.tempo_restante <= 0:
                    self.rodando = False
                    if self.tempo_restante <= 0 and self._callback:
                        self._callback(0) 
                        self.tocar_alarme()
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

    def tocar_alarme(self):

        def _tocar():
            try:
                pygame.mixer.init()
                pygame.mixer.music.load("media/aud/Basic Alarm.mp3")  
                pygame.mixer.music.play(-1)  
                self._alarme_tocando = True
                

                while self._alarme_tocando:
                    time.sleep(0.1)
                    
                pygame.mixer.music.stop()
                pygame.mixer.quit()
            except Exception as e:
                print(f"Erro ao tocar alarme: {e}")

        with self._lock:
            if not self._alarme_tocando:
                self._alarme_thread = threading.Thread(target=_tocar)
                self._alarme_thread.daemon = True
                self._alarme_thread.start()

    def parar_alarme(self):

        with self._lock:
            self._alarme_tocando = False
            if self._alarme_thread:
                self._alarme_thread.join(timeout=0.5)

    def parar_temporizador(self):
        with self._lock:
            self.rodando = False
            self.parar_alarme()
            if self._thread:
                self._thread.join(timeout=0.1)

    def resetar_temporizador(self):
        with self._lock:
            self.rodando = False
            self.tempo_restante = 0.0
            self.total_ms = 0.0
            self.parar_alarme()
            if self._callback:
                self._callback(0)

    def definir_tempo(self, tempo_ms):
        with self._lock:
            self.tempo_restante = tempo_ms