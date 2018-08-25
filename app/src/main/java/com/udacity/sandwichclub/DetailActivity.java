package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // Begin by setting to empty strings
        String alsoKnownAsFormattedString = "";
        String ingredientsFormattedString = "";

        //Initialize the TextViews
        TextView alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        TextView originTextView = findViewById(R.id.origin_tv);
        TextView descriptionTextView = findViewById(R.id.description_tv);
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);

        // get alsoKnownAs strings and place a comma and space after the string
        for (String string: sandwich.getAlsoKnownAs()) {
            alsoKnownAsFormattedString += string + ", ";
        }

        // remove the comma and space (, ) at the end of the string.
        if(alsoKnownAsFormattedString.length() > 0) {
            alsoKnownAsFormattedString = alsoKnownAsFormattedString.substring(0, alsoKnownAsFormattedString.length()-2);
        }

        // Set the TextView to the values in the Formatted String
        alsoKnownAsTextView.setText(replaceMissingString(alsoKnownAsFormattedString));

        // Set the text in originTextView
        originTextView.setText(replaceMissingString(sandwich.getPlaceOfOrigin()));
        // Set the text in descriptionTextView
        descriptionTextView.setText(replaceMissingString(sandwich.getDescription()));

        // Set ingredients on a new line
        for (String string : sandwich.getIngredients()) {
            ingredientsFormattedString += string + "\n";
        }
        // Set text on ingredientsTextView after the new line has been added after each ingredient
        ingredientsTextView.setText(replaceMissingString(ingredientsFormattedString));
    }

    // replace with missing info text with "No Data".
    private String replaceMissingString(String string) {
        if (string.equals("") || string.length() < 0) {
            return "No Data";
        } else {
            return string;
        }
    }
}
