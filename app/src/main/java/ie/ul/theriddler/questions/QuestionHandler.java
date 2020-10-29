package ie.ul.theriddler.questions;

import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ie.ul.theriddler.Util;

public class QuestionHandler
{
    /* OpenTriviaDB API Endpoint */
    private final static String kAPI_ENDPOINT = "https://opentdb.com/api.php";
    private final static String kAPI_TOKEN_ENDPOINT = "https://opentdb.com/api_token.php?command=request";

    // Callback implementation to call on a successful API call to the OpenTriviaDB API
    private IOnAPIQueryCallback mOnAPIQueryCallback = null;
    // Queue of API requests waiting to be executed
    private RequestQueue mRequestQueue = null;
    // Session token generated on initialization
    private String mSessionToken = null;

    /**
     * @param callback interface implementation of IOnAPIQueryCallback to call on a successful query to the API
     * @param queue request queue where API requests will be placed for execution
     */
    public QuestionHandler(IOnAPIQueryCallback callback, RequestQueue queue)
    {
        mOnAPIQueryCallback = callback;
        mRequestQueue = queue;
        GenerateSessionToken();
    }

    /**
     * Queries the OpenTriviaDB API and handles the response, calls mOnALIQueryCallback when request is finished processing
     * @param category the category of the questions
     * @param difficulty the difficulty of the questions
     */
    public void QueryAPI(Question.Category category, Question.Difficulty difficulty, int amount)
    {
        // Create request object
        JsonObjectRequest jsonRequestObject = new JsonObjectRequest(
                Request.Method.GET,
                GetApiEndpointFromQuestionParams(category, difficulty, amount),
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
        )
        {
            /* Set priority to immediate*/
            @Override  public Priority getPriority() {  return Priority.NORMAL;  }
        };

        // Add request to be processed by the request queue
        mRequestQueue.add(jsonRequestObject);
    }

    /**
     * Gets the endpoint (URL) of the OpenTriviaDB API using a set of parameters to specify the data to be expected
     * @param category the category of the questions
     * @param difficulty the difficulty of the questions
     * @param amount number of questions to be queried for
     * @return API endpoint to query from using the specified parameters
     */
    private String GetApiEndpointFromQuestionParams(Question.Category category, Question.Difficulty difficulty, int amount)
    {
        return kAPI_ENDPOINT +
                "?amount=" + amount +
                (category == Question.Category.ALL ? "" : "&category=" + category.GetCategoryValue()) +
                (difficulty == Question.Difficulty.ANY ? "" : "&difficulty=" + difficulty.GetDifficultyValue()) +
                "&type=multiple&encode=base64" +
                (mSessionToken == null ? "" : "&token=" + mSessionToken)
                ;
    }

    /**
     * Starts API request to generate a session token and saves new session token state on API response
     */
    private void GenerateSessionToken()
    {
        JsonObjectRequest jsonRequestObject = new JsonObjectRequest(
                Request.Method.GET,
                kAPI_TOKEN_ENDPOINT,
                new JSONObject(),
                new com.android.volley.Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println(  response.getString("response_message") );
                            if (response.getInt("response_code") == 0)
                                mSessionToken = response.getString("token");
                        }
                        catch (JSONException e){ /* TODO: Handle Exception */ }
                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        /* TODO: Handle Exception */
                    }
                }
        )
        {
            /* Set priority to immediate*/
            @Override  public Priority getPriority() {  return Priority.IMMEDIATE;  }
        };

        mRequestQueue.add(jsonRequestObject);
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
                case 3: GenerateSessionToken(); /* TODO: handle 'Token Not Found: Session Token does not exist.' */ break;
                case 4: GenerateSessionToken(); /* TODO: handle 'Token Empty: Session Token has returned all possible questions for the specified query.' */ break;
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
                question.mQuestion = Util.DecodeBase64( questionJSON.getString("question") );
                question.mCorrectAnswer = Util.DecodeBase64( questionJSON.getString("correct_answer") );

                JSONArray incorrectAnswersJSON = questionJSON.getJSONArray("incorrect_answers");
                String[] incorrectAnswers = new String[3];
                for(int j = 0; j < incorrectAnswersJSON.length(); j++)
                    incorrectAnswers[j] = Util.DecodeBase64( incorrectAnswersJSON.getString(j) );
                question.mIncorrectAnswers = incorrectAnswers;

                questionSet.add(question);
            }

        } catch(Exception e){
            System.out.println(e.getStackTrace());
        }

        return questionSet;
    }

} // CLASS
