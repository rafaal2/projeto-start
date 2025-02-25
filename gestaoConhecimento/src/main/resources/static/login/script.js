// Seleciona o botão de Login
const btnLogin = document.getElementById('btnLogin');

// Adiciona evento de clique para redirecionar
btnLogin.addEventListener('click', () => {
  // Ajuste o caminho do arquivo conforme o nome da página Home
  // (por exemplo: 'home.html' ou 'index-home.html', etc.)
  window.location.href = '../home/index.html';
});
