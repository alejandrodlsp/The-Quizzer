package ie.ul.theriddler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import ie.ul.theriddler.hub.MainHubActivity;
import ie.ul.theriddler.login.LoginRegisterActivity;
import ie.ul.theriddler.questions.IOnAPIQueryCallback;
import ie.ul.theriddler.questions.Question;
import ie.ul.theriddler.questions.QuestionHandler;

public class MainActivity extends AppCompatActivity implements IOnAPIQueryCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        QuestionHandler handler = new QuestionHandler(this, requestQueue);
        handler.QueryAPI(Question.Category.ALL, Question.Difficulty.EASY, 10);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginRegisterActivity.class));
            }
        });

        Button guestButton = (Button) findViewById(R.id.guestButton);
        guestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent (MainActivity.this, MainHubActivity.class));
            }
        });
    }

    @Override
    public void OnAPIQueryCallback(ArrayList<Question> questions) {
        /** TextView tv = findViewById(R.id.sampleText);

        StringBuilder sb = new StringBuilder();
        for(Question qs : questions)
        {
            sb.append(qs.mQuestion);
            sb.append("\n");
        }
        tv.setText(sb.toString()); **/

    }


}