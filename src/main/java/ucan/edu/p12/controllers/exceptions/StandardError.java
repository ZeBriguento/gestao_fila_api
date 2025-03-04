/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ucan.edu.p12.controllers.exceptions;

/**
 *
 * @author controljp
 */
import java.io.Serializable;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author controljp
 */
@Getter
@Setter
public class StandardError implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public StandardError()
    {
    }
    
}