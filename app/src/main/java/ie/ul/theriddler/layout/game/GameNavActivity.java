package ie.ul.theriddler.layout.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import ie.ul.theriddler.R;
import ie.ul.theriddler.layout.hub.MainHubActivity;
import ie.ul.theriddler.questions.Question;

public class GameNavActivity extends AppCompatActivity {

    private Question.Category mCategory;
    private int mCategoryIndex;
    public int mCurrentScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_nav);

        mCurrentScore = 0;

        mCategoryIndex = getIntent().getIntExtra("CATEGORY_INDEX", 0);
        mCategory = Question.Category.valueOf(mCategoryIndex);
    }

    public int GetCategoryIndex()
    {
        return mCategoryIndex;
    }

    public Question.Category GetCategory() { return mCategory; }

    public void NavigateMainHub()
    {
        Intent toNewActivity = new Intent(this, MainHubActivity.class);
        startActivity(toNewActivity);
    }
}