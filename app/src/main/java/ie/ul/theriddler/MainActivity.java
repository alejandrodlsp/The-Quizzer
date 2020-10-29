package ie.ul.theriddler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

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
    }

    @Override
    public void OnAPIQueryCallback(ArrayList<Question> questions) {
        TextView tv = findViewById(R.id.sampleText);

        StringBuilder sb = new StringBuilder();
        for(Question qs : questions)
        {
            sb.append(qs.mQuestion);
            sb.append("\n");
        }
        tv.setText(sb.toString());
    }
}