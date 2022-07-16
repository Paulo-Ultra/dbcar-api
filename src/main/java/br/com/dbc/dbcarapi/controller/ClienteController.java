package br.com.dbc.dbcarapi.controller;

import br.com.dbc.dbcarapi.dto.CarroCreateDTO;
import br.com.dbc.dbcarapi.dto.CarroDTO;
import br.com.dbc.dbcarapi.dto.ClienteCreateDTO;
import br.com.dbc.dbcarapi.dto.ClienteDTO;
import br.com.dbc.dbcarapi.entity.Carro;
import br.com.dbc.dbcarapi.entity.Cliente;
import br.com.dbc.dbcarapi.exception.BancoDeDadosException;
import br.com.dbc.dbcarapi.service.ClienteService;
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
@RequestMapping("/cliente")
@Validated
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Listar todos os clientes", description = "Realizará a listagem de todos os clientes da DBCAR.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! A listagem dos clientes foi realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> list() throws SQLException {
        return new ResponseEntity<>(clienteService.list(), HttpStatus.OK);
    }

    @Operation(summary = "Listar cliente pelo ID", description = "Listara o cliente associado ao id e todos os seus dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! A listagem do cliente foi realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> listByIdCliente(Integer idCliente) throws Exception {
        return new ResponseEntity<>(clienteService.listByIdCliente(idCliente), HttpStatus.OK);
    }

    @Operation(summary = "Adicionar um novo cliente", description = "Adicionará um novo cliente a DBCar.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Sucesso! O novo cliente foi adicionado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<ClienteDTO> create(@RequestBody @Valid ClienteCreateDTO cliente) throws SQLException {
        return new ResponseEntity<>(clienteService.create(cliente), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar os dados de um cliente", description = "Atualizará os dados de um cliente associado ao ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Os dados do cliente foram atualizados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> update(@PathVariable("idCliente") Integer idCliente, @RequestBody @Valid ClienteCreateDTO clienteAtualizar) throws Exception {
        return new ResponseEntity<>(clienteService.update(idCliente, clienteAtualizar), HttpStatus.OK);
    }

    @Operation(summary = "Deletar cliente", description = "Removerá o cliente e todos os seus dados do banco de dados da DBCar.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! O cliente foi removido com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> delete(@PathVariable("idCliente") Integer idCliente) throws SQLException {
        clienteService.delete(idCliente);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
