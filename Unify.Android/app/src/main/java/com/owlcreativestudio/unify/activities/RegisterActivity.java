package com.owlcreativestudio.unify.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.owlcreativestudio.unify.R;
import com.owlcreativestudio.unify.helpers.ProgressHelper;
import com.owlcreativestudio.unify.tasks.UserRegisterTask;


public class RegisterActivity extends AppCompatActivity {
    private ProgressHelper progressHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        View registerLayout = findViewById(R.id.register_form);
        View registerProgress = findViewById(R.id.login_progress);
        this.progressHelper = new ProgressHelper(registerProgress, registerLayout);
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

        Intent intent = new Intent(this, ARActivity.class);


        this.progressHelper.showProgress(true);
        UserRegisterTask registerTask = new UserRegisterTask(this, this.progressHelper, username, email, password, intent);
        registerTask.execute();
    }
}