package com.semana02.ejercicio05;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;

public class ClienteZipPais {
    public static void main(String[] args) {
        new ClienteZipPais();

    }

    private final int PORT = 13;
    private final String HOST = "localhost";

    public ClienteZipPais() {
        // PASO 1 Generar los archivos
        System.out.println("1 Generando archivos JSON, XML y ZIP");
        GenerateZipPais generateZipPais = new GenerateZipPais();
        generateZipPais.procesar();

        // PASO 2 crear el socke cliente

        try {
            System.out.println("2 Client Started");
            Socket socket = new Socket(HOST, PORT);
            System.out.println("3 Connected to server");

        // PASO 3 Enviar el archivo ZIP

            File file = new File("D:" + File.separator + "CARRERA - CIBERTEC" + File.separator + "CICLO 6"
                    + File.separator + "4698 DESARROLLO DE SERVICIOS WEB II" + File.separator + "Sesion02"
                    + File.separator + "Cliente"
                    + File.separator + "paiscomprimido_Alva_G1.zip");
            FileInputStream fis = new FileInputStream(file);
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());

            // enviar archivo
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
