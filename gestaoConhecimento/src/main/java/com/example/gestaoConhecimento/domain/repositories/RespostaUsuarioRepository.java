package com.example.gestaoConhecimento.domain.repositories;

import com.example.gestaoConhecimento.domain.entities.RespostaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespostaUsuarioRepository extends JpaRepository<RespostaUsuario, Long> {


}
