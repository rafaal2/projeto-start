package com.example.gestaoConhecimento.aplication.controllers;

import com.example.gestaoConhecimento.domain.dtos.AlternativaDTO;
import com.example.gestaoConhecimento.aplication.services.AlternativaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/alternativas")
public class AlternativaController {

    @Autowired
    private AlternativaService service;

    @GetMapping("/{id}")
    public ResponseEntity<AlternativaDTO> findById(@PathVariable Long id) {
        AlternativaDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<AlternativaDTO> save(@RequestBody AlternativaDTO dto) {
        dto = service.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlternativaDTO> update(@PathVariable Long id, @RequestBody AlternativaDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
