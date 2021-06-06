package com.example.diechichat.vista.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.diechichat.databinding.FragmentChatListadoBinding;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.vista.adaptadores.AdaptadorChat;
import com.example.diechichat.vista.adaptadores.AdaptadorClientes;
import com.example.diechichat.vistamodelo.ChatViewModel;
import com.example.diechichat.vistamodelo.ClienteViewModel;

import java.util.List;

public class ChatListadoFragment extends Fragment {

    private FragmentChatListadoBinding binding;
    private ChatListadoFragmentInterface mListener;
    private ChatViewModel chatVM;
    private AdaptadorChat mAdaptadorChat;
    private AdaptadorClientes mAdaptadorClis;

    public ChatListadoFragment() {
        // Required empty public constructor
    }

    public interface ChatListadoFragmentInterface {
        void onSeleccionarClienteFrag(Cliente c);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ChatListadoFragment.ChatListadoFragmentInterface) {
            mListener = (ChatListadoFragmentInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ChatListadoFragmentInterface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }


        chatVM = new ViewModelProvider(requireActivity()).get(ChatViewModel.class);

        mAdaptadorChat = new AdaptadorChat();
        if (chatVM.getLoginCliente() != null) {
            mAdaptadorChat.setmLoginCliente(chatVM.getLoginCliente());
        } else if (chatVM.getLoginNutricionista() != null) {
            mAdaptadorChat.setmLoginNutricionista(chatVM.getLoginNutricionista());
        }

        // Inits
        ClienteViewModel clisVM = new ViewModelProvider(requireActivity()).get(ClienteViewModel.class);
        mAdaptadorClis = new AdaptadorClientes();

        // Inits Incs Observer
        clisVM.getClientesME().observe(this, new Observer<List<Cliente>>() {
            @Override
            public void onChanged(List<Cliente> clis) {
                mAdaptadorClis.setDatos(clis);
                mAdaptadorClis.notifyDataSetChanged();
                if (mAdaptadorClis.getItemPos() != -1 &&
                        mAdaptadorClis.getItemPos() < mAdaptadorClis.getItemCount()) {
                    binding.rvListadoChats.scrollToPosition(mAdaptadorClis.getItemPos());
                } else if (mAdaptadorClis.getItemCount() > 0) {
                    binding.rvListadoChats.scrollToPosition(mAdaptadorClis.getItemCount() - 1);
                }
                mAdaptadorClis.setItemPos(-1);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentChatListadoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init RecyclerView Clientes
        binding.rvListadoChats.setHasFixedSize(true);
        binding.rvListadoChats.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.rvListadoChats.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        binding.rvListadoChats.setAdapter(mAdaptadorChat);

        // Listeners
        mAdaptadorChat.setOnClickListener(mAdaptadorChat_OnClickListener);

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
            int posRecicler = mAdaptadorClis.getItemPos();
            if (posRecicler >= 0) {
                if (mListener != null) {
                    mListener.onSeleccionarClienteFrag(mAdaptadorClis.getItem(posRecicler));
                }
            }
        }
    };

}