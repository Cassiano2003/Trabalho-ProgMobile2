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

import com.example.trabalhoprogmoba2.databinding.FragmentSixBinding;

import java.util.ArrayList;
import java.util.List;

public class SixFragment extends Fragment {

    public List<Jogador> jogadorList = null;
    public AppDataBase db;
    public FragmentSixBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSixBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        binding.novoJogador.setOnClickListener(v ->
                NavHostFragment.findNavController(SixFragment.this)
                        .navigate(R.id.action_sixFragment_to_threeFragment)
        );
        db =AppDataBase.getDataBase(requireContext());
        atualiza();

        binding.listaJogadores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Jogador j = jogadorList.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("valor",j.getIDJogador());

                NavHostFragment.findNavController(SixFragment.this)
                        .navigate(R.id.action_sixFragment_to_fiveFragment,bundle);
            }
        });

        binding.pesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = binding.pesquisaJogador.getText().toString().trim();
                try {
                    if(!nome.equals("")){
                        String[] nomes = db.jogadorDao().buscaNOME();
                        int controle = 0;
                        for (String n : nomes){
                            if(nome.equals(n)){
                                controle = 1;
                                break;
                            }
                        }
                        if(controle == 0) {
                            nomes = db.jogadorDao().buscaNICK();
                            for (String n : nomes) {
                                if (nome.equals(n)) {
                                    controle = 2;
                                    break;
                                }
                            }
                        }
                        if(controle == 1){
                            List<Jogador> j = new ArrayList<>();
                            j.add(db.jogadorDao().busca(db.jogadorDao().buscanomeID(nome)));
                            ArrayAdapter<Jogador> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_activated_1, j);
                            binding.listaJogadores.setAdapter(adapter);
                        } else if (controle == 2) {
                            List<Jogador> j = new ArrayList<>();
                            j.add(db.jogadorDao().busca(db.jogadorDao().buscanickID(nome)));
                            ArrayAdapter<Jogador> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_activated_1, j);
                            binding.listaJogadores.setAdapter(adapter);
                        } else{
                            Toast.makeText(requireContext(), "Jogador nao encontrado", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        atualiza();
                        Toast.makeText(requireContext(), "Nome invalido", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(requireContext(), "ERRO", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        atualiza();
    }

    public void atualiza(){
        jogadorList = db.jogadorDao().getall();
        ArrayAdapter<Jogador> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_activated_1, jogadorList);
        binding.listaJogadores.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }
}
