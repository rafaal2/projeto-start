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
  window.location.href = '../home/index.html';
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
    const response = await fetch(`/processos/${processoId}`);
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
  // Preenche o banner com título, dificuldade e setor
  document.getElementById('processoTitulo').textContent = processo.titulo;
  document.getElementById('processoDificuldade').textContent = processo.dificuldade ? processo.dificuldade : "Não definida";
  document.getElementById('processoSetor').textContent = processo.setorNome ? processo.setorNome : "Não definido";

  // Escolhe a imagem conforme o setor
  const bannerImg = document.querySelector('.banner-img');
  if (processo.setorNome.toLowerCase().includes('rh')) {
    bannerImg.src = "https://funcional.com/images/uploads/posts/entenda-por-que-contratar-mais-um-profissional-de-rh.png";
  } else if (processo.setorNome.toLowerCase().includes('financeiro')) {
    bannerImg.src = "https://www.idebrasil.com.br/blog/wp-content/uploads/2019/12/blog-o-que-e-o-que-faz-setor-financeiro-de-uma-empresa-850x441.jpg";
  } else {
    bannerImg.src = "https://png.pngtree.com/png-vector/20220528/ourlarge/pngtree-minimalist-graphic-depiction-of-contemporary-business-ideas-collection-vector-png-image_13688376.jpg"; // Default
  }

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
      li.textContent = `Etapa ${index + 1}: ${etapa.titulo}`;
      ul.appendChild(li);
    });
  } else {
    ul.innerHTML = "<li>Este processo não possui etapas.</li>";
  }
}

// Botão global para iniciar o processo
const btnComecarProcesso = document.getElementById('btnComecarProcesso');
btnComecarProcesso.addEventListener('click', () => {
  // Redireciona para responder/index.html com o parâmetro do processo
  window.location.href = `../responder/index.html?processoId=${processoId}`;
});

fetchProcessDetails();
