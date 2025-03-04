/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ucan.edu.p12.services.exceptions;

/**
 *
 * @author controljp
 */
public class DatabaseException extends RuntimeException
{

    private static final long serialVersionUID = 1L;

    public DatabaseException(String message)
    {
        super(message);
    }
}
