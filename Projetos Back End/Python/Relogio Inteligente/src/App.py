import datetime
import os
import pygame
import time
import threading
import utils
import customtkinter as ctk  # type: ignore
from tkinter import Listbox, messagebox  
from cronometro import Cronometro
from despertador import Despertador
from temporizador import Temporizador
from relogio import Relogio 
# python -m pip install --upgrade pip
# pip install customtkinter
# pip install pygame



ctk.set_appearance_mode("System")
ctk.set_default_color_theme("blue")  

class App(ctk.CTk):

    def __init__(self):
        super().__init__()

        self.title("Relógio Inteligente")
        self.geometry("350x500")
        self.resizable(True, True)

        self.BASE_DIR = os.path.dirname(os.path.abspath(__file__))
    

        caminho_icone = os.path.join(self.BASE_DIR, "..", "media", "img", "despertador.png")
        

        print(f"Procurando ícone em: {caminho_icone}")
        print(f"Arquivo existe? {os.path.exists(caminho_icone)}")
        
        utils.carregar_icone(self, caminho_icone)

        self.relogio = Relogio()
        self.horario_atual = self.relogio.obter_horario()
        
        # Frame principal
        self.main_frame = ctk.CTkFrame(self)
        self.main_frame.pack(pady=20, padx=20, fill="both", expand=True)

        self.clock_frame = ctk.CTkFrame(self.main_frame)
        self.clock_frame.pack(pady=(0, 20), fill="x")

        self.lbl_horario = ctk.CTkLabel(
            self.clock_frame,
            text="",
            font=("Helvetica", 36, "bold"),
            anchor="center"
        )
        self.lbl_horario.pack(pady=(10, 5), fill="x")

        self.lbl_data = ctk.CTkLabel(
            self.clock_frame,
            text="",
            font=("Helvetica", 14),
            anchor="center"
        )
        self.lbl_data.pack(pady=(0, 5), fill="x")

        self.lbl_localizacao = ctk.CTkLabel(
            self.clock_frame,
            text="",
            font=("Helvetica", 12),
            anchor="center"
        )
        self.lbl_localizacao.pack(pady=(0, 10), fill="x")

        self.atualizar_relogio()

        # Botões
        self.btn_cronometro = ctk.CTkButton(
            self.main_frame, 
            text="⏱️ Cronômetro",
            command=self.abrir_cronometro,
            height=40,
            corner_radius=10
        )
        self.btn_cronometro.pack(pady=10, fill="x")

        self.btn_temporizador = ctk.CTkButton(
            self.main_frame, 
            text="⏲️ Temporizador",
            command=self.abrir_temporizador,
            height=40,
            corner_radius=10
        )
        self.btn_temporizador.pack(pady=10, fill="x")

        self.btn_despertador = ctk.CTkButton(
            self.main_frame, 
            text="⏰ Despertador",
            command=self.abrir_despertador,
            height=40,
            corner_radius=10
        )
        self.btn_despertador.pack(pady=10, fill="x")

        self.cronometro = Cronometro()
        self.temporizador = Temporizador()
        self.despertador = Despertador()
        pygame.mixer.init()
        self.alarme_tocando = False
        self.audio_alarme = None

        self.BASE_DIR = os.path.dirname(os.path.abspath(__file__))

    def atualizar_relogio(self):
        self.horario_atual = self.relogio.obter_horario()
        
        if self.horario_atual:
            self.lbl_horario.configure(text=self.horario_atual.strftime("%H:%M:%S"))
            
            dia_semana = self.relogio.obter_dia_semana_ptbr(self.horario_atual)
            self.lbl_data.configure(text=f"{dia_semana}, {self.horario_atual.strftime('%d/%m/%Y')}")
            
            if self.relogio.geo_data:
                localizacao = f"{self.relogio.geo_data.get('city', '')}, {self.relogio.geo_data.get('country', '')}"
                self.lbl_localizacao.configure(text=localizacao)
            else:
                self.lbl_localizacao.configure(text=self.relogio.fuso_padrao)
    
        self.after(1000, self.atualizar_relogio)

    # ---------------------------------------------------#
    # Cronômetro
    def abrir_cronometro(self):
        if hasattr(self, 'janela_cronometro') and self.janela_cronometro.winfo_exists():
            self.janela_cronometro.lift()
            return

        self.janela_cronometro = ctk.CTkToplevel(self)
        self.janela_cronometro.geometry("350x170")
        self.janela_cronometro.title("Cronômetro")
        self.janela_cronometro.resizable(True, True)

        frame = ctk.CTkFrame(self.janela_cronometro)
        frame.pack(pady=20, padx=20, fill="both", expand=True)

        utils.abrir_janela_em_foco(self.janela_cronometro, master=self)

        self.label_cronometro = ctk.CTkLabel(
            frame,
            text="00:00:00:000",
            font=("Roboto Mono", 28)
        )
        self.label_cronometro.pack(pady=20)

        btn_frame = ctk.CTkFrame(frame, fg_color="transparent")
        btn_frame.pack()

        self.btn_iniciar = ctk.CTkButton(
            btn_frame,
            text="Iniciar",
            command=self.iniciar_cronometro,
            width=80
        )
        self.btn_iniciar.pack(side="left", padx=5)

        self.btn_parar = ctk.CTkButton(
            btn_frame,
            text="Parar",
            command=self.parar_cronometro,
            width=80,
            fg_color="#d9534f",
            hover_color="#c9302c"
        )
        self.btn_parar.pack(side="left", padx=5)

        self.btn_resetar = ctk.CTkButton(
            btn_frame,
            text="Resetar",
            command=self.resetar_cronometro,
            width=80
        )
        self.btn_resetar.pack(side="left", padx=5)

    def iniciar_cronometro(self):
        self.cronometro.iniciar_cronometro(
            callback=lambda tempo: utils.atualizar_interface(tempo, self.label_cronometro)
        )

    def parar_cronometro(self):
        self.cronometro.parar_cronometro()

    def resetar_cronometro(self):
        self.cronometro.resetar_cronometro()
        self.label_cronometro.configure(text="00:00:00:000")

    # ---------------------------------------------------#
    # Temporizador
    def abrir_temporizador(self):
        if hasattr(self, 'janela_temporizador') and self.janela_temporizador.winfo_exists():
            self.janela_temporizador.lift()
            return

        self.janela_temporizador = ctk.CTkToplevel(self)
        self.janela_temporizador.geometry("400x300")
        self.janela_temporizador.title("Temporizador")
        self.janela_temporizador.resizable(True, True)

        frame = ctk.CTkFrame(self.janela_temporizador)
        frame.pack(pady=20, padx=20, fill="both", expand=True)
        
        label_font = ("Arial", 16)  
        entry_font = ("Arial", 14)  
        entry_width = 80 
        entry_height = 40  

        entrada_frame = ctk.CTkFrame(frame, fg_color="transparent")
        entrada_frame.pack(pady=15)

        entrada_frame.grid_columnconfigure(0, weight=1)
        entrada_frame.grid_columnconfigure(1, weight=1)
        entrada_frame.grid_columnconfigure(2, weight=1)

        ctk.CTkLabel(entrada_frame, text="Horas").grid(row=0, column=0, padx=5)
        self.entrada_horas = ctk.CTkEntry(entrada_frame, width=60, validate="key")
        self.entrada_horas.configure(validatecommand=(self.register(self.validar_numerico), '%P'))
        self.entrada_horas.insert(0, "00")
        self.entrada_horas.grid(row=1, column=0, padx=5, pady=5)

        ctk.CTkLabel(entrada_frame, text="Minutos").grid(row=0, column=1, padx=5)
        self.entrada_minutos = ctk.CTkEntry(entrada_frame, width=60, validate="key")
        self.entrada_minutos.configure(validatecommand=(self.register(self.validar_numerico), '%P'))
        self.entrada_minutos.insert(0, "00")
        self.entrada_minutos.grid(row=1, column=1, padx=5, pady=5)

        ctk.CTkLabel(entrada_frame, text="Segundos").grid(row=0, column=2, padx=5)
        self.entrada_segundos = ctk.CTkEntry(entrada_frame, width=60, validate="key")
        self.entrada_segundos.configure(validatecommand=(self.register(self.validar_numerico), '%P'))
        self.entrada_segundos.insert(0, "00")
        self.entrada_segundos.grid(row=1, column=2, padx=5, pady=5)

        self.label_temporizador = ctk.CTkLabel(
            frame,
            text="00:00:00:000",
            font=("Roboto Mono", 28)
        )
        self.label_temporizador.pack(pady=20)

        btn_frame = ctk.CTkFrame(frame, fg_color="transparent")
        btn_frame.pack(pady=10)

        # Botões
        self.btn_iniciar_temporizador = ctk.CTkButton(
            btn_frame,
            text="Iniciar",
            command=self.iniciar_temporizador,
            width=80
        )
        self.btn_iniciar_temporizador.pack(side="left", padx=5)

        self.btn_parar_temporizador = ctk.CTkButton(
            btn_frame,
            text="Parar",
            command=self.parar_temporizador,
            width=80,
            fg_color="#d9534f",
            hover_color="#c9302c"
        )
        self.btn_parar_temporizador.pack(side="left", padx=5)

        self.btn_resetar_temporizador = ctk.CTkButton(
            btn_frame,
            text="Resetar",
            command=self.resetar_temporizador,
            width=80
        )
        self.btn_resetar_temporizador.pack(side="left", padx=5)

        self.janela_temporizador.focus()
        self.janela_temporizador.grab_set()

    def validar_numerico(self, texto):
        return texto.isdigit() or texto == ""

    def iniciar_temporizador(self):
        try:
            horas = int(self.entrada_horas.get() or 0)
            minutos = int(self.entrada_minutos.get() or 0)
            segundos = int(self.entrada_segundos.get() or 0)
            tempo_total_ms = (horas * 3600 + minutos * 60 + segundos) * 1000

            if tempo_total_ms <= 0:
                utils.mostrar_mensagem("Erro", "Digite um tempo maior que zero.", "warning")
                return

            self.entrada_horas.configure(state="disabled")
            self.entrada_minutos.configure(state="disabled")
            self.entrada_segundos.configure(state="disabled")

            def callback_com_alarme(tempo):
                utils.atualizar_interface(tempo, self.label_temporizador)
                if tempo <= 0:
                    self.tocar_alarme_temporizador()

            self.temporizador.definir_tempo(tempo_total_ms)
            self.temporizador.iniciar_temporizador(
                hora=horas,
                minuto=minutos,
                segundo=segundos,
                callback=callback_com_alarme
            )
        except ValueError:
            utils.mostrar_mensagem("Erro", "Por favor, insira apenas números.", "error")
            self.resetar_temporizador()

    def tocar_alarme_temporizador(self):
        caminho_audio = os.path.join(self.BASE_DIR, "..", "media", "aud", "Basic Alarm.mp3")
        try:
            if os.path.exists(caminho_audio):
                self.alarme_tocando = True  # Adiciona esta linha para controlar o estado
                pygame.mixer.music.load(caminho_audio)
                pygame.mixer.music.play(-1)
                
                # Abre a janela para parar o alarme
                self.abrir_janela_parar_alarme_temporizador()
            else:
                utils.mostrar_mensagem("Erro", "Arquivo de alarme não encontrado.", "error")
        except Exception as e:
            utils.mostrar_mensagem("Erro", f"Não foi possível reproduzir o alarme: {str(e)}", "error")

    def abrir_janela_parar_alarme_temporizador(self):
        if hasattr(self, 'janela_parar_alarme_temporizador') and self.janela_parar_alarme_temporizador.winfo_exists():
            self.janela_parar_alarme_temporizador.lift()
            return

        self.janela_parar_alarme_temporizador = ctk.CTkToplevel(self)
        self.janela_parar_alarme_temporizador.geometry("350x200")
        self.janela_parar_alarme_temporizador.title("⏰ Alarme do Temporizador!")
        self.janela_parar_alarme_temporizador.resizable(True, True)

        frame = ctk.CTkFrame(self.janela_parar_alarme_temporizador)
        frame.pack(pady=30, padx=20, fill="both", expand=True)

        utils.abrir_janela_em_foco(self.janela_parar_alarme_temporizador, master=self)

        ctk.CTkLabel(
            frame,
            text="⏰ Temporizador Concluído!",
            font=("Arial", 24)
        ).pack(pady=10)

        btn_frame = ctk.CTkFrame(frame, fg_color="transparent")
        btn_frame.pack()

        ctk.CTkButton(
            btn_frame,
            text="Parar",
            command=lambda: [self.janela_parar_alarme_temporizador.destroy(), self.parar_alarme_temporizador()],
            width=100,
            fg_color="#d9534f",
            hover_color="#c9302c"
        ).pack(pady=20)

    def parar_alarme_temporizador(self):
        if self.alarme_tocando:
            pygame.mixer.music.stop()
            self.alarme_tocando = False
        self.resetar_temporizador()

    def parar_temporizador(self):
        self.temporizador.parar_temporizador()
        
    def resetar_temporizador(self):
        self.temporizador.resetar_temporizador()
        self.label_temporizador.configure(text="00:00:00:000")

        self.entrada_horas.configure(state="normal")
        self.entrada_minutos.configure(state="normal")
        self.entrada_segundos.configure(state="normal")

    # ---------------------------------------------------#
    # Despertador
    def abrir_despertador(self):
        if hasattr(self, 'janela_despertador') and self.janela_despertador.winfo_exists():
            self.janela_despertador.lift()
            return

        self.janela_despertador = ctk.CTkToplevel(self)
        self.janela_despertador.geometry("650x700")
        self.janela_despertador.title("Despertador")
        self.janela_despertador.resizable(True, True)

        main_frame = ctk.CTkFrame(self.janela_despertador)
        main_frame.pack(pady=20, padx=20, fill="both", expand=True)

        utils.abrir_janela_em_foco(self.janela_despertador, master=self)

        btn_frame = ctk.CTkFrame(main_frame, fg_color="transparent")
        btn_frame.pack(pady=10)

        botoes = [
            ("➕ Novo Alarme", self.config_alarme),
            ("✏️ Alterar", self.alterar_alarme),
            ("✅ Habilitar", self.habilitar_despertador),
            ("⛔ Desabilitar", self.desabilitar_despertador),
            ("❌ Excluir", self.delete_despertador)
        ]

        for text, command in botoes:
            btn = ctk.CTkButton(
                btn_frame,
                text=text,
                command=command,
                width=110,
                height=30
            )
            btn.pack(side="left", padx=2)

        lista_frame = ctk.CTkFrame(main_frame)
        lista_frame.pack(fill="both", expand=True, pady=10)

        self.lista_despertadores = Listbox(
            lista_frame,
            selectmode="extended",
            font=("Arial", 12),
            bg="#343638",
            fg="white",
            selectbackground="#4a6baf",
            selectforeground="white"
        )

        scrollbar = ctk.CTkScrollbar(lista_frame, command=self.lista_despertadores.yview)
        self.lista_despertadores.configure(yscrollcommand=scrollbar.set)

        self.lista_despertadores.pack(side="left", fill="both", expand=True)
        scrollbar.pack(side="right", fill="y")

        self.atualizar_lista_despertadores()

    def atualizar_lista_despertadores(self):
        self.lista_despertadores.delete(0, "end")
        for alarme in self.despertador.alarmes:
            status = "Ativo" if alarme["ativo"] else "Inativo"
            repetir = " (R)" if alarme["repetir"] else ""
            self.lista_despertadores.insert(
                "end", f"{alarme['hora']:02d}:{alarme['minuto']:02d} {status}{repetir}")

    def alterar_alarme(self):
        selecionados = self.lista_despertadores.curselection()
        if len(selecionados) != 1:
            utils.mostrar_mensagem("Aviso", "Selecione apenas um alarme para alterar.", "warning")
            return
        index = selecionados[0]
        alarme = self.despertador.alarmes[index]
        self.config_alarme(editar=True, alarme=alarme)

    def config_alarme(self, editar=False, alarme=None):
        if hasattr(self, "janela_config") and self.janela_config.winfo_exists():
            self.janela_config.lift()
            return

        self.janela_config = ctk.CTkToplevel(self)
        self.janela_config.geometry("430x450")  
        self.janela_config.title("Configurar Alarme")
        self.janela_config.resizable(False, False)

        utils.abrir_janela_em_foco(self.janela_config, master=self)

        self.var_hora = ctk.IntVar(value=alarme["hora"] if editar else 0)
        self.var_minuto = ctk.IntVar(value=alarme["minuto"] if editar else 0)
        self.var_repetir = ctk.BooleanVar(value=alarme["repetir"] if editar else False)
        self.var_audio = ctk.StringVar()

        time_frame = ctk.CTkFrame(self.janela_config, fg_color="transparent")
        time_frame.pack(pady=10)

        hora_frame = ctk.CTkFrame(time_frame, fg_color="transparent")
        hora_frame.pack(side="left", padx=10)
        ctk.CTkLabel(hora_frame, text="Hora:").pack()
        self.entrada_hora_alarme = ctk.CTkEntry(
            hora_frame,
            textvariable=self.var_hora,
            width=60,
            validate="key"
        )
        self.entrada_hora_alarme.configure(validatecommand=(self.register(self.validar_numerico), '%P'))
        self.entrada_hora_alarme.pack()

        minuto_frame = ctk.CTkFrame(time_frame, fg_color="transparent")
        minuto_frame.pack(side="left", padx=10)
        ctk.CTkLabel(minuto_frame, text="Minuto:").pack()
        self.entrada_minuto_alarme = ctk.CTkEntry(
            minuto_frame,
            textvariable=self.var_minuto,
            width=60,
            validate="key"
        )
        self.entrada_minuto_alarme.configure(validatecommand=(self.register(self.validar_numerico), '%P'))
        self.entrada_minuto_alarme.pack()

        def toggle_dias_semana():
            for dia, var in self.vars_dias.items():
                cb = getattr(self, f"cb_{dia}")
                if self.var_repetir.get():
                    cb.configure(state="normal")
                else:
                    cb.configure(state="disabled")
                    var.set(False)

        self.var_repetir.trace_add("write", lambda *args: toggle_dias_semana())
        ctk.CTkCheckBox(
            self.janela_config,
            text="Repetir semanalmente",
            variable=self.var_repetir
        ).pack(pady=10)

        ctk.CTkLabel(self.janela_config, text="Dias da Semana:").pack()

        dias_semana = ["Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom"]
        self.vars_dias = {}

        dias_frame = ctk.CTkFrame(self.janela_config, fg_color="transparent")
        dias_frame.pack()

        linha1_frame = ctk.CTkFrame(dias_frame, fg_color="transparent")
        linha1_frame.pack()
        for dia in dias_semana[:4]:
            var = ctk.BooleanVar()
            if editar:
                var.set(dia in alarme["dias"])
            self.vars_dias[dia] = var
            
            cb = ctk.CTkCheckBox(
                linha1_frame,
                text=dia,
                variable=var,
                state="normal" if self.var_repetir.get() else "disabled"
            )
            cb.pack(side="left", padx=10, pady=5)
            setattr(self, f"cb_{dia}", cb)

        linha2_frame = ctk.CTkFrame(dias_frame, fg_color="transparent")
        linha2_frame.pack(padx=18) 
        for dia in dias_semana[4:]:
            var = ctk.BooleanVar()
            if editar:
                var.set(dia in alarme["dias"])
            self.vars_dias[dia] = var
            
            cb = ctk.CTkCheckBox(
                linha2_frame,
                text=dia,
                variable=var,
                state="normal" if self.var_repetir.get() else "disabled"
            )
            cb.pack(side="left", padx=10, pady=5)
            setattr(self, f"cb_{dia}", cb)

        ctk.CTkLabel(self.janela_config, text="Som do Alarme:").pack(pady=(10, 5))

        pasta_audio = os.path.join(self.BASE_DIR, "..", "media", "aud")
        try:
            self.sons_disponiveis = [f for f in os.listdir(pasta_audio) if f.endswith((".mp3", ".wav"))]
        except FileNotFoundError:
            self.sons_disponiveis = []
            utils.mostrar_mensagem("Erro", f"Pasta de áudio não encontrada:\n{pasta_audio}", "error")
            return

        if not self.sons_disponiveis:
            utils.mostrar_mensagem("Erro", "Nenhum som de alarme encontrado na pasta 'media/aud'.", "error")
            return

        if editar:
            self.var_audio.set(alarme.get("audio", self.sons_disponiveis[0]))
        else:
            self.var_audio.set(self.sons_disponiveis[0])

        ctk.CTkOptionMenu(
            self.janela_config,
            variable=self.var_audio,
            values=self.sons_disponiveis
        ).pack()

        btn_frame = ctk.CTkFrame(self.janela_config, fg_color="transparent")
        btn_frame.pack(pady=15)

        ctk.CTkButton(
            btn_frame,
            text="Salvar",
            command=lambda: self.add_despertador(editar, alarme)
        ).pack(side="left", padx=10)

        if editar:
            ctk.CTkButton(
                btn_frame,
                text="Excluir",
                command=lambda: self.delete_despertador(alarme),
                fg_color="#d9534f",
                hover_color="#c9302c"
            ).pack(side="right", padx=10)


    def add_despertador(self, editar=False, alarme_antigo=None):
        try:
            hora = int(self.var_hora.get())
            minuto = int(self.var_minuto.get())

            if not (0 <= hora <= 23 and 0 <= minuto <= 59):
                utils.mostrar_mensagem("Erro", "Hora ou minuto inválidos", "warning")
                return

            repetir = self.var_repetir.get()
            dias = [dia for dia, var in self.vars_dias.items() if var.get()]
            audio = self.var_audio.get()

            if editar:
                self.despertador.delete_alarme(alarme_antigo["hora"], alarme_antigo["minuto"])

            if repetir and not dias:
                utils.mostrar_mensagem("Erro", "Selecione ao menos um dia para repetir o alarme.", "warning")
                return

            success = self.despertador.adicionar_alarme(
                hora=hora,
                minuto=minuto,
                audio=audio,
                repetir=repetir,
                dias=dias,
                callback=lambda: self.tocando_despertador(audio)
            )

            if success:
                self.atualizar_lista_despertadores()
                self.janela_config.destroy()
            else:
                utils.mostrar_mensagem("Erro", "Não foi possível adicionar o alarme", "error")

        except ValueError:
            utils.mostrar_mensagem("Erro", "Por favor, insira valores numéricos válidos.", "error")

    def delete_despertador(self, alarme_especifico=None):
        if alarme_especifico:
            self.despertador.delete_alarme(alarme_especifico["hora"], alarme_especifico["minuto"])
        else:
            selecionados = self.lista_despertadores.curselection()
            for i in reversed(selecionados):
                alarme = self.despertador.alarmes[i]
                self.despertador.delete_alarme(alarme["hora"], alarme["minuto"])

        self.atualizar_lista_despertadores()
        if hasattr(self, "janela_config") and self.janela_config.winfo_exists():
            self.janela_config.destroy()

    def habilitar_despertador(self):
        selecionados = self.lista_despertadores.curselection()
        if not selecionados:
            utils.mostrar_mensagem("Aviso", "Nenhum alarme selecionado", "warning")
            return

        for i in selecionados:
            alarme = self.despertador.alarmes[i]
            self.despertador.ativar_alarme(alarme["hora"], alarme["minuto"])
        self.atualizar_lista_despertadores()

    def desabilitar_despertador(self):
        selecionados = self.lista_despertadores.curselection()
        if not selecionados:
            utils.mostrar_mensagem("Aviso", "Nenhum alarme selecionado", "warning")
            return

        for i in selecionados:
            alarme = self.despertador.alarmes[i]
            self.despertador.desativar_alarme(alarme["hora"], alarme["minuto"])
        self.atualizar_lista_despertadores()

    def tocando_despertador(self, nome_audio):
        if hasattr(self, 'janela_tocandoDespertador') and self.janela_tocandoDespertador.winfo_exists():
            self.janela_tocandoDespertador.lift()
            return

        self.janela_tocandoDespertador = ctk.CTkToplevel(self)
        self.janela_tocandoDespertador.geometry("350x200")
        self.janela_tocandoDespertador.title("⏰ Alarme Tocando!")
        self.janela_tocandoDespertador.resizable(True, True)

        frame = ctk.CTkFrame(self.janela_tocandoDespertador)
        frame.pack(pady=30, padx=20, fill="both", expand=True)

        utils.abrir_janela_em_foco(self.janela_tocandoDespertador, master=self)

        ctk.CTkLabel(
            frame,
            text="⏰ Alarme!",
            font=("Arial", 24)
        ).pack(pady=10)

        btn_frame = ctk.CTkFrame(frame, fg_color="transparent")
        btn_frame.pack()

        ctk.CTkButton(
            btn_frame,
            text="Parar",
            command=lambda: [self.janela_tocandoDespertador.destroy(), self.parar_alarme()],
            width=100,
            fg_color="#d9534f",
            hover_color="#c9302c"
        ).pack(side="left", padx=20)

        ctk.CTkButton(
            btn_frame,
            text="Soneca (5 min)",
            command=lambda: [self.janela_tocandoDespertador.destroy(), self.soneca_alarme(5)],
            width=100
        ).pack(side="right", padx=20)

        caminho_audio = os.path.join(self.BASE_DIR, "..", "media", "aud", nome_audio)
        try:
            if os.path.exists(caminho_audio):
                self.alarme_tocando = True
                pygame.mixer.music.load(caminho_audio)
                pygame.mixer.music.play(-1) 
        except Exception as e:
            utils.mostrar_mensagem("Erro", f"Não foi possível reproduzir o áudio: {str(e)}", "error")

    def soneca_alarme(self, minutos):
        agora = datetime.datetime.now()
        self.despertador.soneca_alarme(agora.hour, agora.minute, minutos)


    def parar_alarme(self):
        agora = datetime.datetime.now()
        self.despertador.parar_alarme(agora.hour, agora.minute)
        if self.alarme_tocando:
            pygame.mixer.music.stop()  
            self.alarme_tocando = False

if __name__ == "__main__":
    app = App()
    app.mainloop()