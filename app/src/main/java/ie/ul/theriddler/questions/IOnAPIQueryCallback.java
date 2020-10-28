package ie.ul.theriddler.questions;

import java.util.ArrayList;

/**
 * Implement to be able to receive callbacks when a question set is queried from the OpenTriviaDB API
 */
public interface IOnAPIQueryCallback {
    /**
     * @param questions the result from the question query to the OpenTriviaDB API
     */
    public void OnAPIQueryCallback(ArrayList<Question> questions);
}
