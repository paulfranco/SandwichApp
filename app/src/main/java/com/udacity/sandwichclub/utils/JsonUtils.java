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

        try{
            JSONObject jsonObject = new JSONObject(json);

            JSONObject nameObject = jsonObject.getJSONObject("name");
            String mainName = nameObject.getString("mainName");
            JSONArray alsoKnownAs = nameObject.getJSONArray("alsoKnownAs");
            ArrayList<String> alsoKnownAsArrayList = new ArrayList<>();
            int looper = 0;
            while(looper <alsoKnownAs.length())
            {
                alsoKnownAsArrayList.add(alsoKnownAs.getString(looper));
                looper += 1;
            }

            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            String description = jsonObject.getString("description");
            String imageSrc = jsonObject.getString("image");
            JSONArray ingredients = jsonObject.getJSONArray("ingredients");
            ArrayList<String> ingredientsArrayList = new ArrayList<>();
            looper = 0;
            while (looper < ingredients.length())
            {
                ingredientsArrayList.add(ingredients.getString(looper));
                looper += 1;
            }
            return new Sandwich(mainName, alsoKnownAsArrayList, placeOfOrigin, description, imageSrc, ingredientsArrayList);

        }
        catch(JSONException e)
        {
            Log.i(TAG, "An error had occurred on JSON Parsing");
            e.printStackTrace();
            return null;
        }
    }
}
