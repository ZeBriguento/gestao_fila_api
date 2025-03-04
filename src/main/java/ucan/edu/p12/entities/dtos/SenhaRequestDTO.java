/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ucan.edu.p12.entities.dtos;

import java.util.UUID;

import lombok.Data;

/**
 *
 * @author controljp
 */
@Data
public class SenhaRequestDTO {
    private UUID fkServico;
    private UUID fkUsuario;
}
