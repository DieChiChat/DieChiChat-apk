package com.example.diechichat.vista.fragmentos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diechichat.databinding.FragmentChatBinding;
import com.example.diechichat.modelo.Chat;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.vista.adaptadores.AdaptadorChat;
import com.example.diechichat.vista.adaptadores.AdaptadorClientes;
import com.example.diechichat.vistamodelo.ChatViewModel;
import com.example.diechichat.vistamodelo.ClienteViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;

public class ChatFragment extends Fragment {

    private FragmentChatBinding binding;
    private ChatFragmentInterface mListener;
    private ChatViewModel chatVM;
    private AdaptadorChat mAdaptadorChat;
    private Cliente cliente;

    public ChatFragment() {
        // Required empty public constructor
    }

    public interface ChatFragmentInterface {
        void onAceptarChatFrag(Chat chat);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ChatFragment.ChatFragmentInterface) {
            mListener = (ChatFragmentInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ChatFragmentInterface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cliente = getArguments().getParcelable("cliente");
        }


        chatVM = new ViewModelProvider(requireActivity()).get(ChatViewModel.class);

        mAdaptadorChat = new AdaptadorChat();
        if (chatVM.getLoginCliente() != null) {
            mAdaptadorChat.setmLoginCliente(chatVM.getLoginCliente());
        } else if (chatVM.getLoginNutricionista() != null) {
            mAdaptadorChat.setmLoginNutricionista(chatVM.getLoginNutricionista());
        }

        // Inits Incs Observer
        chatVM.getChatME().observe(this, new Observer<List<Chat>>() {
            @Override
            public void onChanged(List<Chat> chats) {
                mAdaptadorChat.setDatos(chats);
                mAdaptadorChat.notifyDataSetChanged();
                if (mAdaptadorChat.getItemPos() != -1 && mAdaptadorChat.getItemPos() < mAdaptadorChat.getItemCount()) {
                    binding.rvChats.scrollToPosition(mAdaptadorChat.getItemPos());
                } else if (mAdaptadorChat.getItemCount() > 0) {
                    binding.rvChats.scrollToPosition(mAdaptadorChat.getItemCount() - 1);
                }
                mAdaptadorChat.setItemPos(-1);
            }
        });
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentChatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init RecyclerView Clientes
        binding.rvChats.setHasFixedSize(true);
        binding.rvChats.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.rvChats.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        binding.rvChats.setAdapter(mAdaptadorChat);

        // Listeners0
        mAdaptadorChat.setOnClickListener(mAdaptadorChat_OnClickListener);
        binding.btEnviar.setOnClickListener(btEnviar_onClickListener);

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
    }

    private final View.OnClickListener mAdaptadorChat_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = mAdaptadorChat.getItemPos();
        }
    };

    private final View.OnClickListener btEnviar_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!binding.etTexto.getText().toString().equals("")) {
                Chat chat = new Chat();
//                String id = (chatVM.getLoginCliente() != null) ? chatVM.getLoginCliente().getId() : (chatVM.getLoginNutricionista() != null) ? String.valueOf(chatVM.getLoginNutricionista().getId()) : "";
//                chat.setId(id);
                chat.setMensaje(binding.etTexto.getText().toString());
                mListener.onAceptarChatFrag(chat);
                mAdaptadorChat.notifyDataSetChanged();
                binding.etTexto.setText("");
            }
        }
    };
}