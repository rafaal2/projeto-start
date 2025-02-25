package com.example.gestaoConhecimento.domain.dtos;

import com.example.gestaoConhecimento.domain.entities.Etapa;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EtapaDTO {
    private Long id;

    @NotBlank(message = "Título da etapa obrigatório")
    private String titulo;

    @NotBlank(message = "Tipo da etapa obrigatório")
    private String tipo;  // QUESTION, TEXT, ou LINK

    // Para etapas do tipo TEXT ou LINK, armazena o conteúdo (texto ou URL)
    private String conteudo;

    // Para etapas do tipo QUESTION, haverá alternativas
    private List<AlternativaDTO> alternativas;

    @NotNull(message = "Processo obrigatório")
    @JsonProperty("processoId")
    private Long processoId;

    public EtapaDTO(Etapa entity) {
        this.id = entity.getId();
        this.titulo = entity.getTitulo();
        this.tipo = entity.getTipo() != null ? entity.getTipo().toString() : null;
        this.conteudo = entity.getConteudo();
        if (entity.getAlternativas() != null) {
            this.alternativas = entity.getAlternativas().stream().map(AlternativaDTO::new).collect(Collectors.toList());
        }
        if (entity.getProcesso() != null) {
            this.processoId = entity.getProcesso().getId();
        }
    }
}
