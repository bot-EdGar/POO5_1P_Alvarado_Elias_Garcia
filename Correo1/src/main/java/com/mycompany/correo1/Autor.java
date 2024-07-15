package com.mycompany.correo1;
import static com.mycompany.correo1.RolUsuario.AUTOR;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author edgar
 */
public class Autor extends Usuario{
    private String institucion;
    private String campoInvestigacion;
    private String codigoUnico;
    
    
    
    public Autor(String nombre,String apellido, String correoElectronico, String contraseña, RolUsuario rol, String institucion, String campoInvestigacion, String codigoUnico){
        super(nombre,apellido,correoElectronico,contraseña,rol);
        this.rol=AUTOR;
        this.institucion=institucion;
        this.campoInvestigacion=campoInvestigacion;
        this.codigoUnico=Sistema.generarCodigo();
        
        
    } 
    
    
    public String toString1() {
        return "Autor:" + getNombre() + getApellido() +'\n'+"Campo de investigacion: "+getCampoInvestigacion();
    }
    
    public String toString2() {
        return getNombre() + ","+ getApellido() +","+getCorreoElectronico()+","+getContraseña()+","+getRol()+","+getInstitucion()+","+getCampoInvestigacion()+","+getCodigoUnico();
    }
    
    @Override
    public String toString(){
        return getNombre();
    }
    public static Autor ingresarDatosAu(Scanner sc){
        System.out.println("Ingrese los datos correspondientes: ");
        System.out.println("Ingrese su nombre: ");
        String nombre=sc.nextLine();
        System.out.println("Ingrese su apellido: ");
        String apellido=sc.nextLine();
        System.out.println("Ingrese su correo Electronico: ");
        String correoElectronico=sc.nextLine();
        System.out.println("Ingrese su Contraseña: ");
        String contraseña=sc.nextLine();
        System.out.println("Ingrese su institucion: ");
        String institucion=sc.nextLine();
        System.out.println("Ingrese su campo de investigacion: ");
        String campoInvestigacion=sc.nextLine();
        String codigoUnico=Sistema.generarCodigo();
        RolUsuario rol=RolUsuario.AUTOR;
        Autor autor=new Autor(nombre, apellido,correoElectronico,contraseña,rol,institucion,campoInvestigacion,codigoUnico);
        guardarAutor(autor);
        return new Autor(nombre, apellido,correoElectronico,contraseña,rol,institucion,campoInvestigacion,codigoUnico);
        
    }
    
    
    public static String someterArticulo(Scanner sc){
        Autor autor = ingresarDatosAu(sc);
        Articulo articulo=Articulo.ingresarDatosArti(sc,autor);
        
        return autor.toString()+articulo.toString();
        

    }

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public String getInstitucion() {
        return institucion;
    }

    public String getCampoInvestigacion() {
        return campoInvestigacion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public void setCampoInvestigacion(String campoInvestigacion) {
        this.campoInvestigacion = campoInvestigacion;
    }

    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }
    
     
    public static void guardarAutor(Autor autor){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Usuarios.txt", true))) {
            bw.write(autor.toString2());
            bw.newLine();
            System.out.println("Se registro correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}