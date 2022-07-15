package br.com.dbc.dbcarapi.entity;

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
    private String alugado;
    private String nomeCarro;
    private String marca;
    private String classe;
    private Integer qntPassageiros;
    private Integer kmRodados;
    private Double precoDiaria;

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Carro)) {
            return false;
        }
        Carro carro = (Carro) o;
        return Objects.equals(idCarro, carro.idCarro) && Objects.equals(alugado, carro.alugado) && Objects.equals(nomeCarro, carro.nomeCarro)
                && Objects.equals(marca, carro.marca) && Objects.equals(classe, carro.classe)
                && Objects.equals(qntPassageiros, carro.qntPassageiros)
                && Objects.equals(kmRodados, carro.kmRodados) && Objects.equals(precoDiaria, carro.precoDiaria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCarro, alugado, nomeCarro, marca, classe, qntPassageiros, kmRodados, precoDiaria);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return "{"
                + "\"idCarro\": " + getIdCarro() + ", "
                + "\"nome\": \"" + getNomeCarro() + "\", "
                + "\"marca\": \"" + getMarca() + "\", "
                + "\"classe\": \"" + getClasse() + "\", "
                + "\"Km Rodados\": " + getKmRodados() + ", "
                + "\"Preço Diária\": \"" + df.format(getPrecoDiaria().doubleValue()) + "\", "
                + "\"Quantidade de Passageiros\": " + getQntPassageiros() + ", "
                + "\"Alugado\": \"" + getAlugado()
                + "\"}";
    }
}
