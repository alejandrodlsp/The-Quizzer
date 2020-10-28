package ie.ul.theriddler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    /* Context singleton */
    protected static Context mContext = null;
    protected static void SetContext(Context ctx) { mContext = ctx; }
    public static Context GetContext() { return mContext; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetContext(this);
    }
}