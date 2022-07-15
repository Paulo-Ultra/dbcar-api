package br.com.dbc.dbcarapi.service;

import br.com.dbc.dbcarapi.entity.Carro;
import br.com.dbc.dbcarapi.exception.BancoDeDadosException;
import br.com.dbc.dbcarapi.repository.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;
    public List<Carro> list() throws BancoDeDadosException {
        return carroRepository.findAllCarros();
    }
}
