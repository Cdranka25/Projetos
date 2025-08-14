from tkinter import messagebox
from PIL import Image
import customtkinter as ctk # type: ignore
import os
# pip install pillow
# pip install customtkinter

def formatar_tempo(ms):
    if ms < 0:
        raise ValueError("Tempo não pode ser negativo")
    
    ms = int(ms)
    horas = ms // 3600000
    minutos = (ms % 3600000) // 60000
    segundos = (ms % 60000) // 1000
    milissegundos = ms % 1000
    
    return f"{horas:02}:{minutos:02}:{segundos:02}:{milissegundos:03}"

def abrir_janela_em_foco(janela, master=None):
    janela.lift()
    janela.focus_force()
    janela.attributes("-topmost", True)
    janela.after(100, lambda: janela.attributes("-topmost", False))

    janela.update_idletasks()  
    largura = janela.winfo_width()
    altura = janela.winfo_height()

    if master:        
        x = master.winfo_rootx() + (master.winfo_width() // 2) - (largura // 2)
        y = master.winfo_rooty() + (master.winfo_height() // 2) - (altura // 2)
    else:        
        largura_tela = janela.winfo_screenwidth()
        altura_tela = janela.winfo_screenheight()
        x = (largura_tela // 2) - (largura // 2)
        y = (altura_tela // 2) - (altura // 2)

    janela.geometry(f"+{x}+{y}")

def atualizar_interface(tempo_ms, label):
    tempo_formatado = formatar_tempo(int(tempo_ms))
    label.configure(text=tempo_formatado)

def mostrar_mensagem(titulo, mensagem, tipo):
    if tipo == "error":
        messagebox.showerror(titulo, mensagem)
    elif tipo == "warning":
        messagebox.showwarning(titulo, mensagem)
    else:
        messagebox.showinfo(titulo, mensagem)

def carregar_icone(janela, caminho_icone):
    try:
        
        if not os.path.exists(caminho_icone):
            print(f"Arquivo de ícone não encontrado: {caminho_icone}")
            return False

        
        if os.name == 'nt':  
            janela.iconbitmap(caminho_icone)
        else:  
            from PIL import ImageTk
            img = Image.open(caminho_icone)
            photo = ImageTk.PhotoImage(img)
            janela.wm_iconphoto(True, photo)
        
        return True
    except Exception as e:
        print(f"Erro ao carregar ícone: {e}")
        return False
