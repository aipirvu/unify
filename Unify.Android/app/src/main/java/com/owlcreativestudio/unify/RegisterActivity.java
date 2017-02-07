package com.owlcreativestudio.unify;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.owlcreativestudio.unify.Models.User;
import com.owlcreativestudio.unify.Helpers.HttpHelper;
import com.owlcreativestudio.unify.Helpers.UrlHelper;


/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity {
    private View mProgressView;
    private View mRegisterFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    public void register(View view) {
        EditText usernameEditText = (EditText) findViewById(R.id.username);
        EditText emailEditText = (EditText) findViewById(R.id.email);
        EditText passwordEditText = (EditText) findViewById(R.id.password);
        EditText confirmPasswordEditText = (EditText) findViewById(R.id.confirm_password);

        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmationPassword = confirmPasswordEditText.getText().toString();

        if (!password.equals(confirmationPassword)) {
            return;
        }

        Intent intent = new Intent(this, FullscreenActivity.class);

        showProgress(true);
        UserRegisterTask registerTask = new UserRegisterTask(username, email, password, intent);
        registerTask.execute();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mUsername;
        private final Intent mPostExecuteSuccessIntent;

        UserRegisterTask(String username, String email, String password, Intent postExecuteSuccessIntent) {
            mEmail = email;
            mPassword = password;
            mUsername = username;
            mPostExecuteSuccessIntent = postExecuteSuccessIntent;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            User user = new User();
            user.setUsername(mUsername);
            user.setEmail(mEmail);
            user.setPassword(mPassword);

            try {
                HttpHelper.Post(UrlHelper.getUserUrl(), user);
            } catch (Exception ex) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
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