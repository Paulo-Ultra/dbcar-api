package br.com.dbc.dbcarapi.repository;

import java.util.List;


public interface Repositories<CHAVE, OBJECT> {
    List<OBJECT> findAll();
    OBJECT create(OBJECT object);
    OBJECT update(CHAVE id, OBJECT object);
    void delete(CHAVE id);
    OBJECT findById(CHAVE id);
}
