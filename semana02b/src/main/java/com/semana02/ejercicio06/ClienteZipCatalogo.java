package com.semana02.ejercicio06;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

import com.semana02.ejercicio05.GenerateZipPais;

public class ClienteZipCatalogo {
public static void main(String[] args) {
    new ClienteZipCatalogo();

}


private final int PORT = 13;
private final String HOST = "localhost";

private ClienteZipCatalogo(){
     System.out.println("1 Generando archivos JSON, XML y ZIP");
        GenerateZipPais generateZipPais = new GenerateZipPais();
        generateZipPais.procesar();


 try {
            System.out.println("2 Client Started");
            Socket socket = new Socket(HOST, PORT);
            System.out.println("3 Connected to server");


            File file = new File("D:" + File.separator + "CARRERA - CIBERTEC" + File.separator + "CICLO 6"
            + File.separator + "4698 DESARROLLO DE SERVICIOS WEB II" + File.separator + "Sesion02"
            + File.separator + "Cliente"
            + File.separator + "CatalogoComprimido_Alva_G1.zip");
        
            FileInputStream fis = new FileInputStream(file);
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            int byteLeidos = 0;
            while ((byteLeidos = fis.read()) != -1) {
                salida.write(byteLeidos);
            }
            System.out.println("3 File sent");
            fis.close();
            salida.close();
            System.out.println("4 Client finished");

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}


