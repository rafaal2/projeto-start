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

// --- Função para obter query parameters ---
function getQueryParameter(param) {
  const urlParams = new URLSearchParams(window.location.search);
  return urlParams.get(param);
}

const processoId = getQueryParameter('id');
let currentProcesso = null; // para armazenar os dados do processo atual

// --- Função para buscar os detalhes do processo via API ---
async function fetchProcesso() {
  try {
    const response = await fetch(`http://localhost:8080/processos/${processoId}`);
    if (response.ok) {
      const processo = await response.json();
      currentProcesso = processo;
      renderProcesso(processo);
    } else {
      console.error('Erro ao buscar processo:', response.statusText);
    }
  } catch (error) {
    console.error('Erro na requisição do processo:', error);
  }
}

// --- Função para renderizar o processo e suas etapas ---
function renderProcesso(processo) {
  const processoDetails = document.getElementById('processoDetails');
  // Exibe o título do processo
  processoDetails.innerHTML = `<h2>${processo.titulo}</h2>`;

  const etapasContainer = document.getElementById('etapasContainer');
  etapasContainer.innerHTML = '';

  if (processo.etapas && processo.etapas.length > 0) {
    processo.etapas.forEach((etapa, index) => {
      const etapaDiv = document.createElement('div');
      etapaDiv.classList.add('etapa-item');

      // Exibe a pergunta da etapa
      const question = document.createElement('p');
      question.textContent = `Etapa ${index + 1}: ${etapa.pergunta}`;
      etapaDiv.appendChild(question);

      // Cria o campo para resposta
      const inputResposta = document.createElement('input');
      inputResposta.type = 'text';
      inputResposta.id = `respostaEtapa${etapa.id || index}`;
      inputResposta.name = `respostaEtapa${etapa.id || index}`;
      inputResposta.placeholder = 'Digite sua resposta';
      inputResposta.required = true;
      etapaDiv.appendChild(inputResposta);

      etapasContainer.appendChild(etapaDiv);
    });
  } else {
    etapasContainer.innerHTML = '<p>Este processo não possui etapas.</p>';
  }
}

// Chama a função para buscar o processo ao carregar a página
fetchProcesso();

// --- Lógica de envio das respostas ---
const formResponderProcesso = document.getElementById('formResponderProcesso');
formResponderProcesso.addEventListener('submit', async (event) => {
  event.preventDefault();

  // Coleta as respostas de cada etapa
  const respostaInputs = document.querySelectorAll('#etapasContainer input');
  const respostas = [];
  respostaInputs.forEach((input, idx) => {
    respostas.push({
      // Utiliza o id da etapa presente no processo retornado
      etapaId: currentProcesso.etapas[idx].id,
      resposta: input.value
    });
  });

  // Monta o objeto para enviar (ajuste conforme o seu DTO/endpoint)
  const respostaData = {
    processoId: currentProcesso.id,
    respostas: respostas
  };

  try {
    const response = await fetch('http://localhost:8080/respostas-usuario', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(respostaData)
    });

    if (response.ok) {
      alert('Respostas enviadas com sucesso!');
      // Opcional: redirecionar ou limpar o formulário
    } else {
      const error = await response.text();
      alert(`Erro ao enviar respostas: ${error}`);
    }
  } catch (error) {
    console.error('Erro na requisição de respostas:', error);
    alert('Erro ao conectar com o servidor.');
  }
});
