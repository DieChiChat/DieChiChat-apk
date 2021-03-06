package com.example.diechichat.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.diechichat.R;
import com.example.diechichat.databinding.ActivityChatBinding;
import com.example.diechichat.modelo.Chat;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.modelo.Nutricionista;
import com.example.diechichat.vista.fragmentos.ChatFragment;
import com.example.diechichat.vista.fragmentos.ChatListadoFragment;
import com.example.diechichat.vistamodelo.ChatViewModel;

import java.util.Calendar;

public class ChatActivity extends AppCompatActivity implements
        ChatFragment.ChatFragmentInterface,
        ChatListadoFragment.ChatListadoFragmentInterface {

    private ActivityChatBinding binding;
    private NavController mNavC;
    private ChatViewModel chatVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        chatVM = new ViewModelProvider(this).get(ChatViewModel.class);

        Intent i = getIntent();
        if (i != null) {
            if(i.getParcelableExtra("login") instanceof Nutricionista) {
                chatVM.setLoginNutricionista(i.getParcelableExtra("login"));
                mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.listadoClientesChatCV)).getNavController();
            } else if(i.getParcelableExtra("login") instanceof Cliente) {
                chatVM.setLoginCliente(i.getParcelableExtra("login"));
                mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.misClientesFragCV)).getNavController();
            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onAceptarChatFrag(Chat chat) {
        if (chat != null) {
            if (chatVM.getLoginNutricionista() != null) {
                chat.setId(String.valueOf(chatVM.getLoginNutricionista().getId()));
            } else if (chatVM.getLoginCliente() != null) {
                chat.setId(asignarFechaYHora(chatVM.getLoginCliente().getId()));
            }
            chatVM.altaChat(chat).observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean ok) {
                }
            });
        }
    }

    public String asignarFechaYHora(String s) {
        Calendar calendario = Calendar.getInstance();
        String hora = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY)) + ":" + String.valueOf(calendario.get(Calendar.MINUTE)) + ":" + String.valueOf(calendario.get(Calendar.SECOND));
        String fecha = String.valueOf(calendario.get(Calendar.DAY_OF_WEEK)) + String.valueOf(calendario.get(Calendar.DAY_OF_MONTH)) + String.valueOf(calendario.get(Calendar.DAY_OF_YEAR));
        return s + "-" + fecha + "-" + hora;
    }

    @Override
    public void onSeleccionarClienteFrag(Cliente c) {
        Bundle b = new Bundle();
        b.putParcelable("cliente", c);
        mNavC.navigate(R.id.action_ChatListadoFragment_to_ChatFragment, b);

    }
}