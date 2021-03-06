package ie.ul.theriddler.layout.hub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.Hashtable;

import ie.ul.theriddler.R;
import ie.ul.theriddler.database.DatabaseHandler;
import ie.ul.theriddler.layout.game.GameNavActivity;
import ie.ul.theriddler.layout.login.LoginRegisterActivity;

/**
 *
 */
public class MainHubActivity extends AppCompatActivity {

    /**
     * Key: Button R.id
     * Value: Category index associated with button
     */
    private static final Hashtable<Integer, Integer> kCategoryButtonTable = new Hashtable<Integer, Integer>()
    {{  put(R.id.all_questions_button, 0);
        put(R.id.general_knowledge_button, 9);
        put(R.id.movies_button, 11);
        put(R.id.music_button, 12);
        put(R.id.tv_button, 14);
        put(R.id.video_games_button, 15);
        put(R.id.nature_button, 17);
        put(R.id.computers_button, 18);
        put(R.id.sports_button, 21);
        put(R.id.geography_button, 22);
        put(R.id.history_button, 23);
        put(R.id.art_button, 25);
        put(R.id.animals_button, 27);
        put(R.id.vehicles_button, 28);
    }};

    /**
     * OnCreate override
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hub);

        TextView rankingView = (TextView) findViewById(R.id.rankingView);
        TextView total_points_textView = (TextView) findViewById(R.id.total_points_textView);

        String rankBuilder = "Current Rank: ";
        String totalPoints = "Total Points: ";

        String rank = "?";
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
            rank = Integer.toString( DatabaseHandler.GetInstance().GetTotalRanking() );
        rankBuilder += rank;
        rankingView.setText(rankBuilder);

        String points = "?";
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
            points = Integer.toString( DatabaseHandler.GetInstance().GetTotalAnsweredQuestions() );
        totalPoints += points;
        total_points_textView.setText(totalPoints);

        /* Go trough all buttons and add OnClick event listeners */

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            Button high_score_button = (Button) findViewById(R.id.high_score_button);
            high_score_button.setText("CHECK HIGHSCORES");
            high_score_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainHubActivity.this, HighScoresActivity.class));
                }
            });
        }else{
            Button high_score_button = (Button) findViewById(R.id.high_score_button);
            high_score_button.setText("LOG IN");
            high_score_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent (MainHubActivity.this, LoginRegisterActivity.class));
                }
            });
        }

        /*
         * Loop trough category button hashtable and add onClick listeners to each one of them
         * */
        for(int rId : kCategoryButtonTable.keySet()) {
            int categoryIndex = kCategoryButtonTable.get(rId);
            // Add event listener for each button
            Button btn = (Button) findViewById(rId);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnCategoryButtonClick(categoryIndex);
                }
            });
        }
    }

    /**
     * @param CategoryIndex
     */
    private void OnCategoryButtonClick(int CategoryIndex)
    {
        Switch triviathon_switch = (Switch) findViewById(R.id.TriviathonSwitch);
        boolean isTriviathon = triviathon_switch.isChecked();
        Intent toNewActivity = new Intent(MainHubActivity.this, GameNavActivity.class);
        // Intent extra
        toNewActivity.putExtra("CATEGORY_INDEX", CategoryIndex);
        toNewActivity.putExtra("IS_TRIVIATHON", isTriviathon);
        // Goto activity
        startActivity(toNewActivity);
    }

}