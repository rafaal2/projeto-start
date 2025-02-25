package com.example.gestaoConhecimento.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "alternativa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Alternativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    private boolean correta;

    @ManyToOne
    @JoinColumn(name = "etapa_id")
    private Etapa etapa;
}

