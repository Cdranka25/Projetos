# src/utils.py
from pathlib import Path
from tkinter import messagebox
<<<<<<< HEAD
=======
from PIL import Image
import customtkinter as ctk # type: ignore
>>>>>>> 0023f805d26db8d4b1b5affd704bd70c3a46ccac
import os
from PIL import Image, ImageTk
import customtkinter as ctk #type ignore
import tkinter as tk
import logging
from config import AUDIO_DIR

logger = logging.getLogger(__name__)

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

def carregar_icone(janela, caminho_icone: Path | str):
    try:
        caminho = Path(caminho_icone)
        if not caminho.exists():
            print(f"Arquivo de ícone não encontrado: {caminho}")
            return False

        if os.name == 'nt':
            try:
                janela.iconbitmap(str(caminho))
            except Exception:

                img = Image.open(caminho)
                photo = ImageTk.PhotoImage(img)
                janela.wm_iconphoto(True, photo)

                if not hasattr(janela, "_icon_ref"):
                    janela._icon_ref = photo
        else:
            img = Image.open(caminho)
            photo = ImageTk.PhotoImage(img)
            janela.wm_iconphoto(True, photo)
            if not hasattr(janela, "_icon_ref"):
                janela._icon_ref = photo
        return True
    except Exception as e:
        print(f"Erro ao carregar ícone: {e}")
        return False

_imagem_cache = {}

def carregar_imagem(nome_arquivo: str, tamanho: tuple[int, int] = None, modo: str = "ctk"):
    caminho_img = IMG_DIR / nome_arquivo
    if not caminho_img.exists():
        logger.error(f"Arquivo de imagem não encontrado: {caminho_img}")
        return None
    try:
        img = Image.open(caminho_img)
        if tamanho:
            img = img.resize(tamanho, Image.LANCZOS)
        if modo == "ctk":
            imagem = ctk.CTkImage(light_image=img, dark_image=img)
        else:
            imagem = tk.PhotoImage(file=str(caminho_img))
        _imagem_cache[nome_arquivo] = imagem
        return imagem
    except Exception as e:
        logger.exception(f"Erro ao carregar imagem {nome_arquivo}: {e}")
        return None
