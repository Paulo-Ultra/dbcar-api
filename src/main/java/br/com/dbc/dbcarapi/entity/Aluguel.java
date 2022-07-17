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

//    public Long diasComCarro() {
//        return diaDaEntrega.until(diaDoAluguel, ChronoUnit.DAYS);
//    }
//
//    public Double calcularDiaria() {
//        if (carro.getClasse().equals(ClasseCarro.A.getTipo())) {
//            return diasComCarro() * carro.getPrecoDiaria() * 1.5;
//        } else if (carro.getClasse().equals(ClasseCarro.B.getTipo())) {
//            return diasComCarro() * carro.getPrecoDiaria() * 1.2;
//        } else if (carro.getClasse().equals(ClasseCarro.C.getTipo())) {
//            return diasComCarro() * carro.getPrecoDiaria();
//        } else {
//            System.out.println("O carro informado não está disponível.");
//            return null;
//        }
//    }
//
//    @Override
//    public String toString() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        DecimalFormat df = new DecimalFormat("#,###.00");
//        return "{"
//                + "\"idAluguel\": " + getIdAluguel() + ", "
//                + "\"Carro\": " + getCarro() + ", "
//                + "\"Cliente\": " + getCliente() + ", "
//                + "\"Dia do Aluguel\": \"" + getDiaDoAluguel().format(formatter) + "\", "
//                + "\"Dia da Entrega\": \"" + getDiaDaEntrega().format(formatter) + "\", "
//                + "\"Dias\": " + diasComCarro() + ", "
//                + "\"Diária\": \"" + df.format(calcularDiaria())
//                + "\"}";
//    }
}
