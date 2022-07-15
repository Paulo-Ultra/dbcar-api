package br.com.dbc.dbcarapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEnderecoDTO {

    //todo classe endereço?
    @Schema(description = "Identificador (ID) do cliente")
    private Integer idCliente;

    @Schema(description = "Tipo de endereço do cliente")
    @NotNull(message = "O tipo de endereço não pode ser vazio.")
    private TipoEndereco tipo;

    @Schema(description = "Logradouro da residência")
    @NotEmpty(message = "O logradouro não pode ser vazio/nulo")
    @Size(max = 250, message = "Informe um logradouro válido, contendo até 250 caracteres.")
    private String logradouro;

    @Schema(description = "Número da residência")
    @NotNull(message = "O número da residência não pode ser nulo.")
    private Integer numero;

    @Schema(description = "Complemento da residência")
    private String complemento;

    @Schema(description = "CEP da residência")
    @NotEmpty(message = "O CEP não pode ser vazio/nulo.")
    @Size(min = 8, max = 8, message = "O CEP deve conter 8 números.")
    private String cep;

    @Schema(description = "Cidade em que está localizado o endereço")
    @NotEmpty(message = "A cidade não pode ser nula/vazia.")
    @Size(max = 250, message = "Informe uma cidade válida, contendo até 250 caracteres.")
    private String cidade;

    @Schema(description = "Estado em que está localizado a residência")
    @NotNull(message = "O estado não pode ser nulo.")
    private String estado;

    @Schema(description = "País em que está localizado a residência")
    @NotNull(message = "O país não pode ser nulo.")
    private String pais;
}
