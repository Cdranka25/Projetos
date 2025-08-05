import datetime
import threading
import time


class Despertador:

    DIAS_SEMANA = {
        "segunda-feira": 0,
        "terça-feira": 1,
        "quarta-feira": 2,
        "quinta-feira": 3,
        "sexta-feira": 4,
        "sábado": 5,
        "domingo": 6
    }

    def __init__(self):
        self.alarmes = []
        self._lock = threading.Lock()

    def __del__(self):
        with self._lock:
            for alarme in self.alarmes:
                alarme["ativo"] = False
                if "thread" in alarme:
                    alarme["thread"].join(timeout=1)
    
    def adicionar_alarme(self, hora, minuto, audio, repetir=False, dias=None, callback=None):
        
        with self._lock:
            if not (0 <= hora <= 23 and 0 <= minuto <= 59):
                raise ValueError("Hora e minuto devem estar dentro dos limites válidos")
        
        dias = dias or []

        for alarme in self.alarmes:
            if (alarme["hora"] == hora and alarme["minuto"] == minuto and
                alarme["repetir"] == repetir and alarme["dias"] == dias):
                return False

        alarme = {
            "hora": hora,
            "minuto": minuto,
            "audio": audio,
            "repetir": repetir,
            "dias": dias,
            "callback": callback,
            "ativo": True,
            "executando": False
        }

        thread = threading.Thread(target=self._verificar_alarme, args=(alarme,))
        thread.daemon = True
        thread.start()
        alarme["thread"] = thread
        self.alarmes.append(alarme)
        return True

    def _verificar_alarme(self, alarme):
        while alarme["ativo"]:
            agora = datetime.datetime.now()
            dia_atual = agora.strftime("%A").lower()
            dia_atual_pt = {
                "monday": "segunda-feira",
                "tuesday": "terça-feira",
                "wednesday": "quarta-feira",
                "thursday": "quinta-feira",
                "friday": "sexta-feira",
                "saturday": "sábado",
                "sunday": "domingo"
            }.get(dia_atual)

            hora_certa = (agora.hour == alarme["hora"] and 
                         agora.minute == alarme["minuto"] and 
                         not alarme["executando"])
            
            if hora_certa:
                if not alarme["dias"] or dia_atual_pt in alarme["dias"]:
                    alarme["executando"] = True
                    if alarme["callback"]:
                        alarme["callback"]()
                    if not alarme["repetir"]:
                        alarme["ativo"] = False

            time.sleep(30)

    def soneca_alarme(self, hora, minuto, minutos_soneca):
        for alarme in self.alarmes:
            if alarme["hora"] == hora and alarme["minuto"] == minuto:
                alarme["executando"] = False
                nova_hora = datetime.datetime.now() + datetime.timedelta(
                    minutes=minutos_soneca
                )
                print(
                    f"🕒 Soneca ativada! Novo alarme em {nova_hora.hour}:{nova_hora.minute:02d}"
                )
                self.adicionar_alarme(
                    nova_hora.hour,
                    nova_hora.minute,
                    repetir=False,
                    callback=alarme["callback"],
                )

    def parar_alarme(self, hora, minuto):
        for alarme in self.alarmes:
            if alarme["hora"] == hora and alarme["minuto"] == minuto:
                alarme["executando"] = False

    def desativar_alarme(self, hora, minuto):
        for alarme in self.alarmes:
            if alarme["hora"] == hora and alarme["minuto"] == minuto:
                alarme["ativo"] = False

    def ativar_alarme(self, hora, minuto):
        for alarme in self.alarmes:
            if alarme["hora"] == hora and alarme["minuto"] == minuto:
                alarme["ativo"] = True

    def delete_alarme(self, hora, minuto):
        for alarme in self.alarmes:
            if alarme["hora"] == hora and alarme["minuto"] == minuto:
                self.alarmes.remove(alarme)
                print(f"Alarme de {hora}:{minuto} removido com sucesso!")

    