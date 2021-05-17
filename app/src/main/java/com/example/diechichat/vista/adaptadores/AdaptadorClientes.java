package com.example.diechichat.vista.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diechichat.R;
import com.example.diechichat.databinding.ContentRvClientesBinding;
import com.example.diechichat.modelo.Cliente;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorClientes extends RecyclerView.Adapter<AdaptadorClientes.ClienteVH> {

    private List<Cliente> mDatos;
    private int mItemPos;
    private View.OnClickListener mListener;
    private AdaptadorClientesInterface mListener2;

    public AdaptadorClientes() {
        mDatos = null;
        mItemPos = -1;
        mListener = null;
    }

    public void setDatos(List<Cliente> datos) {
        mDatos = new ArrayList<>();
        Cliente c= new Cliente();
        c.setAltura(165);
        c.setNombreCompleto("María Martínez Gómez");
        mDatos.add(c); // ES DE PRUEBA PORQUE NO HAY CLIENTES EN LA COLECCIÓN
        mDatos.add(c);
        mDatos.add(c);
        mDatos.add(c);
    //    mDatos = datos; --> este es el que lo recoge
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
    public ClienteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_rv_clientes, parent, false);
        return new ClienteVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorClientes.ClienteVH holder, int position) {
        if (mDatos != null) {
            holder.setItem(mDatos.get(position));
            holder.binding.llrvClis.setActivated(mItemPos == position);
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

        private final ContentRvClientesBinding binding;

        public ClienteVH(@NonNull View itemView) {
            super(itemView);
            binding = ContentRvClientesBinding.bind(itemView);
            binding.llrvClis.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.rv_item_seleccionado));
            itemView.setOnClickListener(this);

            binding.imagebVer.setOnClickListener(imagebVer_OnClick);

        }

        private View.OnClickListener imagebVer_OnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
//        @Override
//        public void onClick(View v) {
//            if (v == binding.imagebAdd) {
//                mListener2.onClickIgmagebAdd();
//            } else if (v == binding.imagebCamara) {
//                mListener2.onClickImagebCamara();
//            } else if (v == binding.imagebVer) {
//                mListener2.onClickImagebVer();
//            } else {
//                int pos = getLayoutPosition();
//                notifyItemChanged(mItemPos);
//                mItemPos = (mItemPos == pos) ? -1 : pos;
//                notifyItemChanged(mItemPos);
//                if (mListener != null) {
//                    mListener.onClick(v);
//                }
//            }
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

        private void setItem(Cliente cli) {
            binding.tvNombreCli.setText(cli.getNombreCompleto());
            binding.imagebVer.setImageBitmap(cli.getFoto());
        }
    }
    public interface AdaptadorClientesInterface {
        void onClickImagebVer();
        void onClickIgmagebAdd();
        void onClickImagebCamara();
    }

}
