# cronometro.py
import time

class Cronometro:
    def __init__(self):
        self.tempo_inicial = None
        self.tempo_decorrido = 0 #segundos
        self.rodando = False

    def iniciar(self):
        if not self.rodando:
            self.tempo_inicial = time.time()
            self.rodando = True

    def pausar(self):
        if self.rodando:
            self.tempo_decorrido += time.time() - self.tempo_inicial
            self.rodando = False

    def resetar(self):
        self.tempo_inicial = None
        self.tempo_decorrido = 0
        self.rodando = False

    def tempo_atual(self):
        total_segundos = self.tempo_decorrido
        if self.rodando:
            total_segundos += time.time() - self.tempo_inicial
        return int(total_segundos * 1000)
    
    