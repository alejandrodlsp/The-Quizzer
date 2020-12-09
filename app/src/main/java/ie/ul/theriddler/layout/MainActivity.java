package ie.ul.theriddler.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import ie.ul.theriddler.R;
import ie.ul.theriddler.layout.hub.MainHubActivity;
import ie.ul.theriddler.layout.login.LoginRegisterActivity;

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

        // If logged in already redirect to main hub automatically
        if(FirebaseAuth.getInstance().getCurrentUser().getUid() != null)
        {
            startActivity(new Intent (MainActivity.this, MainHubActivity.class));
        }
    }


}