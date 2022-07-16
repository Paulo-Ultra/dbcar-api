package br.com.dbc.dbcarapi.service;

import br.com.dbc.dbcarapi.dto.CarroCreateDTO;
import br.com.dbc.dbcarapi.dto.CarroDTO;
import br.com.dbc.dbcarapi.dto.ClienteCreateDTO;
import br.com.dbc.dbcarapi.dto.ClienteDTO;
import br.com.dbc.dbcarapi.entity.Carro;
import br.com.dbc.dbcarapi.entity.Cliente;
import br.com.dbc.dbcarapi.exception.BancoDeDadosException;
import br.com.dbc.dbcarapi.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClienteService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteDTO> list() throws BancoDeDadosException {
        return clienteRepository.list().stream()
                .map(cliente -> objectMapper.convertValue(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    public ClienteDTO create(ClienteCreateDTO cliente) throws BancoDeDadosException {
        log.info("Adicionando um novo cliente ao banco de dados...");
        Cliente clienteEntity = convertClienteEntity(cliente);
        Cliente clienteCriado = clienteRepository.create(clienteEntity);
        ClienteDTO clienteDTO = convertClienteDTO(clienteCriado);
        log.info("O cliente " + clienteDTO.getNome() + " foi adicionado com sucesso!");
        return clienteDTO;
    }

    public Cliente findByIdCliente(Integer idCliente) throws Exception {
        return clienteRepository.list().stream()
                .filter(cliente -> cliente.getIdUsuario().equals(idCliente))
                .findFirst()
                .orElseThrow(() -> new Exception ("Cliente não encontrado"));
    }

    private Cliente convertClienteEntity(ClienteCreateDTO cliente) {
        return objectMapper.convertValue(cliente, Cliente.class);
    }

    private ClienteDTO convertClienteDTO(Cliente clienteEntity) {
        return objectMapper.convertValue(clienteEntity, ClienteDTO.class);
    }
}
