import sys
import os

PROJECT_ROOT = os.path.dirname(__file__)
SRC_DIR = os.path.join(PROJECT_ROOT, "src")

if SRC_DIR not in sys.path:
    sys.path.insert(0, SRC_DIR)  

from App import App
import utils  


def main():
    app = App()
    utils.abrir_janela_em_foco(app, master=app)
    app.mainloop()

if __name__ == "__main__":
    main()
