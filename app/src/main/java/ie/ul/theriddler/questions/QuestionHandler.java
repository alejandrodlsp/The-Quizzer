package ie.ul.theriddler.questions;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import ie.ul.theriddler.MainActivity;

public class QuestionHandler
{
    public final static String kAPI_ENDPOINT = "https://opentdb.com/api.php";
    public final static int kQUESTION_SET_SIZE = 100;

    private IOnAPIQuerryCallback mOnAPIQuerryCallback;
    private RequestQueue mRequestQueue = null;

    public QuestionHandler(IOnAPIQuerryCallback callback)
    {
        Context context = MainActivity.GetContext();
        if(context == null) { /* TODO: error handling*/ }

        mOnAPIQuerryCallback = callback;
        mRequestQueue = Volley.newRequestQueue(context);
    }

    private String GetApiEndpointFromQuestionParams(Question.Category category, Question.Difficulty difficulty)
    {
        return kAPI_ENDPOINT +
                "?amount=" + kQUESTION_SET_SIZE +
                (category == Question.Category.ALL ? "" : "&category=" + category.GetCategoryValue()) +
                (difficulty == Question.Difficulty.ANY ? "" : "&difficulty=" + difficulty.GetDifficultyValue()) +
                "&type=multiple";
    }

    public void QuerryAPI(Question.Category category, Question.Difficulty difficulty)
    {
        JsonObjectRequest jsonRequestObject = new JsonObjectRequest(
                Request.Method.GET,
                GetApiEndpointFromQuestionParams(category, difficulty),
                new JSONObject(),
                new com.android.volley.Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        OnAPIQuerryCallback(response);
                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error: ", error.toString());
                    }
                }
        );
        mRequestQueue.add(jsonRequestObject);
    }

    private Question[] GenerateQuestionSetFromJsonObject(JSONObject json)
    {
        return null;
    }

    private void OnAPIQuerryCallback(JSONObject response)
    {
        String responseData = response.toString();
        JSONObject object = response;

        // TODO: Validate response code
        try {
            // Validate JSON object
            object = new JSONObject(responseData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(mOnAPIQuerryCallback != null)
            mOnAPIQuerryCallback.OnAPIQuerryCallback( GenerateQuestionSetFromJsonObject(object) );
    }

}
