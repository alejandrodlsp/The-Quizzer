package ie.ul.theriddler.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import ie.ul.theriddler.R;
import ie.ul.theriddler.layout.hub.MainHubActivity;
import ie.ul.theriddler.layout.login.LoginRegisterActivity;
import ie.ul.theriddler.questions.IOnAPIQueryCallback;
import ie.ul.theriddler.questions.Question;
import ie.ul.theriddler.questions.QuestionHandler;

/**
 * Main activity
 */
public class MainActivity extends AppCompatActivity  {

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Login button callback
        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginRegisterActivity.class));
            }
        });

        // Guest button callback
        Button guestButton = (Button) findViewById(R.id.guestButton);
        guestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent (MainActivity.this, MainHubActivity.class));
            }
        });
    }


}