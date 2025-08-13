package com.example.trabalhoprogmoba2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabalhoprogmoba2.databinding.FragmentSecondBinding;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    public List<Duelo> duelosList = null;
    public AppDataBase db;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.novoDuelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.jogadorDao().quantosJogadores() >= 2){
                    NavHostFragment.findNavController(SecondFragment.this)
                            .navigate(R.id.action_secondFragment_to_fourFragment);
                }else{
                    Toast.makeText(requireContext(), "Tem que ter dois jogadores", Toast.LENGTH_LONG).show();
                }
            }
        });

        db =AppDataBase.getDataBase(requireContext());
        atualiza();
        binding.listaDuelos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Duelo d = duelosList.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("valorD", d.getIDDuelo());

                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_secondFragment_to_sevenFragment,bundle);
            }
        });

        binding.pesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = binding.pesquisaJogador.getText().toString().trim();
                try {
                    if(!nome.equals("")){
                        int id = -1;
                        if(db.jogadorDao().pesquisaNOME(nome)) {
                            id = db.jogadorDao().buscanomeID(nome);
                        } else if (db.jogadorDao().pesquisaNICK(nome)) {
                            id = db.jogadorDao().buscanickID(nome);
                        }
                        if(id != -1){
                            List<Duelo> d1 = db.dueloDao().buscaIDplayer1(id);
                            List<Duelo> d2 = db.dueloDao().buscaIDplayer2(id);
                            List<Duelo> tudo = new ArrayList<>();
                            if(d1 != null){
                                tudo.addAll(d1);
                            }
                            if(d2 != null){
                                tudo.addAll(d2);
                            }
                            ArrayAdapter<Duelo> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_activated_1, tudo);
                            binding.listaDuelos.setAdapter(adapter);
                        }else{
                            Toast.makeText(requireContext(), "Duelo nao encontrado", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        atualiza();
                        Toast.makeText(requireContext(), "Nome invalido", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                }
            }
        });
    }

    public void atualiza(){
        duelosList = db.dueloDao().getall();
        ArrayAdapter<Duelo> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_activated_1, duelosList);
        binding.listaDuelos.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}