import tkinter as tk
from tkinter import messagebox
from cronometro import Cronometro
from despertador import Despertador
from temporizador import Temporizador
from formatador_tempo import formatar_tempo

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
        self.janela_despertador.geometry("300x300")
        self.janela_despertador.title("Despertador")
        
        tk.Button(self.janela_despertador, text="Novo Alarme", command=self.novo_alarme).pack(pady=5)
        
        #lista de alarmes
        self.lista_alarmes = []
        
        tk.Button(self.janela_despertador, text="Alterar", command=self.config_alarme).pack(pady=5)
        tk.Button(self.janela_despertador, text="Habilitar", command=self.habilitar_despertador).pack(pady=5)
        tk.Button(self.janela_despertador, text="Desabilitar", command=self.desabilitar_despertador).pack(pady=5)
        tk.Button(self.config_alarme, text="Excluir", command=self.delete_despertador).pack(pady=5)

    def config_alarme(self):
        self.janela_despertador = tk.Toplevel(self.root)
        self.janela_despertador.geometry("300x300")
        self.janela_despertador.title("Novo Alarme")

        # Entradas com valor inicial 00
        tk.Label(self.janela_despertador, text="Horas:").pack()
        self.entrada_horas = tk.Entry(self.janela_despertador, width=5)
        self.entrada_horas.insert(0, "00")
        self.entrada_horas.pack()

        tk.Label(self.janela_despertador, text="Minutos:").pack()
        self.entrada_minutos = tk.Entry(self.janela_despertador, width=5)
        self.entrada_minutos.insert(0, "00")
        self.entrada_minutos.pack()

        self.label_temporizador = tk.Label(self.janela_despertador, text="00:00", font=("Helvetica", 24))
        self.label_temporizador.pack(pady=10)

        tk.Button(self.config_alarme, text="Salvar", command=self.add_despertador).pack(pady=5)
        tk.Button(self.config_alarme, text="Excluir", command=self.delete_despertador).pack(pady=5)

    def add_despertador(self):
    
    def delete_despertador(self):
        
    def habilitar_despertador(self):
        
    def desabilitar_despertador(self):
        
        
    def tocando_despertador(self):
        self.janela_despertador = tk.Toplevel(self.root)
        self.janela_despertador.geometry("300x300")
        self.janela_despertador.title("Despertador Tocando")        
        
        tk.Button(self.tocando_despertador, text="Parar", command=self.parar_despertador).pack(pady=5)
        tk.Button(self.tocando_despertador, text="Parar", command = self.parar_alarme).pack(pady=5)
        
        
    def soneca_alarme(self):
        
    def parar_alarme(self):
        
    # ---------------------------------------------------#

    def atualizar_interface(self, tempo_ms, label):
        tempo_formatado = formatar_tempo(int(tempo_ms))  
        label.config(text=tempo_formatado)

root = tk.Tk()
app = App(root)
root.mainloop()
