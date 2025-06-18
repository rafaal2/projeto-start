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
    const response = await fetch('/setores');
    if (response.ok) {
      const setores = await response.json();
      const datalist = document.getElementById('setoresDatalist');
      datalist.innerHTML = '';
      setores.forEach(setor => {
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
      // Cria um container para cada etapa
      const etapaDiv = document.createElement('div');
      etapaDiv.classList.add('etapa-item');
      etapaDiv.dataset.etapaIndex = i; // útil para identificar

      // Título da Etapa
      const labelTitulo = document.createElement('label');
      labelTitulo.textContent = `Etapa ${i} - Título:`;
      labelTitulo.setAttribute('for', `tituloEtapa${i}`);
      const inputTitulo = document.createElement('input');
      inputTitulo.type = 'text';
      inputTitulo.id = `tituloEtapa${i}`;
      inputTitulo.name = `tituloEtapa${i}`;
      inputTitulo.required = true;

      // Tipo da Etapa
      const labelTipo = document.createElement('label');
      labelTipo.textContent = `Etapa ${i} - Tipo:`;
      labelTipo.setAttribute('for', `tipoEtapa${i}`);
      const selectTipo = document.createElement('select');
      selectTipo.id = `tipoEtapa${i}`;
      selectTipo.name = `tipoEtapa${i}`;
      selectTipo.required = true;
      // Opções: QUESTION, TEXT, LINK
      const optDefault = document.createElement('option');
      optDefault.value = "";
      optDefault.textContent = "Selecione...";
      selectTipo.appendChild(optDefault);
      ["QUESTION", "TEXT", "LINK"].forEach(tipo => {
        const opt = document.createElement('option');
        opt.value = tipo;
        opt.textContent = tipo;
        selectTipo.appendChild(opt);
      });

      // Container para detalhes específicos da etapa (alternativas ou conteúdo)
      const detalhesDiv = document.createElement('div');
      detalhesDiv.id = `detalhesEtapa${i}`;

      // Quando o tipo mudar, atualiza os detalhes
      selectTipo.addEventListener('change', () => {
        updateDetalhesEtapa(i, selectTipo.value);
      });

      etapaDiv.appendChild(labelTitulo);
      etapaDiv.appendChild(inputTitulo);
      etapaDiv.appendChild(labelTipo);
      etapaDiv.appendChild(selectTipo);
      etapaDiv.appendChild(detalhesDiv);

      etapasContainer.appendChild(etapaDiv);
    }
  }
});

// Função que atualiza os detalhes de uma etapa com base no tipo
function updateDetalhesEtapa(index, tipo) {
  const detalhesDiv = document.getElementById(`detalhesEtapa${index}`);
  detalhesDiv.innerHTML = ""; // Limpa detalhes anteriores

  if (tipo === "TEXT" || tipo === "LINK") {
    // Para TEXT ou LINK, exibir um único campo de conteúdo
    const labelConteudo = document.createElement('label');
    labelConteudo.textContent = `Etapa ${index} - Conteúdo:`;
    labelConteudo.setAttribute('for', `conteudoEtapa${index}`);
    const inputConteudo = document.createElement('input');
    inputConteudo.type = 'text';
    inputConteudo.id = `conteudoEtapa${index}`;
    inputConteudo.name = `conteudoEtapa${index}`;
    inputConteudo.required = true;
    detalhesDiv.appendChild(labelConteudo);
    detalhesDiv.appendChild(inputConteudo);
  } else if (tipo === "QUESTION") {
    // Para QUESTION, exibir um container para alternativas e um botão para adicioná-las
    const alternativasContainer = document.createElement('div');
    alternativasContainer.id = `alternativasContainer${index}`;

    // Botão para adicionar alternativa
    const btnAddAlt = document.createElement('button');
    btnAddAlt.type = 'button';
    btnAddAlt.textContent = 'Adicionar Alternativa';
    btnAddAlt.classList.add('btn-add-alt');
    btnAddAlt.addEventListener('click', () => {
      addAlternativa(index);
    });

    detalhesDiv.appendChild(alternativasContainer);
    detalhesDiv.appendChild(btnAddAlt);
  }
}

// Função para adicionar uma alternativa para uma etapa QUESTION
function addAlternativa(etapaIndex) {
  const container = document.getElementById(`alternativasContainer${etapaIndex}`);
  const altDiv = document.createElement('div');
  altDiv.classList.add('alternativa-item');

  const inputTexto = document.createElement('input');
  inputTexto.type = 'text';
  inputTexto.placeholder = 'Texto da alternativa';
  inputTexto.required = true;
  inputTexto.classList.add('alt-texto');

  const checkboxCorreta = document.createElement('input');
  checkboxCorreta.type = 'checkbox';
  checkboxCorreta.classList.add('alt-correta');

  const labelCorreta = document.createElement('label');
  labelCorreta.textContent = 'Correta';
  labelCorreta.prepend(checkboxCorreta);

  altDiv.appendChild(inputTexto);
  altDiv.appendChild(labelCorreta);

  container.appendChild(altDiv);
}

formCriarProcesso.addEventListener('submit', async (event) => {
  event.preventDefault();

  const nomeProcesso = document.getElementById('nomeProcesso').value;
  const descricaoProcesso = document.getElementById('descricaoProcesso').value;
  const dificuldadeProcesso = document.getElementById('dificuldadeProcesso').value;
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
    const titulo = document.getElementById(`tituloEtapa${i}`).value;
    const tipo = document.getElementById(`tipoEtapa${i}`).value;
    let conteudo = null;
    let alternativas = null;
    if (tipo === "TEXT" || tipo === "LINK") {
      conteudo = document.getElementById(`conteudoEtapa${i}`).value;
    } else if (tipo === "QUESTION") {
      alternativas = [];
      const container = document.getElementById(`alternativasContainer${i}`);
      const altItems = container.querySelectorAll('.alternativa-item');
      altItems.forEach(altItem => {
        const texto = altItem.querySelector('.alt-texto').value;
        const correta = altItem.querySelector('.alt-correta').checked;
        alternativas.push({ texto, correta });
      });
    }
    etapas.push({ titulo, tipo, conteudo, alternativas });
  }

  const processoData = {
    titulo: nomeProcesso,
    descricao: descricaoProcesso,
    dificuldade: dificuldadeProcesso,
    setorId: parseInt(setorId),
    etapas: etapas
  };

  console.log("Enviando dados do processo:", processoData);

  try {
    const response = await fetch('/processos', {
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
