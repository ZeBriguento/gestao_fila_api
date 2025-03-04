/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */

package ucan.edu.p12.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ucan.edu.p12.entities.Fila;

/**
 *
 * @author controljp
 */
public interface FilaRepository extends JpaRepository<Fila, UUID> {

       /*
        * @Query("SELECT COUNT(f) FROM Fila f JOIN f.fkSenha s " +
        * "WHERE f.fkServico.pkServico = :servicoId " +
        * "AND s.status = 'PENDENTE' " +
        * "AND s.numero < :numeroSenha")
        * int countByFkServicoAndSenhaMenorQue(@Param("servicoId") UUID servicoId,
        * 
        * @Param("numeroSenha") String numeroSenha);
        */

       @Query("SELECT COUNT(f) FROM Fila f JOIN f.fkSenha s " +
                     "WHERE f.fkServico.pkServico = :servicoId " +
                     "AND s.status <> 'CONCLUIDO' " +
                     "AND s.status <> 'CANCELADO' " +
                     "AND s.numero < :numeroSenha")

       int findPosicaoNaFila(@Param("servicoId") UUID servicoId,
                     @Param("numeroSenha") String numeroSenha);

      

       @Query("SELECT COUNT(f) FROM Fila f JOIN f.fkSenha s " +
                     "WHERE f.fkServico.pkServico = :servicoId " +
                     "AND s.status NOT IN ('CONCLUIDO', 'CANCELADO') " +
                     "AND s.numero < :numeroSenha") // Comparação direta sem CAST
       int findPosicaoNaFila2(@Param("servicoId") UUID servicoId,
                     @Param("numeroSenha") String numeroSenha);

}
