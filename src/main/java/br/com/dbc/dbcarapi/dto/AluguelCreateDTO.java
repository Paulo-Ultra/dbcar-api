package br.com.dbc.dbcarapi.dto;

import br.com.dbc.dbcarapi.entity.Carro;
import br.com.dbc.dbcarapi.entity.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AluguelCreateDTO {

    @Schema(description = "Identificador (ID) do aluguel")
    private Integer idAluguel;

    @Schema(description = "Carro associado ao aluguel")
    @NotNull
    private Carro carro;

    @Schema(description = "Cliente associado ao aluguel")
    @NotNull
    private Cliente cliente;

    @Schema(description = "Data que o carro foi alugado")
    @NotNull
    private LocalDate diaDoAluguel;

    @Schema(description = "Data que o carro foi devolvido")
    @NotNull
    private LocalDate diaDaEntrega;
}
