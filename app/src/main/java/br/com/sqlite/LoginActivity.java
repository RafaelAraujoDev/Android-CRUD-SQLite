package br.com.sqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
                        dialogBox();
                        //Toast.makeText(getBaseContext(), "Usuario ou senha invalido!", Toast.LENGTH_SHORT).show();
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

                Uri uri = Uri.parse("http://google.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    public void dialogBox() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Usuario ou senha invalido!");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.cancel();
                    }
                });

        /*alertDialogBuilder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });*/

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
