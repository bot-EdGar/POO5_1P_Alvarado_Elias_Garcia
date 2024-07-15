package com.mycompany.correo1;
/**
 *
 * @author edgar
 */
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Scanner;
public abstract class Usuario {
    protected String user;
    protected String password;
    protected String nombre;
    protected String apellido;
    protected String correoElectronico;
    protected RolUsuario rol;

    public abstract String generarCorreoElectronico(String nombre, String apellido); 
    public abstract void decidirSobreArticulo(String nombreArticulo, Scanner sc);

    public Usuario(String user, String password, String nombre, String apellido, RolUsuario rol,String correo){
        this.user = user;
        this.correoElectronico = correo;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rol = rol;
    }

    public Usuario(String user, String password){
        this.user = user;
        this.password = password;
    }

    public void enviarCorreo(String destinatario, String asunto, String cuerpo){//DESTINATARIO = CORREO AL QUE LLEGA
        Properties props=new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("editorial.proyecto1.parcial@gmail.com", "pszalxluqqnpmjlo");
            }
            });
        try {
            // Crear el mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("editorial.proyecto1.parcial@gmail.com"));
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

    public String getNombre(){
        return nombre;
    }

    public String getApellido(){
        return apellido;
    }

    public String getCorreoElectronico(){
        return correoElectronico;
    }

    public String getUser(){
        return user;
    }

    public String getPassword(){
        return password;
    }

    public RolUsuario getRol(){
        return rol;
    }
}
