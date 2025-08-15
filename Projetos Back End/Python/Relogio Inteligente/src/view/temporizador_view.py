# src/view/temporizador_view.py
from __future__ import annotations
import os
import customtkinter as ctk # type: ignore
import pygame
from pathlib import Path
from .. import utils
from ..config import AUDIO_DIR

class TemporizadorView(ctk.CTkToplevel):
    def __init__(self, master, temporizador, audio_dir: Path):
        super().__init__(master)
        self.temporizador = temporizador
        self.audio_dir = Path(audio_dir)
        self.title("Temporizador")
        self.geometry("400x300")
        self.resizable(True, True)
        utils.abrir_janela_em_foco(self, master=master)

        entrada_frame = ctk.CTkFrame(self)
        entrada_frame.pack(pady=15, padx=15, fill="x")

        entrada_frame.grid_columnconfigure(0, weight=1)
        entrada_frame.grid_columnconfigure(1, weight=1)
        entrada_frame.grid_columnconfigure(2, weight=1)

        ctk.CTkLabel(entrada_frame, text="Horas").grid(row=0, column=0)
        ctk.CTkLabel(entrada_frame, text="Minutos").grid(row=0, column=1)
        ctk.CTkLabel(entrada_frame, text="Segundos").grid(row=0, column=2)

        self.entrada_horas = ctk.CTkEntry(entrada_frame, width=60, validate="key")
        self.entrada_minutos = ctk.CTkEntry(entrada_frame, width=60, validate="key")
        self.entrada_segundos = ctk.CTkEntry(entrada_frame, width=60, validate="key")
        self.entrada_horas.insert(0, "00")
        self.entrada_minutos.insert(0, "00")
        self.entrada_segundos.insert(0, "00")

        self.entrada_horas.grid(row=1, column=0, padx=5, pady=5)
        self.entrada_minutos.grid(row=1, column=1, padx=5, pady=5)
        self.entrada_segundos.grid(row=1, column=2, padx=5, pady=5)

        self.label_temporizador = ctk.CTkLabel(self, text="00:00:00:000", font=("Roboto Mono", 28))
        self.label_temporizador.pack(pady=15)

        btn_frame = ctk.CTkFrame(self, fg_color="transparent")
        btn_frame.pack(pady=10)

        self.btn_start = ctk.CTkButton(btn_frame, text="Iniciar", command=self.iniciar)
        self.btn_stop = ctk.CTkButton(btn_frame, text="Parar", command=self.parar, state="disabled")
        self.btn_reset = ctk.CTkButton(btn_frame, text="Resetar", command=self.resetar)

        self.btn_start.pack(side="left", padx=5)
        self.btn_stop.pack(side="left", padx=5)
        self.btn_reset.pack(side="left", padx=5)

        # conecta callback do temporizador (assumindo que seu Temporizador aceita callback)
        # vamos passar uma função que chama after para atualizar UI thread-safe
        self._is_running = False

    def validar_numerico(self, texto: str) -> bool:
        return texto.isdigit() or texto == ""

    def iniciar(self):
        try:
            h = int(self.entrada_horas.get() or 0)
            m = int(self.entrada_minutos.get() or 0)
            s = int(self.entrada_segundos.get() or 0)
        except ValueError:
            utils.mostrar_mensagem("Erro", "Por favor, insira apenas números.", "error")
            return

        total_ms = (h * 3600 + m * 60 + s) * 1000
        if total_ms <= 0:
            utils.mostrar_mensagem("Erro", "Digite um tempo maior que zero.", "warning")
            return

        # bloqueia campos
        self.entrada_horas.configure(state="disabled")
        self.entrada_minutos.configure(state="disabled")
        self.entrada_segundos.configure(state="disabled")
        self.btn_start.configure(state="disabled")
        self.btn_stop.configure(state="normal")

        # define total no modelo e inicia com callback
        self.temporizador.set_total_ms(total_ms)

        def callback_thread(remaining_ms):
            # esta função pode rodar em thread do modelo -> usar after para UI
            self.after(0, lambda: self._on_tick(remaining_ms))

        # chama seu método (assume start_temporizador(callback=...))
        self.temporizador.start_temporizador(callback=callback_thread)
        self._is_running = True

    def _on_tick(self, remaining_ms: int):
        try:
            utils.atualizar_interface(remaining_ms, self.label_temporizador)
            if remaining_ms <= 0:
                # tocar alarme
                caminho = self.audio_dir / "temporizadorAlarm.mp3"
                if caminho.exists():
                    try:
                        pygame.mixer.music.load(str(caminho))
                        pygame.mixer.music.play(-1)
                    except Exception:
                        pass
                # reabilitar botões e campos
                self.entrada_horas.configure(state="normal")
                self.entrada_minutos.configure(state="normal")
                self.entrada_segundos.configure(state="normal")
                self.btn_start.configure(state="normal")
                self.btn_stop.configure(state="disabled")
                self._is_running = False
        except Exception:
            pass

    def parar(self):
        try:
            self.temporizador.para_temporizador()
        except Exception:
            pass
        # garantir UI consistente
        self.entrada_horas.configure(state="normal")
        self.entrada_minutos.configure(state="normal")
        self.entrada_segundos.configure(state="normal")
        self.btn_start.configure(state="normal")
        self.btn_stop.configure(state="disabled")
        self._is_running = False

    def resetar(self):
        try:
            self.temporizador.reseta_temporizador()
        except Exception:
            pass
        self.label_temporizador.configure(text="00:00:00:000")
        self.entrada_horas.configure(state="normal")
        self.entrada_minutos.configure(state="normal")
        self.entrada_segundos.configure(state="normal")
        self.btn_start.configure(state="normal")
        self.btn_stop.configure(state="disabled")
        self._is_running = False
