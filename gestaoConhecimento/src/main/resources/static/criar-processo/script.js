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
  window.location.href = '../home/index.html';
});

// --- Preencher Datalist com Setores ---
let setoresMap = {}; // Mapeia o nome para o id do setor

async function fetchSetores() {
  try {
    const response = await fetch('http://localhost:8080/setores');
    if (response.ok) {
      const setores = await response.json();
      const datalist = document.getElementById('setoresDatalist');
      datalist.innerHTML = '';
      setores.forEach(setor => {
        // Armazena a relação: nome => id
        setoresMap[setor.nome] = setor.id;
        const option = document.createElement('option');
        option.value = setor.nome;
        datalist.appendChild(option);
      });
    } else {
      console.error('Erro ao buscar setores:', response.statusText);
    }
  } catch (error) {
    console.error('Erro na requisição dos setores:', error);
  }
}
fetchSetores();

// --- Lógica do Formulário de Criação de Processo ---
const formCriarProcesso = document.getElementById('formCriarProcesso');
const etapasProcessoInput = document.getElementById('etapasProcesso');
const etapasContainer = document.getElementById('etapasContainer');

etapasProcessoInput.addEventListener('input', () => {
  const numEtapas = parseInt(etapasProcessoInput.value);
  etapasContainer.innerHTML = '';
  if (numEtapas > 0) {
    for (let i = 1; i <= numEtapas; i++) {
      const etapaDiv = document.createElement('div');
      etapaDiv.classList.add('etapa-item');

      // Pergunta
      const labelPergunta = document.createElement('label');
      labelPergunta.textContent = `Etapa ${i} - Pergunta:`;
      labelPergunta.setAttribute('for', `perguntaEtapa${i}`);
      const inputPergunta = document.createElement('input');
      inputPergunta.type = 'text';
      inputPergunta.id = `perguntaEtapa${i}`;
      inputPergunta.name = `perguntaEtapa${i}`;
      inputPergunta.required = true;

      // Resposta
      const labelResposta = document.createElement('label');
      labelResposta.textContent = `Etapa ${i} - Resposta:`;
      labelResposta.setAttribute('for', `respostaEtapa${i}`);
      const inputResposta = document.createElement('input');
      inputResposta.type = 'text';
      inputResposta.id = `respostaEtapa${i}`;
      inputResposta.name = `respostaEtapa${i}`;
      inputResposta.required = true;

      etapaDiv.appendChild(labelPergunta);
      etapaDiv.appendChild(inputPergunta);
      etapaDiv.appendChild(labelResposta);
      etapaDiv.appendChild(inputResposta);

      etapasContainer.appendChild(etapaDiv);
    }
  }
});

formCriarProcesso.addEventListener('submit', async (event) => {
  event.preventDefault();

  const nomeProcesso = document.getElementById('nomeProcesso').value;
  const descricaoProcesso = document.getElementById('descricaoProcesso').value;
  const setorNome = document.getElementById('setorProcessoName').value;
  const numEtapas = parseInt(etapasProcessoInput.value);

  // Converte o nome do setor para id usando o mapa
  const setorId = setoresMap[setorNome];
  if (!setorId) {
    alert('Setor inválido ou não encontrado. Selecione um setor válido.');
    return;
  }

  const etapas = [];
  for (let i = 1; i <= numEtapas; i++) {
    const pergunta = document.getElementById(`perguntaEtapa${i}`).value;
    const resposta = document.getElementById(`respostaEtapa${i}`).value;
    etapas.push({ pergunta, resposta });
  }

  const processoData = {
    titulo: nomeProcesso,
    descricao: descricaoProcesso,
    setorId: parseInt(setorId),
    etapas: etapas
  };

  console.log("Enviando dados do processo:", processoData);

  try {
    const response = await fetch('http://localhost:8080/processos', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(processoData)
    });

    if (response.ok) {
      const novoProcesso = await response.json();
      alert(`Processo "${novoProcesso.titulo}" criado com sucesso!`);
      formCriarProcesso.reset();
      etapasContainer.innerHTML = '';
    } else {
      const error = await response.text();
      alert(`Erro ao criar processo: ${error}`);
    }
  } catch (error) {
    console.error("Erro na requisição:", error);
    alert("Erro ao conectar com o servidor.");
  }
});

/* Botão Editar Processo */
const btnEditarProcesso = document.getElementById('btnEditarProcesso');
btnEditarProcesso.addEventListener('click', () => {
  window.location.href = 'editar-processo.html';
});
