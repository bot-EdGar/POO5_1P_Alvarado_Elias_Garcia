package com.mycompany.correo1;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
/**
 *
 * @author edgar
 */
public class Articulo {
    
    private String codigoUnicoArticulo;
    private String titulo;
    private String resumen;
    private String contenido;
    private ArrayList<String> palabraClaves;
    private Autor autor;
    private EstadoArticulo estadoArticulo;

    
    

    public Articulo(String codigoUnicoArticulo, String titulo, String resumen, String contenido, ArrayList<String> palabraClaves, Autor autor, EstadoArticulo estadoArticulo) {
        this.codigoUnicoArticulo = codigoUnicoArticulo;
        this.titulo = titulo;
        this.resumen = resumen;
        this.contenido = contenido;
        this.palabraClaves = palabraClaves;
        this.autor=autor;
        this.estadoArticulo=estadoArticulo;
    }
    
    public static Articulo ingresarDatosArti(Scanner sc, Autor autor){
        
        System.out.println("Ingrese los datos de su artículo-------------- ");
        System.out.println("-Ingrese el título: ");
        String titulo=sc.nextLine();
        System.out.println("-Ingrese el resumen: ");
        String resumen=sc.nextLine();
        System.out.println("-Ingrese el contenido: ");
        String contenido=sc.nextLine();
        System.out.println("-Ingrese la cantidad de palabras claves: ");
        int cantPala=sc.nextInt();
        sc.nextLine();
        ArrayList<String> palabraClaves=new ArrayList<>();
        for (int i=0; i<cantPala; i++){
            System.out.println("-Ingrese la palabra clave N(" +(i+1)+")");
            String palaClave=sc.nextLine();
            palabraClaves.add(palaClave);
        }
        String codigoUnicoArticulo=Sistema.generarCodigo();
        EstadoArticulo estadoArticulo=EstadoArticulo.INGRESADO;
        Articulo articulo=new Articulo(codigoUnicoArticulo, titulo, resumen, contenido, palabraClaves, autor,estadoArticulo);
        guardarArticulo(articulo);
        return new Articulo(codigoUnicoArticulo, titulo, resumen, contenido, palabraClaves, autor,estadoArticulo);
    }
    
    
    
    @Override
    public String toString() {
        return """
               Articulo------------------------
               Titulo: """+getTitulo()+"\n***Codigo: "+ getCodigoUnicoArticulo()+"\n***Resumen: "+ getResumen()+ "\n***Palabras claves: "+ getPalabraClaves()+"\n***Contenido: "+ getContenido();
    }
    
    
    

    public String getCodigoUnicoArticulo() {
        return codigoUnicoArticulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getResumen() {
        return resumen;
    }

    public String getContenido() {
        return contenido;
    }

    public ArrayList<String> getPalabraClaves() {
        return palabraClaves;
    }

    public Autor getAutor() {
        return autor;
    }
    
    
    

    public String toString1(){
        return getCodigoUnicoArticulo()+","+getTitulo()+","+getResumen()+","+getContenido()+","+getPalabraClaves()+","+getAutor()+","+getEstadoArticulo();
    }
    
    
    


    public EstadoArticulo getEstadoArticulo() {
        return estadoArticulo;
    }
    
    
    


    public static void guardarArticulo(Articulo articulo){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Articulos.txt", true))) {
            bw.write(articulo.toString1());
            bw.newLine();
            System.out.println("Artículo sometido exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
