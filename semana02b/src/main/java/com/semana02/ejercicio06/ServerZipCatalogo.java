package com.semana02.ejercicio06;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerZipCatalogo {
    public static void main(String[] args) {
        new ServerZipCatalogo();
    }

    private final int PORT =13;

    public ServerZipCatalogo(){
              System.out.println("1 Server started");
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("2 Server waiting for client");
            while(true){
                Socket socket = server.accept();
                System.out.println("3 Client connected");
    
                //Flujos para recibir archivos
                DataInputStream entrada = new DataInputStream(socket.getInputStream());
                FileOutputStream fos = new FileOutputStream("D:" + File.separator + "CARRERA - CIBERTEC" + File.separator + "CICLO 6"
                    + File.separator + "4698 DESARROLLO DE SERVICIOS WEB II" + File.separator + "Sesion02"
                    + File.separator + "Server"
                    + File.separator + "CatalogoComprimido_Alva_G1.zip");
    
                //recibir archivo
                int byteLeidos = 0;
                while ((byteLeidos = entrada.read()) != -1) {
                    fos.write(byteLeidos);
                }
                System.out.println("4 File received");
    
                fos.close();
                entrada.close();
                System.out.println("5 Server finished");
            }
        } catch (Exception e) {
            e.printStackTrace();
}
    }
}
