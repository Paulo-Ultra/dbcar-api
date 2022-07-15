package br.com.dbc.dbcarapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateContatoDTO {

    @Schema(description = "Identificador (ID) do cliente")
    private Integer idCliente;

    @Schema(description = "Tipo de contato do cliente")
    @NotBlank(message = "O tipo de contato deve ser COMERCIAL/RESIDENCIAL")
    private TipoDeContato tipo;

    @Schema(description = "O número de contato do cliente")
    @NotBlank
    @Size(max = 13)
    private String numero;

    @Schema(description = "Descrição do contato")
    @NotBlank
    private String descricao;
}
