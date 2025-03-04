/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */

package ucan.edu.p12.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ucan.edu.p12.entities.dtos.TipoUsuarioDTO;
import ucan.edu.p12.services.TipoUsuarioService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 *
 * @author controljp
 */
@RestController
@RequestMapping("/tipoUsuario")
public class TipoUsuarioController {
    
    @Autowired
    TipoUsuarioService service;

    @GetMapping()
    public ResponseEntity<List<TipoUsuarioDTO>> findAll() {
        List<TipoUsuarioDTO> tipoUsuarios = this.service.findAll();
        return ResponseEntity.ok(tipoUsuarios);
    }

    @PostMapping
    public ResponseEntity<TipoUsuarioDTO> save(@RequestBody TipoUsuarioDTO tipoUsuarioDTO)
    {
        tipoUsuarioDTO = service.save(tipoUsuarioDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{pk}").buildAndExpand(tipoUsuarioDTO.getPkTipoUsuario()).toUri();
        return ResponseEntity.created(uri).body(tipoUsuarioDTO);
    }


     @GetMapping(value = "/{pkTipoUsuario}")
    public ResponseEntity<TipoUsuarioDTO> findById(@PathVariable UUID pkTipoUsuario)
    {
        TipoUsuarioDTO tipoUsuarioDTO = service.findById(pkTipoUsuario);
        return ResponseEntity.ok().body(tipoUsuarioDTO);
    }

    @PutMapping(value = "/{pkTipoUsuario}")
    public ResponseEntity<TipoUsuarioDTO> update(@PathVariable UUID pkTipoUsuario, @RequestBody TipoUsuarioDTO tipoUsuarioDTO)
    {
        tipoUsuarioDTO = service.update(pkTipoUsuario, tipoUsuarioDTO);
        return ResponseEntity.ok().body(tipoUsuarioDTO);
    }

    @DeleteMapping(value = "/{pkTipoUsuario}")
    public ResponseEntity<TipoUsuarioDTO> delete(@PathVariable UUID pkTipoUsuario)
    {
        service.delete(pkTipoUsuario);
        return ResponseEntity.noContent().build();
    }
}
