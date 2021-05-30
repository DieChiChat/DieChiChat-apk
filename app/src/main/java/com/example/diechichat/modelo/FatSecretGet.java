package com.example.diechichat.modelo;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class FatSecretGet {
    /**
     * FatSecret Authentication
     * http://platform.fatsecret.com/api/default.aspx?screen=rapiauth
     * Reference
     * https://github.com/ethan-james/cookbox/blob/master/src/com/vitaminc4/cookbox/FatSecret.java
     */
    final static private String APP_METHOD = "GET";
    final static private String APP_KEY = "16995ded71c442538d9ec8a9137fb3db";
    final static private String APP_SECRET = "f73badd77a924319b81654f26ba6757a";
    final static private String APP_URL = "https://platform.fatsecret.com/rest/server.api";
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    public Object getFood(String ab) {
        Alimento alimento;
        ArrayList<Alimento> tAlimentos = null;
        List<String> params = new ArrayList<>(Arrays.asList(generateOauthParams()));
        String[] template = new String[1];
        params.add("method=food.get");
        params.add("food_name=" + ab);
        params.add("oauth_signature=" + sign(APP_METHOD, APP_URL, params.toArray(template)));
        JSONObject food = null;

        try {
            URL url = new URL(APP_URL + "?" + paramify(params.toArray(template)));
            URLConnection api = url.openConnection();
            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(api.getInputStream()));
            while ((line = reader.readLine()) != null)
                builder.append(line);
            JSONObject foodGet = new JSONObject(builder.toString());
            food = foodGet.getJSONObject("food");

            // Initialize iterator and results fields.
            int i = 0;
            double calorias = 0;
            String nombre = "";
            double grasaSaturada = 0;
            double colesterol = 0;
            double sodio = 0;
            double fibra = 0;
            double azucares = 0;
            double proteinas = 0;
            double calcio = 0;
            double hierro = 0;
            double potasio = 0;
            double vitaminaA = 0;
            double vitaminaC = 0;

            // Look for results in the items array, exiting
            // when both the title and author
            // are found or when all items have been checked.
            while (i < food.length()) {
                // Get the current item information.
                 foodGet = new JSONObject(builder.toString());
                food = foodGet.getJSONObject("food");


                JSONObject volumeInfo = food.getJSONObject("volumeInfo");

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    nombre = volumeInfo.getString("name");
                    calorias = volumeInfo.getDouble("calories");
                    grasaSaturada = volumeInfo.getDouble("saturated");
                    colesterol = volumeInfo.getDouble("cholesterol");
                    sodio = volumeInfo.getDouble("sodium");
                    fibra = volumeInfo.getDouble("fiber");
                    azucares = volumeInfo.getDouble("sugars");
                    proteinas = volumeInfo.getDouble("protein");
                    calcio = volumeInfo.getDouble("calcium");
                    hierro = volumeInfo.getDouble("iron");
                    potasio = volumeInfo.getDouble("potassium");
                    vitaminaA = volumeInfo.getDouble("vitamin a");
                    vitaminaC = volumeInfo.getDouble("vitamin c");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alimento = new Alimento();
                alimento.setCalorias(calorias);
                alimento.setNombre(nombre);
                alimento.setAzucares(azucares);
                alimento.setCalcio(calcio);
                alimento.setColesterol(colesterol);
                alimento.setFibra(fibra);
                alimento.setGrasaSaturada(grasaSaturada);
                alimento.setSodio(sodio);
                alimento.setProteinas(proteinas);
                alimento.setHierro(hierro);
                alimento.setPotasio(potasio);
                alimento.setVitaminaA(vitaminaA);
                alimento.setVitaminaC(vitaminaC);

                DatosAlimentos.getInstance().getAlimentos().add(alimento);
                // Move to the next item.
                i++;
            }
            return  tAlimentos;
        } catch (Exception e) {
            Log.w("Fit", e.toString());
            e.printStackTrace();
        }
        return food;
    }

    private static String[] generateOauthParams() {
        return new String[]{
            "oauth_consumer_key=" + APP_KEY,
            "oauth_signature_method=HMAC-SHA1",
            "oauth_timestamp=" +
                Long.valueOf(System.currentTimeMillis() * 2).toString(),
            "oauth_nonce=" + nonce(),
            "oauth_version=1.0",
            "format=json"};
    }


    private static String sign(String method, String uri, String[] params) {
        String[] p = {method, Uri.encode(uri), Uri.encode(paramify(params))};
        String s = join(p, "&");
        SecretKey sk = new SecretKeySpec(APP_SECRET.getBytes(), HMAC_SHA1_ALGORITHM);
        try {
            Mac m = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            m.init(sk);
            return Uri.encode(new String(Base64.encode(m.doFinal(s.getBytes()), Base64.DEFAULT)).trim());
        } catch (java.security.NoSuchAlgorithmException e) {
            Log.w("FatSecret_TEST FAIL", e.getMessage());
            return null;
        } catch (java.security.InvalidKeyException e) {
            Log.w("FatSecret_TEST FAIL", e.getMessage());
            return null;
        }
    }

    private static String paramify(String[] params) {
        String[] p = Arrays.copyOf(params, params.length);
        Arrays.sort(p);
        return join(p, "&");
    }

    private static String join(String[] array, String separator) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0)
                b.append(separator);
            b.append(array[i]);
        }
        return b.toString();
    }

    private static String nonce() {
        Random r = new Random();
        StringBuilder n = new StringBuilder();
        for (int i = 0; i < r.nextInt(8) + 2; i++)
            n.append(r.nextInt(26) + 'a');
        return n.toString();
    }
}
