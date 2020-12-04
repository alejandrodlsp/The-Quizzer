package ie.ul.theriddler.layout.game;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import ie.ul.theriddler.R;
import ie.ul.theriddler.database.DatabaseHandler;
import ie.ul.theriddler.questions.IOnAPIQueryCallback;
import ie.ul.theriddler.questions.Question;
import ie.ul.theriddler.questions.QuestionHandler;

/**
 *
 */
public class GameFragment extends Fragment implements IOnAPIQueryCallback {

    private QuestionHandler mQuestionHandler;           // Instance of a QuestionHandler object

    private CountDownTimer mCountdownTimer;             // Instance of the current countdown timer; null if no timer is active
    private final long kMaxTimeMilliseconds = 15000;    // Starting value of timer in milliseconds
    private final long kMaxTimeMillisecondsTriviathon = 60000;  // Starting value of timer in milliseconds for triviathon gamemode
    private long mTimeLeftMilliseconds = 0;             // Current timer value in milliseconds

    private Question mCurrentQuestion;                  // Current active question; null if no question is active (waiting for API callback)
    private int mCorrectIndex;                          // Index of the correct answer for current question
    private Question.Category mCurrentCategory;         // Active category to query questions from
    private final Question.Difficulty kDifficulty = Question.Difficulty.EASY; // Active difficulty to query questions from

    private GameNavActivity mActivity;                           // Parent activity

    private int mCorrectAnswerCount;                    // Count of total correct answered questions (Score)
    private boolean mTriviathonStarted = false;                 // State of triviathon gamemode

    /**
     * Required empty public constructor
     */
    public GameFragment() {
    }

    /**
     * On create view override method from Android fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return Inflated layout
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    /**
     * Overrides callback called when fragment view is created
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        /* Call super method */
        super.onViewCreated(view, savedInstanceState);

        /* Gets current GameNav activity */
        Activity currentActivity = getActivity();
        if(!(currentActivity instanceof GameNavActivity))
            return;             // TODO: Error handling
        mActivity = (GameNavActivity) currentActivity;

        /* Gets current category from GameNavActivity */
        int category = mActivity.GetCategoryIndex();
        mCurrentCategory = Question.Category.valueOf(category);

        /* Initialize request queue and question handler */
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
        mQuestionHandler = new QuestionHandler(this, requestQueue);
        /* Queries question API and waits for response */
        mQuestionHandler.QueryAPI(mCurrentCategory, kDifficulty, 1);

        /* Initialize parameters */
        mCorrectAnswerCount = 0;
        mCorrectIndex = -1;
        mCurrentQuestion = null;

        /* Initialize text fields with default values */
        UpdateTimerText();
        UpdateScoreText();

        /* Button event listeners */
        Button question1Button = (Button) mActivity.findViewById(R.id.GameAnswer1);
        question1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { OnAnswerClicked(0); }
        });
        Button question2Button = (Button) mActivity.findViewById(R.id.GameAnswer2);
        question2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { OnAnswerClicked(1); }
        });
        Button question3Button = (Button) mActivity.findViewById(R.id.GameAnswer3);
        question3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { OnAnswerClicked(2); }
        });
        Button question4Button = (Button) mActivity.findViewById(R.id.GameAnswer4);
        question4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { OnAnswerClicked(3); }
        });
        Button exitButton = (Button) mActivity.findViewById(R.id.GamExitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { mActivity.NavigateMainHub(); }
        });
    }

    /**
     * Callback from questions API executed when a successful API question response is received
     * @param questions the result from the question query to the OpenTriviaDB API
     */
    @Override
    public void OnAPIQueryCallback(ArrayList<Question> questions) {
        if(questions.size() < 1) return; // TODO: Error handling

        View view = getView();
        if(view == null) return; // TODO: Error handling

        mCurrentQuestion = questions.get(0);

        /* Change question title */
        TextView questionTitle = (TextView) getView().findViewById(R.id.GameQuestion);
        questionTitle.setText(mCurrentQuestion.mQuestion);

        /* Change question answers randomly */
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

        // If is not triviathon or triviathon is not started yet
        if(!mActivity.IsTriviathon() || !mTriviathonStarted)
            /* Starts question countdown timer*/
            StartTimer();
    }

    /**
     * @param index index of a question answer (Range 0 to 3 inclusive)
     * @return the button view associated with that answer index
     */
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

    /**
     * Called when an answer is clicked
     * @param index index of the answer clicked
     */
    void OnAnswerClicked(int index)
    {
        if(mCurrentQuestion == null) return;
        if(mCountdownTimer != null && !mActivity.IsTriviathon()) mCountdownTimer.cancel();
        if(index == mCorrectIndex) OnCorrectAnswer();
        else OnInCorrectAnswer();

        /* Update score text view UI */
        UpdateScoreText();
    }

    /**
     * Method called when a correct answer is clicked
     */
    void OnCorrectAnswer()
    {
        mCurrentQuestion = null;
        mCorrectAnswerCount ++;

        // If user is logged in
        if(FirebaseAuth.getInstance().getCurrentUser().getUid() != null)
        {
            // Increment total answered questions
            DatabaseHandler.GetInstance().IncrementTotalAnsweredQuestions();
        }
        mQuestionHandler.QueryAPI(mCurrentCategory, kDifficulty, 1);
    }

    /**
     * Method called when an incorrect answer is clicked
     */
    void OnInCorrectAnswer()
    {
        EndGame();
    }

    /**
     * Finishes game and redirects to score fragment
     */
    void EndGame()
    {
        if(mCountdownTimer != null){
            mCountdownTimer.cancel();
        }
        mActivity.mCurrentScore = mCorrectAnswerCount;
        Navigation.findNavController(getView()).navigate(R.id.action_gameFragment_to_scoreFragment);
    }

    /**
     * Starts timer for current question
     */
    void StartTimer()
    {
        mTriviathonStarted = true;

        if(mCountdownTimer != null) mCountdownTimer.cancel();

        if(mActivity.IsTriviathon())
            mTimeLeftMilliseconds = kMaxTimeMillisecondsTriviathon;
        else
            mTimeLeftMilliseconds = kMaxTimeMilliseconds;

        mCountdownTimer = new CountDownTimer(mActivity.IsTriviathon() ? kMaxTimeMillisecondsTriviathon : kMaxTimeMilliseconds, 1000) {
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

    /**
     * Updates UI for timer text view
     */
    void UpdateTimerText()
    {
        int seconds = (int) mTimeLeftMilliseconds % 60000 / 1000;
        int millis = (int) mTimeLeftMilliseconds - (seconds * 1000);

        String timeString = "";
        if(seconds < 10) timeString += "0";
        timeString += seconds;
        if(millis < 10) timeString += "0";
        timeString += millis;

        TextView tv = getView().findViewById(R.id.GameTimer);
        tv.setText(timeString);
    }

    /**
     * Updates UI for score text view
     */
    void UpdateScoreText()
    {
        TextView tv = getView().findViewById(R.id.GameQuestionScore);
        tv.setText("Score: " + mCorrectAnswerCount);
    }
}