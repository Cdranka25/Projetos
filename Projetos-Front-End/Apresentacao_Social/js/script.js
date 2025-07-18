function ajustarTexto() {
    const section = document.getElementById('texto');
    if (!section) return;

    const h3 = section.querySelector('h3');
    if (!h3) return;

    if (window.innerHeight < 720) {
      h3.innerHTML = h3.innerHTML.replace(/<br\s*\/?>/i, ' ');
    } else if (!h3.innerHTML.includes('<br>')) {
      // Reinsere o <br> se a altura for suficiente
      h3.innerHTML = 'Selecione uma rede social <br> e clique em Acessar';
    }
  }