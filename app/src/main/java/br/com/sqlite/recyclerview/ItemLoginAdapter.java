package br.com.sqlite.recyclerview;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import br.com.sqlite.R;
import br.com.sqlite.datamodel.ItemLogin;

public class ItemLoginAdapter extends RecyclerView.Adapter<ItemLoginAdapter.ViewHolder> {

    private Context context;
    private List<ItemLogin> lista;
    private RecyclerOnClickListener recyclerOnClickListener;
    private RecyclerOnClickListener deleteOnClickListener;

    public void setRecyclerOnClickListener(RecyclerOnClickListener recyclerOnClickListener) {
        this.recyclerOnClickListener = recyclerOnClickListener;
    }

    public ItemLoginAdapter(Context context, List<ItemLogin> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemLogin itemUser = lista.get(position);
        holder.email.setText(itemUser != null ? itemUser.getEmail_usuario() : null);
        holder.senha.setText(itemUser != null ? itemUser.getSenha_usuario() : null);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public ItemLogin getItemUser(int position) {
        return lista.get(position);
    }

    public void setDeleteOnClickListener(RecyclerOnClickListener deleteOnClickListener) {
        this.deleteOnClickListener = deleteOnClickListener;
    }

    public void removeItem(int position) {
        lista.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView email, senha;
        ImageButton btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            email = (TextView) itemView.findViewById(R.id.txtUser);
            senha = (TextView) itemView.findViewById(R.id.txtSenha);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete);
            itemView.setOnClickListener(this);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(deleteOnClickListener != null) {
                        deleteOnClickListener.onClickListener(getAdapterPosition());
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            if(recyclerOnClickListener != null) {
                recyclerOnClickListener.onClickListener(getAdapterPosition());
            }
        }
    }
}