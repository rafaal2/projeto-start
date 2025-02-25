package com.example.gestaoConhecimento.domain.dtos;

import com.example.gestaoConhecimento.domain.entities.Processo;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessoDTO {

    private Long id;

    @NotBlank(message = "Título obrigatório")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
    private String titulo;

    @NotNull(message = "Setor obrigatório")
    @JsonProperty("setorId")
    private Long setorId;

    private String setorNome;

    private List<EtapaDTO> etapas;

    public ProcessoDTO(Processo entity) {
        this.id = entity.getId();
        this.titulo = entity.getTitulo();
        if (entity.getSetor() != null) {
            this.setorId = entity.getSetor().getId();
            this.setorNome = entity.getSetor().getNome();
        }
        if (entity.getEtapas() != null) {
            this.etapas = entity.getEtapas().stream().map(EtapaDTO::new).collect(Collectors.toList());
        }
    }
}
