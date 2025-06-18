package com.example.gestaoConhecimento.aplication.controllers;

import com.example.gestaoConhecimento.domain.dtos.EtapaDTO;
import com.example.gestaoConhecimento.aplication.services.EtapaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/etapas")
public class EtapaController {

    @Autowired
    private EtapaService service;

    @GetMapping
    public ResponseEntity<List<EtapaDTO>> findAll() {
        List<EtapaDTO> etapas = service.findAll();
        return ResponseEntity.ok(etapas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtapaDTO> findById(@PathVariable Long id) {
        EtapaDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<EtapaDTO> save(@RequestBody EtapaDTO dto) {
        dto = service.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EtapaDTO> update(@PathVariable Long id, @RequestBody EtapaDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
