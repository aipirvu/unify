package com.owlcreativestudio.unify.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.owlcreativestudio.unify.R;
import com.owlcreativestudio.unify.helpers.AlertHelper;
import com.owlcreativestudio.unify.helpers.ProgressHelper;
import com.owlcreativestudio.unify.models.Register;
import com.owlcreativestudio.unify.models.UserAccount;
import com.owlcreativestudio.unify.services.SharedPreferencesService;
import com.owlcreativestudio.unify.tasks.UserRegisterTask;


public class RegisterActivity extends AppCompatActivity {
    private ProgressHelper progressHelper;
    private UserAccount userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SharedPreferencesService sharedPreferencesService = new SharedPreferencesService(this);

        View registerLayout = findViewById(R.id.register_form);
        View registerProgress = findViewById(R.id.login_progress);
        this.progressHelper = new ProgressHelper(registerProgress, registerLayout);

        this.userAccount = sharedPreferencesService.getUserAccount();
        if (null == userAccount) {
            userAccount = new UserAccount();
        }
        String displayName = userAccount.getDisplayName();
        if (null != displayName && !displayName.isEmpty()) {
            EditText displayNameEditText = (EditText) findViewById(R.id.displayName);
            displayNameEditText.setText(userAccount.getDisplayName());
        }
        String email = userAccount.getEmail();
        if (null != email && !email.isEmpty()) {
            EditText emailEditText = (EditText) findViewById(R.id.email);
            emailEditText.setText(userAccount.getEmail());
        }
    }

    public void register(View view) {
        EditText displayNameEditText = (EditText) findViewById(R.id.displayName);
        EditText emailEditText = (EditText) findViewById(R.id.email);
        EditText passwordEditText = (EditText) findViewById(R.id.password);
        EditText confirmPasswordEditText = (EditText) findViewById(R.id.confirm_password);

        String displayName = displayNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmationPassword = confirmPasswordEditText.getText().toString();

        if (displayName.isEmpty()) {
            AlertHelper.show(this, "Field required", "Display name is not set.");
        } else if (email.isEmpty()) {
            AlertHelper.show(this, "Field required", "Email is not set.");
        } else if (password.isEmpty()) {
            AlertHelper.show(this, "Field required", "Password is not set.");
        } else if (!password.equals(confirmationPassword)) {
            AlertHelper.show(this, "Field required", "Password fields do not match.");
        } else {
            userAccount.setEmail(email);
            userAccount.setDisplayName(displayName);

            Register register = new Register();
            register.setUserAccount(userAccount);
            register.setPassword(password);

            Intent intent = new Intent(this, ARActivity.class);


            this.progressHelper.showProgress(true);
            UserRegisterTask registerTask = new UserRegisterTask(this, this.progressHelper, register, intent);
            registerTask.execute();
        }
    }
}