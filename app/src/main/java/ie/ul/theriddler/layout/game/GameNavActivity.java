package ie.ul.theriddler.layout.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import ie.ul.theriddler.R;
import ie.ul.theriddler.layout.hub.MainHubActivity;
import ie.ul.theriddler.questions.Question;

/**
 *
 */
public class GameNavActivity extends AppCompatActivity {

    private Question.Category mCategory;        // Current category
    private int mCategoryIndex;                 // Current category index
    private boolean mIsTriviathon;              // Is triviathon gamemode
    public int mCurrentScore;                   // Current user score

    /**
     * OnCreate override
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_nav);

        mCurrentScore = 0;

        mCategoryIndex = getIntent().getIntExtra("CATEGORY_INDEX", 0);
        mIsTriviathon = getIntent().getBooleanExtra("IS_TRIVIATHON", false);
        mCategory = Question.Category.valueOf(mCategoryIndex);
    }

    /**
     * @return Current category index
     */
    public int GetCategoryIndex()
    {
        return mCategoryIndex;
    }

    /**
     * @return Current category
     */
    public Question.Category GetCategory()
    {
        return mCategory;
    }

    /**
     * @return True if triviathon mode is selected, false otherwise
     */
    public boolean IsTriviathon() { return mIsTriviathon; }

    /**
     * Navigates to main hub menu
     */
    public void NavigateMainHub()
    {
        Intent toNewActivity = new Intent(this, MainHubActivity.class);
        startActivity(toNewActivity);
    }
}