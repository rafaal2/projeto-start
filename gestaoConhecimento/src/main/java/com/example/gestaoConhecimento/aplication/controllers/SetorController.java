package com.example.gestaoConhecimento.aplication.controllers;

import com.example.gestaoConhecimento.aplication.services.SetorService;
import com.example.gestaoConhecimento.domain.dtos.SetorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setores")
public class SetorController {

    @Autowired
    private SetorService service;


    @GetMapping
    public ResponseEntity<List<SetorDTO>> findAll() {
        List<SetorDTO> setores = service.findAll();
        return ResponseEntity.ok(setores);
    }

    @PostMapping
    public ResponseEntity<SetorDTO> save(@RequestBody SetorDTO dto) {
        dto = service.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SetorDTO> update(@PathVariable Long id, @RequestBody SetorDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
