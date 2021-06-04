package com.example.diechichat.modelo;

import android.net.Uri;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkUtils {
    // Base URL for Books API.
    private static final String RUTA_BUSQUEDA = "https://api.edamam.com/search?q=palabra_a_buscar&app_id=57eaa490&app_key=47f6530c4dda609ba6dc58fb4e62bfad";
    private static final String FOOD_ID = "72781461";
    private static final String FOOD_KEY = "a22b274c5fbcb98413cdbb392927e2e3";
    // Parameter that limits search results.
    private static final String MAX_RESULTS = "maxResults";

    public static String getFoodInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;
        String rutaDefinitiva = "https://api.edamam.com/api/food-database/v2/parser?ingr=" + queryString +"&app_id=72781461&app_key=a22b274c5fbcb98413cdbb392927e2e3";
        try {
            Uri builtURI = Uri.parse(rutaDefinitiva).buildUpon()
                    .appendQueryParameter(MAX_RESULTS, "20")
                    .build();
            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Get the InputStream.
            InputStream inputStream = urlConnection.getInputStream();

            // Create a buffered reader from that input stream.
            reader = new BufferedReader(new InputStreamReader(inputStream));

            // Use a StringBuilder to hold the incoming response.
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            if (builder.length() == 0) {
                return null;
            }
            bookJSONString = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Cerrar conexión de red cuando se reciban los datos JSON
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bookJSONString;
    }

    public static ArrayList<Alimento> interpretarJson(String s) {
        Alimento alimento = null;
        ArrayList<Alimento> tAlimentos = null;
        try {

            tAlimentos = new ArrayList<Alimento>();
            // Convert the response into a JSON object.
            JSONObject jsonObject = new JSONObject(s);
            int i = 0;
            while (i < jsonObject.length()) {
                // Get the current item information.

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    alimento = new Alimento();
                    alimento.setNombre(jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food").getString("label"));
                    alimento.setKcal(jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food").getJSONObject("nutrients").getDouble("ENERC_KCAL"));
                    alimento.setProteinas(jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food").getJSONObject("nutrients").getDouble("PROCNT"));
                    alimento.setGrasa(jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food").getJSONObject("nutrients").getDouble("FAT"));
                    alimento.setCarbohidratos(jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food").getJSONObject("nutrients").getDouble("CHOCDF"));
                    alimento.setFibra(jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food").getJSONObject("nutrients").getDouble("FIBTG"));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                DatosAlimentos.getInstance().getAlimentos().add(alimento);
                tAlimentos.add(alimento);
                // Move to the next item.
                i++;
            }
            return  tAlimentos;
        } catch (JSONException e) {
            ;
        }
        return tAlimentos;
    }
}

