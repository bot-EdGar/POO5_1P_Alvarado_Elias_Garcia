package com.mycompany.correo1;

import static com.mycompany.correo1.Decision.ACEPTADO;
import static com.mycompany.correo1.Decision.RECHAZADO;
import static com.mycompany.correo1.RolUsuario.REVISOR;
import java.util.Scanner;


/**
 *
 * @author edgar
 */
public class Revisor extends Usuario{
    private String especialidad;
    private int numArtiRevisados;
    
    
    public Revisor(String nombre, String apellido, String correoELectronico, String contraseña,RolUsuario rol, String especialidad, int numArtiRevisados){
        super(nombre, apellido, correoELectronico,contraseña,rol);
        this.especialidad=especialidad;
        this.numArtiRevisados=numArtiRevisados;
        this.rol=REVISOR;
    }
    //mostrar los articulos que se le asignaron
    //proporcionar comentarios
    //decision de revision

    public void setNumArtiRevisados(int numArtiRevisados) {
        this.numArtiRevisados = numArtiRevisados;
    }

    public int getNumArtiRevisados() {
        return numArtiRevisados;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    public static Decision tomarDeicion(Scanner sc){
        int desicion=0;
        System.out.println("Elige la decision a tomar: ");
        System.out.println("1.-Rechazar");
        System.out.println("2.-Aceptar");
        desicion=sc.nextInt();
        if (desicion==1){
            return RECHAZADO;
        }if (desicion==2){
            return ACEPTADO;
        }else{
            return RECHAZADO;
        }
    }
        public static String comentarArticulo(Scanner sc){
            
            System.out.println("Escribe un comentario del articulo: ");
            String comentario=sc.nextLine();
            return comentario;
        }
}
