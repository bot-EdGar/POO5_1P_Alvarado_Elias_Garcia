package com.mycompany.correo1;
import java.util.Random;

import java.util.Scanner;
import java.util.ArrayList;
/**
 *
 * @author edgar
 */
public class Articulo{
  private String titulo;
  private String contenido;
  private String resumen;
  private ArrayList<String> palabrasClaves;
  private String codigoArti;
  private Autor autor; // COMPROBAR SI SE USA, EN CASO QUE NO, ELIMINARLA
  private EstadoArticulo estado;
  private static final String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  private static String generarCodigoArticulo(){
    Random rd = new Random();
    StringBuilder codigo = new StringBuilder(5);
    for(int i = 0; i< 5;i++){
      int var = rd.nextInt(caracteres.length());
      codigo.append(caracteres.charAt(var));
    }
    return codigo.toString();
}

  public Articulo(Autor autor,String titulo, String contenido, ArrayList<String> palabrasClaves, EstadoArticulo estado, String resumen){
    this.titulo = titulo;
    this.contenido = contenido;
    this.resumen = resumen;
    this.codigoArti = generarCodigoArticulo();
    this.palabrasClaves = palabrasClaves;
    this.estado = estado;
    this.autor = autor;
    Editorial.articulos.add(this);
  }

  public static Articulo ingresarDatosArticulo(Scanner sc, Autor autor){
    System.out.println("------------------------------------");
    System.out.println(autor.getNombre() + " " + autor.getApellido() + ", Ingrese los datos de su artículo");
    System.out.println("------------------------------------");
    System.out.print("Ingrese el título del artículo: ");
    String titulo = sc.nextLine();
    System.out.print("Ingrese el contenido del artículo: ");
    String contenido = sc.nextLine();
    System.out.print("Ingrese la cantidad de palabras clave que va ingresar: ");
    int cantP = sc.nextInt();
    sc.nextLine();
    ArrayList<String> palabrasClaves = new ArrayList<>();
    for(int i = 1; i < cantP + 1;i++){
      System.out.println("Ingrese la palabra clave N(" +i+")");
      String pClave = sc.nextLine();
      palabrasClaves.add(pClave);
    }
    System.out.print("Ingrese resumen del artículo: ");
    String resumen = sc.nextLine();

    return new Articulo(autor,titulo,contenido,palabrasClaves,EstadoArticulo.INGRESADO,resumen);
  }

  @Override
  public String toString(){
    return "Autor: "+ autor.getNombre() + " " + autor.getApellido() + 
    ", Título: " + titulo + 
    ", Código: "+ codigoArti + 
    ", Contenido: " + contenido +  
    ", Palabras Claves: " + palabrasClaves.toString() + 
    ", Estado artículo: " + estado + 
    ", Resumen: "  + resumen;
  }

  public void enviarArticuloARevision(){//AQUI INICIA LA GESTION DE REVISION
    System.out.println("---------------------------------------");
    setEstado(EstadoArticulo.EN_REVISION);
    //POR HACER:
    asignarRevisores(this); //Asignar automáticamente a dos revisores de la lista de revisores 
    //Enviar un correo a los revisores indicando que se les ha asignado tal artículo - PENDIENTE
  } 

  private void asignarRevisores(Articulo articulo){
    Random rd = new Random();
    if (Editorial.revisores.size() < 2){
      System.out.println("No hay suficientes revisores");
    }else{
      int r1 = rd.nextInt(Editorial.revisores.size());
      int r2;
      do{
        r2 = rd.nextInt(Editorial.revisores.size());
      } while(r2 == r1);
  
      Revisor revisor1 = Editorial.revisores.get(r1);
      revisor1.setArticulo(articulo);
      revisor1.enviarCorreo(revisor1.getCorreoElectronico(), "ASIGNACION DE ARTICULO", articulo.toString());

      Revisor revisor2 = Editorial.revisores.get(r2);
      revisor2.setArticulo(articulo);
      revisor2.enviarCorreo(revisor1.getCorreoElectronico(), "ASIGNACION DE ARTICULO", articulo.toString());
     
      //Editorial.enviarCorreo(revisor1.getCorreoElectronico(),"Asignación de artículo" , articulo.toString());
      //Editorial.enviarCorreo(revisor2.getCorreoElectronico(),"Asignación de artículo" , articulo.toString());


      System.out.println("Revisores asignados al artículo: " + articulo.getTitulo());
      System.out.println(" * " + revisor1.getNombre() + " " + revisor1.getApellido());
      System.out.println(" * " + revisor2.getNombre() + " " + revisor1.getApellido());

      //Aqui se crea una revision del articulo para que puedan acceder los revisores y los editores para proporcionar comentarios y su decisión
      Revision.agregarRevision(revisor1, revisor2,articulo);
    }

  }

  public String getTitulo(){
    return titulo;
  }


  public void setTitulo(String titulo){
    this.titulo=titulo;
  }
  public String getContenido(){
    return contenido;
  }
  public void setContenido(String contenido){
    this.contenido=contenido;
  }
  public String getResumen(){
    return resumen;
  }
  public void setResumen(String resumen){
    this.resumen=resumen;
  }
  public String getCodigoArti(){
    return codigoArti;
  }
  public ArrayList<String> getPalaCla(){
    return palabrasClaves;
  }
  public void setPalaCla(ArrayList<String> palabrasClaves){
    this.palabrasClaves=palabrasClaves;
  }
  public Autor getAutor(){
    return autor;
  }
  public void setAutor(Autor autor){
    this.autor=autor;
  }
  public EstadoArticulo getEstado(){
    return estado;
  }
  public void setEstado(EstadoArticulo estado){
    this.estado = estado;
  }
  
}
