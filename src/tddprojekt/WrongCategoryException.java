/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tddprojekt;

/**
 *
 * @author Wojtek
 */
public class WrongCategoryException extends RuntimeException {
    
    public WrongCategoryException (String message) {
        super(message);
    }
}

