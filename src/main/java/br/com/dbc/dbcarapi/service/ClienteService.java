package br.com.dbc.dbcarapi.service;

import br.com.dbc.dbcarapi.dto.ClienteCreateDTO;
import br.com.dbc.dbcarapi.dto.ClienteDTO;
import br.com.dbc.dbcarapi.entity.Cliente;
import br.com.dbc.dbcarapi.exception.BancoDeDadosException;
import br.com.dbc.dbcarapi.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClienteService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteDTO> list() throws SQLException {
        return clienteRepository.list().stream()
                .map(cliente -> {
                    ClienteDTO clienteDTO = objectMapper.convertValue(cliente, ClienteDTO.class);
                    return clienteDTO;
                })
                .collect(Collectors.toList());
    }

    public ClienteDTO listByIdCliente(Integer idCliente) throws Exception {
        return (ClienteDTO) clienteRepository.list().stream()
                .map(this::convertClienteDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO create(ClienteCreateDTO cliente) throws SQLException {
        log.info("Adicionando um novo cliente ao banco de dados...");

        Cliente clienteEntity = convertClienteEntity(cliente);
        Cliente clienteCriado = clienteRepository.create(clienteEntity);

        ClienteDTO clienteDTO = convertClienteDTO(clienteCriado);
        log.info("O cliente " + clienteDTO.getNome() + " foi adicionado com sucesso!");
        return clienteDTO;
    }

    public ClienteDTO update(Integer idCliente, ClienteCreateDTO clienteCreateDTO) throws Exception {
        log.info("Atualizando dados do cliente...");

        Cliente clienteRecuperado = clienteRepository.findByIdCliente(idCliente);
        if (clienteRecuperado != null) {

            Cliente clienteEntity = convertClienteEntity(clienteCreateDTO);
            clienteEntity.setIdUsuario(clienteRecuperado.getIdUsuario());
            clienteEntity.setCpf(clienteRecuperado.getCpf());
            clienteEntity.setTelefone(clienteRecuperado.getTelefone());
            clienteEntity.setEndereco(clienteRecuperado.getEndereco());
            clienteEntity.setEmail(clienteRecuperado.getEmail());

            clienteRepository.update(idCliente, clienteEntity);

            log.info("Dados do cliente atualizados.");

            return convertClienteDTO(clienteEntity);
        } else {
            throw new Exception("Cliente não atualizado");
        }
    }

    public void delete(Integer idCliente) throws SQLException {
        log.info("Removendo cliente...");

        Cliente verifyCliente = clienteRepository.findByIdCliente(idCliente);

        clienteRepository.delete(idCliente);

        log.info("O cliente " + verifyCliente.getNome() + " foi removido com sucesso!");
    }

    private Cliente convertClienteEntity(ClienteCreateDTO cliente) {
        return objectMapper.convertValue(cliente, Cliente.class);
    }

    private ClienteDTO convertClienteDTO(Cliente clienteEntity) {
        return objectMapper.convertValue(clienteEntity, ClienteDTO.class);
    }
}
