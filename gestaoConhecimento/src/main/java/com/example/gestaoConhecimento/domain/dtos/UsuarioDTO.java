package com.example.gestaoConhecimento.domain.dtos;

import com.example.gestaoConhecimento.domain.entities.Usuario;
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
public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "Nome do usuário obrigatório")
    @Size(max = 100, message = "O nome do usuário deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "CPF obrigatório")
    @Size(max = 11, message = "O CPF deve ter no máximo 11 caracteres")
    private String cpf;

    @NotBlank(message = "Email obrigatório")
    @Size(max = 100, message = "O email deve ter no máximo 100 caracteres")
    private String email;

    @NotBlank(message = "Senha obrigatória")
    private String senha;

    public UsuarioDTO(Usuario entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.cpf = entity.getCpf();
        this.email = entity.getEmail();
        this.senha = entity.getSenha();
    }
}