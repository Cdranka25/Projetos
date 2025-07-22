import time

class Cronometro:

    def __init__(self):
        self.inicio = 0
        self.fim = 0

    def iniciar_cronometro(self):
        self.inicio = time.perf_counter()
        print("Cronômetro iniciado.")

    def parar_cronometro(self):
        self.fim = time.perf_counter()
        self.tempo()

    def tempo(self):
        tempo_total = self.fim - self.inicio
        print(f"Tempo total: {tempo_total:.2f} segundos")

    def reiniciar_cronometro(self):
        self.inicio = 0
        self.fim = 0
        print("Cronômetro reiniciado.")

    def get_inicial(self):
        return self.inicio
    def set_inicial(self, valor):
        self.inicio = valor
    def get_final(self):
        return self.fim
    def set_final(self, valor):
        self.fim = valor
    


