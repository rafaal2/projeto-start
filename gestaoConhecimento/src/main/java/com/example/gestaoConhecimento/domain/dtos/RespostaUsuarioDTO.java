package com.example.gestaoConhecimento.domain.dtos;

import com.example.gestaoConhecimento.domain.entities.RespostaUsuario;
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
public class RespostaUsuarioDTO {

    private Long id;

    @NotNull(message = "Usuário obrigatório")
    @JsonProperty("usuarioId")
    private Long usuarioId;

    @NotNull(message = "Etapa obrigatória")
    @JsonProperty("etapaId")
    private Long etapaId;

    @NotBlank(message = "Resposta obrigatória")
    private String resposta;

    public RespostaUsuarioDTO(RespostaUsuario entity) {
        this.id = entity.getId();
        if (entity.getUsuario() != null) {
            this.usuarioId = entity.getUsuario().getId();
        }
        if (entity.getEtapa() != null) {
            this.etapaId = entity.getEtapa().getId();
        }
        this.resposta = entity.getResposta();
    }
}
