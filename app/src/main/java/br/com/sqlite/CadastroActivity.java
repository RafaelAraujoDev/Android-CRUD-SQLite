package br.com.sqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.sqlite.datacontroller.DbController;
import br.com.sqlite.datamodel.ItemLogin;

public class CadastroActivity extends AppCompatActivity {

    private String email_usuario, senha_usuario, senha2;
    private DbController db;
    private Boolean editar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cadastro);

        final Intent intent = getIntent();
        final ItemLogin itemLogin = (ItemLogin) intent.getSerializableExtra("USUARIO");
        editar = intent.getBooleanExtra("EDITAR", false);

        final EditText edtEmailCadastro = (EditText)findViewById(R.id.edtEmailCadastro);
        final EditText edtEmailSenha = (EditText)findViewById(R.id.edtSenhaCadastro);
        final EditText edtSenha2Cadastro = (EditText)findViewById(R.id.edtSenha2Cadastro);
        Button btnCadastrar = (Button) findViewById(R.id.btnCadastrar);

        edtEmailCadastro.requestFocus();

        if(editar){
            btnCadastrar.setText("Editar");
            edtEmailCadastro.setText(itemLogin.getEmail_usuario());
            edtEmailSenha.setText(itemLogin.getSenha_usuario());
            edtSenha2Cadastro.setText(itemLogin.getSenha_usuario());
        }

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int error = 0;

                email_usuario = edtEmailCadastro.getText().toString().trim();
                senha_usuario = edtEmailSenha.getText().toString().trim();
                senha2 = edtSenha2Cadastro.getText().toString().trim();

                if(email_usuario.equals("")){
                    edtEmailCadastro.setError("Este campo nao pode estar vazio!");
                    edtEmailCadastro.requestFocus();
                    error = 1;
                }else if (senha_usuario.equals("")){
                    edtEmailSenha.setError("Este campo nao pode estar vazio!");
                    edtEmailSenha.requestFocus();
                    error = 1;
                }else if (senha2.equals("")){
                    edtSenha2Cadastro.setError("Este campo nao pode estar vazio!");
                    edtSenha2Cadastro.requestFocus();
                    error = 1;
                }

                if(error == 0){
                    Intent intent2;

                    if(senha_usuario.equals(senha2)){
                        if(!editar){
                            db.insertRegistro(email_usuario, senha_usuario);
                            intent2 = new Intent(getBaseContext(), LoginActivity.class);
                        }else{
                            db.updateRegistro(itemLogin.getId_usuario(), email_usuario, senha_usuario);
                            intent2 = new Intent(getBaseContext(), PrincipalActivity.class);
                        }

                        startActivity(intent2);
                        finish();
                    }else{
                        edtSenha2Cadastro.setError("As senhas nao coincidem");
                        edtSenha2Cadastro.requestFocus();
                    }

                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent it;

        if(editar){
            it = new Intent(getBaseContext(), PrincipalActivity.class);
        }else {
            it = new Intent(getBaseContext(), LoginActivity.class);
        }

        startActivity(it);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        db = new DbController(getBaseContext());
    }
}
