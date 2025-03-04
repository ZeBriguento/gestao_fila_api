/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */

package ucan.edu.p12.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ucan.edu.p12.entities.dtos.SexoDTO;
import ucan.edu.p12.services.SexoService;

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
@RequestMapping("/sexo")
public class SexoController {
    
    @Autowired
    SexoService service;

    @GetMapping()
    public ResponseEntity<List<SexoDTO>> findAll() {
        List<SexoDTO> sexos = this.service.findAll();
        return ResponseEntity.ok(sexos);
    }

    @PostMapping
    public ResponseEntity<SexoDTO> save(@RequestBody SexoDTO sexoDTO)
    {
        sexoDTO = service.save(sexoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{pk}").buildAndExpand(sexoDTO.getPkSexo()).toUri();
        return ResponseEntity.created(uri).body(sexoDTO);
    }


     @GetMapping(value = "/{pkSexo}")
    public ResponseEntity<SexoDTO> findById(@PathVariable UUID pkSexo)
    {
        SexoDTO sexoDTO = service.findById(pkSexo);
        return ResponseEntity.ok().body(sexoDTO);
    }

    @PutMapping(value = "/{pkSexo}")
    public ResponseEntity<SexoDTO> update(@PathVariable UUID pkSexo, @RequestBody SexoDTO sexoDTO)
    {
        sexoDTO = service.update(pkSexo, sexoDTO);
        return ResponseEntity.ok().body(sexoDTO);
    }

    @DeleteMapping(value = "/{pkSexo}")
    public ResponseEntity<SexoDTO> delete(@PathVariable UUID pkSexo)
    {
        service.delete(pkSexo);
        return ResponseEntity.noContent().build();
    }
}
