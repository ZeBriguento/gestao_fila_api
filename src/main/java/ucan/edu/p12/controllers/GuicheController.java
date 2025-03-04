/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */

package ucan.edu.p12.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ucan.edu.p12.entities.dtos.GuicheDTO;
import ucan.edu.p12.services.GuicheService;

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
@RequestMapping("/guiche")
public class GuicheController {
    @Autowired
    private GuicheService service;

    @GetMapping()
    public ResponseEntity<List<GuicheDTO>> findAll() {
        List<GuicheDTO> guiches = this.service.findAll();
        return ResponseEntity.ok(guiches);
    }

    @PostMapping
    public ResponseEntity<GuicheDTO> save(@RequestBody GuicheDTO guicheDTO) {
        guicheDTO = service.save(guicheDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{pk}").buildAndExpand(guicheDTO.getPkGuiche()).toUri();
        return ResponseEntity.created(uri).body(guicheDTO);
    }

    @GetMapping(value = "/{pkGuiche}")
    public ResponseEntity<GuicheDTO> findById(@PathVariable UUID pkGuiche) {
        GuicheDTO guicheDTO = service.findById(pkGuiche);
        return ResponseEntity.ok().body(guicheDTO);
    }

    @PutMapping(value = "/{pkGuiche}")
    public ResponseEntity<GuicheDTO> update(@PathVariable UUID pkGuiche, @RequestBody GuicheDTO guicheDTO) {
        guicheDTO = service.update(pkGuiche, guicheDTO);
        return ResponseEntity.ok().body(guicheDTO);
    }

    @DeleteMapping(value = "/{pkGuiche}")
    public ResponseEntity<Void> delete(@PathVariable UUID pkGuiche) {
        service.delete(pkGuiche);
        return ResponseEntity.noContent().build();
    }
}

