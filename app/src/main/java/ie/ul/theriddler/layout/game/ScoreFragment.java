package ie.ul.theriddler.layout.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ie.ul.theriddler.R;
import ie.ul.theriddler.layout.hub.HighScoresActivity;
import ie.ul.theriddler.layout.hub.MainHubActivity;

public class ScoreFragment extends Fragment {

    /**
     * Required empty public constructor
     */
    public ScoreFragment() {
    }

    /**
     * On create view override method from Android fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score, container, false);
    }

    /**
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        final Button goBackButton = (Button) getActivity().findViewById(R.id.ScoreGoBack);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (getActivity(), MainHubActivity.class));
            }
        });

        Activity currentActivity = getActivity();
        if(!(currentActivity instanceof GameNavActivity))
            return;             // TODO: Error handling
        GameNavActivity mActivity = (GameNavActivity) currentActivity;

        TextView correctCount = (TextView) view.findViewById(R.id.ScoreScore);
        correctCount.setText("Your Score: " + mActivity.mCurrentScore);
    }
}