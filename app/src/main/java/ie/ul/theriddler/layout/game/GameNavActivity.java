package ie.ul.theriddler.layout.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import ie.ul.theriddler.R;
import ie.ul.theriddler.layout.hub.MainHubActivity;

public class GameNavActivity extends AppCompatActivity {

    private int mCategoryIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_nav);

        mCategoryIndex = getIntent().getIntExtra("CATEGORY_INDEX", 0);
    }

    public int GetCategoryIndex()
    {
        return mCategoryIndex;
    }

    public void NavigateMainHub()
    {
        Intent toNewActivity = new Intent(this, MainHubActivity.class);
        startActivity(toNewActivity);
    }

}