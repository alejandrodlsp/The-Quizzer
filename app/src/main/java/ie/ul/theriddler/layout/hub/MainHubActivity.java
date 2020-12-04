package ie.ul.theriddler.layout.hub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                startActivity(toNewActivity);
            }
        });

        Button general_knowledge_button = (Button) findViewById(R.id.general_knowledge_button);
        general_knowledge_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 9);
                startActivity(toNewActivity);
            }
        });

        Button movies_button = (Button) findViewById(R.id.movies_button);
        movies_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 11);
                startActivity(toNewActivity);
            }
        });

        Button music_button = (Button) findViewById(R.id.music_button);
        music_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 12);
                startActivity(toNewActivity);
            }
        });

        Button tv_button = (Button) findViewById(R.id.tv_button);
        tv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 14);
                startActivity(toNewActivity);
            }
        });

        Button video_games_button = (Button) findViewById(R.id.video_games_button);
        video_games_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 15);
                startActivity(toNewActivity);
            }
        });

        Button computers_button = (Button) findViewById(R.id.computers_button);
        computers_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 18);
                startActivity(toNewActivity);
            }
        });

        Button sports_button = (Button) findViewById(R.id.sports_button);
        sports_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 21);
                startActivity(toNewActivity);
            }
        });

        Button geography_button = (Button) findViewById(R.id.geography_button);
        geography_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 22);
                startActivity(toNewActivity);
            }
        });

        Button history_button = (Button) findViewById(R.id.history_button);
        history_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 23);
                startActivity(toNewActivity);
            }
        });

        Button art_button = (Button) findViewById(R.id.art_button);
        art_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 25);
                startActivity(toNewActivity);
            }
        });

        Button animals_button = (Button) findViewById(R.id.animals_button);
        animals_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 27);
                startActivity(toNewActivity);
            }
        });

        Button vehicles_button = (Button) findViewById(R.id.vehicles_button);
        vehicles_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
                toNewActivity.putExtra("CATEGORY_INDEX", 28);
                startActivity(toNewActivity);
            }
        });

    }

}