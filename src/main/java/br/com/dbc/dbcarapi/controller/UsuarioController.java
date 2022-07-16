package br.com.dbc.dbcarapi.controller;

import br.com.dbc.dbcarapi.dto.CarroCreateDTO;
import br.com.dbc.dbcarapi.dto.CarroDTO;
import br.com.dbc.dbcarapi.dto.UsuarioCreateDTO;
import br.com.dbc.dbcarapi.dto.UsuarioDTO;
import br.com.dbc.dbcarapi.service.UsuarioService;
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
@RequestMapping("/usuario")
@Validated
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Lista todos os usuários", description = "Realizará a listagem de todos os usuários no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! A listagem dos usuários foi realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> list() throws SQLException {
        return new ResponseEntity<>(usuarioService.list(), HttpStatus.OK);
    }

    @Operation(summary = "Listar os usuários do banco de dados pela identificação", description = "Realizará a listagem dos usuários no banco de dados pelo número da identificação")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! A listagem dos usuários disponíveis foi realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @GetMapping("{idUsuario}")
    public ResponseEntity<UsuarioDTO> findById(Integer idUsuario) throws Exception {
        return new ResponseEntity<>(usuarioService.findByIdUsuario(idUsuario), HttpStatus.OK);
    }

    @Operation(summary = "Adicionar usuário ao banco de dados", description = "Adicionará um novo usuário ao banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Sucesso! O novo usuário foi adicionado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioCreateDTO usuario) throws SQLException {
        return new ResponseEntity<>(usuarioService.create(usuario), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar um usuário do banco de dados", description = "Atualizará um usuário do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! O usuário selecionado foi atualizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable("idUsuario") Integer idUsuario, @RequestBody @Valid UsuarioCreateDTO usuarioAtualizar) throws Exception {
        return new ResponseEntity<>(usuarioService.update(idUsuario, usuarioAtualizar), HttpStatus.OK);
    }

    @Operation(summary = "Deletar usuário do banco de dados", description = "Deletará o usuário e todos os seus dados do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Sucesso! O usuário e todos os seus dados foram removidos com sucesso do banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Permissao negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execuçao, foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> delete(@PathVariable("idUsuario") Integer idUsuario) throws SQLException {
        usuarioService.delete(idUsuario);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
