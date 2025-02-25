// --- Menu Lateral e Navegação ---
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

const btnhome = document.getElementById('btnhome');
btnhome.addEventListener('click', () => {
  window.location.href = 'index.html';
});

const btnMinhaConta = document.getElementById('btnMinhaConta');
btnMinhaConta.addEventListener('click', () => {
  alert('Ir para página Minha Conta.');
});

const btnFiltro = document.getElementById('btnFiltro');
btnFiltro.addEventListener('click', () => {
  alert('Ir para página de Pesquisa Filtrada.');
});

const btnCriar = document.getElementById('btnCriar');
btnCriar.addEventListener('click', () => {
  window.location.href = '../criar-processo/index.html';
});

const btnver = document.getElementById('btnver');
btnver.addEventListener('click', () => {
  alert('Ir para Meus Aprendizados.');
});

// --- Helper: Obter parâmetros da URL ---
function getQueryParameter(param) {
  const params = new URLSearchParams(window.location.search);
  return params.get(param);
}

const processoId = getQueryParameter('id');

// --- Buscar detalhes do processo via API ---
async function fetchProcessDetails() {
  try {
    const response = await fetch(`http://localhost:8080/processos/${processoId}`);
    if (response.ok) {
      const processo = await response.json();
      renderProcessDetails(processo);
    } else {
      console.error("Erro ao buscar processo:", response.statusText);
    }
  } catch (error) {
    console.error("Erro na requisição do processo:", error);
  }
}

function renderProcessDetails(processo) {
  // Preenche o banner com o título do processo
  document.getElementById('processoTitulo').textContent = processo.titulo;

  // Preenche o card de descrição
  document.getElementById('processoDescricao').textContent = processo.descricao ? processo.descricao : "Sem descrição";

  // Renderiza as etapas no card de etapas
  renderEtapas(processo.etapas);
}

function renderEtapas(etapas) {
  const ul = document.getElementById('etapasList');
  ul.innerHTML = "";
  if (etapas && etapas.length > 0) {
    etapas.forEach((etapa, index) => {
      const li = document.createElement('li');
      li.textContent = `Etapa ${index + 1}: ${etapa.pergunta}`;
      ul.appendChild(li);
    });
  } else {
    ul.innerHTML = "<li>Este processo não possui etapas.</li>";
  }
}

const btnComecarProcesso = document.getElementById('btnComecarProcesso');
btnComecarProcesso.addEventListener('click', () => {
  // Redireciona para a página de resposta completa do processo
  window.location.href = `responder-processo.html?processoId=${processoId}`;
});

fetchProcessDetails();
