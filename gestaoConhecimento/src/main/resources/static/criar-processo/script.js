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
  window.location.href = 'index.html';
});


// Seletores e lógica do formulário de criação de processo
const formCriarProcesso = document.getElementById('formCriarProcesso');
const etapasProcessoInput = document.getElementById('etapasProcesso');
const etapasContainer = document.getElementById('etapasContainer');

// Atualiza o container de etapas sempre que o número muda
etapasProcessoInput.addEventListener('input', () => {
  const numEtapas = parseInt(etapasProcessoInput.value);
  // Limpa o container
  etapasContainer.innerHTML = '';
  if (numEtapas > 0) {
    for (let i = 1; i <= numEtapas; i++) {
      // Cria um container para cada etapa
      const etapaDiv = document.createElement('div');
      etapaDiv.classList.add('etapa-item');

      // Cria label e input para a pergunta
      const labelPergunta = document.createElement('label');
      labelPergunta.textContent = `Etapa ${i} - Pergunta:`;
      labelPergunta.setAttribute('for', `perguntaEtapa${i}`);
      const inputPergunta = document.createElement('input');
      inputPergunta.type = 'text';
      inputPergunta.id = `perguntaEtapa${i}`;
      inputPergunta.name = `perguntaEtapa${i}`;
      inputPergunta.required = true;

      // Cria label e input para a resposta
      const labelResposta = document.createElement('label');
      labelResposta.textContent = `Etapa ${i} - Resposta:`;
      labelResposta.setAttribute('for', `respostaEtapa${i}`);
      const inputResposta = document.createElement('input');
      inputResposta.type = 'text';
      inputResposta.id = `respostaEtapa${i}`;
      inputResposta.name = `respostaEtapa${i}`;
      inputResposta.required = true;

      // Adiciona os elementos ao container da etapa
      etapaDiv.appendChild(labelPergunta);
      etapaDiv.appendChild(inputPergunta);
      etapaDiv.appendChild(labelResposta);
      etapaDiv.appendChild(inputResposta);

      // Adiciona o container da etapa ao container principal
      etapasContainer.appendChild(etapaDiv);
    }
  }
});

// Submissão do formulário
formCriarProcesso.addEventListener('submit', async (event) => {
  event.preventDefault();

  const nomeProcesso = document.getElementById('nomeProcesso').value;
  const setorProcesso = document.getElementById('setorProcesso').value;
  const numEtapas = parseInt(etapasProcessoInput.value);

  // Cria o array de etapas
  const etapas = [];
  for (let i = 1; i <= numEtapas; i++) {
    const pergunta = document.getElementById(`perguntaEtapa${i}`).value;
    const resposta = document.getElementById(`respostaEtapa${i}`).value;
    etapas.push({ pergunta, resposta });
  }

  // Monta o objeto a ser enviado (adapte os nomes conforme seu DTO)
  const processoData = {
    titulo: nomeProcesso,
    setorId: parseInt(setorProcesso),
    etapas: etapas
  };

  console.log("Enviando dados do processo:", processoData);

  try {
    // Exemplo de chamada à API (ajuste a URL conforme sua configuração)
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
      etapasContainer.innerHTML = ''; // limpa os campos de etapas
    } else {
      const error = await response.text();
      alert(`Erro ao criar processo: ${error}`);
    }
  } catch (error) {
    console.error("Erro na requisição:", error);
    alert("Erro ao conectar com o servidor.");
  }
});

// Botão Editar Processo
const btnEditarProcesso = document.getElementById('btnEditarProcesso');
btnEditarProcesso.addEventListener('click', () => {
  window.location.href = 'editar-processo.html';
});

// Botão Página Inicial
const btnhome = document.getElementById('btnhome');
btnhome.addEventListener('click', () => {
  window.location.href = '../home/index.html';
});
