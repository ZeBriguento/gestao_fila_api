/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */

package ucan.edu.p12.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ucan.edu.p12.entities.Localidade;

/**
 *
 * @author controljp
 */
public interface LocalidadeRepository extends JpaRepository<Localidade, UUID> {

    public List<Localidade> findByfkLocalidadePaiIsNull();
	
    @Query("SELECT l FROM Localidade l WHERE l.fkLocalidadePai.pkLocalidade = :pkLocalidadePai")
    public  List<Localidade> findAllByFkLocalidadePai(UUID pkLocalidadePai);

}
