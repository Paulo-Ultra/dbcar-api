package br.com.dbc.dbcarapi.controller;

import br.com.dbc.dbcarapi.dto.CarroCreateDTO;
import br.com.dbc.dbcarapi.dto.CarroDTO;
import br.com.dbc.dbcarapi.service.CarroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/carro")
@Validated
public class CarroController {

    @Autowired
    private CarroService carroService;

    @Operation(summary = "Listar os carros do catálogo", description = "Realizará a listagem de todos os carros disponiveis no catálogo")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! A listagem dos carros disponíveis foi realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<CarroDTO>> list() throws SQLException {
        return new ResponseEntity<>(carroService.list(), HttpStatus.OK);
    }

    @Operation(summary = "Listar os carros do catálogo não alugados", description = "Realizará a listagem de todos os carros disponiveis para aluguel no catálogo")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! A listagem dos carros disponíveis foi realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @GetMapping("disponiveis")
    public ResponseEntity<List<CarroDTO>> listNaoAlugados() throws SQLException {
        return new ResponseEntity<>(carroService.listNaoAlugados(), HttpStatus.OK);
    }
    @Operation(summary = "Listar os carros do catálogo pela identificação", description = "Realizará a listagem dos carros disponíveis no catálogo pelo número da identificação")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! A listagem dos carros disponíveis foi realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @GetMapping("/{idCarro}")
    public ResponseEntity<CarroDTO> findById (@PathVariable Integer idCarro) throws Exception {
        return new ResponseEntity<>(carroService.findByIdCarro(idCarro), HttpStatus.OK);
    }

    @Operation(summary = "Adicionar carro ao catálogo", description = "Adicionará um novo carro ao catálogo")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Sucesso! O novo carro foi adicionado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<CarroDTO> create(@RequestBody @Valid CarroCreateDTO carro) throws SQLException {
        return new ResponseEntity<>(carroService.create(carro), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar um carro do catálogo", description = "Atualizará um carro do catálogo")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! O carro selecionado foi atualizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @PutMapping("/{idCarro}")
    public ResponseEntity<CarroDTO> update(@PathVariable("idCarro") Integer idCarro, @RequestBody @Valid CarroCreateDTO carroAtualizar) throws Exception {
        return new ResponseEntity<>(carroService.update(idCarro, carroAtualizar), HttpStatus.OK);
    }

    @Operation(summary = "Deletar carro do catálogo", description = "Deletará o carro e todos os seus dados do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Sucesso! O carro e todos os seus dados foram removidos com sucesso do banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Permissao negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execuçao, foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idCarro}")
    public ResponseEntity<Void> delete(@PathVariable("idCarro") Integer idCarro) throws SQLException {
        carroService.delete(idCarro);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}