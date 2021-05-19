package com.example.diechichat.vista;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.diechichat.R;
import com.example.diechichat.databinding.ActivityChatBinding;
import com.example.diechichat.vista.fragmentos.ChatFragment;

import java.util.Objects;

public class ChatActivity extends AppCompatActivity implements ChatFragment.ChatFragmentInterface {

    private ActivityChatBinding binding;
    private NavController mNavC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.chatFragCV)).getNavController();
    }

}

