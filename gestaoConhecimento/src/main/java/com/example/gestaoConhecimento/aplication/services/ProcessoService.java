package com.example.gestaoConhecimento.aplication.services;

import com.example.gestaoConhecimento.aplication.services.exceptions.DatabaseException;
import com.example.gestaoConhecimento.aplication.services.exceptions.ResourceNotFoundException;
import com.example.gestaoConhecimento.domain.dtos.ProcessoDTO;
import com.example.gestaoConhecimento.domain.entities.Processo;
import com.example.gestaoConhecimento.domain.entities.Setor;
import com.example.gestaoConhecimento.domain.entities.Etapa;
import com.example.gestaoConhecimento.domain.entities.Alternativa;
import com.example.gestaoConhecimento.domain.enums.Dificuldade;
import com.example.gestaoConhecimento.domain.repositories.ProcessoRepository;
import com.example.gestaoConhecimento.domain.repositories.SetorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessoService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private SetorRepository setorRepository;

    @Transactional(readOnly = true)
    public ProcessoDTO findById(Long id) {
        Processo processo = processoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Processo não encontrado para o ID: " + id));
        return new ProcessoDTO(processo);
    }

    @Transactional(readOnly = true)
    public List<ProcessoDTO> findAll() {
        List<Processo> processos = processoRepository.findAll();
        return processos.stream()
                .map(ProcessoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProcessoDTO save(ProcessoDTO dto) {
        Processo entity = new Processo();
        copyDtoToEntity(dto, entity);
        entity = processoRepository.save(entity);
        return new ProcessoDTO(entity);
    }

    @Transactional
    public ProcessoDTO update(Long id, ProcessoDTO dto) {
        try {
            Processo entity = processoRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = processoRepository.save(entity);
            return new ProcessoDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Processo não encontrado para o ID: " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!processoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Processo não encontrado para o ID: " + id);
        }
        try {
            processoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao tentar excluir o processo. Verifique as dependências.");
        }
    }

    private void copyDtoToEntity(ProcessoDTO dto, Processo entity) {
        entity.setTitulo(dto.getTitulo());
        entity.setDescricao(dto.getDescricao());
        if(dto.getDificuldade() != null) {
            entity.setDificuldade(Dificuldade.valueOf(dto.getDificuldade()));
        }
        Setor setor = setorRepository.findById(dto.getSetorId())
                .orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado para o ID: " + dto.getSetorId()));
        entity.setSetor(setor);

        // Se houver etapas, mapeia as etapas
        if (dto.getEtapas() != null) {
            if (entity.getEtapas() == null) {
                entity.setEtapas(new ArrayList<>());
            } else {
                entity.getEtapas().clear();
            }
            dto.getEtapas().forEach(etapaDTO -> {
                Etapa etapa = new Etapa();
                etapa.setTitulo(etapaDTO.getTitulo());
                etapa.setTipo(etapaDTO.getTipo() != null ?
                        com.example.gestaoConhecimento.domain.enums.TipoEtapa.valueOf(etapaDTO.getTipo()) : null);
                etapa.setConteudo(etapaDTO.getConteudo());
                // Se houver alternativas, mapeia-as
                if (etapaDTO.getAlternativas() != null) {
                    List<Alternativa> alternativas = etapaDTO.getAlternativas().stream().map(altDTO -> {
                        Alternativa alt = new Alternativa();
                        alt.setTexto(altDTO.getTexto());
                        alt.setCorreta(altDTO.isCorreta());
                        alt.setEtapa(etapa);
                        return alt;
                    }).collect(Collectors.toList());
                    etapa.setAlternativas(alternativas);
                }
                etapa.setProcesso(entity);
                entity.getEtapas().add(etapa);
            });
        }
    }
}
