package com.example.diechichat.vista;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
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
import com.example.diechichat.databinding.ActivityMisClientesBinding;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.vista.dialogos.DlgConfirmacion;
import com.example.diechichat.vista.dialogos.DlgSeleccionFecha;
import com.example.diechichat.vista.fragmentos.DietaFragment;
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

public class MisClientesActivity extends AppCompatActivity implements
        MisClientesFragment.MisClientesFragmentInterface,
        NuevoCienteFragment.NuevoCliFragmentInterface,
        DlgSeleccionFecha.DlgSeleccionFechaListener,
        DlgConfirmacion.DlgConfirmacionListener, DietaFragment.DietaFragmentInterface{

    private ActivityMisClientesBinding binding;
    private NavController mNavC;
    private ClienteViewModel cliVM;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Cliente cli;

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
        mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.misClientesFragCV)).getNavController();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onVerClienteFrag(Cliente cli) {
        Bundle bundleCli = new Bundle();
        bundleCli.putParcelable("cliente", cli);
        bundleCli.putInt("opcion", NuevoCienteFragment.OP_EDITAR);
        mNavC.navigate(R.id.action_mis_clientes_to_nuevoCienteFragment, bundleCli);
    }

    @Override
    public void onAddDietaFrag(Cliente cli) {
        Intent i = new Intent(MisClientesActivity.this, DietaActivity.class);
        i.putExtra("cliente", cli);
        startActivity(i);
    }

    /**
     * Métodos NuevoClienteFragment
     ***************************************/
    @Override
    public void onAceptarNuevoFrag(int op, Cliente c) {
        if (c != null) {
            switch (op) {
                case NuevoCienteFragment.OP_CREAR:
                    cliVM.altaCliente(c).observe(this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean ok) {
                            Toast.makeText(getApplication(), (ok) ? R.string.msg_altaCorrecta : R.string.msg_altaIncorrecta, Toast.LENGTH_SHORT).show();
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
        if (c != null) {
            cli = c;
            mostrarDlgEliminar();
        }
    }

    @Override
    public void onEditadoSinHabilitarFrag() {
        Snackbar.make(binding.getRoot(), R.string.btAceptarEdicionKO, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDlgSeleccionFechaClick(DialogFragment dialog, String fecha) {
        cliVM.setmFechaDlg(fecha);
    }

    @Override
    public void onDlgSeleccionFechaCancel(DialogFragment dialog) {
        cliVM.setmFechaDlg("");
    }

    /**
     * Métodos Cámara
     ****************************************/
    @SuppressLint("QueryPermissionsNeeded")
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

    //TODO: SIN TERMINAR
    public void borrarFotoStorage(Cliente cli) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        StorageReference storageReference = storage.getReferenceFromUrl("gs://diechichat.appspot.com").child("fotosClientes/" + cli.getId()).child("imagen" + cli.getId() + ".jpeg");
        // Create a reference to the file to delete
        StorageReference desertRef = storageRef.child("fotosClientes/" + cli.getId()).child("imagen" + cli.getId() + ".jpeg");

        // Delete the file
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
            }
        });
    }

    /**
     * MÉTODOS DIÁLOGO CONFIRMACIÓN
     *****************************************/

    @Override
    public void onDlgConfirmacionPositiveClick(DialogFragment dialog) {
        if (cli != null) {
            cliVM.bajaCliente(cli).observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean ok) {
                    Toast.makeText(getApplication(), (ok) ? R.string.msg_clieteEliminado : R.string.msg_clieteNoEliminado, Toast.LENGTH_SHORT).show();
//                    if (ok) {
//                        borrarFotoStorage(cli);
//                    }
                }
            });
        }
        mNavC.navigate(R.id.mis_clientes_nav_graph);
    }

    @Override
    public void onDlgConfirmacionNegativeClick(DialogFragment dialog) {
    }

    private void mostrarDlgEliminar() {
        //Lanzamos DlgConfirmacion
        Bundle bundle = new Bundle();
        bundle.putInt("titulo", R.string.app_name);
        bundle.putInt("mensaje", R.string.msg_DlgConfirmacion_Eliminar);
        bundle.putString("tag", "tagConfirmacion_Salir");
        mNavC.navigate(R.id.dlgConfirmacionMisClientes, bundle);
    }

    @Override
    public void onAsignarAlimento(Cliente c, int opcion) {
        
    }
}
