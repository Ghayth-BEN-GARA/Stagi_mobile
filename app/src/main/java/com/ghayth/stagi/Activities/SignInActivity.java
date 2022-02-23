package com.ghayth.stagi.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.ghayth.stagi.R;
import com.google.android.material.textfield.TextInputEditText;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {
    private CheckBox rememberUser;
    private TextInputEditText email, password;
    private AppCompatButton signIn;
    private TextView erreurEmail, erreurPassord;
    private ImageView emailMark, passwordMark;
    private Boolean testEmail = false, testPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        rememberUser = (CheckBox) findViewById(R.id.remembrer_user);
        email = (TextInputEditText) findViewById(R.id.email);
        signIn = (AppCompatButton) findViewById(R.id.btn_signin);
        erreurEmail = (TextView) findViewById(R.id.email_erreur);
        password = (TextInputEditText) findViewById(R.id.password);
        erreurPassord = (TextView) findViewById(R.id.password_erreur);
        emailMark = (ImageView) findViewById(R.id.mark_email);
        passwordMark = (ImageView) findViewById(R.id.mark_password);

        setCheckBoxChanger();
        setInputFocused(email);
        setEmailInputListener();
        setInputFocused(password);
        setPasswordInputListener();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validationFormulaire();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), ChoixAuthentificationActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_left,R.anim.stay_animation);
    }

    public void setCheckBoxChanger(){
        rememberUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    modifierStyleCheckBoxToBleu();
                }

                else{
                    modifierStyleCheckBoxToNormal();
                }
            }
        });
    }

    public void modifierStyleCheckBoxToBleu(){
        rememberUser.setTextColor(getColor(R.color.first_color));
        rememberUser.setButtonTintList(ColorStateList.valueOf(getColor(R.color.first_color)));
    }

    public void modifierStyleCheckBoxToNormal(){
        rememberUser.setTextColor(getColor(R.color.grise));
        rememberUser.setButtonTintList(ColorStateList.valueOf(getColor(R.color.grise)));
    }

    public void setInputFocused(TextInputEditText input){
        input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    inputFucsedIn(input);
                }

                else{
                    inputFocusedOut(input);
                }
            }
        });
    }

    public void inputFucsedIn(TextInputEditText input){
        input.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.first_color)));
        input.setBackground(getDrawable(R.drawable.background_inputs_bleu));
        input.setTextColor(getColor(R.color.first_color));
        input.setHintTextColor(getColor(R.color.first_color));
    }

    public void inputFocusedOut(TextInputEditText input){
        input.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.grise)));
        input.setBackground(getDrawable(R.drawable.background_inputs_white));
        input.setTextColor(getColor(R.color.grise));
        input.setHintTextColor(getColor(R.color.grise));
    }

    public void setEmailInputListener(){
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validationEmail();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void validationEmail(){
        if(!checkInputVide(email.getText().toString())){
            erreurEmail.setText(getString(R.string.email_vide));
            activeIconValidate(emailMark,"erreur");
            testEmail = false;
        }

        else if(!formatEmail(email.getText().toString())){
            erreurEmail.setText(getString(R.string.format_email_invalide));
            activeIconValidate(emailMark,"erreur");
            testEmail = false;
        }

        else{
            erreurEmail.setText(null);
            activeIconValidate(emailMark,"success");
            testEmail = true;
        }
    }

    public boolean formatEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public boolean checkInputVide(String input){
        if(input.isEmpty()){
            return false;
        }

        else{
            return true;
        }
    }

    public boolean passwordMiniscule(String password) {
        Matcher matcher = Pattern.compile("((?=.*[a-z]).{8,100})").matcher(password);
        return matcher.matches();
    }

    public boolean passwordMajuscule(String password) {
        Matcher matcher = Pattern.compile("((?=.*[A-Z]).{8,100})").matcher(password);
        return matcher.matches();
    }

    public boolean passwordCaractere(String password) {
        Matcher matcher = Pattern.compile("((?=.*[@#$%^&+=!?*]).{8,100})").matcher(password);
        return matcher.matches();
    }

    public boolean passwordChiffre(String password) {
        Matcher matcher = Pattern.compile("((?=.*[0-9]).{8,100})").matcher(password);
        return matcher.matches();
    }

    public boolean checkPasswordLength(){
        return password.length() >= 8;
    }

    public boolean checkCaracteresPassword(String password){
        Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$\\%\\^\\&\\+\\=\\?\\*]{8,100}");

        return !TextUtils.isEmpty(password) && PASSWORD_PATTERN.matcher(password).matches();
    }

    public void setPasswordInputListener(){
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validationPassword();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void validationPassword(){
        if(!checkInputVide(password.getText().toString())){
            erreurPassord.setText(getString(R.string.password_vide));
            activeIconValidate(passwordMark,"erreur");
            testPassword = false;
        }

        else if(!checkPasswordLength()){
            erreurPassord.setText(getString(R.string.password_length));
            activeIconValidate(passwordMark,"erreur");
            testPassword = false;
        }

        else if(!passwordMiniscule(password.getText().toString())){
            erreurPassord.setText(getString(R.string.password_miniscule));
            activeIconValidate(passwordMark,"erreur");
            testPassword = false;
        }

        else if(!passwordMajuscule(password.getText().toString())){
            erreurPassord.setText(getString(R.string.password_majuscule));
            activeIconValidate(passwordMark,"erreur");
            testPassword = false;
        }

        else if(!passwordCaractere(password.getText().toString())){
            erreurPassord.setText(getString(R.string.password_caracteres_speaciaux));
            activeIconValidate(passwordMark,"erreur");
            testPassword = false;
        }

        else if(!passwordChiffre(password.getText().toString())){
            erreurPassord.setText(getString(R.string.password_chiffre));
            activeIconValidate(passwordMark,"erreur");
            testPassword = false;
        }

        else if(!checkCaracteresPassword(password.getText().toString())){
            erreurPassord.setText(getString(R.string.password_non_valide));
            activeIconValidate(passwordMark,"erreur");
            testPassword = false;
        }

        else{
            erreurPassord.setText(null);
            activeIconValidate(passwordMark,"success");
            testPassword = true;
        }
    }

    public void activeIconValidate(ImageView image, String type){
        if(type.equals("erreur")){
            image.setVisibility(View.VISIBLE);
            image.setImageResource(R.drawable.erreur);
            image.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        else if(type.equals("success")){
            image.setVisibility(View.VISIBLE);
            image.setImageResource(R.drawable.check);
            image.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        else{
            image.setVisibility(View.INVISIBLE);
        }
    }

    public void validationFormulaire(){
        if(!checkInputVide(email.getText().toString())){
            erreurEmail.setText(getString(R.string.email_vide));
            activeIconValidate(emailMark,"erreur");
            testEmail = false;
        }

        else if(!checkInputVide(password.getText().toString())){
            erreurPassord.setText(getString(R.string.password_vide));
            activeIconValidate(passwordMark,"erreur");
            testPassword = false;
        }

        else{
            erreurEmail.setText(null);
            erreurPassord.setText(null);
            activeIconValidate(emailMark,"success");
            activeIconValidate(passwordMark,"success");
        }
    }
}