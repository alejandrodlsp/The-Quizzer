package ie.ul.theriddler.layout.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ie.ul.theriddler.R;

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

}