package com.semana02.ejercicio05;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GenerateZipPais {

    public static void main(String[] args) {
        GenerateZipPais generateZipPais = new GenerateZipPais();
        generateZipPais.procesar();

    }

    public void procesar() {
        ArrayList<Pais> paises = new ArrayList<Pais>();
        // 1 Generar el arraylist de paises de la base de datos
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = MySqlDBConexion.getConexion();
            String query = "SELECT * FROM pais";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idPais = resultSet.getInt("idPais");
                String iso = resultSet.getString("iso");
                String nombre = resultSet.getString("nombre");
                Pais pais = new Pais(idPais, iso, nombre);
                paises.add(pais);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println(paises);

        // 2 Generar el archivo JSON de los paises
        FileWriter fileWriter = null;
        try {
            // Leer el archivo
            File file = new File(
                    "D:\\CARRERA - CIBERTEC\\CICLO 6\\4698 DESARROLLO DE SERVICIOS WEB II\\Sesion02\\paises_Alva_G1.json");
            fileWriter = new FileWriter(file);

            // Convertir el objeto a JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(paises, fileWriter);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

 

    // 3 Generar el archivo XML de los paises


  
        try {
            // Leer el archivo
            File file = new File(
                    "D:\\CARRERA - CIBERTEC\\CICLO 6\\4698 DESARROLLO DE SERVICIOS WEB II\\Sesion02\\paises_Alva_G1.xml");
            fileWriter = new FileWriter(file);

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable((SerializationFeature.INDENT_OUTPUT));
            xmlMapper.writeValue(fileWriter, paises);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }





    // 4 Generar el archivo ZIP del JSON y XML

      String[] files = {
            "D:" + File.separator + "CARRERA - CIBERTEC" + File.separator + "CICLO 6" + File.separator + "4698 DESARROLLO DE SERVICIOS WEB II" + File.separator + "Sesion02" + File.separator + "paises_Alva_G1.json",
            "D:" + File.separator + "CARRERA - CIBERTEC" + File.separator + "CICLO 6" + File.separator + "4698 DESARROLLO DE SERVICIOS WEB II" + File.separator + "Sesion02" + File.separator + "paises_Alva_G1.xml"
        };

        try {
            // Output ZIP file
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(
                    "D:" + File.separator + "CARRERA - CIBERTEC" + File.separator + "CICLO 6" + File.separator + "4698 DESARROLLO DE SERVICIOS WEB II" + File.separator + "Sesion02" + File.separator + "paiscomprimido_Alva_G1.zip"));

            for (String filePath : files) {
                File file = new File(filePath);

                if (file.exists()) {
                    System.out.println("Adding file: " + filePath);
                    FileInputStream fis = new FileInputStream(file);

                    // Create a new entry in the zip file
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zipOut.putNextEntry(zipEntry);

                    // Write the file's content to the zip file
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = fis.read(buffer)) > 0) {
                        zipOut.write(buffer, 0, len);
                    }

                    fis.close();
                    zipOut.closeEntry();
                } else {
                    System.err.println("File not found: " + filePath);
                }
            }

            zipOut.close();
            System.out.println("Zip file created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
