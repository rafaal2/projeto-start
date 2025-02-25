package com.example.gestaoConhecimento.aplication.controllers;

import com.example.gestaoConhecimento.domain.dtos.RespostaUsuarioDTO;
import com.example.gestaoConhecimento.aplication.services.RespostaUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/respostas-usuario")
public class RespostaUsuarioController {

    @Autowired
    private RespostaUsuarioService service;

    @GetMapping
    public ResponseEntity<List<RespostaUsuarioDTO>> findAll() {
        List<RespostaUsuarioDTO> respostas = service.findAll();
        return ResponseEntity.ok(respostas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaUsuarioDTO> findById(@PathVariable Long id) {
        RespostaUsuarioDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<RespostaUsuarioDTO> save(@RequestBody RespostaUsuarioDTO dto) {
        dto = service.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespostaUsuarioDTO> update(@PathVariable Long id, @RequestBody RespostaUsuarioDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}