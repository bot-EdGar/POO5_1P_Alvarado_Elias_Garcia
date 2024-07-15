package com.mycompany.correo1;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class Sistema {
    
    private final String username;
    private final String password;
    private static final String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    
    public Sistema(String username, String password){
        this.username=username;
        this.password=password;
    }
    
    
    
    public void enviarCorreo(String destinatario, String asunto, String cuerpo){
        Properties props=new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
            });
        try {
            // Crear el mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(cuerpo);

            // Enviar el mensaje
            Transport.send(message);

            System.out.println("Correo enviado exitosamente");

        } catch(Exception e) {
            System.out.println(e);
        }
    
    }
    
    
    public static void cargarRevisiones(List<String> revisio){
        try (BufferedReader br = new BufferedReader(new FileReader("Revisiones.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] revisi = linea.split(",");
                revisio.add(linea);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public static String generarCodigo(){
        Random rd = new Random();
        StringBuilder codigo = new StringBuilder(5);
        for(int i = 0; i< 5;i++){
            int var = rd.nextInt(caracteres.length());
            codigo.append(caracteres.charAt(var));
        }
        //System.out.println(codigo.toString());
        return codigo.toString();
    }
    
    
    
    public static List<String> cargarArchivos() {
        List<Autor> autores = new ArrayList<>();
        List<Revisor> revisores = new ArrayList<>();
        List<Editor> editores = new ArrayList<>();
        List<String> articulos = new ArrayList<>();
        cargarUsuarios(autores,editores, revisores);
        try (BufferedReader br = new BufferedReader(new FileReader("Articulos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                articulos.add(linea);

                if (datos.length >= 6) { // Verificar que haya suficientes elementos en la línea
                    String codigoUnico=datos[0];
                    String titulo = datos[1];
                    String resumen = datos[2];
                    String contenido = datos[3];
                    String[] palabrasClave = datos[4].split(";");
                    String nombreAutor = datos[5];
                    String estadoArticulo = datos[6];
                    
                    

//                    for (Autor autor : autores) {
//                        
//                        if (nombreAutor.equals(autor.getNombre())) {
//                            System.out.println("\n*******Artitulo*******");
//                            System.out.println("Codigo: " + codigoUnico);
//                            System.out.println("Titulo: " + titulo);
//                            System.out.println("Titulo: " + titulo);
//                            System.out.println("Resumen: " + resumen);
//                            System.out.println("Contenido: " + contenido);
//                            System.out.println("Palabras Clave: " + String.join(", ", palabrasClave));
//                            System.out.println("Estado del Articulo: " + estadoArticulo);
//                             // Asumiendo que un artículo solo puede pertenecer a un autor
//                             break;
//                        }else{
//                            break;
//                        }
//                    }
                } else {
                    //System.out.println("Línea incorrecta: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articulos;
    }

    
    
    public static void cargarUsuarios(List<Autor> autores, List<Editor> editores,List<Revisor> revisores){
        try (BufferedReader br = new BufferedReader(new FileReader("Usuarios.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                
                if (datos.length >= 5) { // Verificar que haya suficientes elementos en la línea
                    String nombre = datos[0];
                    String apellido = datos[1];
                    String user = datos[2];
                    String password = datos[3];
                    String rolUsuario = datos[4];

                    switch (rolUsuario) {
                        case "AUTOR":
                            Autor autor = new Autor(nombre, apellido, user, password, RolUsuario.AUTOR, datos[5], datos[6], datos[7]);
                            autores.add(autor);
                            break;
                        case "EDITOR":
                            Editor editor = new Editor(nombre, apellido, user, password, RolUsuario.EDITOR, datos[5]);
                            editores.add(editor);
                            break;
                        case "REVISOR":
                            String numArticulos = datos[6];
                            int numeroArticulos = Integer.parseInt(numArticulos);
                            Revisor revisor = new Revisor(nombre, apellido, user, password, RolUsuario.REVISOR, datos[5], numeroArticulos);
                            revisores.add(revisor);
                            break;
                        default:
                            System.out.println("Tipo de usuario no permitido: " + rolUsuario);
                            break;
                    }
                } 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        for(Autor autor:autores){
//            System.out.println(autor.apellido);
//        }
    }
    
      
    public static String[] escoger(List<Revisor> revisores){
        String[] correosRevisores = new String[2];
        if (revisores.size()>= 2) {
            Random random = new Random();
            int index1 = random.nextInt(revisores.size());
            int index2 = random.nextInt(revisores.size());

            // Asegurarse de que los dos índices sean diferentes
            while (index2 == index1) {
                index2 = random.nextInt(revisores.size());
            }

            Revisor revisor1 = revisores.get(index1);
            
            Revisor revisor2 = revisores.get(index2);
            
            correosRevisores[0]=revisor1.correoElectronico;
            correosRevisores[1]=revisor2.correoElectronico;
            return correosRevisores;
            
        } else {
            System.out.println("No hay suficientes editores en la lista.");
            return null;
        }
    }
    
    
    public static void guardarRevisiones(String comentarios){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Revisiones.txt", true))) {
            bw.write(comentarios);
            bw.newLine();
            System.out.println("Las revisiones se guardaron exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void iniciarSesion(Scanner sc) {
        int op1=0;
        List<Autor> autores = new ArrayList<>();
        List<Revisor> revisores = new ArrayList<>();
        List<Editor> editores = new ArrayList<>();
        cargarUsuarios(autores,editores, revisores);
        List<String> revisio=new ArrayList<>();
        cargarRevisiones(revisio);
        
        
        System.out.println("Ingrese su correo electronico: ");
        String correo=sc.nextLine();
        System.out.println("Ingrese su contraseña: ");
        String contraseña=sc.nextLine();
        for (Usuario usuario : autores) {
            if (usuario.getCorreoElectronico().equalsIgnoreCase(correo)) {
                if (usuario.getContraseña().equals(contraseña)) {
                    System.out.println("Bienvenido 1 "+ usuario.getNombre()+" "+ usuario.getApellido());
                    System.out.println("Ingrese la opcion que desee ");
                    System.out.println("1.-Mostrar los Articulos subidos: ");
                    System.out.println("2.-Salir: ");
                    op1=sc.nextInt();
                    switch (op1){
                        case 1:
                             System.out.println("Sus articulos ingresados son: ");
                             cargarArchivos();
                             System.out.print("\nPresione cualquier numero para volver el menu inicial...");
                             op1=sc.nextInt();
                             break;
                        case 2:
                            System.out.println("Saliendo al menu principal......");
                            break;
                        default:
                            System.out.println("Opcion Invalida");
                            System.out.println("Saliendo al menu principal......");
                            break;
                    }
                    
                    
                }
                break; // No es necesario seguir buscando si el correo coincide pero la contraseña no
            }
        }
        
        for (Usuario usuario : revisores) {
            List<String> revisiones=new ArrayList<>();
            if (usuario.getCorreoElectronico().equalsIgnoreCase(correo)) {
                if (usuario.getContraseña().equals(contraseña)) {
                    System.out.println("Bienvenido "+ usuario.getNombre()+" "+ usuario.getApellido());
                    //ver los articulos subidos;
                    
                    System.out.println("Ingresa el codigo del articulo asignado: ");
                    String codigo=sc.nextLine();
                    List<String> articulos=cargarArchivos();
                    List<String> comentarios=new ArrayList<>();
                    List<String> desici=new ArrayList<>();
                    for (String articulo:articulos){
                        String[] datos = articulo.split(",");
                        if (datos[0].equals(codigo)){
                            revisiones.add(datos[0]);
                           revisiones.add(datos[1]);
                           System.out.println("\nTitulo: "+datos[1]+"\nResumen: "+datos[2]+"\nContenido: "+datos[3]+"\nPalabras Claves: ");
                           System.out.print(datos[4]+"\nAutor: "+datos[5]+"\n");
                           comentarios.add( Revisor.comentarArticulo(sc));
                           datos[6]=Revisor.tomarDeicion(sc).toString();
                           desici.add(datos[6]);
                           System.out.println("Su decision será enviada a un Editor");
                        }
                        
                    }
                    String resultado = String.join("; ", comentarios);
                    revisiones.add(resultado);
                    String resultado2 = String.join("; ", desici);
                    revisiones.add(resultado2);
                   
                }
                System.out.println(String.join(", ", revisiones));
                Sistema.guardarRevisiones(String.join(", ", revisiones));
                
                break; // No es necesario seguir buscando si el correo coincide pero la contraseña no
                
            }
            
        }

        for (Usuario usuario : editores) {
            try{
                if (usuario.getCorreoElectronico().equalsIgnoreCase(correo)) {
                if (usuario.getContraseña().equals(contraseña)) {
                    System.out.println("Bienvenido "+ usuario.getNombre()+" "+ usuario.getApellido());
                    System.out.println("Ingrese el codigo del articulo: ");
                    String codigo=sc.nextLine();
                    
                    for (String revision:revisio){
                        String[] revision1 = revision.split(",");
                        
                        if (revision1[0].equals(codigo)){
                            System.out.println("Estos son los comentarios de los revisores: ");
                            
                                if (revision1[2].split(";").length>=2){
                                    String[] revision1_1 = revision1[2].split(";");
                                    String[] revision1_2 = revision1[3].split(";");
                                    System.out.println("Comentario del primer revisor: "+revision1_1[0]+"\nDesicion: "+revision1_2[0]);
                                    System.out.println("Comentario del segundo revisor: "+revision1_1[1]+"\nDesicion: "+revision1_2[1]);
                                    String cuerpo="Comentario del primer revisor: "+revision1_1[0]+"\nDesicion: "+revision1_2[0]+"\nComentario del segundo revisor: "+revision1_1[1]+"\nDesicion: "+revision1_2[1];
                                    revisio.add(Editor.tomarDeicion(sc).toString());
                                    String cuerpoFinal= cuerpo+" \nLa desicion final del editor "+Editor.tomarDeicion(sc).toString();
                                    
                                    
                                }else{
                                    System.out.println("Comentario del primer revisor: "+revision1[2]+"\nDesicion: "+revision1[3]);
                                    String cuerpo="Comentario del primer revisor: "+revision1[2]+"\nDesicion: "+revision1[3];
                                    revisio.add(Editor.tomarDeicion(sc).toString());
                                    String cuerpoFinal= cuerpo+" \nLa desicion final del editor "+Editor.tomarDeicion(sc).toString();
                                    
                                }
                                Sistema.guardarRevisiones(String.join(", ", revisio));
                        }
                        
                    }
                }
                
                break; // No es necesario seguir buscando si el correo coincide pero la contraseña no
            }
            }catch (Exception e){
                
            }
        }
        System.out.println("Saliendo  al menu principal.......");
    }  
    
    
    
    public static void mostrarMenu(){ 
        // List<Usuario> usuarios = new ArrayList<>();
        List<Autor> autores = new ArrayList<>();
        List<Revisor> revisores = new ArrayList<>();
        List<Editor> editores = new ArrayList<>();
        
        Scanner sc = new Scanner(System.in);
        int op = 0;
        do{
            System.out.println("-----------------------------------------------------");
            
            System.out.println("1. Someter Artículo");
            System.out.println("2. Iniciar Sesión");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            op = sc.nextInt();
            sc.nextLine();
            System.out.println("-----------------------------------------------------");
            

            switch (op) {
                
                case 1:
                    cargarUsuarios(autores,editores, revisores);
                    System.out.println("Ha escogido la opción de Someter Artículo");
                    
                    String cuerpo =Autor.someterArticulo(sc);
                    
                
                    for (String correo: escoger(revisores)){
                        String correoUsuario = "editorial.proyecto1.parcial@gmail.com";
                        String passwordUsuario = "pszalxluqqnpmjlo";
                        Sistema sistema = new Sistema(correoUsuario, passwordUsuario);
                        String destinatario = correo;
                        String asunto = "Asignacion de nuevo articulo por revisar";
                        
                        sistema.enviarCorreo(destinatario, asunto, cuerpo);
                    }
                    break;
                case 2:
                    System.out.println("Ha escogido la opción de Iniciar Sesión");
                    iniciarSesion(sc);                  
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    break;
                default:
                System.out.println("Opción Inválida");
                    break;
            }
        }while(op<3);
        sc.close();
    }

    
    
    public static void main(String[] args) {
        
        mostrarMenu();
    }
}
