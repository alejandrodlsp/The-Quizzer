package ie.ul.theriddler.questions;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ie.ul.theriddler.MainActivity;

public class QuestionHandler
{
    /* OpenTriviaDB API Endpoint */
    public final static String kAPI_ENDPOINT = "https://opentdb.com/api.php";
    /* Total number of questions to query for */
    public final static int kQUESTION_SET_SIZE = 100;

    // Callback implementation to call on a successful API call to the OpenTriviaDB API
    private IOnAPIQueryCallback mOnAPIQueryCallback = null;
    // Queue of API requests waiting to be executed
    private RequestQueue mRequestQueue = null;

    /**
     * @param callback interface implementation of IOnAPIQueryCallback to call on a successful query to the API
     */
    public QuestionHandler(IOnAPIQueryCallback callback)
    {
        // Retrieve context from main activity ; TODO: change from main activity to game nav activity
        Context context = MainActivity.GetContext();
        if(context == null) { /* TODO: error handling*/ }

        mOnAPIQueryCallback = callback;
        mRequestQueue = Volley.newRequestQueue(context);
    }

    /**
     * Queries the OpenTriviaDB API and handles the response, calls mOnALIQueryCallback when request is finished processing
     * @param category the category of the questions
     * @param difficulty the difficulty of the questions
     */
    public void QuerryAPI(Question.Category category, Question.Difficulty difficulty)
    {
        // Create request object
        JsonObjectRequest jsonRequestObject = new JsonObjectRequest(
                Request.Method.GET,
                GetApiEndpointFromQuestionParams(category, difficulty),
                new JSONObject(),
                // On success listener
                new com.android.volley.Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        OnAPIQuerryCallback(response);
                    }
                },
                // On error listener
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error: ", error.toString());
                    }
                }
        );

        // Add request to be processed by the request queue
        mRequestQueue.add(jsonRequestObject);
    }

    /**
     * Gets the endpoint (URL) of the OpenTriviaDB API using a set of parameters to specify the data to be expected
     * @param category the category of the questions
     * @param difficulty the difficulty of the questions
     * @return API endpoint to query from using the specified parameters
     */
    private String GetApiEndpointFromQuestionParams(Question.Category category, Question.Difficulty difficulty)
    {
        return kAPI_ENDPOINT +
                "?amount=" + kQUESTION_SET_SIZE +
                (category == Question.Category.ALL ? "" : "&category=" + category.GetCategoryValue()) +
                (difficulty == Question.Difficulty.ANY ? "" : "&difficulty=" + difficulty.GetDifficultyValue()) +
                "&type=multiple";
    }

    /**
     * Called on a successful request to the OpenTriviaDB API
     * @param response response from the API as a JSON Object
     */
    private void OnAPIQuerryCallback(JSONObject response)
    {
        String responseData = response.toString();
        JSONObject object = response;

        try {
            // Validate JSON object
            object = new JSONObject(responseData);

            // Validate response code
            switch (response.getInt("response_code"))
            {
                case 0: break;
                case 1: /* TODO: handle 'No Results: Could not return results.' */ break;
                case 2: /* TODO: handle 'Invalid Parameter: Contains an invalid parameter.' */ break;
                case 3: /* TODO: handle 'Token Not Found: Session Token does not exist.' */ break;
                case 4: /* TODO: handle 'Token Empty: Session Token has returned all possible questions for the specified query.' */ break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<Question> questionSet = GenerateQuestionSetFromJsonObject(object);
        // Call registered OnAPIQueryCallback with array list of Question objects
        if(mOnAPIQueryCallback != null)
            mOnAPIQueryCallback.OnAPIQueryCallback( questionSet );
    }

    /**
     * Deserializes API request from JSON object to ArrayList of Question objects
     * @param json JSON object containing the result from a API call to the OpenTriviaDB API
     * @return list of questions objects deserialize from the JSON object
     */
    private ArrayList<Question> GenerateQuestionSetFromJsonObject(JSONObject json)
    {
        // Initialize question set
        ArrayList<Question> questionSet = new ArrayList<Question>();

        try {
            // Get results as a JSON array
            JSONArray resultsJSON = json.getJSONArray("results");

            // For each result
            for(int i = 0; i < resultsJSON.length(); i++)
            {
                JSONObject questionJSON = resultsJSON.getJSONObject(i);

                // Create a question object and deserialize JSON object into parameters

                Question question = new Question();
                question.mQuestion = questionJSON.getString("question");
                question.mCorrectAnswer = questionJSON.getString("correct_answer");

                JSONArray incorrectAnswersJSON = questionJSON.getJSONArray("incorrect_answers");
                String[] incorrectAnswers = new String[3];
                for(int j = 0; j < incorrectAnswersJSON.length(); j++)
                    incorrectAnswers[j] = incorrectAnswersJSON.getString(i);
                question.mIncorrectAnswers = incorrectAnswers;

                questionSet.add(question);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return questionSet;
    }

} // CLASS
