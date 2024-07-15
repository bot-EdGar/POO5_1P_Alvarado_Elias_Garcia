package com.mycompany.correo1;
import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author edgar
 */
public class Autor{
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private String codigoAutor;
    private String institucion;
    private String campoInvestigacion;
    private Articulo articulo; // UN ARTICULO TIENE UN SOLO AUTOR
private static final String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  private static String generarCodigoAutor(){
    Random rd = new Random();
    StringBuilder codigo = new StringBuilder(5);
    for(int i = 0; i< 5;i++){
      int var = rd.nextInt(caracteres.length());
      codigo.append(caracteres.charAt(var));
    }
    return codigo.toString();
}
    public Autor(String nombre, String apellido, String correoElectronico, String institucion, String campoInvestigacion){
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.institucion = institucion;
        this.campoInvestigacion = campoInvestigacion;
        this.codigoAutor = generarCodigoAutor();
        Editorial.autores.add(this);
    }

    public static Autor ingresarDatosAutor(Scanner sc){
        System.out.println("Antes de someter un artículo, debe registrar sus datos en la aplicación");
        System.out.print("Ingrese su nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese su apellido: ");
        String apellido = sc.nextLine();
        System.out.print("Ingrese su correo Electrónico: ");//TENER UN CORREO LISTO bolivaralvarado09@gmail.com
        String correoElectronico = sc.nextLine();
        System.out.print("Ingrese su institución: ");
        String institucion = sc.nextLine();
        System.out.print("Ingrese su campo de investigación: ");
        String campoInvestigacion = sc.nextLine();
        return new Autor(nombre, apellido, correoElectronico, institucion, campoInvestigacion);
    }


    // Gestion de someter articulo
    public static void someterArticulo(Scanner sc){
        Autor autor = ingresarDatosAutor(sc); //Ingresa los datos del autor


        Articulo articulo = Articulo.ingresarDatosArticulo(sc, autor); //Ingresa los datos del artículo
        autor.setArticulo(articulo); // Le asigna el articulo al autor
        Editorial.escribirArchivo("autores.txt", autor.toString());// Guarda los datos ingresados en el archivo autores.txt
        Editorial.escribirArchivo("articulos.txt",articulo.toString());// Guarda los datos ingresados en el archivo articulos.txt

        System.out.println("---------------------------------------");
        System.out.println("Iniciar Gestión de Revisión?"); 
        System.out.println("Pulse '1' si desea continuar, caso contrario pulse cualquier otro número");
        System.out.print("Ingresar opción: ");
        int opc = sc.nextInt();
        sc.nextLine();

        if (opc == 1){
            articulo.enviarArticuloARevision(); 
        }else{
            System.out.println("Volviendo al menú...");
        }
    }


    @Override
    public String toString(){
        return "Nombre: " + nombre  +
         ", Apellido: " + apellido + 
         ", Código: " + codigoAutor + 
         ", Correo Electrónico: " + correoElectronico + 
        ", Institución: "+ institucion + 
        ", Campo de investigación: " + campoInvestigacion + 
        ", Artículos: " + articulo.toString();
    }





    //getters, setters
    public String getNombre(){
        return nombre;
    }

    public String getApellido(){
        return apellido;
    }

    public void setArticulo(Articulo articulo){
        this.articulo = articulo;
    }

    public String getCorreoElectronico(){
        return correoElectronico;
    }

    public Articulo getArticulos(){
        return articulo;  
    }
    
    public String getCodigoAutor(){
        return codigoAutor;
    }

    public String getInstitucion(){
        return institucion;
    }
    public String getCampoInvestigacion(){
        return campoInvestigacion;
    }
    public void setInstitucion(String institucion){
        this.institucion = institucion;
    }
    public void setCampoInvestigacion(String campoInvestigacion){
        this.campoInvestigacion = campoInvestigacion;
    }
}