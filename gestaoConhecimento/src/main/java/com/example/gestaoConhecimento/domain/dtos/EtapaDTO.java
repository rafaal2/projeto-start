package com.example.gestaoConhecimento.domain.dtos;

import com.example.gestaoConhecimento.domain.entities.Etapa;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EtapaDTO {
    private Long id;

    @NotBlank(message = "Pergunta obrigatória")
    @Size(max = 500, message = "A pergunta deve ter no máximo 500 caracteres")
    private String pergunta;

    @NotBlank(message = "Resposta obrigatória")
    private String resposta;

    @NotNull(message = "Processo obrigatório")
    @JsonProperty("processoId")
    private Long processoId;

    public EtapaDTO(Etapa entity) {
        this.id = entity.getId();
        this.pergunta = entity.getPergunta();
        this.resposta = entity.getResposta();
        if (entity.getProcesso() != null) {
            this.processoId = entity.getProcesso().getId();
        }
    }
}

