# relogio.py — versão não bloqueante e mais robusta
import datetime
import threading
from typing import Dict, Optional, List
from zoneinfo import ZoneInfo, ZoneInfoNotFoundError
import requests


class Relogio:
    def __init__(self, fuso_padrao: str = "America/Sao_Paulo", fetch_on_init: bool = False,
                 geo_api_urls: Optional[List[str]] = None):
        self.fuso_padrao = fuso_padrao
        self.regiao = fuso_padrao 
        self.geo_data: Optional[Dict] = None
        self._lock = threading.Lock()
        self._fetch_thread: Optional[threading.Thread] = None
        self._stop_event = threading.Event()

        self.geo_api_urls = geo_api_urls or [
            "https://ipapi.co/json/",
            "https://ipinfo.io/json",
            "http://ip-api.com/json/"
        ]

        if fetch_on_init:
            self.start_location_fetch()

    def start_location_fetch(self, background: bool = True) -> None:
        if requests is None:
            return

        if self._fetch_thread and self._fetch_thread.is_alive():
            return

        def _worker():
            for url in self.geo_api_urls:
                if self._stop_event.is_set():
                    return
                try:
                    resp = requests.get(url, timeout=4)
                    resp.raise_for_status()
                    data = resp.json()
                    tz = None
                  
                    if isinstance(data, dict):
                        tz = data.get("timezone") or data.get("time_zone") or data.get("timezone_name") or data.get("tz")

                    if not tz:
                        tz = data.get('time_zone', {}).get('name') if isinstance(data.get('time_zone'), dict) else None

                    if tz:
                        try:
                            ZoneInfo(tz)
                            with self._lock:
                                self.regiao = tz
                                self.geo_data = data
                            return
                        except ZoneInfoNotFoundError:
                            pass
                except Exception:
                    continue
            with self._lock:
                self.geo_data = None
                self.regiao = self.fuso_padrao

        if background:
            self._fetch_thread = threading.Thread(target=_worker, daemon=True)
            self._fetch_thread.start()
        else:
            _worker()

    def stop_fetch(self) -> None:
        self._stop_event.set()
        if self._fetch_thread and self._fetch_thread.is_alive():
            self._fetch_thread.join(timeout=1)

    def set_timezone(self, tz_name: str) -> bool:
        """Força um timezone; retorna True se válido."""
        try:
            ZoneInfo(tz_name)  
            with self._lock:
                self.regiao = tz_name
            return True
        except Exception:
            return False

    def obter_horario(self) -> datetime.datetime:
        """Retorna datetime.now() no timezone configurado (regiao)."""
        with self._lock:
            tz_name = self.regiao or self.fuso_padrao
        try:
            return datetime.datetime.now(ZoneInfo(tz_name))
        except Exception:
            try:
                return datetime.datetime.now(ZoneInfo(self.fuso_padrao))
            except Exception:
                return datetime.datetime.utcnow()

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
