package com.example.diechichat.vista.fragmentos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diechichat.R;
import com.example.diechichat.databinding.FragmentChatBinding;
import com.example.diechichat.databinding.FragmentMisClientesBinding;
import com.example.diechichat.databinding.FragmentNuevoClienteBinding;
import com.example.diechichat.modelo.Cliente;

public class ChatFragment extends Fragment {

    private ChatFragmentInterface mListener;
    private FragmentChatBinding binding;

    public ChatFragment() {
        // Required empty public constructor
    }

    public interface ChatFragmentInterface {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ChatFragment.ChatFragmentInterface) {
            mListener = (ChatFragment.ChatFragmentInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ChatFragmentInterface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}