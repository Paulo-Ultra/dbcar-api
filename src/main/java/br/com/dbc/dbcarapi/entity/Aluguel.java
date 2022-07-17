package br.com.dbc.dbcarapi.entity;

import br.com.dbc.dbcarapi.enums.ClasseCarro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Aluguel {

    private Integer idAluguel;
    private Integer idCliente;
    private Integer idCarro;
    private LocalDate diaDoAluguel;
    private LocalDate diaDaEntrega;
}
