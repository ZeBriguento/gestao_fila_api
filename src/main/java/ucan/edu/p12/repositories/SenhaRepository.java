/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */

package ucan.edu.p12.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ucan.edu.p12.entities.Fila;
import ucan.edu.p12.entities.Senha;

/**
 *
 * @author controljp
 */
public interface SenhaRepository extends JpaRepository<Senha, UUID> {

        long countByFkServicoPkServico(UUID pkServico);

        Optional<Senha> findByNumero(String numeroSenha);

        @Query("SELECT f FROM Fila f JOIN f.fkSenha s " +
                        "WHERE s.status = 'PENDENTE' " +
                        "ORDER BY f.prioridade DESC, s.numero ASC")
        List<Fila> buscarProximaSenhaNaFila();

        @Query("SELECT f FROM Fila f JOIN f.fkSenha s " +
                        "WHERE s.status = 'PENDENTE' " +
                        "ORDER BY s.numero ASC")
        List<Fila> listarSenhasPendentes();

        @Query("""
                            SELECT
                                s.fkServico.nome,
                                COUNT(DISTINCT s.fkUsuario.pkUsuario)
                            FROM Senha s
                            WHERE s.status = 'CONCLUIDO'
                            AND s.dataFinalizacao BETWEEN :dataInicio AND :dataFim
                            GROUP BY s.fkServico.nome
                            ORDER BY s.fkServico.nome ASC
                        """)
        List<Object[]> contarAtendimentosPorServicoRaw(
                        @Param("dataInicio") LocalDateTime dataInicio,
                        @Param("dataFim") LocalDateTime dataFim);

}
