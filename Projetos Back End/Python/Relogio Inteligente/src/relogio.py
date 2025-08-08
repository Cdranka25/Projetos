import datetime
import requests
from zoneinfo import ZoneInfo

# Configuração do default
FUSO_PADRAO = "America/Sao_Paulo" 

def get_location():
    try:
        response = requests.get('https://ipapi.co/json/', timeout=5).json()
        return {
            "country": response.get('country_name'),
            "region": response.get('region'),
            "city": response.get('city'),
            "timezone": response.get('timezone'),  
            "ip": response.get('ip')
        }
    except requests.exceptions.RequestException:
        return None

def relogio_automatico():
    geo_data = get_location()
    
    if not geo_data:
        print("Erro ao detectar localização. Usando fuso horário padrão (Brasília).")
        regiao = FUSO_PADRAO
    else:
        regiao = geo_data["timezone"]
        print(f"\nDetectado:")
        print(f"País: {geo_data['country']}")
        print(f"Região: {geo_data['region']}")
        print(f"Cidade: {geo_data['city']}")
        print(f"Fuso horário: {regiao}")

    try:
        localizacao = datetime.datetime.now(ZoneInfo(regiao))
    except:
        print("\nAviso: Fuso horário inválido. Usando padrão (Brasília).")
        regiao = FUSO_PADRAO
        localizacao = datetime.datetime.now(ZoneInfo(regiao))
    
    # Formatações
    print("\n--- Horário Local ---")
    print(f"Data: {localizacao.strftime('%d/%m/%Y')}")
    print(f"Dia da semana: {localizacao.strftime('%A')}")
    print(f"Hora atual: {localizacao.strftime('%H:%M:%S')}")
    print(f"Fuso horário: {regiao}")

