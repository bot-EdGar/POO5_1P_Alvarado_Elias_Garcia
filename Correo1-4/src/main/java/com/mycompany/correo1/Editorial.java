package com.mycompany.correo1;
import java.util.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Editorial{
    //private static String emailFromString="edgarcia20033@gmail.com";
    //MegMente123
    //private Properties props;

    public static ArrayList<Usuario> usuarios = new ArrayList<>();
    public static ArrayList<Revisor> revisores = new ArrayList<>();
    public static ArrayList<Autor> autores = new ArrayList<>();
    public static ArrayList<Editor> editores = new ArrayList<>();
    public static ArrayList<Articulo> articulos = new ArrayList<>();
    public static ArrayList<Revision> revisiones = new ArrayList<>();
    
    public static void main(String[] args) {
        // Crear editor, revisor a partir del archivo de usuarios
        cargarUsuarios();
        //Mostrar las opciones 
        mostrarMenu();
 
    }


    public static void mostrarMenu(){ 
        Scanner sc = new Scanner(System.in);
        int op = 0;
        do{
            System.out.println("-----------------------------------------------------");
            System.out.println("1. Mostar los usuarios");
            System.out.println("2. Mostar revisores asignados a un artīculo");
            System.out.println("3. Someter Artículo");
            System.out.println("4. Iniciar Sesión");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            op = sc.nextInt();
            sc.nextLine();
            System.out.println("-----------------------------------------------------");

            switch (op) {
                case 1:
                    usuarios.forEach(u -> System.out.println("Usuario: " + u.getUser() + ", Contraseña: " + u.getPassword()+", ROL: " + u.getRol() + ", Correo: " + u.getCorreoElectronico()));
                    break;
                case 2:
                    mostrarUsuariosAsignados();
                    break;
                case 3:
                    System.out.println("Ha escogido la opción de Someter Artículo");
                    Autor.someterArticulo(sc);
                    break;
                case 4:
                    System.out.println("Ha escogido la opción de Iniciar Sesión");
                    iniciarSesion(sc);
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                System.out.println("Opción Inválida");
                    break;
            }
        }while(op!=5);
    }

    public static void cargarUsuarios() {
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                
                if (datos.length >= 5) { // Verificar que haya suficientes elementos en la línea
                    String rolUsuario = datos[0];
                    String user = datos[3];
                    String password = datos[4];
                    String nombre = datos[1];
                    String apellido = datos[2];
                    String correo = datos[5];

                    switch (rolUsuario) {
                        case "EDITOR":
                            // Crear y agregar editor a la lista de usuarios
                            Editor editor = new Editor(user, password, nombre, apellido,correo);
                            usuarios.add(editor);
                            break;
                        case "REVISOR":
                            // Crear y agregar revisor a la lista de usuarios
                            Revisor revisor = new Revisor(user, password, nombre, apellido,correo);
                            usuarios.add(revisor);
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
    }

    public static void mostrarUsuariosAsignados(){
        boolean comprobar = false;
        for(Revisor revisor : revisores){
            if(!revisor.getArticulosRevisor().isEmpty()){
                comprobar = true;
                System.out.println("Usuario: "+revisor.getUser() + ", Contraseña: "+revisor.getPassword() + ", Artículo(s) Asignado(s): " + revisor.getNombreArticulos());
            }
        }
        if(!comprobar)
            System.out.println("No hay revisores asignados, para asignar revisores ingrese un artículo(Opción 3)");
    }

    public static void iniciarSesion(Scanner sc){
        System.out.print("Ingrese su usuario: ");
        String userV = sc.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String contraV = sc.nextLine();
        boolean comprobarUsuario = false;
        for(Usuario usuario: usuarios){
            if(usuario.getPassword().equals(contraV) && usuario.getUser().equals(userV)){ 
                comprobarUsuario = true;
                if (usuario instanceof Revisor){
                    Revisor revisor = (Revisor) usuario;
                    System.out.println("------------------------------------------------");
                    System.out.println("Bienvenido " + revisor.getNombre() + " " + revisor.getApellido());
                    revisor.mostrarTareaRealizar(sc);
                    break;
                }else if(usuario instanceof Editor){
                    Editor editor = (Editor) usuario;
                    System.out.println("------------------------------------------------");
                    System.out.println("Bienvenido " + editor.getNombre() + " " + editor.getApellido());
                    editor.mostarTareaRealizar(sc);
                }
                break;
            } 
        }
        if(!comprobarUsuario){
            System.out.println("Usuario o contraseña incorrectos...");
            System.out.println("-----------------------------------------");
        }
    }

// metodo para escribir en los archivos
        public static void escribirArchivo(String nombreArchivo, Object objeto) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            bw.write(objeto.toString() + "\n");
            if(objeto instanceof Autor){
                System.out.println(" escrito en el archivo" + nombreArchivo);
            }else if(objeto instanceof Revisor){
                System.out.println(" escrito en el archivo" + nombreArchivo);
            }else if(objeto instanceof Articulo){
                System.out.println(" escrito en el archivo" + nombreArchivo);
            }
        } catch (IOException e) {
            e.printStackTrace();
            }
        }      
}

