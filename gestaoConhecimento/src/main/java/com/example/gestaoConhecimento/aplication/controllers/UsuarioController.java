package com.example.gestaoConhecimento.aplication.controllers;


import com.example.gestaoConhecimento.aplication.services.UsuarioService;
import com.example.gestaoConhecimento.domain.dtos.UsuarioDTO;
import com.example.gestaoConhecimento.domain.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    private static Long usuarioLogado;

    @Autowired
    private UsuarioService service;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id) {

        UsuarioDTO usuarioDTO = service.findById(id);
        return ResponseEntity.status(200).body(usuarioDTO);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> save(@RequestBody UsuarioDTO usuarioDTO) {
        usuarioDTO = service.save(usuarioDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuarioDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(usuarioDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        usuarioDTO = service.update(id, usuarioDTO);
        return ResponseEntity.status(200).body(usuarioDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/static/login")
//    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
//        boolean isAuthenticated = service.autenticarUsuario(loginRequest.getEmail(), loginRequest.getSenha());
//        usuarioLogado = usuarioRepository.findByEmail(loginRequest.getEmail()).get().getId();
//        return isAuthenticated
//                ? ResponseEntity.ok("Login bem-sucedido")
//                : ResponseEntity.status(401).body("Credenciais inválidas");
//    }
//
//    @GetMapping(value = "/usuario-logado")
//    public ResponseEntity<Long> getUsuarioLogado() {
//        Long usuarioLogadoId = service.getUsuarioLogado();
//        return ResponseEntity.ok(usuarioLogadoId);
//    }

}
