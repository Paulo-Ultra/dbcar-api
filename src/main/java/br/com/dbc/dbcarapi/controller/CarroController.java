package br.com.dbc.dbcarapi.controller;

import br.com.dbc.dbcarapi.dto.CarroCreateDTO;
import br.com.dbc.dbcarapi.dto.CarroDTO;
import br.com.dbc.dbcarapi.entity.Carro;
import br.com.dbc.dbcarapi.exception.BancoDeDadosException;
import br.com.dbc.dbcarapi.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/carro")
@Validated
public class CarroController {

    @Autowired
    private CarroService carroService;

    @GetMapping
    public ResponseEntity<List<Carro>> list() throws BancoDeDadosException {
        return new ResponseEntity<>(carroService.list(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CarroDTO> create(@RequestBody @Valid CarroCreateDTO pessoa) throws BancoDeDadosException {
        return new ResponseEntity<>(carroService.create(pessoa), HttpStatus.CREATED);
    }
}
