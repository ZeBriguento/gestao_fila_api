/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ucan.edu.p12.entities.dtos;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.Data;
import ucan.edu.p12.entities.Localidade;
import ucan.edu.p12.entities.Pessoa;
import ucan.edu.p12.entities.Sexo;
import ucan.edu.p12.entities.Usuario;

/**
 *
 * @author controljp
 */
@Data
public class PessoaDTO {

    private UUID pkPessoa;
    private String primeiroNome;
    private String ultimoNome;
    private String numeroIdentificacao;
    private String email;
    private UUID fkSexo; // Representa apenas o ID do sexo para evitar referências cíclicas
    private UUID fkUsuario; // Representa apenas o ID do usuario para evitar referências cíclicas
    private UUID fkLocalNascimento; // Representa apenas o ID do local de nascimento

    public static PessoaDTO toDTO(Pessoa pessoa) {
        if (pessoa == null) {
            return null; // Retorna null para entradas nulas
        }
        PessoaDTO dto = new PessoaDTO();
        dto.setPkPessoa(pessoa.getPkPessoa());
        dto.setPrimeiroNome(pessoa.getPrimeiroNome());
        dto.setUltimoNome(pessoa.getUltimoNome());
        dto.setNumeroIdentificacao(pessoa.getNumeroIdentificacao());
        dto.setEmail(pessoa.getEmail());
        dto.setFkSexo(pessoa.getFkSexo() != null ? pessoa.getFkSexo().getPkSexo() : null);
        dto.setFkUsuario(pessoa.getFkUsuario() != null ? pessoa.getFkUsuario().getPkUsuario() : null);
        dto.setFkLocalNascimento(pessoa.getFkLocalNascimento() != null ? pessoa.getFkLocalNascimento().getPkLocalidade() : null);
        return dto;
    }

    public static Pessoa toEntity(PessoaDTO dto) {
        if (dto == null) {
            return null; // Retorna null para entradas nulas
        }
        Pessoa pessoa = new Pessoa();
        pessoa.setPkPessoa(dto.getPkPessoa());
        pessoa.setPrimeiroNome(dto.getPrimeiroNome());
        pessoa.setUltimoNome(dto.getUltimoNome());
        pessoa.setNumeroIdentificacao(dto.getNumeroIdentificacao());
        pessoa.setEmail(dto.getEmail());

        if (dto.getFkSexo() != null) {
            Sexo sexo = new Sexo();
            sexo.setPkSexo(dto.getFkSexo());
            pessoa.setFkSexo(sexo);
        }

        if (dto.getFkLocalNascimento() != null) {
            Localidade localidade = new Localidade();
            localidade.setPkLocalidade(dto.getFkLocalNascimento());
            pessoa.setFkLocalNascimento(localidade);
        }

        if (dto.getFkUsuario() != null) {
            Usuario usuario = new Usuario();
            usuario.setPkUsuario(dto.getFkUsuario());
            pessoa.setFkUsuario(usuario);
        }

        return pessoa;
    }

    public static List<PessoaDTO> converterListParaDTO(List<Pessoa> entidades) {
        if (entidades == null) {
            return List.of(); // Retorna uma lista vazia para entradas nulas
        }
        return entidades.stream()
                        .map(PessoaDTO::toDTO)
                        .collect(Collectors.toList());
    }
}


