package br.com.sqlite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import br.com.sqlite.datacontroller.DbController;
import br.com.sqlite.datamodel.ItemLogin;
import br.com.sqlite.recyclerview.DividerItemDecoration;
import br.com.sqlite.recyclerview.ItemLoginAdapter;
import br.com.sqlite.recyclerview.RecyclerOnClickListener;

public class PrincipalActivity extends AppCompatActivity {

    private RecyclerView lista;
    private ItemLoginAdapter adapter;
    private DbController db;
    private Vibrator vb;
    private RecyclerOnClickListener clickListener = new RecyclerOnClickListener() {
        @Override
        public void onClickListener(int position) {
            Intent intent = new Intent(getBaseContext(), CadastroActivity.class);
            ItemLogin user = adapter.getItemUser(position);
            intent.putExtra("USUARIO", user);
            intent.putExtra("EDITAR", true);
            startActivity(intent);
        }
    };

    private RecyclerOnClickListener clickDeleteListener = new RecyclerOnClickListener() {
        @Override
        public void onClickListener(int position) {

            final ItemLogin itemLogin = adapter.getItemUser(position);
            db.deleteRegistro(itemLogin.getId_usuario());
            adapter.removeItem(position);
            vb.vibrate(50);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_principal);

        lista = (RecyclerView) findViewById(R.id.lista);

        vb = (Vibrator)getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent it = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(it);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        db = new DbController(getBaseContext());

        adapter = new ItemLoginAdapter(getBaseContext(), new ArrayList<ItemLogin>());
        adapter.setDeleteOnClickListener(clickDeleteListener);
        adapter.setRecyclerOnClickListener(clickListener);
        lista.setHasFixedSize(true);
        lista.addItemDecoration(new DividerItemDecoration(getBaseContext()));
        lista.setLayoutManager(new LinearLayoutManager(this));
        lista.setAdapter(adapter);
        adapter = new ItemLoginAdapter(getBaseContext(), db.selectAllRegistros());
        adapter.setRecyclerOnClickListener(clickListener);
        adapter.setDeleteOnClickListener(clickDeleteListener);
        lista.setAdapter(adapter);
    }
}
