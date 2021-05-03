package com.example.diechichat.vista.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diechichat.R;
import com.example.diechichat.modelo.Cliente;

import java.util.List;

public class AdaptadorClientes extends RecyclerView.Adapter<AdaptadorClientes.ClienteVH> {

    private List<Cliente> mDatos;
    private int mItemPos;
    private View.OnClickListener mListener;

    public AdaptadorClientes() {
        mDatos = null;
        mItemPos = -1;
        mListener = null;
    }

    public void setDatos(List<Cliente> datos) {
        mDatos = datos;
    }

    public int getItemPos() {
        return mItemPos;
    }

    public void setItemPos(int itemPos) {
        mItemPos = itemPos;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        mListener = listener;
    }

    public Cliente getItem(int pos) {
        return mDatos.get(pos);
    }

    @NonNull
    @Override
    public AdaptadorClientes.ClienteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_rv_clientes, parent, false);
//        return new ClienteVH(v);
        //TODO: retornar vista listado
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorClientes.ClienteVH holder, int position) {
        if (mDatos != null) {
//            holder.setItem(mDatos.get(position));
//            holder.binding.llrvDptos.setActivated(mItemPos == position);
        }
    }

    @Override
    public int getItemCount() {
        if (mDatos != null) {
            return mDatos.size();
        } else {
            return 0;
        }
    }

    public class ClienteVH extends RecyclerView.ViewHolder implements View.OnClickListener {

//        private final ContentRvNutrisBinding binding;

        public ClienteVH(@NonNull View itemView) {
            super(itemView);
//            binding = ContentRvNutrisBinding.bind(itemView);
//            binding.llrvDptos.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.rv_item_seleccionado));
            itemView.setOnClickListener(this);
        }

//        private void setItem(@NonNull Nutricionista nutri) {
//            binding.tvDptoRvId.setText(String.format(itemView.getContext().getResources().getString(R.string.msg_Dpto_Id), dpto.getId()));
//            binding.tvDptoRvNombre.setText(String.format(itemView.getContext().getResources().getString(R.string.msg_Dpto_Nombre), dpto.getNombre()));
//        }

        @Override
        public void onClick(View v) {
            int pos = getLayoutPosition();
            notifyItemChanged(mItemPos);
            mItemPos = (mItemPos == pos) ? -1 : pos;
            notifyItemChanged(mItemPos);
            if (mListener != null) {
                mListener.onClick(v);
            }
        }
    }

}
