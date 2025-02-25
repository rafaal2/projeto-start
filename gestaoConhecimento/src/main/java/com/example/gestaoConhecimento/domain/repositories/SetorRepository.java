package com.example.gestaoConhecimento.domain.repositories;

import com.example.gestaoConhecimento.domain.entities.Processo;
import com.example.gestaoConhecimento.domain.entities.Setor;
import com.example.gestaoConhecimento.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SetorRepository extends JpaRepository<Setor, Long> {
}
