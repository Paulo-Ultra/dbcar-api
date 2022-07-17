package br.com.dbc.dbcarapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioCreateDTO {

    @Schema(description = "Nome do funcionário")
    @NotBlank
    private String nome;
    @Schema(description = "Matricula do funcionário")
    @NotBlank
    private String matricula;
}
