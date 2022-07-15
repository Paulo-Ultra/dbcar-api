package br.com.dbc.dbcarapi.enums;

import java.util.Arrays;

public enum Alugado {

    ALUGADO("N"),
    DISPONIVEL("S");

    private String status;

    Alugado(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static Alugado ofTipo(String status) {
        return Arrays.stream(Alugado.values())
                .filter(st -> st.getStatus().equals(status))
                .findFirst()
                .get();
    }
}
