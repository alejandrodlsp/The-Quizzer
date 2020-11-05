package ie.ul.theriddler.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ie.ul.theriddler.questions.Question;

public class DatabaseHandler {

    /* Singleton instance */
    private static DatabaseHandler mInstance;

    /**
     * Thread safe singleton getter
     * @return singleton instance of DatabaseHandler
     */
    public static synchronized DatabaseHandler GetInstance()
    {
        if(mInstance == null) mInstance = new DatabaseHandler();
        return mInstance;
    }

    // Reference to the firebase real time database
    FirebaseDatabase mDatabase;
    // Reference to the categories database
    DatabaseReference mCategoriesDatabase;
    // Reference to the users database
    DatabaseReference mUsersDatabase;
    // User ID
    String mUserID;

    private int mUserTotalQuestions;
    private ArrayList<CategoryScore> mCategoryScores;

    /**
     * DatabaseHandler constructor
     */
    private DatabaseHandler()
    {
        if(FirebaseAuth.getInstance().getCurrentUser().getUid() == null)
            Log.w("AUTH", "Could not generate database handler, user is not authenticated");

        mDatabase = FirebaseDatabase.getInstance();
        mCategoriesDatabase = mDatabase.getReference("categories");
        mUsersDatabase = mDatabase.getReference("users");

        mUserID =  FirebaseAuth.getInstance().getCurrentUser().getUid();
        InitializeCategoriesDatabase(mUserID);
        InitializeUserDatabase(mUserID);
    }

    /**
     * Fetches categories database scores for specified user
     * @param uid User ID for db
     */
    private void InitializeCategoriesDatabase(String uid)
    {
        mCategoryScores = new ArrayList<CategoryScore>();
        for(final Question.Category category : Question.Category.values())
        {
            mCategoryScores.add(new CategoryScore(category, 0));
            DatabaseReference dbr = mCategoriesDatabase.child(category.toString()).child(uid);
            dbr.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int score = snapshot.getValue(int.class);
                    for(CategoryScore sc : mCategoryScores)
                    {
                        if(sc.mCategory == category)
                            sc.mScore = score;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w("DB","Failed to read DB value for category.", error.toException());
                }
            });
        }
    }

    /**
     * Fetches total answered questions for a specific user
     * @param uid User ID for db
     */
    private void InitializeUserDatabase(String uid)
    {
        mUserTotalQuestions = 0;
        DatabaseReference dbr = mUsersDatabase.child(uid);
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int answeredQuestions = snapshot.getValue(int.class);
                mUserTotalQuestions = answeredQuestions;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("DB","Failed to read DB value for category.", error.toException());
            }
        });
    }

    /**
     * @return Total answered questions for authenticated user
     */
    public int GetTotalAnsweredQuestions()
    {
        return mUserTotalQuestions;
    }

    /**
     * Increments total answered questions for authenticated user by 1 and saves to DB
     */
    public void IncrementTotalAnsweredQuestions()
    {
        mUserTotalQuestions ++;
        mUsersDatabase.child(mUserID).setValue(mUserTotalQuestions);
    }

    /**
     * Gets highscore for specified category for current authenticated user
     * @param category Category to fetch highscore from
     * @return highscore for current user for specified category
     */
    public int GetCategoryHighscore(Question.Category category)
    {
        for(CategoryScore score : mCategoryScores)
        {
            if(score.mCategory == category)
                return score.mScore;
        }
        return 0;
    }

    /**
     * Sets highscore value for specified category DB
     * @param category Category to set highscore from
     * @param score Score to save into DB
     */
    public void SetCategoryHighscore(Question.Category category, int score)
    {
        mCategoriesDatabase.child(category.toString()).child(mUserID).setValue(score);
    }

    /**
     * Sets highscore value for specified category only if score is greater to score value saved in DB
     * @param category Category to set highscore from
     * @param score Score to save into DB
     */
    public void SetCategoryScore(Question.Category category, int score)
    {
        if(score > GetCategoryHighscore(category))
        {
            SetCategoryHighscore(category, score);
        }
    }

}
