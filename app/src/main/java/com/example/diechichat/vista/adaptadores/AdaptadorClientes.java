package com.example.diechichat.vista.adaptadores;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diechichat.R;
import com.example.diechichat.databinding.ContentRvClientesBinding;
import com.example.diechichat.modelo.Cliente;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
        mDatos = new ArrayList<>();
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
    public ClienteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_rv_clientes, parent, false);
        return new ClienteVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorClientes.ClienteVH holder, int position) {
        if (mDatos != null) {
            holder.setItem(mDatos.get(position));
            holder.binding.llrvClis.setActivated(mItemPos == position);
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

    public class ClienteVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ContentRvClientesBinding binding;

        public ClienteVH(@NonNull View itemView) {
            super(itemView);
            binding = ContentRvClientesBinding.bind(itemView);
            binding.llrvClis.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.rv_item_seleccionado));
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
        private void setItem(Cliente cli) {
            binding.tvNombreCli.setText(cli.getNombreCompleto());
            mostrarImagenStorage(cli);
            if(!mostrarImagenStorage(cli)) {
                binding.imagebCamara.setImageResource(R.drawable.perfil_logo_small);
            }
        }

        public Boolean mostrarImagenStorage(Cliente cli) {
            final boolean[] existeFoto = {false};
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReferenceFromUrl("gs://diechichat.appspot.com").child("fotosClientes/" + cli.getId()).child("imagen" + cli.getId() + ".jpeg");
            try {
                final File localFile = File.createTempFile("imagen" + cli.getId() + ".jpeg", "jpeg");
                storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        binding.imagebCamara.setImageBitmap(bitmap);
                        existeFoto[0] = true;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        existeFoto[0] = false;
                    }
                });
            } catch (IOException e ) {}
            return existeFoto[0];
        }
    }
}




