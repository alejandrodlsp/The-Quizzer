package ie.ul.theriddler.layout.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ie.ul.theriddler.R;
import ie.ul.theriddler.layout.hub.MainHubActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = RegisterFragment.class.getName();

    EditText mEmail, mPassword;
    Button mLoginButton;
    TextView mRegisterButton;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    String email, password;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment login.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private Boolean validateEmail() {
        String checker = mEmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(checker.isEmpty()){
            mEmail.setError("Field cannot be empty.");
            return false;
        } else if (!checker.matches(emailPattern)) {
            mEmail.setError("Invalid email address");
            return false;
        } else {
            mEmail.setError(null);
            return true;
        }
    }

    private  Boolean validatePassword() {
        String checker = mPassword.getText().toString();
        String passwordValues = "^" +
                ".{4,}" +               //at least 4 characters
                "$";

        if(checker.isEmpty()) {
            mPassword.setError("Field cannot be Empty");
            return false;
        } else if(!checker.matches(passwordValues)) {
            mPassword.setError("Password is too weak.");
            return false;
        } else {
            mPassword.setError(null);
            return true;
        }
    }

    public void loginAccount() {
        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d(TAG, "onComplete: UserLogin:success");
                    Toast.makeText(getActivity(), "Login Success", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = fAuth.getCurrentUser();

                    Intent intent = new Intent();
                    intent.setClass(getActivity(), MainHubActivity.class);
                    getActivity().startActivity(intent);
                } else {
                    Log.d(TAG, "onComplete: UserLogin:failed");
                    Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEmail          = (EditText) getView().findViewById(R.id.email_login);
        mPassword       = (EditText) getView().findViewById(R.id.loginpassword);
        progressBar     = getView().findViewById(R.id.login_progressbar);
        fAuth           = FirebaseAuth.getInstance();
        mLoginButton    = (Button) getView().findViewById(R.id.login_button);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email       = mEmail.getText().toString().trim();
                password    = mPassword.getText().toString();

                if(validateEmail() | validatePassword()) {
                    progressBar.setVisibility(View.VISIBLE);
                    loginAccount();
                }
            }
        });

        Button register_button = view.findViewById(R.id.register_button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_login_to_register);
            }
        });
    }
}