package com.example.gestaoConhecimento.aplication.services;

import com.example.gestaoConhecimento.aplication.services.exceptions.DatabaseException;
import com.example.gestaoConhecimento.aplication.services.exceptions.ResourceNotFoundException;
import com.example.gestaoConhecimento.domain.dtos.RespostaUsuarioDTO;
import com.example.gestaoConhecimento.domain.entities.RespostaUsuario;
import com.example.gestaoConhecimento.domain.entities.Usuario;
import com.example.gestaoConhecimento.domain.entities.Etapa;
import com.example.gestaoConhecimento.domain.repositories.RespostaUsuarioRepository;
import com.example.gestaoConhecimento.domain.repositories.UsuarioRepository;
import com.example.gestaoConhecimento.domain.repositories.EtapaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RespostaUsuarioService {

    @Autowired
    private RespostaUsuarioRepository respostaUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EtapaRepository etapaRepository;

    @Transactional(readOnly = true)
    public RespostaUsuarioDTO findById(Long id) {
        RespostaUsuario resposta = respostaUsuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resposta do usuário não encontrada para o ID: " + id));
        return new RespostaUsuarioDTO(resposta);
    }

    @Transactional(readOnly = true)
    public List<RespostaUsuarioDTO> findAll() {
        List<RespostaUsuario> respostas = respostaUsuarioRepository.findAll();
        return respostas.stream()
                .map(resposta -> new RespostaUsuarioDTO(resposta))
                .collect(Collectors.toList());
    }

    @Transactional
    public RespostaUsuarioDTO save(RespostaUsuarioDTO dto) {
        RespostaUsuario entity = new RespostaUsuario();
        copyDtoToEntity(dto, entity);
        entity = respostaUsuarioRepository.save(entity);
        return new RespostaUsuarioDTO(entity);
    }

    @Transactional
    public RespostaUsuarioDTO update(Long id, RespostaUsuarioDTO dto) {
        try {
            RespostaUsuario entity = respostaUsuarioRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = respostaUsuarioRepository.save(entity);
            return new RespostaUsuarioDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resposta do usuário não encontrada para o ID: " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!respostaUsuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Resposta do usuário não encontrada para o ID: " + id);
        }
        try {
            respostaUsuarioRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao tentar excluir a resposta do usuário. Verifique as dependências.");
        }
    }

    private void copyDtoToEntity(RespostaUsuarioDTO dto, RespostaUsuario entity) {
        entity.setResposta(dto.getResposta());
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado para o ID: " + dto.getUsuarioId()));
        entity.setUsuario(usuario);
        Etapa etapa = etapaRepository.findById(dto.getEtapaId())
                .orElseThrow(() -> new ResourceNotFoundException("Etapa não encontrada para o ID: " + dto.getEtapaId()));
        entity.setEtapa(etapa);
    }
}
