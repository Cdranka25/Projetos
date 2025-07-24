import datetime
import tkinter as tk
from tkinter import messagebox
from cronometro import Cronometro
from despertador import Despertador
from temporizador import Temporizador
from formatador_tempo import formatar_tempo
from tkinter import Tk, Listbox

class App:
    def __init__(self, root):
        self.root = root
        self.root.title("Relógio Inteligente")
        self.root.geometry("300x300")

        tk.Button(root, text="Cronômetro", command=self.abrir_cronometro).pack(pady=10)
        tk.Button(root, text="Temporizador", command=self.abrir_temporizador).pack(pady=10)
        tk.Button(root, text="Despertador", command=self.abrir_despertador).pack(pady=10)

        self.cronometro = Cronometro()
        self.temporizador = Temporizador()
        self.despertador = Despertador()

    # ---------------------------------------------------#
    # Cronômetro

    def abrir_cronometro(self):
        self.janela_cronometro = tk.Toplevel(self.root)
        self.janela_cronometro.geometry("300x300")
        self.janela_cronometro.title("Cronômetro")

        self.label_cronometro = tk.Label(self.janela_cronometro, text="00:00:00:000", font=("Helvetica", 24))
        self.label_cronometro.pack(pady=20)

        tk.Button(self.janela_cronometro, text="Iniciar", command=self.iniciar_cronometro).pack(pady=5)
        tk.Button(self.janela_cronometro, text="Parar", command=self.parar_cronometro).pack(pady=5)
        tk.Button(self.janela_cronometro, text="Resetar", command=self.resetar_cronometro).pack(pady=5)
    def iniciar_cronometro(self):
        self.cronometro.iniciar_cronometro(callback=lambda tempo: self.atualizar_interface(tempo, self.label_cronometro))

    def parar_cronometro(self):
        self.cronometro.parar_cronometro()

    def resetar_cronometro(self):
        self.cronometro.resetar_cronometro()
        self.label_cronometro.config(text="00:00:00:000")

    # ---------------------------------------------------#
    # Temporizador

    def abrir_temporizador(self):
        self.janela_temporizador = tk.Toplevel(self.root)
        self.janela_temporizador.geometry("300x300")
        self.janela_temporizador.title("Temporizador")

        # Entradas com valor inicial 00
        tk.Label(self.janela_temporizador, text="Horas:").pack()
        self.entrada_horas = tk.Entry(self.janela_temporizador, width=5)
        self.entrada_horas.insert(0, "00")
        self.entrada_horas.pack()

        tk.Label(self.janela_temporizador, text="Minutos:").pack()
        self.entrada_minutos = tk.Entry(self.janela_temporizador, width=5)
        self.entrada_minutos.insert(0, "00")
        self.entrada_minutos.pack()

        tk.Label(self.janela_temporizador, text="Segundos:").pack()
        self.entrada_segundos = tk.Entry(self.janela_temporizador, width=5)
        self.entrada_segundos.insert(0, "00")
        self.entrada_segundos.pack()

        self.label_temporizador = tk.Label(self.janela_temporizador, text="00:00:00:000", font=("Helvetica", 24))
        self.label_temporizador.pack(pady=10)

        tk.Button(self.janela_temporizador, text="Iniciar", command=self.iniciar_temporizador).pack(pady=5)
        tk.Button(self.janela_temporizador, text="Parar", command=self.parar_temporizador).pack(pady=5)
        tk.Button(self.janela_temporizador, text="Resetar", command=self.resetar_temporizador).pack(pady=5)

    def iniciar_temporizador(self):
        try:
            horas = int(self.entrada_horas.get() or 0)
            minutos = int(self.entrada_minutos.get() or 0)
            segundos = int(self.entrada_segundos.get() or 0)
            tempo_total_ms = (horas * 3600 + minutos * 60 + segundos) * 1000

            if tempo_total_ms <= 0:
                messagebox.showwarning("Erro", "Digite um tempo maior que zero.")
                return

            # Desativa campos
            self.entrada_horas.config(state="disabled")
            self.entrada_minutos.config(state="disabled")
            self.entrada_segundos.config(state="disabled")

            self.temporizador.definir_tempo(tempo_total_ms)
            self.temporizador.iniciar_Temporizador(
                hora=horas,
                minuto=minutos,
                segundo=segundos,
                callback=lambda tempo: self.atualizar_interface(tempo, self.label_temporizador)
            )
        except ValueError:
            messagebox.showerror("Erro", "Por favor, insira apenas números.")
        
    def parar_temporizador(self):
        self.temporizador.parar_temporizador()

    def resetar_temporizador(self):
        self.temporizador.resetar_temporizador()
        self.label_temporizador.config(text="00:00:00:000")

        # Reabilita os campos
        self.entrada_horas.config(state="normal")
        self.entrada_minutos.config(state="normal")
        self.entrada_segundos.config(state="normal")

    # ---------------------------------------------------#
    # Despertador

    def abrir_despertador(self):
        self.janela_despertador = tk.Toplevel(self.root)
        self.janela_despertador.geometry("320x400")
        self.janela_despertador.title("Despertador")
        
        #Botões superiores
        btn_frame = tk.Frame(self.janela_despertador)
        btn_frame.pack(pady=5)
        
        
        tk.Button(btn_frame, text="Novo Alarme", command=self.config_alarme).pack(side="left", padx=5)
        tk.Button(btn_frame, text="Alterar", command=self.alterar_alarme).pack(side="left", padx=5)
        tk.Button(btn_frame, text="Habilitar", command=self.habilitar_despertador).pack(side="left", padx=5)
        tk.Button(btn_frame, text="Desabilitar", command=self.desabilitar_despertador).pack(side="left", padx=5)
        tk.Button(btn_frame, text="Excluir", command=self.delete_despertador).pack(side="left", padx=5)

        #lista de alarmes
        frame_lista = tk.Frame(self.janela_despertador)
        frame_lista.pack(fill="both", expand=True)

        scrollbar = tk.Scrollbar(frame_lista)
        scrollbar.pack(side="right", fill="y")

        self.lista_despertadores = tk.Listbox(frame_lista, selectmode="extended", yscrollcommand=scrollbar.set)
        self.lista_despertadores.pack(side="left", fill="both", expand=True)
        scrollbar.config(command=self.lista_despertadores.yview)

        self.atualizar_lista_despertadores()
        

    def atualizar_lista_despertadores(self):
        self.lista_despertadores.delete(0, tk.END)
        for alarme in self.despertador.alarmes:
            status = "Ativo" if alarme["ativo"] else "Inativo"
            repetir = " (R)" if alarme["repetir"] else ""
            self.lista_despertadores.insert(tk.END, f"{alarme['hora']:02d}:{alarme['minuto']:02d} {status}{repetir}")
            
    def alterar_alarme(self):
        selecionados = self.lista_despertadores.curselection()
        if len(selecionados) != 1:
            messagebox.showwarning("Aviso", "Selecione apenas um alarme para alterar.")
            return
        index = selecionados[0]
        self.alterar_alarme_index = index
        alarme = self.despertador.alarmes[index]
        self.config_alarme(editar=True, alarme=alarme)
    
    def config_alarme(self, editar=False, alarme=None):
        self.janela_config = tk.Toplevel(self.root)
        self.janela_config.geometry("300x320")
        self.janela_config.title("Configurar Alarme")

        self.var_hora = tk.IntVar(value=alarme["hora"] if editar else 0)
        self.var_minuto = tk.IntVar(value=alarme["minuto"] if editar else 0)
        self.var_repetir = tk.BooleanVar(value=alarme["repetir"] if editar else False)

        # Entrada de hora
        tk.Label(self.janela_config, text="Hora:").pack()
        tk.Spinbox(self.janela_config, from_=0, to=23, textvariable=self.var_hora, width=5, format="%02.0f").pack()

        # Entrada de minuto
        tk.Label(self.janela_config, text="Minuto:").pack()
        tk.Spinbox(self.janela_config, from_=0, to=59, textvariable=self.var_minuto, width=5, format="%02.0f").pack()

        # Repetição
        tk.Checkbutton(self.janela_config, text="Repetir semanalmente", variable=self.var_repetir).pack(pady=5)

        # Botões
        tk.Button(self.janela_config, text="Salvar", command=lambda: self.add_despertador(editar, alarme)).pack(pady=5)
        if editar:
            tk.Button(self.janela_config, text="Excluir", command=lambda: self.delete_despertador(alarme)).pack(pady=5)

    def add_despertador(self, editar=False, alarme_antigo=None):
        hora = self.var_hora.get()
        minuto = self.var_minuto.get()
        repetir = self.var_repetir.get()
        
        if editar:
            # Remove o alarme antigo
            self.despertador.delete_alarme(alarme_antigo["hora"], alarme_antigo["minuto"])
        
        self.despertador.adicionar_alarme(
            hora=hora,
            minuto=minuto,
            repetir=repetir,
            callback=self.tocando_despertador
        )
        
        self.atualizar_lista_despertadores()
        self.janela_config.destroy()
    
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
        for i in self.lista_despertadores.curselection():
            alarme = self.despertador.alarmes[i]
            self.despertador.ativar_alarme(alarme["hora"], alarme["minuto"])
        self.atualizar_lista_despertadores()

    def desabilitar_despertador(self):
        for i in self.lista_despertadores.curselection():
            alarme = self.despertador.alarmes[i]
            self.despertador.desativar_alarme(alarme["hora"], alarme["minuto"])
        self.atualizar_lista_despertadores()
          
    def tocando_despertador(self):
        janela = tk.Toplevel(self.root)
        janela.geometry("300x150")
        janela.title("Despertador Tocando")

        tk.Label(janela, text="⏰ Alarme!", font=("Helvetica", 18)).pack(pady=10)
        tk.Button(janela, text="Parar", command=janela.destroy).pack(side="left", padx=20, pady=20)
        tk.Button(janela, text="Soneca (5 min)", command=lambda: [janela.destroy(), self.soneca_alarme(5)]).pack(side="right", padx=20, pady=20)
            
    def soneca_alarme(self, minutos):
        agora = datetime.datetime.now()
        self.despertador.soneca_alarme(agora.hour, agora.minute, minutos)
        
    def parar_alarme(self):
        agora = datetime.datetime.now()
        self.despertador.parar_alarme(agora.hour, agora.minute)
					
    # ---------------------------------------------------#

    def atualizar_interface(self, tempo_ms, label):
        tempo_formatado = formatar_tempo(int(tempo_ms))  
        label.config(text=tempo_formatado)

root = tk.Tk()
app = App(root)
root.mainloop()
