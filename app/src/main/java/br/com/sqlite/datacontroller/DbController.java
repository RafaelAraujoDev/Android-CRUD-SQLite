package br.com.sqlite.datacontroller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;


import br.com.sqlite.database.DbHelper;
import br.com.sqlite.datamodel.ItemLogin;

public class DbController {

    private static DbHelper dbHelper;
    private  static SQLiteDatabase db;
    private static final String leitura = "leitura";
    private static final String escrita = "escrita";

    public DbController(Context context) {
        dbHelper = new DbHelper(context);
    }

    //abre a conexao
    private static SQLiteDatabase openConn(String operacao){
        if (db == null ||  !db.isOpen())
            if (operacao.equals(escrita))
                db = dbHelper.getWritableDatabase();
            else
                db = dbHelper.getReadableDatabase();
        return db;
    }

    //fecha conexao
    private static  void closeConn(){
        if (dbHelper != null) dbHelper.close();
        if (db != null) db.close();
    }

    //insert
    public String insertRegistro(String email, String senha){

        long resultado;

        openConn(escrita);
        ContentValues valores = new ContentValues();
        valores.put("email_usuario", email);
        valores.put("senha_usuario", senha);
        resultado = db.insertWithOnConflict("usuarios", null, valores, SQLiteDatabase.CONFLICT_REPLACE);
        closeConn();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";
    }

    //select
    public Boolean selectRegistro(String email, String senha){

        Boolean isValid = false;
        String where = " where email_usuario = '" + email + "' ";
        String and = " and senha_usuario = '" + senha + "' ";
        String query = " select * from usuarios" + where + and;

        Cursor cursor = openConn(leitura).rawQuery(query, null);

        if(cursor!=null){
            while ( cursor.moveToNext()){
                ItemLogin itemLogin = new ItemLogin();
                itemLogin.setId_usuario(cursor.getInt(cursor.getColumnIndex("id_usuario")));
                itemLogin.setEmail_usuario(cursor.getString(cursor.getColumnIndex("email_usuario")));
                itemLogin.setSenha_usuario(cursor.getString(cursor.getColumnIndex("senha_usuario")));
                isValid = true;
            }
            cursor.close();
        }

        closeConn();
        return isValid;
    }

    //select all
    public ArrayList<ItemLogin> selectAllRegistros(){
        ArrayList<ItemLogin> myarray = new ArrayList<>();
        Cursor cursor = openConn("leitura").rawQuery("select * from usuarios", null );

        if(cursor!=null){
            while ( cursor.moveToNext()){
                ItemLogin itemLogin = new ItemLogin();
                itemLogin.setId_usuario(cursor.getInt(cursor.getColumnIndex("id_usuario")));
                itemLogin.setEmail_usuario(cursor.getString(cursor.getColumnIndex("email_usuario")));
                itemLogin.setSenha_usuario(cursor.getString(cursor.getColumnIndex("senha_usuario")));
                myarray.add(itemLogin);
            }
            cursor.close();
        }

        closeConn();

        return myarray;
    }

    //update
    public String updateRegistro(int id_usuario, String email, String senha){
        //falta testar este resultado para ver se vai funcionar
        long resultado;

        String where = " id_usuario = " + id_usuario;

        openConn(escrita);
        ContentValues valores = new ContentValues();
        valores.put("email_usuario", email);
        valores.put("senha_usuario", senha);
        resultado = db.update("usuarios",valores,where,null);
        closeConn();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";
    }

    //delete
    public void deleteRegistro(int id_usuario){
        String where = "id_usuario = " + id_usuario;
        openConn(leitura);
        db.delete("usuarios",where,null);
        closeConn();
    }


}
