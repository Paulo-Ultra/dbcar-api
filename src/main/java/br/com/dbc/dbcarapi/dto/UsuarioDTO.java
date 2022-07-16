package br.com.dbc.dbcarapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioDTO extends UsuarioCreateDTO {

    @Schema(description = "Identificador (ID) do usuário")
    private Integer idUsuario;
}
