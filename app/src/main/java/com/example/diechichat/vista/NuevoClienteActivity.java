package com.example.diechichat.vista;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.diechichat.R;
import com.example.diechichat.databinding.ActivityNuevoClienteBinding;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.vista.dialogos.DlgConfirmacion;
import com.example.diechichat.vista.dialogos.DlgSeleccionFecha;
import com.example.diechichat.vista.fragmentos.NuevoCienteFragment;
import com.example.diechichat.vistamodelo.ClienteViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NuevoClienteActivity extends AppCompatActivity implements
        NuevoCienteFragment.NuevoCliFragmentInterface,
        DlgSeleccionFecha.DlgSeleccionFechaListener {

    private ActivityNuevoClienteBinding binding;
    private NavController mNavC;
    private ClienteViewModel cliVM;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Cliente cli;
    private int opcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNuevoClienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        cliVM = new ViewModelProvider(this).get(ClienteViewModel.class);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nuevoClienteFragCV)).getNavController();

    }

    @Override
    public void onAceptarNuevoFrag(int op, Cliente c) {
        if (c != null) {
            switch (op) {
                case NuevoCienteFragment.OP_CREAR:
                    cliVM.altaCliente(c).observe(this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean ok) {
                            Toast.makeText(getApplication(), (ok) ? R.string.msg_altaCorrecta : R.string.msg_altaIncorrecta, Toast.LENGTH_SHORT).show();
//                            Snackbar.make(binding.getRoot(), (ok) ? R.string.msg_altaCorrecta : R.string.msg_altaIncorrecta, Snackbar.LENGTH_SHORT).show();
                            if (ok) {
                                subirFotoAStorage(c, cliVM.getFoto());
                            }
                        }
                    });
                    break;
                case NuevoCienteFragment.OP_EDITAR:
                    cliVM.editarCliente(c).observe(this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean ok) {
                            Toast.makeText(getApplication(), (ok) ? R.string.msg_altaCorrecta : R.string.msg_altaIncorrecta, Toast.LENGTH_SHORT).show();
//                            Snackbar.make(binding.getRoot(), (ok) ? R.string.msg_altaCorrecta : R.string.msg_altaIncorrecta, Snackbar.LENGTH_SHORT).show();
                            if (ok) {
                                subirFotoAStorage(c, cliVM.getFoto());
                            }
                        }
                    });
                    break;
            }
            finish();
        } else {
            Snackbar.make(binding.getRoot(), R.string.msg_datosObligatorios, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCancelarNuevoFrag() {
        finish();
    }

    /**
     * Métodos Cámara
     ****************************************/
    @Override
    public void onAbrirCamaraFrag() {
        Intent i = new Intent();
        i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
        }

    }

    @Override
    public void onEliminarClienteFrag(Cliente c) { ; }

    @Override
    public void onEditadoSinHabilitarFrag() { ; }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE: {
                    if (resultCode == RESULT_OK) {
                        cliVM.setFoto((Bitmap) data.getExtras().get("data"));
                    }
                }
            }
        }
    }

    public void subirFotoAStorage(Cliente cli, LiveData<Bitmap> fotoLive) {
        String hora = new SimpleDateFormat("HHmmss", Locale.ENGLISH).format(new Date());
        ;
        String nombreFoto = "imagen" + cli.getIdAdmin() + cli.getId();

        Bitmap foto = fotoLive.getValue();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl("gs://diechichat.appspot.com").child("fotosClientes/" + cli.getId()).child("imagen" + cli.getId() + ".jpeg");
        if (foto != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            foto.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            byte[] data = outputStream.toByteArray();

            UploadTask uploadTask = storageReference.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                }
            });
        }
    }

    /**
     * Métodos Diálogo de Selección de Fecha
     **********************************/

    @Override
    public void onDlgSeleccionFechaClick(DialogFragment dialog, String fecha) {
        cliVM.setmFechaDlg(fecha);
    }

    @Override
    public void onDlgSeleccionFechaCancel(DialogFragment dialog) {
        cliVM.setmFechaDlg("");
    }

}
