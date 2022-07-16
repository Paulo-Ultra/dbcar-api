package br.com.dbc.dbcarapi.entity;

import br.com.dbc.dbcarapi.enums.Alugado;
import br.com.dbc.dbcarapi.enums.ClasseCarro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Carro {

    private Integer idCarro;

    private Alugado alugado;

    private String nomeCarro;

    private String marca;

    private ClasseCarro classe;

    private Integer qntPassageiros;

    private Integer kmRodados;

    private Double precoDiaria;
}
