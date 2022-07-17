package br.com.dbc.dbcarapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteCreateDTO {

    @Schema(description = "Nome do cliente")
    @NotBlank
    private String nome;
    @Schema(description = "CPF do cliente")
    @NotBlank
    private String cpf;

    @Schema(description = "Telefone para contato")
    @NotBlank
    private String telefone;

    @Schema(description = "Endereço do cliente")
    @NotBlank
    private String endereco;

    @Schema(description = "E-mail do cliente")
    @NotBlank
    private String email;
}
