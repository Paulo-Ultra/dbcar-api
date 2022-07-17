package br.com.dbc.dbcarapi.controller;

import br.com.dbc.dbcarapi.dto.AluguelCreateDTO;
import br.com.dbc.dbcarapi.dto.AluguelDTO;
import br.com.dbc.dbcarapi.service.AluguelService;
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
@RequestMapping("/aluguel")
@Validated
public class AluguelController {

    @Autowired
    private AluguelService aluguelService;

    @Operation(summary = "Listar os aluguéis do catálogo", description = "Realizará a listagem de todos os aluguéis no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! A listagem dos aluguéis foi realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<AluguelDTO>> list() throws SQLException {
        return new ResponseEntity<>(aluguelService.list(), HttpStatus.OK);
    }

    @Operation(summary = "Listar os aluguéis pelo identificador", description = "Realizará a listagem dos aluguéis no banco de dados pelo número de identificação")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! A listagem dos aluguéis foi realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @GetMapping("/{idAluguel}")
    public ResponseEntity<AluguelDTO> findById (@PathVariable Integer idAluguel) throws Exception {
        return new ResponseEntity<>(aluguelService.findByIdAluguel(idAluguel), HttpStatus.OK);
    }

    @Operation(summary = "Adicionar aluguel ao banco de dados", description = "Adicionará um novo aluguel ao banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Sucesso! O novo aluguel foi adicionado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<AluguelDTO> create(@RequestBody @Valid AluguelCreateDTO aluguel) throws Exception {
        return new ResponseEntity<>(aluguelService.create(aluguel), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar um aluguel", description = "Atualizará um aluguel de um cliente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! O aluguel do cliente selecionado foi atualizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @PutMapping("/{idAluguel}")
    public ResponseEntity<AluguelDTO> update(@PathVariable("idAluguel") Integer idAluguel, @RequestBody @Valid AluguelCreateDTO aluguelAtualizar) throws Exception {
        return new ResponseEntity<>(aluguelService.update(idAluguel, aluguelAtualizar), HttpStatus.OK);
    }

    @Operation(summary = "Deletar aluguel", description = "Deletará um aluguel associado ao ID do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Sucesso! O aluguel foi removido com sucesso do banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Permissao negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execuçao, foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idAluguel}")
    public ResponseEntity<Void> delete(@PathVariable("idAluguel") Integer idAluguel) throws SQLException {
        aluguelService.delete(idAluguel);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
