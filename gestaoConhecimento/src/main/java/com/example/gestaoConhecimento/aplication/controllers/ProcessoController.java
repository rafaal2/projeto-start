package com.example.gestaoConhecimento.aplication.controllers;

import com.example.gestaoConhecimento.domain.dtos.ProcessoDTO;
import com.example.gestaoConhecimento.aplication.services.ProcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/processos")
public class ProcessoController {

    @Autowired
    private ProcessoService service;

    @GetMapping
    public ResponseEntity<List<ProcessoDTO>> findAll() {
        List<ProcessoDTO> processos = service.findAll();
        return ResponseEntity.ok(processos);
    }

    @PostMapping
    public ResponseEntity<ProcessoDTO> save(@RequestBody ProcessoDTO dto) {
        dto = service.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcessoDTO> update(@PathVariable Long id, @RequestBody ProcessoDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}