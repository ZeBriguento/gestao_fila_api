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

import jakarta.persistence.EntityNotFoundException;
import ucan.edu.p12.entities.TipoUsuario;
import ucan.edu.p12.entities.dtos.TipoUsuarioDTO;
import ucan.edu.p12.repositories.TipoUsuarioRepository;
import ucan.edu.p12.services.exceptions.ResourceNotFoundException;
import ucan.edu.p12.services.exceptions.DatabaseException;

/**
 *
 * @author controljp
 */
@Service
public class TipoUsuarioService {

    @Autowired
    private TipoUsuarioRepository repository;

    public List<TipoUsuarioDTO> findAll() {
        List<TipoUsuario> tipoUsuarios = repository.findAll();
        return TipoUsuarioDTO.converterListParaDTO(tipoUsuarios);
    }

    public TipoUsuarioDTO save(TipoUsuarioDTO tipoUsuarioDTO) {
        TipoUsuario tipoUsuario = TipoUsuarioDTO.toEntity(tipoUsuarioDTO);
        TipoUsuario tipoUsuarioSalvo = repository.save(tipoUsuario);
        return TipoUsuarioDTO.toDTO(tipoUsuarioSalvo);
    }

    public TipoUsuarioDTO findById(UUID pkTipoUsuario) {
        Optional<TipoUsuario> optional = repository.findById(pkTipoUsuario);
        TipoUsuario tipoUsuario = optional.orElseThrow(() -> new ResourceNotFoundException("Id not found"));
        return TipoUsuarioDTO.toDTO(tipoUsuario);
    }


    public TipoUsuarioDTO update(UUID pkTipoUsuario, TipoUsuarioDTO tipoUsuarioDTO) {
        try {
            TipoUsuario tipoUsuario = repository.getReferenceById(pkTipoUsuario);
            tipoUsuario.setDescricao(tipoUsuarioDTO.getDescricao());
            tipoUsuario = repository.save(tipoUsuario);
            return TipoUsuarioDTO.toDTO(tipoUsuario);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found: " + pkTipoUsuario);
        }
    }

    public void delete(UUID pkTipoUsuario) {
         try
        {
            Optional<TipoUsuario> optional = repository.findById(pkTipoUsuario);
            TipoUsuario tipoUsuario = optional.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + pkTipoUsuario));

            repository.delete(tipoUsuario);

        } catch (DataIntegrityViolationException e)
        {
            throw new DatabaseException("Integrity violation occurred while attempting to delete entity");
        }
    }

 
}
