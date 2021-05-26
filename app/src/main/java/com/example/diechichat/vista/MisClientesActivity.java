package com.example.diechichat.vista;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.diechichat.R;
import com.example.diechichat.databinding.ActivityMiPerfilBinding;
import com.example.diechichat.databinding.ActivityMisClientesBinding;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.vista.adaptadores.AdaptadorClientes;
import com.example.diechichat.vista.dialogos.DlgSeleccionFecha;
import com.example.diechichat.vista.fragmentos.MisClientesFragment;
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

public class MisClientesActivity extends AppCompatActivity implements
        MisClientesFragment.MisClientesFragmentInterface,
        NuevoCienteFragment.NuevoCliFragmentInterface,
        DlgSeleccionFecha.DlgSeleccionFechaListener {

    private ActivityMisClientesBinding binding;
    private NavController mNavC;
    private ClienteViewModel cliVM;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMisClientesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        cliVM = new ViewModelProvider(this).get(ClienteViewModel.class);
        mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.misclientesFragCV)).getNavController();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onVerClienteFrag(Cliente cli) {
        Bundle bundleCli= new Bundle();
        bundleCli.putParcelable("clienteVer",cli);
        bundleCli.putInt("op", NuevoCienteFragment.OP_EDITAR);
        mNavC.navigate(R.id.action_mis_lientes_to_nuevoCienteFragment,bundleCli);
    }

    @Override
    public void onAddDietaFrag(Cliente cli) {
        Bundle bundleCli= new Bundle();
        bundleCli.putParcelable("clienteAddDieta",cli);
    //    mNavC.navigate(R.id.action);
    }

    /**Métodos NuevoClienteFragment***************************************/
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
                            Toast.makeText(getApplication(), (ok) ? R.string.msg_editarOk : R.string.msg_editarKo, Toast.LENGTH_SHORT).show();
//                            Snackbar.make(binding.getRoot(), (ok) ? R.string.msg_editarOk : R.string.msg_editarKo, Snackbar.LENGTH_SHORT).show();
                            if (ok) {
                                subirFotoAStorage(c, cliVM.getFoto());
                            }
                        }
                    });
                    break;
            }
            mNavC.navigateUp();
        } else {
            Snackbar.make(binding.getRoot(), R.string.msg_datosObligatorios, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCancelarNuevoFrag() {
        mNavC.navigateUp();
    }

    @Override
    public void onEliminarClienteFrag(Cliente c) {
        if(c != null) {
            cliVM.bajaCliente(c).observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean ok) {
                    Toast.makeText(getApplication(), (ok) ? R.string.msg_clieteEliminado : R.string.msg_clieteNoEliminado, Toast.LENGTH_SHORT).show();
                    if (ok) {
                        subirFotoAStorage(c, cliVM.getFoto());
                    }
                }
            });
            mNavC.navigateUp();
        }
    }

    @Override
    public void onDlgSeleccionFechaClick(DialogFragment dialog, String fecha) { cliVM.setmFechaDlg(fecha); }
    @Override
    public void onDlgSeleccionFechaCancel(DialogFragment dialog) {
        cliVM.setmFechaDlg("");
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
}
