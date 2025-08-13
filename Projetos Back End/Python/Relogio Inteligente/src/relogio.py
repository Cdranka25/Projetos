import datetime
import requests
from zoneinfo import ZoneInfo
from typing import Dict, Optional

class Relogio:
    def __init__(self, fuso_padrao: str = "America/Sao_Paulo"):
        self.fuso_padrao = fuso_padrao
        self.geo_data: Optional[Dict] = None
        self._localizacao: Optional[datetime.datetime] = None
        self.regiao: str = fuso_padrao
        self._buscar_localizacao_inicial()  

    def _buscar_localizacao_inicial(self) -> None:
        try:
            response = requests.get('http://ip-api.com/json/', timeout=5)
            response.raise_for_status()
            self.geo_data = response.json()
            if self.geo_data and "timezone" in self.geo_data:
                self.regiao = self.geo_data["timezone"]
        except requests.exceptions.RequestException as e:
            print(f"Erro na requisição de localização: {e}. Usando fuso horário padrão.")
            self.geo_data = None
            self.regiao = self.fuso_padrao

    def obter_horario(self) -> datetime.datetime:
        """Obtém o horário atual usando a região já determinada"""
        try:
            self._localizacao = datetime.datetime.now(ZoneInfo(self.regiao))
        except Exception as e:
            print(f"\nAviso: {e}. Usando fuso horário padrão.")
            self._localizacao = datetime.datetime.now(ZoneInfo(self.fuso_padrao))
            self.regiao = self.fuso_padrao

        return self._localizacao

    def obter_dia_semana_ptbr(self, data: datetime.datetime) -> str:
        dias_semana = {
            "Monday": "Segunda-feira",
            "Tuesday": "Terça-feira",
            "Wednesday": "Quarta-feira",
            "Thursday": "Quinta-feira",
            "Friday": "Sexta-feira",
            "Saturday": "Sábado",
            "Sunday": "Domingo"
        }
        return dias_semana.get(data.strftime("%A"), data.strftime("%A"))
