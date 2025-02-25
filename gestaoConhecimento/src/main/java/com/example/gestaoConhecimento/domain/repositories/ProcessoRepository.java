package com.example.gestaoConhecimento.domain.repositories;

import com.example.gestaoConhecimento.domain.entities.Processo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {
}
