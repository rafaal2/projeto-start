package com.example.gestaoConhecimento.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "etapa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Etapa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String pergunta;

    @Column
    private String resposta;

    @ManyToOne
    @JoinColumn(name = "processo_id")
    private Processo processo;
}