package com.example.diechichat.vista.adaptadores;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diechichat.R;
import com.example.diechichat.databinding.ContentRvAlimentosBinding;
import com.example.diechichat.modelo.Alimento;

import java.util.List;

public class AdaptadorAlimentos extends RecyclerView.Adapter<AdaptadorAlimentos.AlimentoVH> {

    private List<Alimento> mDatos;
    private int mItemPos;
    private View.OnClickListener mListener;

    public AdaptadorAlimentos() {
        mDatos = null;
        mItemPos = -1;
        mListener = null;
    }

    public void setDatos(List<Alimento> datos) {
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

    public Alimento getItem(int pos) {
        return mDatos.get(pos);
    }


    @NonNull
    @Override
    public AlimentoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_rv_alimentos, parent, false);
        return new AlimentoVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AlimentoVH holder, int position) {
        if (mDatos != null) {
            holder.setItem(mDatos.get(position));
            holder.binding.llrvAlimentos.setActivated(mItemPos == position);
            holder.itemView.setBackgroundColor((mItemPos == position)
                    ? ContextCompat.getColor(holder.itemView.getContext(), R.color.lightGold)
                    : Color.TRANSPARENT);
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

    public class AlimentoVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ContentRvAlimentosBinding binding;

        public AlimentoVH(@NonNull View itemView) {
            super(itemView);
            binding = ContentRvAlimentosBinding.bind(itemView);
            binding.llrvAlimentos.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.rv_item_seleccionado));
            itemView.setOnClickListener(this);

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

        private void setItem(Alimento ali) {
            if(ali.getNombre() != null) {
                binding.tvNombreAlimento.setText(ali.getNombre());
            } else {
                binding.tvNombreAlimento.setText("");
            }
        }
    }
}




