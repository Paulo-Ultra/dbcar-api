package br.com.dbc.dbcarapi.enums;

import java.util.Arrays;

public enum ClasseCarro {

    A("A"),
    B("B"),
    C("C");

    private String tipo;

    ClasseCarro(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public static ClasseCarro ofTipo(String tipo) {
        return Arrays.stream(ClasseCarro.values())
                .filter(c -> c.getTipo().equals(tipo))
                .findFirst()
                .get();
    }
}
