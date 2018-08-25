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
        String alsoKnownAsFormattedString = "";
        String ingredientsFormattedString = "";

        //TextView nameTextView = findViewById(R.id.name_tv);
        TextView alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        TextView originTextView = findViewById(R.id.origin_tv);
        TextView descriptionTextView = findViewById(R.id.description_tv);
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);

        //nameTextView.setText(replaceMissingString(sandwich.getMainName()));


        for (String string: sandwich.getAlsoKnownAs())
        {
            alsoKnownAsFormattedString += string + ", ";
        }

        // used substring to remove the last two characters - this prevents displaying , at the end of the string.
        if(alsoKnownAsFormattedString.length() > 0)
        {
            alsoKnownAsFormattedString = alsoKnownAsFormattedString.substring(0, alsoKnownAsFormattedString.length()-2);
        }

        alsoKnownAsTextView.setText(replaceMissingString(alsoKnownAsFormattedString));



        originTextView.setText(replaceMissingString(sandwich.getPlaceOfOrigin()));
        descriptionTextView.setText(replaceMissingString(sandwich.getDescription()));



        for (String string : sandwich.getIngredients())
        {
            ingredientsFormattedString += string + "\n";
        }

        ingredientsTextView.setText(replaceMissingString(ingredientsFormattedString));

    }
    // Helper method to validate the given string value and replace with missing info text if does not have data.
    private String replaceMissingString(String string)
    {
        if (string.equals("") || string.length() < 0)
        {
            return "There is data for sandwich";
        }
        else
        {
            return string;
        }
    }
}
