package com.example.gestaoConhecimento.domain.dtos;

import com.example.gestaoConhecimento.domain.entities.Setor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SetorDTO {

    private Long id;

    @NotBlank(message = "Nome do setor obrigatório")
    @Size(max = 100, message = "O nome do setor deve ter no máximo 100 caracteres")
    private String nome;

    public SetorDTO(Setor entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
    }
}
