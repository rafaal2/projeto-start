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

const processoId = getQueryParameter('processoId');

// Variável para armazenar as etapas e o índice atual
let etapas = [];
let currentStepIndex = 0;

// --- Buscar detalhes do processo via API ---
async function fetchProcessDetails() {
  try {
    const response = await fetch(`/processos/${processoId}`);
    if (response.ok) {
      const processo = await response.json();
      // Supondo que o DTO de processo retorne um array de etapas no campo "etapas"
      etapas = processo.etapas;
      renderCurrentStep();
    } else {
      console.error("Erro ao buscar processo:", response.statusText);
    }
  } catch (error) {
    console.error("Erro na requisição do processo:", error);
  }
}

function renderCurrentStep() {
  const container = document.getElementById('stepContainer');
  container.innerHTML = "";

  if (currentStepIndex >= etapas.length) {
    // Todas as etapas foram respondidas – finalizar o processo
    container.innerHTML = "<h2>Processo Finalizado!</h2><p>Obrigado por participar.</p>";
    return;
  }

  const etapa = etapas[currentStepIndex];
  // Cria um título para a etapa
  const h2 = document.createElement('h2');
  h2.textContent = etapa.titulo;
  container.appendChild(h2);

  // Para etapas QUESTION: exibe alternativas
  if (etapa.tipo === "QUESTION") {
    const form = document.createElement('form');
    form.id = "formEtapa";

    // Cria um container para as alternativas (radio buttons)
    etapa.alternativas.forEach((alt) => {
      const div = document.createElement('div');
      div.classList.add('alternativa-item');

      const radio = document.createElement('input');
      radio.type = "radio";
      radio.name = "alternativa";
      radio.value = alt.id;
      radio.required = true;

      const label = document.createElement('label');
      label.textContent = alt.texto;

      div.appendChild(radio);
      div.appendChild(label);
      form.appendChild(div);
    });

    // Botão para enviar a resposta
    const btnResponder = document.createElement('button');
    btnResponder.type = "submit";
    btnResponder.classList.add('btn-submit');
    btnResponder.textContent = "Responder";
    form.appendChild(btnResponder);

    form.addEventListener('submit', (e) => {
      e.preventDefault();
      // Obter a alternativa selecionada
      const selected = form.querySelector('input[name="alternativa"]:checked');
      if (selected) {
        const altId = selected.value;
        // Verifica se a alternativa é correta
        const alternativa = etapa.alternativas.find(alt => alt.id == altId);
        if (alternativa && alternativa.correta) {
          alert("Resposta correta!");
          currentStepIndex++;
          renderCurrentStep();
        } else {
          alert("Resposta incorreta, tente novamente.");
        }
      }
    });

    container.appendChild(form);
  } else if (etapa.tipo === "TEXT" || etapa.tipo === "LINK") {
    // Para etapas do tipo TEXT ou LINK, exibe o conteúdo (opcional) e apenas um botão "Continuar"
    const p = document.createElement('p');
    p.textContent = etapa.conteudo ? etapa.conteudo : "";
    container.appendChild(p);

    const btnContinuar = document.createElement('button');
    btnContinuar.classList.add('btn-começar-global');
    btnContinuar.textContent = "Continuar";
    btnContinuar.addEventListener('click', () => {
      currentStepIndex++;
      renderCurrentStep();
    });
    container.appendChild(btnContinuar);
  } else {
    // Caso o tipo não seja reconhecido, apenas continue
    const btnContinuar = document.createElement('button');
    btnContinuar.classList.add('btn-começar-global');
    btnContinuar.textContent = "Continuar";
    btnContinuar.addEventListener('click', () => {
      currentStepIndex++;
      renderCurrentStep();
    });
    container.appendChild(btnContinuar);
  }
}

fetchProcessDetails();
