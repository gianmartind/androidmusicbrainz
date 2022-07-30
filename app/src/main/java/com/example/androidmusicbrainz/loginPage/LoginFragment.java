package com.example.androidmusicbrainz.loginPage;

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

public class LoginFragment extends Fragment implements View.OnClickListener, LoginPresenter.IUserPresenter{
    FragmentListener fragmentListener;
    LoginPresenter loginPresenter;
    BootstrapEditText et_username;
    BootstrapEditText et_password;
    TextView tv_signup;
    BootstrapButton btn_login;

    public LoginFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);
        this.loginPresenter = new LoginPresenter(this,this.getActivity());
        this.et_username = view.findViewById(R.id.et_login_username);
        this.et_password = view.findViewById(R.id.et_login_pass);
        this.tv_signup = view.findViewById(R.id.tv_signup);
        this.btn_login = view.findViewById(R.id.btn_login);
        this.btn_login.setOnClickListener(this);
        this.tv_signup.setOnClickListener(this);
        this.loginPresenter.deleteSettingsPrefServer();
        return view;
    }

    public static LoginFragment newInstance(){
        LoginFragment fragment = new LoginFragment();
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
    public void changePage(int i){
        this.et_username.setText("");
        this.et_password.setText("");
        this.fragmentListener.changePage(i);
    }

    @Override
    public void showToast(String toast) {
        Toast.makeText(this.getActivity(),toast,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void restartApp() {
        this.fragmentListener.restartApp();
    }

    @Override
    public void onClick(View view) {
        if(view==this.btn_login){
            this.loginPresenter.login(this.et_username.getText().toString(), this.et_password.getText().toString());
        }
        else if(view == this.tv_signup){
            this.loginPresenter.changePage(MainActivity.SIGNUP);
        }
    }
}
