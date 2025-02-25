package com.example.gestaoConhecimento.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "respostaUsuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RespostaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "etapa_id")
    private Etapa etapa;

    @Column
    private String resposta;
}
