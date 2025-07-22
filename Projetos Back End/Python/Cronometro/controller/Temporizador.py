import time

class Temporizador:
    
    def __init__(self):
        self.hora = 0
        self.minuto = 0
        self.segundo = 0
        self.total_segundos = 0 
        self.ativo = False
        
    def iniciarTemporizador(self, hora, minuto, segundo):
        self.hora = hora
        self.minuto = minuto
        self.segundo = segundo
        
        self.total_segundos = (self.hora * 3600) + (self.minuto * 60) + self.segundo
        self.ativo = True      

        while self.total_segundos >= 0 and self.ativo:
            horas = self.total_segundos // 3600
            minutos = (self.total_segundos % 3600) // 60
            segundos = self.total_segundos % 60

            print(f"{horas:02}:{minutos:02}:{segundos:02}")
            time.sleep(1)
            self.total_segundos -= 1    

            print(f"{horas:02}:{minutos:02}:{segundos:02}")
            time.sleep(1)
            self.total_segundos -= 1
            
        print("⏰ Tempo esgotado!")
        self.parar_temporizador()
    
    def parar_temporizador(self):
        self.ativo = False