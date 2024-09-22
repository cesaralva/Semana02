package com.semana02.ejercicio06;

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

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.semana02.ejercicio03.Producto;
import com.semana02.ejercicio05.GenerateZipPais;

public class GenerateZipCatalogo {

        public static void main(String[] args) {
            GenerateZipCatalogo generateZipCatalogo = new GenerateZipCatalogo();
        generateZipCatalogo.procesar();
        }
        
        public void procesar() {
            ArrayList<Catalogo> catalogos = new ArrayList<Catalogo>();
            // 1 Generar el arraylist de paises de la base de datos
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            try {
                connection = MySqlDBConexion.getConexion();
                String query = "SELECT * FROM catalogo";
                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int idCatalogo = resultSet.getInt("idCatalogo");
                    String descripcion = resultSet.getString("descripcion");
                    
                    Catalogo catalogo = new Catalogo(idCatalogo, descripcion);
                    catalogos.add(catalogo);
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
    
            System.out.println(catalogos);
    
            // 2 Generar el archivo JSON de los paises
            FileWriter fileWriter = null;
            try {
                // Leer el archivo
                File file = new File(
                        "D:\\CARRERA - CIBERTEC\\CICLO 6\\4698 DESARROLLO DE SERVICIOS WEB II\\Sesion02\\Cliente\\catalogo_Alva_G1.json");
                fileWriter = new FileWriter(file);
    
                // Convertir el objeto a JSON
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(catalogos, fileWriter);
    
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
                        "D:\\CARRERA - CIBERTEC\\CICLO 6\\4698 DESARROLLO DE SERVICIOS WEB II\\Sesion02\\Cliente\\catalogo_Alva_G1.xml");
                fileWriter = new FileWriter(file);
    
                XmlMapper xmlMapper = new XmlMapper();
                xmlMapper.enable((SerializationFeature.INDENT_OUTPUT));
                xmlMapper.writeValue(fileWriter, catalogos);
    
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fileWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    
    // 04 - Generar el archivo xlsx
    String[] HEADERS = { "ID Catalogo", "Descripcion" };
    String SHEET_NAME = "Catalogo";
    int[] COLUMN_WIDTHS = { 3000, 7000 };

     try {
            FileOutputStream fileOut = new FileOutputStream("D:/CARRERA - CIBERTEC/CICLO 6/4698 DESARROLLO DE SERVICIOS WEB II/Sesion02/cliente/catalogo_Alva_G1.xlsx");
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(SHEET_NAME);
        // Establecer el ancho de las columnas
        for (int i = 0; i < COLUMN_WIDTHS.length; i++) {
            sheet.setColumnWidth(i, COLUMN_WIDTHS[i]);
        }
 // Crear la fila de encabezado
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                headerRow.createCell(i).setCellValue(HEADERS[i]);
            }
            
            // Crear las filas de los productos
            for (int i = 0; i < catalogos.size(); i++) {
                Catalogo producto = catalogos.get(i);
                sheet.createRow(i + 1).createCell(0).setCellValue(producto.getIdCatalogo());
                sheet.getRow(i + 1).createCell(1).setCellValue(producto.getDescripcion());
              
            }
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
         
  }
    
        // 5 Generar el archivo ZIP del JSON y XML
    
          String[] files = {
                "D:" + File.separator + "CARRERA - CIBERTEC" + File.separator + "CICLO 6" + File.separator + "4698 DESARROLLO DE SERVICIOS WEB II" + File.separator + "Sesion02" + File.separator + "Cliente" + File.separator +"catalogo_Alva_G1.json",
                "D:" + File.separator + "CARRERA - CIBERTEC" + File.separator + "CICLO 6" + File.separator + "4698 DESARROLLO DE SERVICIOS WEB II" + File.separator + "Sesion02" + File.separator +"Cliente" + File.separator + "catalogo_Alva_G1.xml",
                "D:" + File.separator + "CARRERA - CIBERTEC" + File.separator + "CICLO 6" + File.separator + "4698 DESARROLLO DE SERVICIOS WEB II" + File.separator + "Sesion02" + File.separator +"Cliente" + File.separator + "catalogo_Alva_G1.xlsx"
            };
    
            try {
                // Output ZIP file
                ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(
                    "D:" + File.separator + "CARRERA - CIBERTEC" + File.separator + "CICLO 6" + File.separator + 
                    "4698 DESARROLLO DE SERVICIOS WEB II" + File.separator + "Sesion02" + 
                    File.separator + "Cliente" + File.separator + "CatalogoComprimido_Alva_G1.zip"));
                
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


