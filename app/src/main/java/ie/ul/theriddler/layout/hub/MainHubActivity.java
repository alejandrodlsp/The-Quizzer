package ie.ul.theriddler.layout.hub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import ie.ul.theriddler.R;
import ie.ul.theriddler.layout.game.GameNavActivity;

/**
 *
 */
public class MainHubActivity extends AppCompatActivity {

    /**
     * OnCreate override
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hub);

        Switch triviathon_switch = (Switch) findViewById(R.id.TriviathonSwitch);
        boolean isTriviathon = triviathon_switch.isChecked();

        /* Go trough all buttons and add OnClick event listeners */

        Button high_score_button = (Button) findViewById(R.id.high_score_button);
        high_score_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (MainHubActivity.this, HighScoresActivity.class));
            }
        });

        Button all_questions_button = (Button) findViewById(R.id.all_questions_button);
        all_questions_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 0);
                toNewActivity.putExtra("IS_TRIVIATHON", isTriviathon);
                startActivity(toNewActivity);
            }
        });

        Button general_knowledge_button = (Button) findViewById(R.id.general_knowledge_button);
        general_knowledge_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 9);
                toNewActivity.putExtra("IS_TRIVIATHON", isTriviathon);
                startActivity(toNewActivity);
            }
        });

        Button movies_button = (Button) findViewById(R.id.movies_button);
        movies_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 11);
                toNewActivity.putExtra("IS_TRIVIATHON", isTriviathon);
                startActivity(toNewActivity);
            }
        });

        Button music_button = (Button) findViewById(R.id.music_button);
        music_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 12);
                toNewActivity.putExtra("IS_TRIVIATHON", isTriviathon);
                startActivity(toNewActivity);
            }
        });

        Button tv_button = (Button) findViewById(R.id.tv_button);
        tv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 14);
                toNewActivity.putExtra("IS_TRIVIATHON", isTriviathon);
                startActivity(toNewActivity);
            }
        });

        Button video_games_button = (Button) findViewById(R.id.video_games_button);
        video_games_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 15);
                toNewActivity.putExtra("IS_TRIVIATHON", isTriviathon);
                startActivity(toNewActivity);
            }
        });

        Button computers_button = (Button) findViewById(R.id.computers_button);
        computers_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 18);
                toNewActivity.putExtra("IS_TRIVIATHON", isTriviathon);
                startActivity(toNewActivity);
            }
        });

        Button sports_button = (Button) findViewById(R.id.sports_button);
        sports_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 21);
                toNewActivity.putExtra("IS_TRIVIATHON", isTriviathon);
                startActivity(toNewActivity);
            }
        });

        Button geography_button = (Button) findViewById(R.id.geography_button);
        geography_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 22);
                toNewActivity.putExtra("IS_TRIVIATHON", isTriviathon);
                startActivity(toNewActivity);
            }
        });

        Button history_button = (Button) findViewById(R.id.history_button);
        history_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 23);
                toNewActivity.putExtra("IS_TRIVIATHON", isTriviathon);
                startActivity(toNewActivity);
            }
        });

        Button art_button = (Button) findViewById(R.id.art_button);
        art_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 25);
                toNewActivity.putExtra("IS_TRIVIATHON", isTriviathon);
                startActivity(toNewActivity);
            }
        });

        Button animals_button = (Button) findViewById(R.id.animals_button);
        animals_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 27);
                toNewActivity.putExtra("IS_TRIVIATHON", isTriviathon);
                startActivity(toNewActivity);
            }
        });

        Button vehicles_button = (Button) findViewById(R.id.vehicles_button);
        vehicles_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 28);
                toNewActivity.putExtra("IS_TRIVIATHON", isTriviathon);
                startActivity(toNewActivity);
            }
        });

    }

}