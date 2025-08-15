# src/view/cronometro_view.py
from __future__ import annotations
import customtkinter as ctk # type: ignore
from .. import utils
import config


class CronometroView(ctk.CTkToplevel):
    def __init__(self, master, cronometro):
        super().__init__(master)
        self.cronometro = cronometro
        self.title("Cronômetro")
        self.geometry("350x170")
        self.resizable(True, True)
        utils.abrir_janela_em_foco(self, master=master)

        frame = ctk.CTkFrame(self)
        frame.pack(pady=20, padx=20, fill="both", expand=True)

        self.label = ctk.CTkLabel(frame, text="00:00:00:000", font=("Roboto Mono", 28))
        self.label.pack(pady=20)

        btn_frame = ctk.CTkFrame(frame, fg_color="transparent")
        btn_frame.pack()

        ctk.CTkButton(btn_frame, text="Iniciar", command=self.iniciar).pack(side="left", padx=5)
        ctk.CTkButton(btn_frame, text="Parar", command=self.pausar).pack(side="left", padx=5)
        ctk.CTkButton(btn_frame, text="Resetar", command=self.resetar).pack(side="left", padx=5)

        self._refresh()

    def iniciar(self):
        try:
            self.cronometro.iniciar()
        except Exception:
            pass

    def pausar(self):
        try:
            self.cronometro.pausar()
        except Exception:
            pass

    def resetar(self):
        try:
            self.cronometro.resetar()
        except Exception:
            pass

    def _refresh(self):
        try:
            ms = self.cronometro.tempo_atual()
            utils.atualizar_interface(ms, self.label)
        except Exception:
            pass
        self.after(100, self._refresh)
