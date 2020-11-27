package ie.ul.theriddler.layout.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Random;

import ie.ul.theriddler.R;
import ie.ul.theriddler.layout.hub.HighScoresActivity;
import ie.ul.theriddler.layout.hub.MainHubActivity;
import ie.ul.theriddler.questions.IOnAPIQueryCallback;
import ie.ul.theriddler.questions.Question;
import ie.ul.theriddler.questions.QuestionHandler;

public class GameFragment extends Fragment implements IOnAPIQueryCallback {

    private QuestionHandler mQuestionHandler;
    
    private CountDownTimer mCountdownTimer;
    private final long kMaxTimeMilliseconds = 6000;
    private long mTimeLeftMilliseconds = 0;

    private Question mCurrentQuestion;
    private int mCorrectIndex;
    private Question.Category mCurrentCategory;
    private final Question.Difficulty kDifficulty = Question.Difficulty.MEDIUM;

    private int mCorrectAnswerCount;

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        Activity currentActivity = getActivity();
        if(!(currentActivity instanceof GameNavActivity))
            return;             // TODO: Error handling

        /* Get category from game nav activity */
        GameNavActivity activity = (GameNavActivity) currentActivity;
        int category = activity.GetCategoryIndex();
        mCurrentCategory = Question.Category.valueOf(category);

        /* Initialize request queue and question handler */
        RequestQueue requestQueue = Volley.newRequestQueue(currentActivity);
        mQuestionHandler = new QuestionHandler(this, requestQueue);
        mQuestionHandler.QueryAPI(mCurrentCategory, kDifficulty, 1);

        UpdateTimerText();

        /* Initialize parameters */
        mCorrectAnswerCount = 0;
        mCorrectIndex = -1;
        mCurrentQuestion = null;

        /* Button event listeners */
        Button question1Button = (Button) currentActivity.findViewById(R.id.GameAnswer1);
        question1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { OnAnswerClicked(0); }
        });
        Button question2Button = (Button) currentActivity.findViewById(R.id.GameAnswer2);
        question2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { OnAnswerClicked(1); }
        });
        Button question3Button = (Button) currentActivity.findViewById(R.id.GameAnswer3);
        question3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { OnAnswerClicked(2); }
        });
        Button question4Button = (Button) currentActivity.findViewById(R.id.GameAnswer4);
        question4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { OnAnswerClicked(3); }
        });
        Button exitButton = (Button) currentActivity.findViewById(R.id.GamExitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            // TODO: Go back to main hub
            @Override
            public void onClick(View v) { activity.NavigateMainHub(); }
        });
    }

    @Override
    public void OnAPIQueryCallback(ArrayList<Question> questions) {
        if(questions.size() < 1) return; // TODO: Error handling

        View view = getView();
        if(view == null) return; // TODO: Error handling

        mCurrentQuestion = questions.get(0);

        TextView questionTitle = (TextView) getView().findViewById(R.id.GameQuestion);
        questionTitle.setText(mCurrentQuestion.mQuestion);

        mCorrectIndex = new Random().nextInt(4);
        int buttonIndex = 0;
        for(int i = 0; i < 4; i ++)
        {
            if(i == mCorrectIndex)
                GetButtonByIndex(i).setText(mCurrentQuestion.mCorrectAnswer);
            else {
                GetButtonByIndex(i).setText(mCurrentQuestion.mIncorrectAnswers[buttonIndex]);
                buttonIndex++;
            }
        }

        StartTimer();
    }

    Button GetButtonByIndex(int index)
    {
        switch(index)
        {
            default:
            case 0: return ((Button)getView().findViewById(R.id.GameAnswer1));
            case 1: return ((Button)getView().findViewById(R.id.GameAnswer2));
            case 2: return ((Button)getView().findViewById(R.id.GameAnswer3));
            case 3: return ((Button)getView().findViewById(R.id.GameAnswer4));
        }
    }

    void OnAnswerClicked(int index)
    {
        if(mCurrentQuestion == null) return;
        if(mCountdownTimer != null) mCountdownTimer.cancel();
        if(index == mCorrectIndex) OnCorrectAnswer();
        else OnInCorrectAnswer();
    }

    void OnCorrectAnswer()
    {
        mCurrentQuestion = null;
        mCorrectAnswerCount ++;
        mQuestionHandler.QueryAPI(mCurrentCategory, Question.Difficulty.MEDIUM, 1);
    }

    void OnInCorrectAnswer()
    {
        EndGame();
    }

    void EndGame()
    {
        Bundle bundle = new Bundle();
        bundle.putInt("CORRECT_ANSWERS", mCorrectAnswerCount);
        Navigation.findNavController(getView()).navigate(R.id.action_gameFragment_to_scoreFragment, bundle);
    }

    void StartTimer()
    {
        if(mCountdownTimer != null) mCountdownTimer.cancel();

        mTimeLeftMilliseconds = kMaxTimeMilliseconds;
        mCountdownTimer = new CountDownTimer(kMaxTimeMilliseconds, 1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftMilliseconds = l;
                UpdateTimerText();
            }

            @Override
            public void onFinish() {
                EndGame();
            }
        }.start();
    }

    void UpdateTimerText()
    {
        int seconds = (int) mTimeLeftMilliseconds % 60000 / 1000;
        int millis = (int) mTimeLeftMilliseconds % 1000 / 100;

        String timeString = "";
        if(seconds < 10) timeString += "0";
        timeString += seconds;
        if(millis < 10) timeString += "0";
        timeString += millis;

        TextView tv = getView().findViewById(R.id.GameTimer);
        tv.setText(timeString);
    }
}