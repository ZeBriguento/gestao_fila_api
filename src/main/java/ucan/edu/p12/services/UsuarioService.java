/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */

package ucan.edu.p12.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import ucan.edu.p12.entities.TipoUsuario;
import ucan.edu.p12.entities.Usuario;
import ucan.edu.p12.entities.dtos.UsuarioDTO;
import ucan.edu.p12.repositories.TipoUsuarioRepository;
import ucan.edu.p12.repositories.UsuarioRepository;
import ucan.edu.p12.services.exceptions.DatabaseException;
import ucan.edu.p12.services.exceptions.ResourceNotFoundException;

/**
 *
 * @author controljp
 */
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    public List<UsuarioDTO> findAll() {
        List<Usuario> usuarios = repository.findAll();
        return UsuarioDTO.converterListParaDTO(usuarios);
    }

    public UsuarioDTO save(UsuarioDTO usuarioDTO) {

        // Valida e obtém o objeto TipoUsuario
        TipoUsuario tipoUsuario = validateAndGetTipoUsuario(usuarioDTO.getFkTipoUsuario());

        // Valida e obtém o objeto Localidade

        Usuario usuario = UsuarioDTO.toEntity(usuarioDTO);
        usuario.setFkTipoUsuario(tipoUsuario);
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setPassword(usuarioDTO.getPassword());
        Usuario usuarioSalvo = repository.save(usuario);
        return UsuarioDTO.toDTO(usuarioSalvo);
    }

    // Metodos adicionais
    private TipoUsuario validateAndGetTipoUsuario(UUID fkTipoUsuario) {
        if (fkTipoUsuario != null) {
            return tipoUsuarioRepository.findById(fkTipoUsuario)
                    .orElseThrow(() -> new ResourceNotFoundException("TipoUsuario not found for ID: " + fkTipoUsuario));
        }
        return null; // Se fkTipoUsuario for null, não atribuímos um objeto TipoUsuario.
    }

    public UsuarioDTO findById(UUID pkUsuario) {
         Optional<Usuario> optional = repository.findById(pkUsuario);
        Usuario usuario = optional.orElseThrow(() -> new ResourceNotFoundException("Id not found"));
        return UsuarioDTO.toDTO(usuario);
    }

    public UsuarioDTO update(UUID pkUsuario, UsuarioDTO usuarioDTO) {
         // Busca a entidade Usuario pelo ID ou lança exceção se não encontrada
         Usuario usuario = repository.findById(pkUsuario)
         .orElseThrow(() -> new ResourceNotFoundException("Usuario not found for ID: " + pkUsuario));
 
     // Valida e obtém o objeto TipoUsuario, se informado
     TipoUsuario tipoUsuario = validateAndGetTipoUsuario(usuarioDTO.getFkTipoUsuario());
 
     // Atualiza os campos da entidade Usuario com os dados do DTO
     usuario.setUsername(usuarioDTO.getUsername());
     usuario.setPassword(usuarioDTO.getPassword());
     usuario.setFkTipoUsuario(tipoUsuario);
 
     // Salva a entidade atualizada no banco de dados
     Usuario usuarioAtualizada = repository.save(usuario);
 
     // Retorna o DTO correspondente
     return UsuarioDTO.toDTO(usuarioAtualizada);
    }

    public void delete(UUID pkUsuario) {
        try
        {
            Optional<Usuario> optional = repository.findById(pkUsuario);
            Usuario usuario = optional.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + pkUsuario));

            repository.delete(usuario);

        } catch (DataIntegrityViolationException e)
        {
            throw new DatabaseException("Integrity violation occurred while attempting to delete entity");
        }
    }
}
