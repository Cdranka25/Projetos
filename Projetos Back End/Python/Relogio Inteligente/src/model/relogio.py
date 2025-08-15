# relogio.py — versão não bloqueante e mais robusta
import datetime
import threading
from typing import Dict, Optional, List
from zoneinfo import ZoneInfo, ZoneInfoNotFoundError
import requests


class Relogio:
    """
    Relogio fornece horário local com ZoneInfo.
    Não bloqueia a inicialização: chamar start_location_fetch() para detectar fuso em background.
    """
    def __init__(self, fuso_padrao: str = "America/Sao_Paulo", fetch_on_init: bool = False,
                 geo_api_urls: Optional[List[str]] = None):
        self.fuso_padrao = fuso_padrao
        self.regiao = fuso_padrao  # string de timezone ex: "America/Sao_Paulo"
        self.geo_data: Optional[Dict] = None
        self._lock = threading.Lock()
        self._fetch_thread: Optional[threading.Thread] = None
        self._stop_event = threading.Event()

        # Lista de endpoints para tentar (pode ser substituída para testes)
        self.geo_api_urls = geo_api_urls or [
            "https://ipapi.co/json/",
            "https://ipinfo.io/json",
            "http://ip-api.com/json/"
        ]

        if fetch_on_init:
            self.start_location_fetch()

    def start_location_fetch(self, background: bool = True) -> None:
        """Inicia a busca do timezone em background (thread)."""
        if requests is None:
            # requests não disponível: não tenta fazer nada
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
                    # normalization: diferentes APIs usam chaves diferentes
                    if isinstance(data, dict):
                        tz = data.get("timezone") or data.get("time_zone") or data.get("timezone_name") or data.get("tz")
                        # ipapi.co returns 'timezone' or similar; ipinfo uses 'timezone' in some plans
                        # ipapi returns 'timezone' as e.g. "America/Sao_Paulo"
                        # ipinfo returns 'timezone' sometimes
                        # ipapi.co also returns 'timezone'
                    if not tz:
                        # algumas apis devolvem 'time_zone' como objeto
                        tz = data.get('time_zone', {}).get('name') if isinstance(data.get('time_zone'), dict) else None

                    if tz:
                        # valida ZoneInfo
                        try:
                            ZoneInfo(tz)
                            with self._lock:
                                self.regiao = tz
                                self.geo_data = data
                            return
                        except ZoneInfoNotFoundError:
                            # timezone inválido, ignora e tenta próxima API
                            pass
                except Exception:
                    # ignora e tenta próximo
                    continue
            # se chegou aqui: não obteve timezone válido -> mantem fuso_padrao
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
            ZoneInfo(tz_name)  # valida
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
            # fallback robusto para fuso_padrao
            try:
                return datetime.datetime.now(ZoneInfo(self.fuso_padrao))
            except Exception:
                # último recurso: timezone naive em UTC
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
