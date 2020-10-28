package ie.ul.theriddler.questions;

public class Question
{
    public enum Category {
        ALL(0),
        GENERAL_KNOWLEDGE(9),
        SCIENCE_AND_NATURE(17),
        HISTORY(23),
        ART(25),
        ANIMALS(27),
        CELEBRITIES(26),
        POLITICS(24),
        GEOGRAPHY(22),
        SPORT(21);

        private final int mValue;
        Category(final int value)
        {
            mValue = value;
        }
        public int GetCategoryValue() { return mValue; };
        };

    public enum Difficulty {
        ANY("0"), EASY("easy"), MEDIUM("medium"), HARD("hard");

        private final String mValue;
        Difficulty(final String value)
        {
            mValue = value;
        }
        public String GetDifficultyValue() { return mValue; };
    };

    public Category mCategory;
    public Difficulty mDifficulty;
    public String mQuestion;
    public String mCorrectAnswer;
    public String[] mIncorrectAnswers;
}