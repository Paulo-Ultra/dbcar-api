package br.com.dbc.dbcarapi.dto;

import br.com.dbc.dbcarapi.enums.Alugado;
import br.com.dbc.dbcarapi.enums.ClasseCarro;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarroCreateDTO {

    @Schema(description = "Informativo se o carro esta alugado")
    @NotBlank(message = "É necessário informar se o veículo está/não alugado.")
    private Alugado alugado;

    @Schema(description = "Nome do carro")
    @NotBlank(message = "O nome do veículo não pode ser nulo/vazio.")
    private String nome;

    @Schema(description = "Marca do carro")
    @NotBlank(message = "A marca do veículo não pode ser nula/vazia.")
    private String marca;

    @Schema(description = "Classe do carro")
    @NotBlank(message = "A classe do veículo não pode ser nula/vazia.")
    private ClasseCarro classe;

    @Schema(description = "Quantidade de passageiros que o veículo suporta")
    @NotNull(message = "A quantidade de passageiros não pode ser nula.")
    private Integer quantidade_passageiros;

    @Schema(description = "Kilômetros rodados pelo veículo")
    @NotNull(message = "A quantidade de km rodados pelo veículo não pode ser nula.")
    private Integer kmRodados;

    @Schema(description = "Preço da diária do veículo")
    @NotNull(message = "O veículo não pode ter um preço de diária nulo.")
    private Double precoDiaria;
}
