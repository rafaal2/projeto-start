package com.example.gestaoConhecimento.domain.dtos;

import com.example.gestaoConhecimento.domain.entities.Alternativa;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AlternativaDTO {
    private Long id;

    @NotBlank(message = "Texto da alternativa obrigatório")
    private String texto;

    private boolean correta;

    public AlternativaDTO(Alternativa entity) {
        this.id = entity.getId();
        this.texto = entity.getTexto();
        this.correta = entity.isCorreta();
    }
}
