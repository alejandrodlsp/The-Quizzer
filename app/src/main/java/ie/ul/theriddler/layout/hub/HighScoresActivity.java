package ie.ul.theriddler.layout.hub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ie.ul.theriddler.R;
import ie.ul.theriddler.database.DatabaseHandler;
import ie.ul.theriddler.questions.Question;

/**
 *
 */
public class HighScoresActivity extends AppCompatActivity {

    /**
     * OnCreate Override
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        // Add event listener to main hub button
        Button main_hub_button = (Button) findViewById(R.id.main_hub_button);
        main_hub_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent (HighScoresActivity.this, MainHubActivity.class));
            }
        });
    }
}