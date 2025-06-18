// Seletores do menu lateral
const menuToggle = document.getElementById('menu-toggle');
const sideMenu = document.getElementById('sideMenu');
const overlay = document.getElementById('overlay');

menuToggle.addEventListener('click', () => {
  sideMenu.classList.toggle('open');
  overlay.classList.toggle('open');
});

overlay.addEventListener('click', () => {
  sideMenu.classList.remove('open');
  overlay.classList.remove('open');
});

// Navegação dos botões do menu lateral
const btnCriar = document.getElementById('btnCriar');
btnCriar.addEventListener('click', () => {
  window.location.href = '../criar-processo/index.html';
});

const btnMinhaConta = document.getElementById('btnMinhaConta');
btnMinhaConta.addEventListener('click', () => {
  alert('Ir para página Minha Conta.');
});

const btnFiltro = document.getElementById('btnFiltro');
btnFiltro.addEventListener('click', () => {
  alert('Ir para página de Pesquisa Filtrada.');
});

const btnhome = document.getElementById('btnhome');
btnhome.addEventListener('click', () => {
  window.location.href = 'index.html';
});

// Função para criar o card de um processo
function createProcessCard(processo) {
  const card = document.createElement('div');
  card.classList.add('process-card');

  const img = document.createElement('img');
  // Verifica o setor e define a imagem de acordo
  if (processo.setorNome && processo.setorNome.toLowerCase().includes('rh')) {
    img.src = 'https://funcional.com/images/uploads/posts/entenda-por-que-contratar-mais-um-profissional-de-rh.png';
  } else if (processo.setorNome && processo.setorNome.toLowerCase().includes('financeiro')) {
    img.src = 'https://www.idebrasil.com.br/blog/wp-content/uploads/2019/12/blog-o-que-e-o-que-faz-setor-financeiro-de-uma-empresa-850x441.jpg';
  } else {
    img.src = 'https://png.pngtree.com/png-vector/20220528/ourlarge/pngtree-minimalist-graphic-depiction-of-contemporary-business-ideas-collection-vector-png-image_13688376.jpg';
  }
  img.alt = processo.titulo;
  card.appendChild(img);

  const title = document.createElement('h3');
  title.textContent = processo.titulo;
  card.appendChild(title);

  const btnIniciar = document.createElement('button');
  btnIniciar.classList.add('btn-iniciar');
  btnIniciar.textContent = 'Iniciar';
  btnIniciar.addEventListener('click', () => {
    window.location.href = `../pre-processo/index.html?id=${processo.id}`;
  });
  card.appendChild(btnIniciar);

  return card;
}

// Função para buscar os processos via API
async function fetchProcessos() {
  try {
    const response = await fetch('/processos');
    if (response.ok) {
      const processos = await response.json();
      const processosContainer = document.getElementById('processosContainer');
      processosContainer.innerHTML = ''; // Limpa container
      processos.forEach(processo => {
        const card = createProcessCard(processo);
        processosContainer.appendChild(card);
      });
    } else {
      console.error('Erro ao buscar processos:', response.statusText);
    }
  } catch (error) {
    console.error('Erro na requisição dos processos:', error);
  }
}

fetchProcessos();
