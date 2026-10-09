package co.edu.uptc.inmobiliaria.DAO.DaoJson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import co.edu.uptc.inmobiliaria.Model.Inmobiliaria;

public class DaoInmobiliariaJson {

    private Gson gson = new Gson();
    private String archivo = "src/main/resources/inmobiliaria.json";

    public DaoInmobiliariaJson(){

    }

    public List<Inmobiliaria> leerArchivo(){
        try (Reader reader = new FileReader(archivo)) {

            Type tipoLista = new TypeToken<List<Inmobiliaria>>() {}.getType();
            List<Inmobiliaria> lista = gson.fromJson(reader, tipoLista);

            return lista != null ? lista : new ArrayList<>();

        } catch (IOException e) {

            return new ArrayList<>();
        }
    }

        public void escribirArchivo(List<Inmobiliaria> inmobiliarias) {
        try (Writer writer = new FileWriter(archivo)) {
            gson.toJson(inmobiliarias, writer);
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
