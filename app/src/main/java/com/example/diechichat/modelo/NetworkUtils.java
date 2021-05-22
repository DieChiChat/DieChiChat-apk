package com.example.diechichat.modelo;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
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
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    // Base URL for Books API.
    private static final String FOOD_BASE_URL = "https://test-es.edamam.com/search";
    private static final String RUTA_BUSQUEDA = "https://api.edamam.com/search?q={palabra_a_buscar}&app_id=${tu_id}&app_key=${tu_clave}";
    // Parameter for the search string.
    private static final String QUERY_PARAM = "q";
    // Parameter that limits search results.
    private static final String MAX_RESULTS = "maxResults";
    // Parameter to filter by print type.
    private static final String PRINT_TYPE = "printType";

    public static String getBookInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try {
            Uri builtURI = Uri.parse(FOOD_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "foods")
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
        Log.d(LOG_TAG, bookJSONString);
        return bookJSONString;
    }

    public static ArrayList<Alimento> interpretarJson(String s) {
        Alimento alimento;
        ArrayList<Alimento> tAlimentos = null;
        try {
            tAlimentos = new ArrayList<Alimento>();
            // Convert the response into a JSON object.
            JSONObject jsonObject = new JSONObject(s);
            // Get the JSONArray of book items.
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            // Initialize iterator and results fields.
            int i = 0;
            double grasaSaturada = 0;
            double colesterol = 0;
            double sodio = 0;
            double fibraDietetica = 0;
            double azucares = 0;
            double proteinas = 0;
            double calcio = 0;
            double alcoholes = 0;
            double hierro = 0;
            double potasio = 0;
            double vitaminaA = 0;
            double vitaminaC = 0;

            // Look for results in the items array, exiting
            // when both the title and author
            // are found or when all items have been checked.
            while (i < itemsArray.length()) {
                // Get the current item information.
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    grasaSaturada = volumeInfo.getDouble("");
                    colesterol = volumeInfo.getDouble("");
                    sodio = volumeInfo.getDouble("");
                    fibraDietetica = volumeInfo.getDouble("");
                    azucares = volumeInfo.getDouble("");
                    proteinas = volumeInfo.getDouble("");
                    calcio = volumeInfo.getDouble("");
                    alcoholes = volumeInfo.getDouble("");
                    hierro = volumeInfo.getDouble("");
                    potasio = volumeInfo.getDouble("");
                    vitaminaA = volumeInfo.getDouble("");
                    vitaminaC = volumeInfo.getDouble("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alimento = new Alimento();
                alimento.setAlcoholes(alcoholes);
                alimento.setAzucares(azucares);
                alimento.setCalcio(calcio);
                alimento.setColesterol(colesterol);
                alimento.setFibraDietetica(fibraDietetica);
                alimento.setGrasaSaturada(grasaSaturada);
                alimento.setSodio(sodio);
                alimento.setProteinas(proteinas);
                alimento.setHierro(hierro);
                alimento.setPotasio(potasio);
                alimento.setVitaminaA(vitaminaA);
                alimento.setVitaminaC(vitaminaC);

//                Datos.getInstance().getLibros().add(libro);
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

