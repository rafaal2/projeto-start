package com.example.gestaoConhecimento.aplication.services;

import com.example.gestaoConhecimento.aplication.services.exceptions.DatabaseException;
import com.example.gestaoConhecimento.aplication.services.exceptions.ResourceNotFoundException;
import com.example.gestaoConhecimento.domain.dtos.AlternativaDTO;
import com.example.gestaoConhecimento.domain.entities.Alternativa;
import com.example.gestaoConhecimento.domain.repositories.AlternativaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlternativaService {

    @Autowired
    private AlternativaRepository alternativaRepository;

    @Transactional(readOnly = true)
    public AlternativaDTO findById(Long id) {
        Alternativa alt = alternativaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alternativa não encontrada para o ID: " + id));
        return new AlternativaDTO(alt);
    }

    @Transactional
    public AlternativaDTO save(AlternativaDTO dto) {
        Alternativa alt = new Alternativa();
        copyDtoToEntity(dto, alt);
        alt = alternativaRepository.save(alt);
        return new AlternativaDTO(alt);
    }

    @Transactional
    public AlternativaDTO update(Long id, AlternativaDTO dto) {
        try {
            Alternativa alt = alternativaRepository.getReferenceById(id);
            copyDtoToEntity(dto, alt);
            alt = alternativaRepository.save(alt);
            return new AlternativaDTO(alt);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Alternativa não encontrada para o ID: " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!alternativaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Alternativa não encontrada para o ID: " + id);
        }
        try {
            alternativaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao tentar excluir a alternativa. Verifique as dependências.");
        }
    }

    private void copyDtoToEntity(AlternativaDTO dto, Alternativa entity) {
        entity.setTexto(dto.getTexto());
        entity.setCorreta(dto.isCorreta());
        // A associação com a etapa deve ser configurada externamente (por exemplo, na etapa).
    }
}
