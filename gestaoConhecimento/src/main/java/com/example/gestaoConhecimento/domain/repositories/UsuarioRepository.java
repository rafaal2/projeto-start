package com.example.gestaoConhecimento.domain.repositories;


import com.example.gestaoConhecimento.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
