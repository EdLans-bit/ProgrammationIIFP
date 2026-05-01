/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.Administrador;
import model.Usuario;

/**
 *
 * @author UNICORDOBA
 */
public class LoginController {
    
    private ArrayList<Usuario>usuarios = new ArrayList<>();
    
    public LoginController(){
        
        //Cuenta de Usuario y Administrador
        usuarios.add(new Usuario ("jkamargo23@gmail.com", "quant1xshop", "usuario"));
        usuarios.add(new Administrador ("elans12@gmail.com", "adminShop"));
    }
    
    public String validarLogin(String user, String pass){
        for (Usuario u : usuarios){
            if (u.getUsuario().equalsIgnoreCase(user)
                && u.getClave().equalsIgnoreCase(pass)){
            
            return u.getRol();
        }
    
        }
        return "Error";
    }
    
}
