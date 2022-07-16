package br.com.dbc.dbcarapi.service;

import br.com.dbc.dbcarapi.dto.CarroCreateDTO;
import br.com.dbc.dbcarapi.dto.CarroDTO;
import br.com.dbc.dbcarapi.dto.UsuarioCreateDTO;
import br.com.dbc.dbcarapi.dto.UsuarioDTO;
import br.com.dbc.dbcarapi.entity.Carro;
import br.com.dbc.dbcarapi.entity.Usuario;
import br.com.dbc.dbcarapi.repository.CarroRepository;
import br.com.dbc.dbcarapi.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UsuarioService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> list() throws SQLException {
        return usuarioRepository.list().stream()
                .map(usuario -> convertUsuarioDTO(usuario))
                .collect(Collectors.toList());
    }

    public UsuarioDTO create(UsuarioCreateDTO usuario) throws SQLException {
        log.info("Adicionando o novo usuário...");
        Usuario usuarioEntity = convertUsuarioEntity(usuario);
        Usuario usuarioCriado = usuarioRepository.create(usuarioEntity);
        UsuarioDTO usuarioDTO = convertUsuarioDTO(usuarioCriado);
        log.info("O novo usuário" + usuarioDTO.getNome() + " foi adicionado com sucesso.");
        return usuarioDTO;
    }

    public UsuarioDTO update(Integer idUsuario, UsuarioCreateDTO usuarioCreateDTO) throws Exception {
        log.info("Atualizando dados do usuario...");
        findByIdUsuario(idUsuario);
        Usuario usuarioEntity = convertUsuarioEntity(usuarioCreateDTO);
        Usuario usuarioAtualizar = usuarioRepository.update(idUsuario, usuarioEntity);
        UsuarioDTO usuarioDTO = convertUsuarioDTO(usuarioAtualizar);
        usuarioDTO.setIdUsuario(idUsuario);
        log.info("Dados do usuário atualizados " + usuarioAtualizar);
        return usuarioDTO;

    }

    public void delete(Integer idUsuario) throws SQLException {
        log.info("Deletando usuário do catálogo...");
        Usuario verifyUsuario = usuarioRepository.findById(idUsuario);
        usuarioRepository.delete(idUsuario);
        log.info("O carro " + verifyUsuario.getNome() + " foi removido do catálogo com sucesso!");
    }

    public UsuarioDTO findByIdUsuario(Integer idUsuario) throws Exception {
        Usuario usuarioRecuperado = usuarioRepository.findById(idUsuario);
        if(usuarioRecuperado != null) {
            return convertUsuarioDTO(usuarioRecuperado);
        } else {
            throw new Exception("Usuário não encontrado");
        }
    }

    public Usuario convertUsuarioEntity(UsuarioCreateDTO usuario) {
        return objectMapper.convertValue(usuario, Usuario.class);
    }

    public UsuarioDTO convertUsuarioDTO(Usuario usuarioEntity) {
        return objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
    }
}
