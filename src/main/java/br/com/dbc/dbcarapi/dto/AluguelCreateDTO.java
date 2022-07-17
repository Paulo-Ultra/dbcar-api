package br.com.dbc.dbcarapi.dto;

import br.com.dbc.dbcarapi.entity.Carro;
import br.com.dbc.dbcarapi.entity.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AluguelCreateDTO {

    @Schema(description = "Carro associado ao aluguel")
    @NotNull
    private Integer idCarro;

    @Schema(description = "Cliente associado ao aluguel")
    @NotNull
    private Integer idCliente;

    @Schema(description = "Data que o carro foi alugado")
    @Past
    @NotNull
    private LocalDate diaDoAluguel;

    @Schema(description = "Data que o carro foi devolvido")
    @NotNull
    private LocalDate diaDaEntrega;
    @Schema(description = "Valor total do aluguel")
    @NotNull
    private Double valor;
}
