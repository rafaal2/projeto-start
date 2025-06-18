package com.example.gestaoConhecimento.aplication.services;

import com.example.gestaoConhecimento.aplication.services.exceptions.DatabaseException;
import com.example.gestaoConhecimento.aplication.services.exceptions.ResourceNotFoundException;
import com.example.gestaoConhecimento.domain.dtos.EtapaDTO;
import com.example.gestaoConhecimento.domain.entities.Etapa;
import com.example.gestaoConhecimento.domain.entities.Processo;
import com.example.gestaoConhecimento.domain.entities.Alternativa;
import com.example.gestaoConhecimento.domain.enums.TipoEtapa;
import com.example.gestaoConhecimento.domain.repositories.EtapaRepository;
import com.example.gestaoConhecimento.domain.repositories.ProcessoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class EtapaService {

    @Autowired
    private EtapaRepository etapaRepository;

    @Autowired
    private ProcessoRepository processoRepository;

    @Transactional(readOnly = true)
    public EtapaDTO findById(Long id) {
        Etapa etapa = etapaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Etapa não encontrada para o ID: " + id));
        return new EtapaDTO(etapa);
    }

    @Transactional(readOnly = true)
    public List<EtapaDTO> findAll() {
        List<Etapa> etapas = etapaRepository.findAll();
        List<EtapaDTO> dtos = new ArrayList<>();
        etapas.forEach(etapa -> dtos.add(new EtapaDTO(etapa)));
        return dtos;
    }

    @Transactional
    public EtapaDTO save(EtapaDTO dto) {
        Etapa entity = new Etapa();
        copyDtoToEntity(dto, entity);
        entity = etapaRepository.save(entity);
        return new EtapaDTO(entity);
    }

    @Transactional
    public EtapaDTO update(Long id, EtapaDTO dto) {
        try {
            Etapa entity = etapaRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = etapaRepository.save(entity);
            return new EtapaDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Etapa não encontrada para o ID: " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!etapaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Etapa não encontrada para o ID: " + id);
        }
        try {
            etapaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao tentar excluir a etapa. Verifique as dependências.");
        }
    }

    private void copyDtoToEntity(EtapaDTO dto, Etapa entity) {
        entity.setTitulo(dto.getTitulo());
        if(dto.getTipo() != null) {
            entity.setTipo(TipoEtapa.valueOf(dto.getTipo()));
        }
        entity.setConteudo(dto.getConteudo());
        // Mapeamento das alternativas se existir (para tipo QUESTION)
        if (dto.getAlternativas() != null) {
            if(entity.getAlternativas() == null) {
                entity.setAlternativas(new ArrayList<>());
            } else {
                entity.getAlternativas().clear();
            }
            dto.getAlternativas().forEach(altDTO -> {
                Alternativa alt = new Alternativa();
                alt.setTexto(altDTO.getTexto());
                alt.setCorreta(altDTO.isCorreta());
                alt.setEtapa(entity);
                entity.getAlternativas().add(alt);
            });
        }
        Processo processo = processoRepository.findById(dto.getProcessoId())
                .orElseThrow(() -> new ResourceNotFoundException("Processo não encontrado para o ID: " + dto.getProcessoId()));
        entity.setProcesso(processo);
    }
}
