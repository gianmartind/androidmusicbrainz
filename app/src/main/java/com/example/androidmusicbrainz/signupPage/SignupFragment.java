package com.example.androidmusicbrainz.signupPage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.androidmusicbrainz.R;
import com.example.androidmusicbrainz.mainActivity.FragmentListener;
import com.example.androidmusicbrainz.mainActivity.MainActivity;

public class SignupFragment extends Fragment implements View.OnClickListener ,SignupPresenter.IUserPresenter{
    FragmentListener fragmentListener;
    SignupPresenter signupPresenter;
    BootstrapButton btn_signup;
    BootstrapEditText et_username;
    BootstrapEditText et_password;
    TextView tv_login;

    public SignupFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup, container, false);
        this.signupPresenter = new SignupPresenter(this);
        this.btn_signup = view.findViewById(R.id.btn_signup);
        this.et_username  = view.findViewById(R.id.et_signup_username);
        this.et_password = view.findViewById(R.id.et_signup_pass);
        this.tv_login = view.findViewById(R.id.tv_login);
        this.tv_login.setOnClickListener(this);
        this.btn_signup.setOnClickListener(this);

        return view;
    }

    public static SignupFragment newInstance(){
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof FragmentListener){
            this.fragmentListener = (FragmentListener) context;
        } else{
            throw new ClassCastException(context.toString() + " must implement FragmentListener");
        }
    }

    @Override
    public void onClick(View view){
        if(view==this.btn_signup){
            this.signupPresenter.signUp(this.et_username.getText().toString(), this.et_password.getText().toString());
        } else if(view == this.tv_login){
            this.changePage(MainActivity.LOGIN);
        }
    }

    @Override
    public void changePage(int i){
        this.et_username.setText("");
        this.et_password.setText("");
        this.fragmentListener.changePage(i);
    }

    @Override
    public void showToast(String toast) {
        Toast.makeText(this.getActivity(),toast,Toast.LENGTH_SHORT).show();
    }
}
