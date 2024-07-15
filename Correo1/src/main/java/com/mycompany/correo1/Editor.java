package com.mycompany.correo1;

import static com.mycompany.correo1.Decision.ACEPTADO;
import static com.mycompany.correo1.Decision.RECHAZADO;
import static com.mycompany.correo1.RolUsuario.EDITOR;
import java.util.Scanner;



/**
 *
 * @author edgar
 */
public class Editor extends Usuario {
    private String nombreJournal;
    
    
    
    public Editor(String nombre,String apellido, String correoElectronico, String contraseña,RolUsuario rol, String nombreJournal){
        super(nombre, apellido, correoElectronico,contraseña,rol);
        this.nombreJournal=nombreJournal;
        this.rol=EDITOR;
    }
    
    //poner el codigo y mostrar los comentarios de los dos revisores
    //cambiar de estado del articu lo en rechazado o en aceptado
    
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

    public String getNombreJournal() {
        return nombreJournal;
    }

    public void setNombreJournal(String nombreJournal) {
        this.nombreJournal = nombreJournal;
    }
    
}