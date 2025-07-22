import datatime
import time
from threading import Thread

class Alarme:
    def __init__(self, hora, minuto, repetir_dias=None, ativo=true):
        self.hora = hora
        self.minuto = minuto
        self.repetir_dias = repetir_dias or []
        self.ativo = ativo
        self.tocou_hoje = False

    def __str__(self):
        dias = ", ".join(self.repetir_dias) if self.repetir_dias else "único"
        status = "Ativo" if self.ativo else "Inativo"
        return f"{self.hora:02}:{self.minuto:02} - {dias} - {status}"

