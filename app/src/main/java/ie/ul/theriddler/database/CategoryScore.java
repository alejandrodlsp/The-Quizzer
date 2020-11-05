package ie.ul.theriddler.database;

import ie.ul.theriddler.questions.Question;

public class CategoryScore
{
    public Question.Category mCategory;
    public int mScore;

    public CategoryScore(Question.Category category, int score)
    {
        mCategory = category;
        mScore = score;
    }
}
