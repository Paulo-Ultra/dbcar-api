package br.com.dbc.dbcarapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarroDTO extends CarroCreateDTO {

    @Schema(description = "Identificador (ID) do carro")
    private Integer idCarro;
}
