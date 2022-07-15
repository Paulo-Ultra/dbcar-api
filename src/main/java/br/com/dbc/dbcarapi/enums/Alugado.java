package br.com.dbc.dbcarapi.enums;

import java.util.Arrays;

public enum Alugado {

    ALUGADO(1),
    DISPONIVEL(2);

    private Integer status;

    Alugado(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public static Alugado ofTipo(Integer status) {
        return Arrays.stream(Alugado.values())
                .filter(st -> st.getStatus().equals(status))
                .findFirst()
                .get();
    }
}
