package br.com.sqlite.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    //nome do banco de dados
    private static final  String database_nome = "teste.db";

    //versao do banco de dados
    private static final int database_versao = 1;

    //criando tabela de usuarios
    private static  final String usuarios = " CREATE TABLE IF NOT EXISTS usuarios ("
            + "id_usuario INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + "email_usuario varchar(2) NOT NULL, "
            + "senha_usuario varchar(2) NOT NULL);";


    public DbHelper(Context context) {
        super(context, database_nome, null, database_versao );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(usuarios);
        onUpgrade(db, 1, database_versao);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion >= newVersion) return;

        switch (oldVersion){
            case 1:
                //upgrade

                break;
            default:
                throw new IllegalStateException("No upgrade specified for " +oldVersion+ " -> " +newVersion);
        }

    }
}
