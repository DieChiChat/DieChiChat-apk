package com.example.diechichat.vista.adaptadores;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diechichat.modelo.Nutricionista;

import java.util.List;

public class AdaptadorNutris extends RecyclerView.Adapter<AdaptadorNutris.NutriVH> {

    private List<Nutricionista> mDatos;
    private int mItemPos;
    private View.OnClickListener mListener;

    public AdaptadorNutris() {
        mDatos = null;
        mItemPos = -1;
        mListener = null;
    }

    public void setDatos(List<Nutricionista> datos) {
        mDatos = datos;
    }

    public List<Nutricionista> getItems() { return mDatos; }
    public int getItemPos() {
        return mItemPos;
    }

    public void setItemPos(int itemPos) {
        mItemPos = itemPos;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        mListener = listener;
    }

    public Nutricionista getItem(int pos) {
        return mDatos.get(pos);
    }

    @NonNull
    @Override
    public AdaptadorNutris.NutriVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorNutris.NutriVH holder, int position) {
        if (mDatos != null) {
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

    public class NutriVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        public NutriVH(@NonNull View itemView) {
            super(itemView);
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
    }

}
