
from __future__ import annotations
import os
import pygame
import customtkinter as ctk  # type: ignore
from pathlib import Path

from ..model.cronometro import Cronometro
from ..model.temporizador import Temporizador
from ..model.despertador import Despertador
from ..model.relogio import Relogio
from .. import utils
from ..config import AUDIO_DIR


from .cronometro_view import CronometroView
from .temporizador_view import TemporizadorView
from .despertador_view import DespertadorView

ctk.set_appearance_mode("System")
ctk.set_default_color_theme("blue")


class App(ctk.CTk):
    def __init__(self):
        super().__init__()

        self.cronometro = Cronometro()
        self.temporizador = Temporizador()
        self.despertador = Despertador()
        self.relogio = Relogio()

        self.title("Relógio Inteligente")
        self.geometry("350x500")
        self.resizable(True, True)

        try:
            pygame.mixer.init(frequency=44100, size=-16, channels=2, buffer=512)
        except Exception:
            pass

        self.BASE_DIR = os.path.dirname(os.path.abspath(__file__))

        self.main_frame = ctk.CTkFrame(self)
        self.main_frame.pack(pady=20, padx=20, fill="both", expand=True)

        self.clock_frame = ctk.CTkFrame(self.main_frame)
        self.clock_frame.pack(pady=(0, 20), fill="x")

        self.lbl_horario = ctk.CTkLabel(self.clock_frame, text="", font=("Helvetica", 36, "bold"))
        self.lbl_horario.pack(pady=(10, 5), fill="x")

        self.lbl_data = ctk.CTkLabel(self.clock_frame, text="", font=("Helvetica", 14))
        self.lbl_data.pack(pady=(0, 5), fill="x")

        self.lbl_localizacao = ctk.CTkLabel(self.clock_frame, text="", font=("Helvetica", 12))
        self.lbl_localizacao.pack(pady=(0, 10), fill="x")

        try:
            self.relogio.start_location_fetch()
        except Exception:
            pass

        self.atualizar_relogio()

        self.btn_cronometro = ctk.CTkButton(self.main_frame, text="⏱️ Cronômetro",
                                           command=self.abrir_cronometro, height=40, corner_radius=10)
        self.btn_cronometro.pack(pady=10, fill="x")

        self.btn_temporizador = ctk.CTkButton(self.main_frame, text="⏲️ Temporizador",
                                             command=self.abrir_temporizador, height=40, corner_radius=10)
        self.btn_temporizador.pack(pady=10, fill="x")

        self.btn_despertador = ctk.CTkButton(self.main_frame, text="⏰ Despertador",
                                            command=self.abrir_despertador, height=40, corner_radius=10)
        self.btn_despertador.pack(pady=10, fill="x")

        self._cronometro_view = None
        self._temporizador_view = None
        self._despertador_view = None

        self.protocol("WM_DELETE_WINDOW", self.on_close)

    def atualizar_relogio(self):
        horario = self.relogio.obter_horario()
        if horario:
            self.lbl_horario.configure(text=horario.strftime("%H:%M:%S"))
            dia = self.relogio.obter_dia_semana_ptbr(horario)
            self.lbl_data.configure(text=f"{dia}, {horario.strftime('%d/%m/%Y')}")
            if getattr(self.relogio, "geo_data", None):
                g = self.relogio.geo_data
                self.lbl_localizacao.configure(text=f"{g.get('city','')}, {g.get('country','')}")
            else:
                self.lbl_localizacao.configure(text=getattr(self.relogio, "fuso_padrao", ""))
        self.after(1000, self.atualizar_relogio)

    def abrir_cronometro(self):
        if self._cronometro_view and self._cronometro_view.winfo_exists():
            self._cronometro_view.lift()
            return
        self._cronometro_view = CronometroView(self, self.cronometro)

    def abrir_temporizador(self):
        if self._temporizador_view and self._temporizador_view.winfo_exists():
            self._temporizador_view.lift()
            return
        audio_dir = Path(self.BASE_DIR) / ".." / ".." / "media" / "aud"
        self._temporizador_view = TemporizadorView(self, self.temporizador, audio_dir=audio_dir)

    def abrir_despertador(self):
        if self._despertador_view and self._despertador_view.winfo_exists():
            self._despertador_view.lift()
            return
        self._despertador_view = DespertadorView(self, self.despertador, base_dir=self.BASE_DIR)

    def on_close(self):
        try:
            self.despertador.stop_all()
        except Exception:
            pass
        try:
            self.temporizador.para_temporizador()
        except Exception:
            pass
        try:
            pygame.mixer.music.stop()
            pygame.mixer.quit()
        except Exception:
            pass
        self.destroy()
