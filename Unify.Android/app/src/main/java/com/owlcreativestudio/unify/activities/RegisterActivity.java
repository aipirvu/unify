package com.owlcreativestudio.unify.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.owlcreativestudio.unify.R;
import com.owlcreativestudio.unify.helpers.ProgressHelper;
import com.owlcreativestudio.unify.models.Register;
import com.owlcreativestudio.unify.models.UserAccount;
import com.owlcreativestudio.unify.services.SharedPreferencesService;
import com.owlcreativestudio.unify.tasks.UserRegisterTask;


public class RegisterActivity extends AppCompatActivity {
    private ProgressHelper progressHelper;
    private SharedPreferencesService sharedPreferencesService;
    private UserAccount userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.sharedPreferencesService = new SharedPreferencesService(this);

        View registerLayout = findViewById(R.id.register_form);
        View registerProgress = findViewById(R.id.login_progress);
        this.progressHelper = new ProgressHelper(registerProgress, registerLayout);

        this.userAccount = sharedPreferencesService.getUserAccount();
        if (null == userAccount) {
            userAccount = new UserAccount();
        }
        String name = userAccount.getName();
        if (null != name && !name.isEmpty()) {
            EditText nameEditText = (EditText) findViewById(R.id.name);
            nameEditText.setText(userAccount.getName());
        }
        String email = userAccount.getEmail();
        if (null != email && !email.isEmpty()) {
            EditText emailEditText = (EditText) findViewById(R.id.name);
            emailEditText.setText(userAccount.getEmail());
        }
    }

    public void register(View view) {
        EditText nameEditText = (EditText) findViewById(R.id.name);
        EditText usernameEditText = (EditText) findViewById(R.id.username);
        EditText emailEditText = (EditText) findViewById(R.id.email);
        EditText passwordEditText = (EditText) findViewById(R.id.password);
        EditText confirmPasswordEditText = (EditText) findViewById(R.id.confirm_password);

        String name = nameEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmationPassword = confirmPasswordEditText.getText().toString();

        if (name.isEmpty()) {
            //todo notify user
        }

        if (username.isEmpty()) {
//            todo notify user
        }

        if (email.isEmpty()) {
//            todo notify user
        }

        if (!password.equals(confirmationPassword)) {
//            todo notify user
        }

        userAccount.setEmail(email);
        userAccount.setName(name);
        userAccount.setUsername(username);

        Register register = new Register();
        register.setUserAccount(userAccount);
        register.setPassword(password);

        Intent intent = new Intent(this, ARActivity.class);


        this.progressHelper.showProgress(true);
        UserRegisterTask registerTask = new UserRegisterTask(this, this.progressHelper, register, intent);
        registerTask.execute();
    }
}