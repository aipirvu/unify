package com.owlcreativestudio.unify;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.owlcreativestudio.unify.Helpers.HttpHelper;
import com.owlcreativestudio.unify.Helpers.UrlHelper;
import com.owlcreativestudio.unify.Models.Login;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    public void signIn(View view) {
        EditText emailEditText = (EditText) findViewById(R.id.email);
        EditText passwordEditText = (EditText) findViewById(R.id.password);

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        Intent intent = new Intent(this, FullscreenActivity.class);

        showProgress(true);
        UserLoginTask loginTask = new UserLoginTask(email, password, intent);
        loginTask.execute();
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    //// TODO: 31-Jan-17 Find a method to reuse this showProgress code instead of duplicating it in every activity
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final Intent mPostExecuteSuccessIntent;

        UserLoginTask(String email, String password, Intent postExecuteSuccessIntent) {
            mEmail = email;
            mPassword = password;
            mPostExecuteSuccessIntent = postExecuteSuccessIntent;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Login login = new Login();
            login.setEmail(mEmail);
            login.setPassword(mPassword);

            try {
                HttpHelper.Post(UrlHelper.getLoginUrl(), login);
            } catch (Exception ex) {
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            showProgress(false);
            if (success) {
                startActivity(mPostExecuteSuccessIntent);
                finish();
            }
        }

        @Override
        protected void onCancelled() {
            showProgress(false);
        }
    }
}