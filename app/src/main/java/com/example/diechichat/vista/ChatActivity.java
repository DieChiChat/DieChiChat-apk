package com.example.diechichat.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.example.diechichat.R;
import com.example.diechichat.databinding.ActivityChatBinding;
import com.example.diechichat.modelo.Chat;
import com.example.diechichat.vista.fragmentos.ChatFragment;
import com.example.diechichat.vistamodelo.ClienteViewModel;

public class ChatActivity extends AppCompatActivity
        implements ChatFragment.ChatFragmentInterface {

    private ActivityChatBinding binding;
    private NavController mNavC;
    private ClienteViewModel cliVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        cliVM = new ViewModelProvider(this).get(ClienteViewModel.class);
        mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.misClientesFragCV)).getNavController();

    }

    @Override
    public void onAceptarChatFrag(Chat chat) {

    }
}