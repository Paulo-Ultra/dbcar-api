package br.com.dbc.dbcarapi.service;

import br.com.dbc.dbcarapi.dto.ClienteCreateDTO;
import br.com.dbc.dbcarapi.dto.ClienteDTO;
import br.com.dbc.dbcarapi.entity.Aluguel;
import br.com.dbc.dbcarapi.entity.Cliente;
import br.com.dbc.dbcarapi.exception.BancoDeDadosException;
import br.com.dbc.dbcarapi.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClienteService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmailService emailService;

    public List<ClienteDTO> list() throws SQLException {
        log.info("Listando clientes...");
        try {
            return clienteRepository.list().stream()
                    .map(cliente -> convertClienteDTO(cliente))
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getCause());
        }
    }

    public ClienteDTO findByIdCliente(Integer idCliente) throws SQLException {
        Cliente clienteRecuperado = clienteRepository.findByIdCliente(idCliente);
        if(clienteRecuperado != null){
            return convertClienteDTO(clienteRecuperado);
        } else {
            throw new SQLException("Cliente não encontrado");
        }
    }

    public ClienteDTO create(ClienteCreateDTO cliente) throws SQLException {
        log.info("Adicionando um novo cliente ao banco de dados...");
        Cliente clienteEntity = convertClienteEntity(cliente);
        try {
            clienteEntity = clienteRepository.create(clienteEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getCause());
        }
        ClienteDTO clienteDTO = convertClienteDTO(clienteEntity);
        log.info("O cliente " + clienteDTO.getNome() + " foi adicionado com sucesso ao banco de dados!");
        String tipo = "create";
        emailService.sendEmailNovoCliente(clienteDTO, tipo);
        return clienteDTO;
    }

    public ClienteDTO update(Integer idCliente, ClienteCreateDTO clienteCreateDTO) throws SQLException {
        log.info("Atualizando dados do cliente...");
        findByIdCliente(idCliente);
        Cliente clienteEntity = convertClienteEntity(clienteCreateDTO);
        try {
            clienteEntity = clienteRepository.update(idCliente, clienteEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getCause());
        }
        ClienteDTO clienteDTO = convertClienteDTO(clienteEntity);
        clienteDTO.setIdCliente(idCliente);

        log.info("Dados do cliente " + clienteDTO.getNome() + " foram atualizados.");
        String tipo = "update";
        emailService.sendEmailNovoCliente(clienteDTO, tipo);
        return clienteDTO;
    }

    public void delete(Integer idCliente) throws SQLException {
        log.info("Removendo cliente...");
        try {
            Cliente verifyCliente = clienteRepository.findByIdCliente(idCliente);
            clienteRepository.delete(idCliente);
            log.info("O cliente " + verifyCliente.getNome() + " foi removido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getCause());
        } catch (NullPointerException e) {
            throw new NullPointerException("O identificador (ID) informado não consta em nosso banco de dados!");
        }
    }

    private Cliente convertClienteEntity(ClienteCreateDTO cliente) {
        return objectMapper.convertValue(cliente, Cliente.class);
    }

    private ClienteDTO convertClienteDTO(Cliente clienteEntity) {
        return objectMapper.convertValue(clienteEntity, ClienteDTO.class);
    }
}
