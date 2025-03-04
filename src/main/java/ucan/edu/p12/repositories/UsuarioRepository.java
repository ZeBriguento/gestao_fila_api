/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */

package ucan.edu.p12.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ucan.edu.p12.entities.Usuario;

/**
 *
 * @author controljp
 */
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

}
