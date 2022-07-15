package br.com.dbc.dbcarapi.repository;

import br.com.dbc.dbcarapi.entity.Carro;
import br.com.dbc.dbcarapi.exception.BancoDeDadosException;

import java.util.List;
import java.util.Optional;

public interface Repository <CHAVE, OBJECT> {
    List<OBJECT> findAll();
    OBJECT create(OBJECT object);
    OBJECT update(CHAVE id, OBJECT object);
    void delete(CHAVE id);
    OBJECT findById(CHAVE id);
}
