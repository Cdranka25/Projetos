// Seleciona os elementos do HTML
const input = document.querySelector("input");
const button = document.querySelector("button");
const city = document.querySelector("#city");
const description = document.querySelector("#description");
const degree = document.querySelector("#degree");
const humidity = document.querySelector("#humidity");
const wind = document.querySelector("#wind");
const icon = document.querySelector("#weather-icon");
const weatherBox = document.querySelector(".weather-box");

// Adiciona o evento de clique ao botão
button.addEventListener("click", () => {
    if (!input.value) return;
    getWeatherData(input.value);
});

// Adiciona o evento de pressionar "Enter" no input
input.addEventListener("keypress", (e) => {
    if (e.key === "Enter") {
        button.click();
    }
});

// Função para pegar o clima baseado na cidade digitada
async function getWeatherData(cityName) {
    const apiKey = "8f49f63af87aaa32039b0c0fc8605095";
    const url = `https://api.openweathermap.org/data/2.5/weather?q=${encodeURIComponent(cityName)}&units=metric&lang=pt_br&appid=${apiKey}`;

    try {
        const response = await fetch(url);
        const data = await response.json();

        if (data.cod === "404") {
            alert("Cidade não encontrada!");
            return;
        }

        loadWeatherInfo(data);

        // Chama a função para mostrar a previsão dos próximos dias
        getForecastData(cityName); 
    } catch (error) {
        alert("Erro ao buscar dados!");
        console.error(error);
    }
}

// Função para carregar as informações de clima na página
function loadWeatherInfo(data) {
    city.textContent = `${data.name}, ${data.sys.country}`;
    description.textContent = `Clima: ${data.weather[0].description}`;
    degree.textContent = `Temperatura: ${Math.round(data.main.temp)}°C`;
    humidity.textContent = `Umidade: ${data.main.humidity}%`;
    wind.textContent = `Vento: ${data.wind.speed} km/h`;
    icon.src = `https://openweathermap.org/img/wn/${data.weather[0].icon}@2x.png`;
    weatherBox.style.display = "block";
}

// Quando a página carrega, tenta buscar a localização do usuário
window.addEventListener("load", () => {
    if ("geolocation" in navigator) {
        navigator.geolocation.getCurrentPosition(showWeatherByLocation, showError);
    } else {
        alert("Geolocalização não é suportada pelo seu navegador.");
    }
});

// Função para obter o clima com base na localização atual
async function showWeatherByLocation(position) {
    const { latitude, longitude } = position.coords;
    const apiKey = "8f49f63af87aaa32039b0c0fc8605095";
    const url = `https://api.openweathermap.org/data/2.5/weather?lat=${latitude}&lon=${longitude}&units=metric&lang=pt_br&appid=${apiKey}`;

    try {
        const response = await fetch(url);
        const data = await response.json();

        if (data.cod !== 200) {
            alert("Não foi possível obter os dados de localização.");
            return;
        }

        loadWeatherInfo(data);
    } catch (error) {
        console.error("Erro ao obter clima por localização:", error);
    }
}

// Caso o usuário negue a permissão ou ocorra algum erro
function showError(error) {
    console.warn("Erro ao obter localização:", error.message);
}

// Função para pegar a previsão dos próximos 5 dias
async function getForecastData(cityName) {
    const apiKey = "8f49f63af87aaa32039b0c0fc8605095";
    const url = `https://api.openweathermap.org/data/2.5/forecast?q=${encodeURIComponent(cityName)}&units=metric&lang=pt_br&appid=${apiKey}`;

    try {
        const response = await fetch(url);
        const data = await response.json();

        if (data.cod !== "200") {
            alert("Erro ao buscar a previsão!");
            return;
        }

        showForecastInfo(data);
    } catch (error) {
        console.error("Erro ao buscar previsão:", error);
    }
}

// Função para mostrar as previsões na tela
function showForecastInfo(data) {
    const forecastContainer = document.getElementById("forecast-container");
    forecastContainer.innerHTML = ""; // Limpa a previsão anterior

    // Pega as previsões do dia (a cada 24h)
    const forecastData = data.list.filter((item, index) => index % 8 === 0);

    forecastData.forEach((forecast) => {
        const date = new Date(forecast.dt * 1000);
        const day = date.toLocaleDateString("pt-BR", { weekday: "short", day: "numeric", month: "short" });
        const icon = `https://openweathermap.org/img/wn/${forecast.weather[0].icon}@2x.png`;

        const forecastItem = document.createElement("div");
        forecastItem.classList.add("forecast-item");

        forecastItem.innerHTML = `
            <p>${day}</p>
            <img src="${icon}" alt="Ícone do clima">
            <p>${Math.round(forecast.main.temp_max)}°C / ${Math.round(forecast.main.temp_min)}°C</p>
            <p>${forecast.weather[0].description}</p>
        `;

        forecastContainer.appendChild(forecastItem);
    });

    // Exibe a seção da previsão
    document.querySelector(".forecast-box").style.display = "block";
}