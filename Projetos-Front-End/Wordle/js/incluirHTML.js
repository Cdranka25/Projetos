function incluirHTML(id, arquivo) {
    fetch(arquivo)
      .then(resposta => {
        if (!resposta.ok) throw new Error(`Erro ao carregar ${arquivo}`);
        return resposta.text();
      })
      .then(conteudo => {
        document.getElementById(id).innerHTML = conteudo;
      })
      .catch(erro => {
        console.error("Erro ao incluir HTML:", erro);
      });
  }
  
  window.addEventListener("DOMContentLoaded", () => {
    incluirHTML("header", "header.html");
    incluirHTML("footer", "footer.html");
  });