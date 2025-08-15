# src/view/despertador_view.py
from __future__ import annotations
import os
import customtkinter as ctk # type: ignore
from tkinter import Listbox
from .. import utils
from ..config import AUDIO_DIR

class DespertadorView(ctk.CTkToplevel):
    def __init__(self, master, despertador, base_dir: str):
        super().__init__(master)
        self.despertador = despertador
        self.base_dir = base_dir
        self.title("Despertador")
        self.geometry("650x700")
        self.resizable(True, True)
        utils.abrir_janela_em_foco(self, master=master)

        main_frame = ctk.CTkFrame(self)
        main_frame.pack(pady=20, padx=20, fill="both", expand=True)

        btn_frame = ctk.CTkFrame(main_frame, fg_color="transparent")
        btn_frame.pack(pady=10)

        botoes = [
            ("➕ Novo Alarme", self.config_alarme),
            ("✏️ Alterar", self.alterar_alarme),
            ("✅ Habilitar", self.habilitar),
            ("⛔ Desabilitar", self.desabilitar),
            ("❌ Excluir", self.excluir)
        ]
        for text, cmd in botoes:
            ctk.CTkButton(btn_frame, text=text, command=cmd, width=110, height=30).pack(side="left", padx=2)

        lista_frame = ctk.CTkFrame(main_frame)
        lista_frame.pack(fill="both", expand=True, pady=10)

        self.lista = Listbox(lista_frame, selectmode="extended", font=("Arial", 12),
                             bg="#343638", fg="white", selectbackground="#4a6baf", selectforeground="white")
        scrollbar = ctk.CTkScrollbar(lista_frame, command=self.lista.yview)
        self.lista.configure(yscrollcommand=scrollbar.set)
        self.lista.pack(side="left", fill="both", expand=True)
        scrollbar.pack(side="right", fill="y")

        self._alarms_map = []
        self.atualizar_lista()

    def atualizar_lista(self):
        self.lista.delete(0, "end")
        self._alarms_map = []
        for a in self.despertador.list_alarms():
            status = "Ativo" if a.get("enabled", True) else "Inativo"
            repetir = " (R)" if a.get("repeat", False) else ""
            label = f"{a['hour']:02d}:{a['minute']:02d} {status}{repetir}"
            self.lista.insert("end", label)
            self._alarms_map.append(a["id"])

    def config_alarme(self):
        utils.mostrar_mensagem("Info", "Abra a versão completa para editar alarmes.", "info")

    def alterar_alarme(self):
        sel = self.lista.curselection()
        if len(sel) != 1:
            utils.mostrar_mensagem("Aviso", "Selecione um alarme para alterar.", "warning")
            return
        idx = sel[0]
        alarm_id = self._alarms_map[idx]
        # você pode abrir uma janela de edição aqui

    def habilitar(self):
        for i in self.lista.curselection():
            self.despertador.enable_alarm(self._alarms_map[i])
        self.atualizar_lista()

    def desabilitar(self):
        for i in self.lista.curselection():
            self.despertador.disable_alarm(self._alarms_map[i])
        self.atualizar_lista()

    def excluir(self):
        for i in reversed(self.lista.curselection()):
            self.despertador.remove_alarm(self._alarms_map[i])
        self.atualizar_lista()
