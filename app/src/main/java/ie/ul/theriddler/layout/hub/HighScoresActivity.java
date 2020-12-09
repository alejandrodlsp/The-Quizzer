package ie.ul.theriddler.layout.hub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import ie.ul.theriddler.R;
import ie.ul.theriddler.database.DatabaseHandler;
import ie.ul.theriddler.layout.login.LoginRegisterActivity;
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

        TextView highscoresScores = (TextView) findViewById(R.id.highscoresScores);
        Button logoutButn = (Button) findViewById(R.id.logout_button);

        if(FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            logoutButn.setEnabled(true);
            logoutButn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent ( HighScoresActivity.this, LoginRegisterActivity.class));
                }
            });
        } else
        {
            logoutButn.setEnabled(false);
        }

        // Add event listener to main hub button
        Button main_hub_button = (Button) findViewById(R.id.main_hub_button);
        main_hub_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent (HighScoresActivity.this, MainHubActivity.class));
            }
        });

        String builder = "";
        for(final Question.Category category : Question.Category.values()) {
            builder += category.ToString() + ":";
            int highscore = DatabaseHandler.GetInstance().GetCategoryHighscore(category);
            builder += highscore + "\n";
        }

        highscoresScores.setText(builder);

        String rankBuilder = "Total Ranking: ";
        int rank = DatabaseHandler.GetInstance().GetTotalRanking();
        rankBuilder += rank;
    }
}