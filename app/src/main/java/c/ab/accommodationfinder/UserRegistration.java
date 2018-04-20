package c.ab.accommodationfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dmax.dialog.SpotsDialog;

public class UserRegistration extends Activity {

    EditText txtFirstName, txtLastName, txtEmail, txtPassword;
    Button btnRegister, btnCancel;
    UserEntity userEntity;
    DatabaseHelper Helper;
    String u_firstName, u_lastName, u_email, u_pwd;
    SpotsDialog spotsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        System.out.println("Register page load");
        try {
            Helper = new DatabaseHelper(UserRegistration.this);
            //cust_Helper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        userEntity = new UserEntity();
        btnRegister = (Button) findViewById(R.id.btnReg);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        txtFirstName = (EditText) findViewById(R.id.txtFirstName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPwd);

        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                txtFirstName.setError(null);
                txtLastName.setError(null);
                txtEmail.setError(null);
                txtPassword.setError(null);

                u_firstName = txtFirstName.getText().toString().trim();
                u_lastName = txtLastName.getText().toString().trim();
                //System.out.println(c_name);
                u_email = txtEmail.getText().toString().trim();
                u_pwd = txtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(u_firstName) || TextUtils.isEmpty(u_lastName) || TextUtils.isEmpty(u_email)  || TextUtils.isEmpty(u_pwd)) {
                    Toast.makeText(UserRegistration.this, "All fields are Mandatory", Toast.LENGTH_LONG).show();
                }
                else if (!TextUtils.isEmpty(u_email) && !isEmailValid(u_email)) {
                    txtEmail.setError(getString(R.string.error_invalid_email));
                    txtEmail.requestFocus();
                }
                // Check for a valid password, if the user entered one.
                else if (!TextUtils.isEmpty(u_pwd) && !isPasswordValid(u_pwd)) {
                    txtPassword.setError(getString(R.string.error_invalid_password));
                }
                // Check for a valid email address.
                /*else if (TextUtils.isEmpty(u_email)) {
                    txtEmail.setError(getString(R.string.error_field_required));
                    txtEmail.requestFocus();
                }*/
                else {
                    userEntity.setFirstName(u_firstName);
                    userEntity.setLastName(u_lastName);
                    userEntity.setEmail(u_email);
                    userEntity.setPassword(u_pwd);
                    userEntity.setUserActive(true);
                    long isUserInserted = Helper.addUser(userEntity);
                    if (isUserInserted > -1) {
                        //Redirect to Login page
                        Toast.makeText(UserRegistration.this, "You are Registered Successfully...!!!", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(UserRegistration.this, Login.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("userFullName", u_firstName + " " + u_lastName);
                        i.putExtras(bundle);
                        startActivity(i);

                    } else {
                        Toast.makeText(UserRegistration.this, "Error from DB" + isUserInserted, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iL =new Intent(UserRegistration.this,Login.class);
                System.out.println("Cancel button click");
                startActivity(iL);
            }
        });
    }

    ;

    public void onResume() {
        super.onResume();
        Helper = new DatabaseHelper(UserRegistration.this);
        //cust_Helper.getWritableDatabase();
    }

    public void onStop() {
        super.onStop();
        Helper.close();
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

  /*  private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            spotsDialog=new SpotsDialog(UserRegistration.this,"Please Wait.");
            spotsDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            spotsDialog.dismiss();
        }
    }*/
}
