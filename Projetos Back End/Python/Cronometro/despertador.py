import datetime
import threading
import time
import locale


class Despertador:
    def __init__(self):
        self.alarmes = []

    def adicionar_alarme(
        self, hora, minuto, repetir=False, dias_semana=None, callback=None
    ):

        alarme = {
            "hora": hora,
            "minuto": minuto,
            "repetir": repetir,
            "dias_semana": dias_semana if dias_semana else [],
            "callback": callback,
            "ativo": True,
            "executando": False,
        }
        thread = threading.Thread(target=self._verificar_alarme, args=(alarme,))
        thread.daemon = True
        thread.start()
        alarme["thread"] = thread
        self.alarmes.append(alarme)

    def _verificar_alarme(self, alarme):
        while alarme["ativo"]:
            locale.setlocale(locale.LC_TIME, "pt_BR.UTF-8")
            agora = datetime.datetime.now()
            dia_atual = agora.strftime("%A").lower()
            dias_em_portugues = {
                "monday": "segunda-feira",
                "tuesday": "terça-feira",
                "wednesday": "quarta-feira",
                "thursday": "quinta-feira",
                "friday": "sexta-feira",
                "saturday": "sábado",
                "sunday": "domingo",
            }
            dia_atual_pt = dias_em_portugues.get(dia_atual)

            hora_certa = (
                agora.hour == alarme["hora"] and agora.minute == alarme["minuto"]
            )

            if hora_certa and not alarme["executando"]:
                if not alarme["dias_semana"] or dia_atual_pt in alarme["dias_semana"]:
                    alarme["executando"] = True
                    if alarme["callback"]:
                        alarme["callback"]()
                    if not alarme["repetir"]:
                        alarme["ativo"] = False

            time.sleep(1)

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
