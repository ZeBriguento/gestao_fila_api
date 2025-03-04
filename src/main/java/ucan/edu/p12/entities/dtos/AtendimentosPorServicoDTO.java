/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ucan.edu.p12.entities.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 *
 * @author controljp
 */
@Data
@AllArgsConstructor
public class AtendimentosPorServicoDTO {
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String nomeServico;
    private Long totalAtendimentos;
}
