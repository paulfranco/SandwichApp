package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private static String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject jsonObject = new JSONObject(json);

            // get the JSONObject
            JSONObject obj = jsonObject.getJSONObject("name");
            String mainName = obj.getString("mainName");
            JSONArray alsoKnownAs = obj.getJSONArray("alsoKnownAs");
            ArrayList<String> alsoKnownAsArrayList = new ArrayList<>();
            int i = 0;
            // loop through the also known as strings
            while(i < alsoKnownAs.length()) {
                alsoKnownAsArrayList.add(alsoKnownAs.getString(i));
                i += 1;
            }

            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            String description = jsonObject.getString("description");
            String imageSrc = jsonObject.getString("image");
            JSONArray ingredients = jsonObject.getJSONArray("ingredients");
            ArrayList<String> ingredientsArrayList = new ArrayList<>();
            i = 0;
            // Loop through the ingredients
            while (i < ingredients.length()) {
                ingredientsArrayList.add(ingredients.getString(i));
                i += 1;
            }
            return new Sandwich(mainName, alsoKnownAsArrayList, placeOfOrigin, description, imageSrc, ingredientsArrayList);

        } catch(JSONException e) {
            Log.i(TAG, "An error occurred during JSON Parsing");
            e.printStackTrace();
            return null;
        }
    }
}
