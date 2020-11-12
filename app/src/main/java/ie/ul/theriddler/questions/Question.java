package ie.ul.theriddler.questions;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.Optional;

public class Question
{
    public enum Category {
        ALL(0, "All categories"),
        GENERAL_KNOWLEDGE(9, "General Knowledge"),
        MOVIES(11, "Movies"),
        MUSIC(12, "Music"),
        TV(14, "Television"),
        VIDEO_GAMES(15, "Video Games"),
        NATURE(17, "Nature"),
        COMPUTERS(18, "Computers"),
        SPORTS(21, "Sports"),
        GEOGRAPHY(22, "Geography"),
        HISTORY(23, "History"),
        ART(25, "Art"),
        ANIMALS(27, "Animals"),
        VEHICLES(28, "Vehicles");

        private final int mValue;
        private final String mTitle;
        Category(final int value, final String title)
        {
            mValue = value; mTitle = title;
        }
        public int GetCategoryValue() { return mValue; };
        public String ToString() { return mTitle; };

        public static Category valueOf(int index) {
            for (Category l : Category.values()) {
                if (l.GetCategoryValue() == index) return l;
            }
            throw new IllegalArgumentException("Category not found!");
        }
    };

    public enum Difficulty {
        ANY("0"),
        EASY("easy"),
        MEDIUM("medium"),
        HARD("hard");

        private final String mValue;
        Difficulty(final String value)
        {
            mValue = value;
        }
        public String GetDifficultyValue() { return mValue; };
    };

    public String mQuestion;
    public String mCorrectAnswer;
    public String[] mIncorrectAnswers;
}