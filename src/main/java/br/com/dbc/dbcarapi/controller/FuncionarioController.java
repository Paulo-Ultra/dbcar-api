package br.com.dbc.dbcarapi.controller;

import br.com.dbc.dbcarapi.dto.*;
import br.com.dbc.dbcarapi.service.FuncionarioService;
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
@RequestMapping("/funcionario")
@Validated
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @Operation(summary = "Listar os funcionários do banco de dados", description = "Realizará a listagem de todos os funcionários do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! A listagem dos funcionários disponíveis foi realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> list() throws SQLException {
        return new ResponseEntity<>(funcionarioService.list(), HttpStatus.OK);
    }

    @Operation(summary = "Listar os funcionários do banco de dados pela identificação", description = "Realizará a listagem dos funcionários no banco de dados pelo número da identificação")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! A listagem dos funcionários disponíveis foi realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @GetMapping("{idFuncionario}")
    public ResponseEntity<FuncionarioDTO> findById(Integer idFuncionario) throws Exception {
        return new ResponseEntity<>(funcionarioService.findByIdFuncionario(idFuncionario), HttpStatus.OK);
    }

    @Operation(summary = "Adicionar funcionário ao banco de dados", description = "Adicionará um novo funcionário ao banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Sucesso! O novo funcionário foi adicionado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<FuncionarioDTO> create(@RequestBody @Valid FuncionarioCreateDTO funcionario) throws SQLException {
        return new ResponseEntity<>(funcionarioService.create(funcionario), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar um funcionário do banco de dados", description = "Atualizará um funcionário do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! O funcionário selecionado foi atualizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @PutMapping("/{idFuncionario}")
    public ResponseEntity<FuncionarioDTO> update(@PathVariable("idFuncionario") Integer idFuncionario, @RequestBody @Valid FuncionarioCreateDTO funcionarioAtualizar) throws Exception {
        return new ResponseEntity<>(funcionarioService.update(idFuncionario, funcionarioAtualizar), HttpStatus.OK);
    }

    @Operation(summary = "Deletar funcionário do banco de dados", description = "Deletará o funcionário e todos os seus dados do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Sucesso! O funcionário e todos os seus dados foram removidos com sucesso do banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Permissao negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execuçao, foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idFuncionario}")
    public ResponseEntity<Void> delete(@PathVariable("idFuncionario") Integer idUsuario) throws SQLException {
        funcionarioService.delete(idUsuario);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
