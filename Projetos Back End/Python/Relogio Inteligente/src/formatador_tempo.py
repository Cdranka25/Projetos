

def formatar_tempo(ms):

    if ms < 0:
        raise ValueError("Tempo não pode ser negativo")
    
    ms = int(ms)
    horas = ms // 3600000
    minutos = (ms % 3600000) // 60000
    segundos = (ms % 60000) // 1000
    milissegundos = ms % 1000
    
    return f"{horas:02}:{minutos:02}:{segundos:02}:{milissegundos:03}"