/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ucan.edu.p12.entities.dtos;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
 
import lombok.Data;
import ucan.edu.p12.entities.Servico;
import ucan.edu.p12.entities.enums.PrioridadeServicoEnum;

/**
 *
 * @author controljp
 */

 @Data
 public class ServicoDTO {
     private UUID pkServico;
     private String nome;
     private String descricao;
     private PrioridadeServicoEnum prioridade;
     private Boolean ativo;
     
     public static ServicoDTO toDTO(Servico servico) {
         if (servico == null) {
             return null;
         }
         ServicoDTO dto = new ServicoDTO();
         dto.setPkServico(servico.getPkServico());
         dto.setNome(servico.getNome());
         dto.setDescricao(servico.getDescricao());
         dto.setPrioridade(servico.getPrioridade());
         dto.setAtivo(servico.getAtivo());
         return dto;
     }
 
     public static Servico toEntity(ServicoDTO dto) {
         if (dto == null) {
             return null;
         }
         Servico servico = new Servico();
         servico.setPkServico(dto.getPkServico());
         servico.setNome(dto.getNome());
         servico.setDescricao(dto.getDescricao());
         servico.setPrioridade(dto.getPrioridade());
         servico.setAtivo(dto.getAtivo());
         return servico;
     }
 
     public static List<ServicoDTO> converterListParaDTO(List<Servico> entidades) {
         if (entidades == null) {
             return List.of();
         }
         return entidades.stream()
                         .map(ServicoDTO::toDTO)
                         .collect(Collectors.toList());
     }
 }
 
