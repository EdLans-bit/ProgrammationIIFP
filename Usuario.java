/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author UNICORDOBA
 */
public class Usuario {
    
    private String usuario;
    private String clave;
    private String rol;

    public Usuario(String usuario, String clave, String rol) {
        this.usuario = usuario;
        this.clave = clave;
        this.rol = rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getClave() {
        return clave;
    }

    public String getRol() {
        return rol;
    }
}
