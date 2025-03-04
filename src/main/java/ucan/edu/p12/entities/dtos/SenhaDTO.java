/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ucan.edu.p12.entities.dtos;

/**
 *
 * @author controljp
 */

 import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
 
import lombok.Data;
import ucan.edu.p12.entities.Guiche;
import ucan.edu.p12.entities.Senha;
import ucan.edu.p12.entities.Servico;
import ucan.edu.p12.entities.Usuario;
import ucan.edu.p12.entities.enums.StatusSenhaEnum;

@Data
public class SenhaDTO {
    private UUID pkSenha;
    private String numero;
    private StatusSenhaEnum status;
    private UUID FkUsuario;
    private UUID fkServico;
    private UUID fkGuiche;
    
    public static SenhaDTO toDTO(Senha senha) {
        if (senha == null) {
            return null;
        }
        SenhaDTO dto = new SenhaDTO();
        dto.setPkSenha(senha.getPkSenha());
        dto.setNumero(senha.getNumero());
        dto.setStatus(senha.getStatus());
        dto.setFkUsuario(senha.getFkUsuario() != null ? senha.getFkUsuario().getPkUsuario() : null);
        dto.setFkServico(senha.getFkServico() != null ? senha.getFkServico().getPkServico() : null);
        dto.setFkGuiche(senha.getFkGuiche() != null ? senha.getFkGuiche().getPkGuiche() : null);
        return dto;
    }

    public static Senha toEntity(SenhaDTO dto) {
        if (dto == null) {
            return null;
        }
        Senha senha = new Senha();
        senha.setPkSenha(dto.getPkSenha());
        senha.setNumero(dto.getNumero());
        senha.setStatus(dto.getStatus());

        if (dto.getFkUsuario() != null) {
            Usuario cliente = new Usuario();
            cliente.setPkUsuario(dto.getFkUsuario());
            senha.setFkUsuario(cliente);
        }

        if (dto.getFkServico() != null) {
            Servico servico = new Servico();
            servico.setPkServico(dto.getFkServico());
            senha.setFkServico(servico);
        }

        if (dto.getFkGuiche() != null) {
            Guiche guiche = new Guiche();
            guiche.setPkGuiche(dto.getFkGuiche());
            senha.setFkGuiche(guiche);
        }

        return senha;
    }

    public static List<SenhaDTO> converterListParaDTO(List<Senha> entidades) {
        if (entidades == null) {
            return List.of();
        }
        return entidades.stream()
                        .map(SenhaDTO::toDTO)
                        .collect(Collectors.toList());
    }
}
