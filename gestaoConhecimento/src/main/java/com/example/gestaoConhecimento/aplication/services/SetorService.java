package com.example.gestaoConhecimento.aplication.services;

import com.example.gestaoConhecimento.aplication.services.exceptions.DatabaseException;
import com.example.gestaoConhecimento.aplication.services.exceptions.ResourceNotFoundException;
import com.example.gestaoConhecimento.domain.dtos.SetorDTO;
import com.example.gestaoConhecimento.domain.entities.Setor;
import com.example.gestaoConhecimento.domain.repositories.SetorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetorService {

    @Autowired
    private SetorRepository setorRepository;

    @Transactional(readOnly = true)
    public SetorDTO findById(Long id) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado para o ID: " + id));
        return new SetorDTO(setor);
    }

    @Transactional(readOnly = true)
    public List<SetorDTO> findAll() {
        List<Setor> setores = setorRepository.findAll();
        return setores.stream()
                .map(setor -> new SetorDTO(setor))
                .collect(Collectors.toList());
    }

    @Transactional
    public SetorDTO save(SetorDTO dto) {
        Setor entity = new Setor();
        copyDtoToEntity(dto, entity);
        entity = setorRepository.save(entity);
        return new SetorDTO(entity);
    }

    @Transactional
    public SetorDTO update(Long id, SetorDTO dto) {
        try {
            Setor entity = setorRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = setorRepository.save(entity);
            return new SetorDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Setor não encontrado para o ID: " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!setorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Setor não encontrado para o ID: " + id);
        }
        try {
            setorRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao tentar excluir o setor. Verifique as dependências.");
        }
    }

    private void copyDtoToEntity(SetorDTO dto, Setor entity) {
        entity.setNome(dto.getNome());
    }
}
