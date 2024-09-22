package com.semana02.ejercicio01;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CreateJson {

    public static void main(String[] args) {
        Auto auto1 = new Auto(1, "TOyota");
        Auto auto2 = new Auto(2, "Nissan");
        Auto auto3 = new Auto(3, "Mazda");

        ArrayList<Auto> autos = new ArrayList<>();
        autos.add(auto1);
        autos.add(auto2);
        autos.add(auto3);

System.out.println(autos);


        FileWriter fileWriter = null;
        try {
            // Leer el archivo
            File file = new File(
                    "D:\\CARRERA - CIBERTEC\\CICLO 6\\4698 DESARROLLO DE SERVICIOS WEB II\\Sesion02\\Json_Alva_G1.json");
            fileWriter = new FileWriter(file);

            // Convertir el objeto a JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(autos, fileWriter);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
