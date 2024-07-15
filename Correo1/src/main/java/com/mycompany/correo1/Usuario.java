package com.mycompany.correo1;
/**
 *
 * @author edgar
 */
public abstract class Usuario {
    protected String nombre;
    protected String apellido;
    protected String correoElectronico;
    protected String contraseña;
    protected RolUsuario rol;
    
    public Usuario(String nombre,String apellido, String correoElectronico, String contraseña, RolUsuario rol){
        this.nombre=nombre;
        this.apellido=apellido;
        this.correoElectronico=correoElectronico;
        this.contraseña=contraseña;
        this.rol=rol;
    }
    

    public RolUsuario getRol() {
        return rol;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    
}