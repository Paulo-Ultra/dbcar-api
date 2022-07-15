package br.com.dbc.dbcarapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteCreateDTO extends UsuarioDTO {

    @Schema(description = "CPF do cliente")
    @NotBlank
    private String cpf;

    @Schema(description = "E-mail do cliente")
    @NotBlank
    private String email;
}
