package com.semana02.ejercicio04;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CreateZip {
    public static void main(String[] args) {
        String[] files = {
            "D:" + File.separator + "CARRERA - CIBERTEC" + File.separator + "CICLO 6" + File.separator + "4698 DESARROLLO DE SERVICIOS WEB II" + File.separator + "Sesion02" + File.separator + "Json_Alva_G1.json",
            "D:" + File.separator + "CARRERA - CIBERTEC" + File.separator + "CICLO 6" + File.separator + "4698 DESARROLLO DE SERVICIOS WEB II" + File.separator + "Sesion02" + File.separator + "cliente_Alva_G1.xml"
        };

        try {
            // Output ZIP file
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(
                    "D:" + File.separator + "CARRERA - CIBERTEC" + File.separator + "CICLO 6" + File.separator + "4698 DESARROLLO DE SERVICIOS WEB II" + File.separator + "Sesion02" + File.separator + "comprimido_Alva_G1.zip"));

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
