package com.example.gestaoConhecimento.domain.entities;

import com.example.gestaoConhecimento.domain.enums.Dificuldade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "processo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String titulo;

    @Column(length = 1000)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "setor_id")
    private Setor setor;

    @Enumerated(EnumType.STRING)
    private Dificuldade dificuldade;

    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Etapa> etapas = new ArrayList<>();
}