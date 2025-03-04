/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */

package ucan.edu.p12.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ucan.edu.p12.entities.dtos.UsuarioDTO;
import ucan.edu.p12.services.UsuarioService;

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
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author controljp
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @GetMapping()
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        List<UsuarioDTO> usuarios = this.service.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> save(@RequestBody UsuarioDTO usuarioDTO)
    {
        usuarioDTO = service.save(usuarioDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{pk}").buildAndExpand(usuarioDTO.getPkUsuario()).toUri();
        return ResponseEntity.created(uri).body(usuarioDTO);
    }

      @GetMapping(value = "/{pkUsuario}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable UUID pkUsuario)
    {
        UsuarioDTO usuarioDTO = service.findById(pkUsuario);
        return ResponseEntity.ok().body(usuarioDTO);
    }

     @PutMapping(value = "/{pkUsuario}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable UUID pkUsuario, @RequestBody UsuarioDTO usuarioDTO)
    {
        usuarioDTO = service.update(pkUsuario, usuarioDTO);
        return ResponseEntity.ok().body(usuarioDTO);
    }

    @DeleteMapping(value = "/{pkUsuario}")
    public ResponseEntity<UsuarioDTO> delete(@PathVariable UUID pkUsuario)
    {
        service.delete(pkUsuario);
        return ResponseEntity.noContent().build();
    }
}
