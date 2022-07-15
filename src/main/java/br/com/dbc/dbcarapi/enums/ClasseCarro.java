package br.com.dbc.dbcarapi.enums;

import java.util.Arrays;

public enum ClasseCarro {

    A(1),
    B(2),
    C(3);

    private Integer tipo;

    ClasseCarro(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public static ClasseCarro ofTipo(Integer tipo) {
        return Arrays.stream(ClasseCarro.values())
                .filter(c -> c.getTipo().equals(tipo))
                .findFirst()
                .get();
    }
}
