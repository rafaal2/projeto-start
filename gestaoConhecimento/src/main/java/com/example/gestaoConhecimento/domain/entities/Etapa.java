package com.example.gestaoConhecimento.domain.entities;

import com.example.gestaoConhecimento.domain.enums.TipoEtapa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    // Um título ou enunciado para a etapa
    private String titulo;

    @Enumerated(EnumType.STRING)
    private TipoEtapa tipo;

    // Para etapas do tipo TEXT ou LINK, armazene o conteúdo aqui:
    private String conteudo;

    // Caso seja do tipo QUESTION, alternativas serão cadastradas:
    @OneToMany(mappedBy = "etapa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alternativa> alternativas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "processo_id")
    private Processo processo;
}