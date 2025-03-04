/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */

package ucan.edu.p12.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ucan.edu.p12.entities.Guiche;
import ucan.edu.p12.entities.Usuario;
import ucan.edu.p12.entities.dtos.GuicheDTO;
import ucan.edu.p12.repositories.GuicheRepository;
import ucan.edu.p12.repositories.UsuarioRepository;
import ucan.edu.p12.services.exceptions.DatabaseException;
import ucan.edu.p12.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;


/**
 *
 * @author controljp
 */
@Service
public class GuicheService {
    @Autowired
    private GuicheRepository repository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<GuicheDTO> findAll() {
        List<Guiche> guiches = repository.findAll();
        return GuicheDTO.converterListParaDTO(guiches);
    }

    public GuicheDTO save(GuicheDTO guicheDTO) {
        Usuario usuario = validateAndGetUsuario(guicheDTO.getFkUsuario());

        Guiche guiche = GuicheDTO.toEntity(guicheDTO);
        guiche.setFkUsuario(usuario);
        
        Guiche guicheSalvo = repository.save(guiche);
        return GuicheDTO.toDTO(guicheSalvo);
    }

    private Usuario validateAndGetUsuario(UUID fkUsuario) {
        if (fkUsuario != null) {
            return usuarioRepository.findById(fkUsuario)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario not found for ID: " + fkUsuario));
        }
        return null;
    }

    public GuicheDTO findById(UUID pkGuiche) {
        Guiche guiche = repository.findById(pkGuiche)
                .orElseThrow(() -> new ResourceNotFoundException("Guiche not found for ID: " + pkGuiche));
        return GuicheDTO.toDTO(guiche);
    }

    public GuicheDTO update(UUID pkGuiche, GuicheDTO guicheDTO) {
        Guiche guiche = repository.findById(pkGuiche)
                .orElseThrow(() -> new ResourceNotFoundException("Guiche not found for ID: " + pkGuiche));

        Usuario usuario = validateAndGetUsuario(guicheDTO.getFkUsuario());
        
        guiche.setNome(guicheDTO.getNome());
        guiche.setStatus(guicheDTO.getStatus());
        guiche.setFkUsuario(usuario);
        
        Guiche guicheAtualizado = repository.save(guiche);
        return GuicheDTO.toDTO(guicheAtualizado);
    }

    public void delete(UUID pkGuiche) {
        try {
            Guiche guiche = repository.findById(pkGuiche)
                    .orElseThrow(() -> new ResourceNotFoundException("Guiche not found for ID: " + pkGuiche));
            repository.delete(guiche);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation occurred while attempting to delete entity");
        }
    }
}

