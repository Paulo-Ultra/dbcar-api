package br.com.dbc.dbcarapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioDTO extends UsuarioCreateDTO {

    @Schema(description = "Identificador (ID) do usuário")
    private Integer idUsuario;
}
