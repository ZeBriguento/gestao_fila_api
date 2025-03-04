/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ucan.edu.p12.entities.dtos;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.Data;
import ucan.edu.p12.entities.TipoUsuario;
import ucan.edu.p12.entities.Usuario;

/**
 *
 * @author controljp
 */
@Data
public class UsuarioDTO {
    private UUID pkUsuario;
    private UUID fkTipoUsuario; // Representa apenas o ID da tipoUsuario para evitar referências cíclicas
    private String username;
    private String password;
 

      public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null; // Retorna null para entradas nulas
        }
        UsuarioDTO dto = new UsuarioDTO();
        dto.setPkUsuario(usuario.getPkUsuario());
        dto.setFkTipoUsuario(usuario.getFkTipoUsuario() != null ? usuario.getFkTipoUsuario().getPkTipoUsuario() : null);
        dto.setUsername(usuario.getUsername());
        dto.setPassword(usuario.getPassword());
      
        return dto;
    }

     public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            return null; // Retorna null para entradas nulas
        }
        Usuario usuario = new Usuario();
        usuario.setPkUsuario(dto.getPkUsuario());
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(dto.getPassword());

        if (dto.getFkTipoUsuario() != null) {
            TipoUsuario tipoUsuario = new TipoUsuario();
            tipoUsuario.setPkTipoUsuario(dto.getFkTipoUsuario());
            usuario.setFkTipoUsuario(tipoUsuario);
        }

        return usuario;
    }

     public static List<UsuarioDTO> converterListParaDTO(List<Usuario> entidades) {
        if (entidades == null) {
            return List.of(); // Retorna uma lista vazia para entradas nulas
        }
        return entidades.stream()
                        .map(UsuarioDTO::toDTO)
                        .collect(Collectors.toList());
    }
}
