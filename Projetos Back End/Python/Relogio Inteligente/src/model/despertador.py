# despertador.py 
import threading
import datetime
from typing import Callable, List, Optional, Dict, Any
import logging

logger = logging.getLogger(__name__)

class Despertador:
    def __init__(self):
        self._alarms: List[Dict[str, Any]] = []
        self._lock = threading.Lock()
        self._stop_event = threading.Event()
        self._scheduler_thread = threading.Thread(target=self._scheduler_loop, daemon=True)
        self._scheduler_thread.start()
        self._next_id = 1

    def _normalize_days(self, days: Optional[List[int]]) -> List[int]:
        if not days:
            return []

        return sorted({int(d) for d in days})

    def add_alarm(self, hour: int, minute: int, audio: str,
                  repeat: bool = False, days: Optional[List[int]] = None,
                  callback: Optional[Callable] = None) -> int:
        days = self._normalize_days(days)
        with self._lock:

            for a in self._alarms:
                if (a['hour'] == hour and a['minute'] == minute and a['repeat'] == repeat
                        and a['days'] == days and a['audio'] == audio):
                    return a['id'] 

            alarm = {
                'id': self._next_id,
                'hour': int(hour),
                'minute': int(minute),
                'audio': str(audio),
                'repeat': bool(repeat),
                'days': list(days),
                'callback': callback,
                'enabled': True,
                'fired_today': False
            }
            self._next_id += 1
            self._alarms.append(alarm)
            logger.debug("Alarm added: %s", alarm)
            return alarm['id']

    def remove_alarm(self, alarm_id: int) -> bool:
        with self._lock:
            before = len(self._alarms)
            self._alarms = [a for a in self._alarms if a['id'] != alarm_id]
            removed = len(self._alarms) < before
            if removed:
                logger.debug("Alarm removed: %s", alarm_id)
            return removed

    def enable_alarm(self, alarm_id: int):
        with self._lock:
            for a in self._alarms:
                if a['id'] == alarm_id:
                    a['enabled'] = True

    def disable_alarm(self, alarm_id: int):
        with self._lock:
            for a in self._alarms:
                if a['id'] == alarm_id:
                    a['enabled'] = False

    def edit_alarm(self, alarm_id: int, **kwargs) -> bool:
        allowed = {'hour', 'minute', 'audio', 'repeat', 'days', 'enabled'}
        with self._lock:
            for a in self._alarms:
                if a['id'] == alarm_id:
                    for k, v in kwargs.items():
                        if k in allowed:
                            a[k] = self._normalize_days(v) if k == 'days' else v
                    return True
        return False

    def list_alarms(self) -> List[Dict[str, Any]]:
        with self._lock:

            return [a.copy() for a in self._alarms]

    def stop_all(self, join_timeout: float = 1.0):
        self._stop_event.set()
        try:
            if self._scheduler_thread.is_alive():
                self._scheduler_thread.join(timeout=join_timeout)
        except Exception:
            logger.exception("Erro ao encerrar scheduler thread")

    def _scheduler_loop(self):
        while not self._stop_event.is_set():
            now = datetime.datetime.now()
            weekday = now.weekday() 

            with self._lock:
                alarms_snapshot = [a.copy() for a in self._alarms]

            for a in alarms_snapshot:
                if not a.get('enabled', True):
                    continue

                day_ok = (not a['days']) or (weekday in a['days'])
                if not day_ok:

                    with self._lock:
                        orig = next((o for o in self._alarms if o['id'] == a['id']), None)
                        if orig:
                            orig['fired_today'] = False
                    continue

                if now.hour == a['hour'] and now.minute == a['minute']:
                    if not a.get('fired_today', False):
                        if a.get('callback'):
                            try:

                                threading.Thread(target=self._safe_call, args=(a['callback'],), daemon=True).start()
                            except Exception:
                                logger.exception("Falha ao iniciar thread de callback")

                        with self._lock:
                            orig = next((o for o in self._alarms if o['id'] == a['id']), None)
                            if orig:
                                orig['fired_today'] = True
                                if not orig.get('repeat', False):
                                    orig['enabled'] = False
                else:
                    with self._lock:
                        orig = next((o for o in self._alarms if o['id'] == a['id']), None)
                        if orig:
                            orig['fired_today'] = False

            self._stop_event.wait(5)

    def _safe_call(self, cb: Callable):
        try:
            cb()
        except Exception:
            logger.exception("Erro em callback de alarme")

    def __del__(self):
        try:
            self.stop_all()
        except Exception:
            pass
