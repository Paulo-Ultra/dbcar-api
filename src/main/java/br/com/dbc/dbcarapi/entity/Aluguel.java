package br.com.dbc.dbcarapi.entity;

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
    private Cliente cliente;
    private Carro carro;
    private LocalDate diaDoAluguel;
    private LocalDate diaDaEntrega;

    public Long diasComCarro() {
        return diaDaEntrega.until(diaDoAluguel, ChronoUnit.DAYS);
    }

    public Double calcularDiaria() {
        switch (carro.getClasse()) {
            case "A":
                return diasComCarro() * carro.getPrecoDiaria() * 1.5;
            case "B":
                return diasComCarro() * carro.getPrecoDiaria() * 1.2;
            case "C":
                return diasComCarro() * carro.getPrecoDiaria();
            default:
                System.out.println("O carro informado não está disponível.");
                return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Aluguel)) {
            return false;
        }
        Aluguel aluguel = (Aluguel) o;
        return Objects.equals(idAluguel, aluguel.idAluguel) && Objects.equals(carro, aluguel.carro) && Objects.equals(cliente, aluguel.cliente)
                && Objects.equals(diaDoAluguel, aluguel.diaDoAluguel) && Objects.equals(diaDaEntrega, aluguel.diaDaEntrega);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAluguel, carro, cliente, diaDoAluguel, diaDaEntrega);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormat df = new DecimalFormat("#,###.00");
        return "{"
                + "\"idAluguel\": " + getIdAluguel() + ", "
                + "\"Carro\": " + getCarro() + ", "
                + "\"Cliente\": " + getCliente() + ", "
                + "\"Dia do Aluguel\": \"" + getDiaDoAluguel().format(formatter) + "\", "
                + "\"Dia da Entrega\": \"" + getDiaDaEntrega().format(formatter) + "\", "
                + "\"Dias\": " + diasComCarro() + ", "
                + "\"Diária\": \"" + df.format(calcularDiaria())
                + "\"}";
    }
}
