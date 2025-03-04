/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */

package ucan.edu.p12.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ucan.edu.p12.entities.dtos.AtendimentosPorServicoDTO;
import ucan.edu.p12.entities.dtos.SenhaDTO;
import ucan.edu.p12.entities.dtos.SenhaRequestDTO;
import ucan.edu.p12.services.SenhaService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author controljp
 */
@RestController
@RequestMapping("/senha")
public class SenhaController {

    @Autowired
    private SenhaService service;

    @GetMapping("/relatorio")
    public ResponseEntity<List<AtendimentosPorServicoDTO>> contarAtendimentos(
        @RequestParam("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
        @RequestParam("dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {

        List<AtendimentosPorServicoDTO> resposta = service.contarAtendimentosPorServico(dataInicio, dataFim);

        return ResponseEntity.ok(resposta);
    }

    @GetMapping()
    public ResponseEntity<List<SenhaDTO>> findAll() {
        List<SenhaDTO> senhas = this.service.findAll();
        return ResponseEntity.ok(senhas);
    }

    @PostMapping
    public ResponseEntity<SenhaDTO> save(@RequestBody SenhaDTO senhaDTO) {
        senhaDTO = service.save(senhaDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{pk}")
                .buildAndExpand(senhaDTO.getPkSenha())
                .toUri();
        return ResponseEntity.created(uri).body(senhaDTO);
    }

    @GetMapping(value = "/{pkSenha}")
    public ResponseEntity<SenhaDTO> findById(@PathVariable UUID pkSenha) {
        SenhaDTO senhaDTO = service.findById(pkSenha);
        return ResponseEntity.ok().body(senhaDTO);
    }

    @PutMapping(value = "/{pkSenha}")
    public ResponseEntity<SenhaDTO> update(@PathVariable UUID pkSenha, @RequestBody SenhaDTO senhaDTO) {
        senhaDTO = service.update(pkSenha, senhaDTO);
        return ResponseEntity.ok().body(senhaDTO);
    }

    @DeleteMapping(value = "/{pkSenha}")
    public ResponseEntity<Void> delete(@PathVariable UUID pkSenha) {
        service.delete(pkSenha);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/chamar-proxima")
    public ResponseEntity<SenhaDTO> chamarProximaSenha(@RequestParam UUID fkGuiche) {
        SenhaDTO senhaDTO = service.chamarProximaSenha(fkGuiche);
        return ResponseEntity.ok(senhaDTO);
    }

    @PostMapping("/gerar")
    public ResponseEntity<SenhaDTO> gerarSenha(@RequestBody SenhaRequestDTO requestDTO) {
        SenhaDTO senhaDTO = service.gerarSenha(requestDTO.getFkServico(), requestDTO.getFkUsuario());
        return ResponseEntity.ok(senhaDTO);
    }

    @GetMapping("/posicao/{numeroSenha}")
    public ResponseEntity<Integer> obterPosicaoNaFila(@PathVariable String numeroSenha) {
        int posicao = service.obterPosicaoNaFila(numeroSenha);
        return ResponseEntity.ok(posicao);
    }

    @PutMapping("/concluir/{pkSenha}")
    public ResponseEntity<SenhaDTO> concluirAtendimento(
            @PathVariable UUID pkSenha,
            @RequestParam UUID fkGuiche) {

        SenhaDTO senhaDTO = service.concluirAtendimento(pkSenha, fkGuiche);
        return ResponseEntity.ok(senhaDTO);
    }

    @PutMapping("/cancelar/{pkSenha}")
    public ResponseEntity<SenhaDTO> cancelarSenha(
            @PathVariable UUID pkSenha,
            @RequestParam UUID fkGuiche) {

        SenhaDTO senhaDTO = service.cancelarSenha(pkSenha, fkGuiche);
        return ResponseEntity.ok(senhaDTO);
    }

    @GetMapping("/senhas/pendentes")
    public ResponseEntity<List<SenhaDTO>> listarSenhasPendentes() {
        return ResponseEntity.ok(service.listarSenhasPendentes());
    }

}
