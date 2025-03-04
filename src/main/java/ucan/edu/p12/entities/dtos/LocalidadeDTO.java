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
import ucan.edu.p12.entities.Localidade;

@Data
public class LocalidadeDTO {

    private UUID pkLocalidade;
    private String nome;
    private UUID fkLocalidadePai; // Representa apenas o ID do pai para evitar referências cíclicas

   
    public static LocalidadeDTO toDTO(Localidade localidade) {
        if (localidade == null) {
            return null; // Retorna null para entradas nulas
        }
        LocalidadeDTO dto = new LocalidadeDTO();
        dto.setPkLocalidade(localidade.getPkLocalidade());
        dto.setNome(localidade.getNome());
        dto.setFkLocalidadePai(
            localidade.getFkLocalidadePai() != null ? localidade.getFkLocalidadePai().getPkLocalidade() : null
        );
        return dto;
    }

   
    public static Localidade toEntity(LocalidadeDTO dto) {
        if (dto == null) {
            return null; // Retorna null para entradas nulas
        }
        Localidade localidade = new Localidade();
        localidade.setPkLocalidade(dto.getPkLocalidade());
        localidade.setNome(dto.getNome());

        if (dto.getFkLocalidadePai() != null) {
            Localidade localidadePai = new Localidade();
            localidadePai.setPkLocalidade(dto.getFkLocalidadePai());
            localidade.setFkLocalidadePai(localidadePai);
        }

        return localidade;
    }

    public static List<LocalidadeDTO> converterListParaDTO(List<Localidade> entidades) {
        if (entidades == null) {
            return List.of(); // Retorna uma lista vazia para entradas nulas
        }
        return entidades.stream()
                        .map(LocalidadeDTO::toDTO)
                        .collect(Collectors.toList());
    }
}
