/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ucan.edu.p12.entities.dtos;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.Data;
import ucan.edu.p12.entities.Fila;
import ucan.edu.p12.entities.Senha;
import ucan.edu.p12.entities.Servico;
import ucan.edu.p12.entities.enums.PrioridadeServicoEnum;
/**
 *
 * @author controljp
 */
@Data
public class FilaDTO {
    private UUID pkFila;
    private UUID fkServico;
    private UUID fkSenha;
    private PrioridadeServicoEnum prioridade;
    
    public static FilaDTO toDTO(Fila fila) {
        if (fila == null) {
            return null;
        }
        FilaDTO dto = new FilaDTO();
        dto.setPkFila(fila.getPkFila());
        dto.setFkServico(fila.getFkServico() != null ? fila.getFkServico().getPkServico() : null);
        dto.setFkSenha(fila.getFkSenha() != null ? fila.getFkSenha().getPkSenha() : null);
        dto.setPrioridade(fila.getPrioridade());
        return dto;
    }

    public static Fila toEntity(FilaDTO dto) {
        if (dto == null) {
            return null;
        }
        Fila fila = new Fila();
        fila.setPkFila(dto.getPkFila());
        fila.setPrioridade(dto.getPrioridade());

        if (dto.getFkServico() != null) {
            Servico servico = new Servico();
            servico.setPkServico(dto.getFkServico());
            fila.setFkServico(servico);
        }

        if (dto.getFkSenha() != null) {
            Senha senha = new Senha();
            senha.setPkSenha(dto.getFkSenha());
            fila.setFkSenha(senha);
        }

        return fila;
    }

    public static List<FilaDTO> converterListParaDTO(List<Fila> entidades) {
        if (entidades == null) {
            return List.of();
        }
        return entidades.stream()
                        .map(FilaDTO::toDTO)
                        .collect(Collectors.toList());
    }
}
