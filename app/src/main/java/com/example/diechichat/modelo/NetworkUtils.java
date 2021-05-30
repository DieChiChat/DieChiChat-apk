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
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    // Base URL for Books API.
    private static final String RUTA_BUSQUEDA = "https://api.edamam.com/search?q=palabra_a_buscar&app_id=57eaa490&app_key=47f6530c4dda609ba6dc58fb4e62bfad";
    private static final String FOOD_ID = "72781461";
    private static final String FOOD_KEY = "a22b274c5fbcb98413cdbb392927e2e3";
    // Parameter for the search string.
    private static final String QUERY_PARAM = "q";
    // Parameter that limits search results.
    private static final String MAX_RESULTS = "maxResults";
    // Parameter to filter by print type.
    private static final String PRINT_TYPE = "printType";

    public static String getFoodInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;
        String rutaDefinitiva = "https://api.edamam.com/api/food-database/v2/parser?ingr=" + queryString +"&app_id=72781461&app_key=a22b274c5fbcb98413cdbb392927e2e3";
        try {
            Uri builtURI = Uri.parse(rutaDefinitiva).buildUpon()
//                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "20")
//                    .appendQueryParameter(PRINT_TYPE, "foods")
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
            // Get the JSONArray of book items.
//            JSONArray itemsArray = jsonObject.getJSONArray("json");
//            for (int i = 0; i < jsonObject.length(); i++) {
//                String value = jsonObject.getString("text");
//                Log.e("json", i+"="+value);
//            }
            int i = 0;
            while (i < jsonObject.length()) {
                // Get the current item information.

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
//                    double valor = jsonObject.getJSONArray("hints").getJSONObject(0).getJSONObject("food").getJSONObject("nutrients").getDouble("ENERC_KCAL");
//                    String nombre = jsonObject.getJSONArray("hints").getJSONObject(0).getJSONObject("food").getString("label");
                    alimento = new Alimento();
                    alimento.setNombre(jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food").getString("label"));
                    alimento.setKcal(jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food").getJSONObject("nutrients").getDouble("ENERC_KCAL"));
                    alimento.setProteinas(jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food").getJSONObject("nutrients").getDouble("PROCNT"));
                    alimento.setGrasa(jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food").getJSONObject("nutrients").getDouble("FAT"));
                    alimento.setCarbohidratos(jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food").getJSONObject("nutrients").getDouble("CHOCDF"));
                    alimento.setFibra(jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food").getJSONObject("nutrients").getDouble("FIBTG"));
                    alimento.setVitaminaA(jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food").getJSONObject("nutrients").getDouble("VITA_RAE"));
                    alimento.setVitaminaC(jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food").getJSONObject("nutrients").getDouble("VITC"));
                    alimento.setCalcio(jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food").getJSONObject("nutrients").getDouble("CA"));
                    alimento.setColesterol(jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food").getJSONObject("nutrients").getDouble("COLE"));
                    alimento.setGrasasSaturadas(jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food").getJSONObject("nutrients").getDouble("FASAT"));
                    alimento.setSodio(jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food").getJSONObject("nutrients").getDouble("NA"));
                    alimento.setHierro(jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food").getJSONObject("nutrients").getDouble("FE"));
                    alimento.setPotasio(jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food").getJSONObject("nutrients").getDouble("K"));
                    alimento.setAzucares(jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food").getJSONObject("nutrients").getDouble("SUGAR"));

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

