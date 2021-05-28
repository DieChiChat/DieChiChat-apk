package com.example.diechichat.vista.adaptadores;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diechichat.R;
import com.example.diechichat.databinding.ContentRvChatBinding;
import com.example.diechichat.databinding.ContentRvClientesBinding;
import com.example.diechichat.modelo.Chat;
import com.example.diechichat.modelo.Cliente;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdaptadorChat extends RecyclerView.Adapter<AdaptadorChat.ChatVH> {

    private List<Chat> mDatos;
    private int mItemPos;
    private View.OnClickListener mListener;

    public AdaptadorChat() {
        mDatos = null;
        mItemPos = -1;
        mListener = null;
    }

    public void setDatos(List<Chat> datos) {
        mDatos = datos;
    }

    public List<Chat> getItems() { return mDatos; }
    public int getItemPos() {
        return mItemPos;
    }

    public void setItemPos(int itemPos) {
        mItemPos = itemPos;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        mListener = listener;
    }

    public Chat getItem(int pos) {
        return mDatos.get(pos);
    }

    @NonNull
    @Override
    public AdaptadorChat.ChatVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_rv_nutris, parent, false);
//        return new NutriVH(v);
        //TODO: retornar vista listado
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChatVH holder, int position) {
        if (mDatos != null) {
            holder.setItem(mDatos.get(position));
            holder.binding.llrvChats.setActivated(mItemPos == position);
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

    public class ChatVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ContentRvChatBinding binding;

        public ChatVH(@NonNull View itemView, ContentRvChatBinding binding) {
            super(itemView);
            this.binding = ContentRvChatBinding.bind(itemView);
            itemView.setOnClickListener(this);
        }

        private void setItem(Chat chat) {
            binding.tvMensaje.setText(chat.getMensaje());
            binding.tvHora.setText(chat.getHoraMensaje().toString());
//
//            TODO: buscar comparador para ordenar mensajes a través de su fecha o su posición
//            Collections.sort(chat, new Comparator<Chat>() {
//                @Override
//                public int compare(Chat c1, Chat c2) {
//                    return new Integer(c1.getPos()).compareTo(new Integer(c2.getPos()));
//                }
//            });
//
//            Comparator<Integer> comparador = Collections.reverseOrder();
//            Collections.sort(arrayListInt, comparador);

//
//            List<Chat> tMensajes = new ArrayList<>();
//            int i = 0;
//            int size = tMensajes.size();
//            while(size >= 0) {
//                for (Chat c: tMensajes) {
//                    if(c.getPos() > i ) {
//                        i = c.getPos();
//                    }
//                }
//            }
//            for (Chat c: tMensajes) {
//                if() {
//
//                }
//            }
        }

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
