package br.com.sqlite;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import br.com.sqlite.datacontroller.DbController;

public class LoginActivity extends AppCompatActivity {

    private String email_usuario, senha_usuairo;
    private DbController db = null;
    private Button btnEntrar;
    private Boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        final EditText edtEmailLogin = (EditText)findViewById(R.id.edtEmailLogin);
        final EditText edtSenhaLogin = (EditText)findViewById(R.id.edtSenhaLogin);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                email_usuario = edtEmailLogin.getText().toString().trim();
                senha_usuairo = edtSenhaLogin.getText().toString().trim();

                int error = 0;

                if (email_usuario.equals("")){
                    edtEmailLogin.setError("Este campo nao pode estar vazio");
                    edtEmailLogin.requestFocus();
                    error = 1;
                }else if (senha_usuairo.equals("")){
                    edtSenhaLogin.setError("Este campos nao pode estar vazio");
                    edtSenhaLogin.requestFocus();
                    error = 1;
                }

                if(error == 0){
                    if(db.selectRegistro(email_usuario, senha_usuairo)){
                        Intent it = new Intent(getBaseContext(), PrincipalActivity.class);
                        startActivity(it);
                        finish();
                    }else{
                        Snackbar.make(btnEntrar, "Usuario ou senha invalido!", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(it);
                finish();
            }
        });

        ImageButton btnGit = (ImageButton)findViewById(R.id.btnGit);
        btnGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://github.com/RafaelAraujoDev/Android-CRUD-SQLite");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            android.os.Process.killProcess(android.os.Process.myPid());
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(getBaseContext(), "Pressione novamente para sair", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();

        db = new DbController(getBaseContext());


    }

}
