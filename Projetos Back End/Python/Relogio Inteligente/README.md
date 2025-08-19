Relógio Inteligente

Aplicativo criado em Python utilizando CustomTkinter para interface gráfica.
O app combina diversos processos para criar um relógio inteligente, oferecendo as seguintes funcionalidades:

Funcionalidades

Relógio com ajuste automático de fuso horário:
O app utiliza integração com a API ip-api.com para verificar a localização do usuário e exibir a hora e data corretas, independente do lugar onde esteja.

Cronômetro:
Função de cronômetro que pode ser iniciado, pausado e reiniciado.

Temporizador:
Função de temporizador que também permite iniciar, pausar e reiniciar.

Despertador:
É possível cadastrar alarmes, selecionar o áudio do alarme, configurar repetição nos dias da semana, habilitar/desabilitar e excluir alarmes.
Atualmente, os alarmes não são salvos em arquivo externo, portanto os cadastros permanecem apenas enquanto o app estiver aberto.

Requisitos:
Python 3.10+

Bibliotecas Python:
customtkinter
requests
pillow
tzdata (para suporte a fusos horários)