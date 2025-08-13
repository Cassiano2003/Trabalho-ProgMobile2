package com.example.trabalhoprogmoba2;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabalhoprogmoba2.databinding.FragmentThreeBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ThreeFragment extends Fragment {
    private AppDataBase db;
    private int valor = 0;
    private int cont_h = 0;


    private FragmentThreeBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentThreeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<String> habilidades = new ArrayList<>(Arrays.asList(
                "Dual Blades",
                "One-handed Sword",
                "Parry",
                "Battle Healing",
                "Sprint",
                "Fishing",
                "Weapon Defense",
                "Rapier",
                "Linear",
                "Cooking",
                "Katana",
                "One-handed Curved Sword",
                "Two-handed Axe",
                "Merchant",
                "Holy Sword",
                "Immortal Object",
                "Shield Mastery",
                "Dagger",
                "Beast Taming",
                "Mace",
                "Smithing",
                "Spear",
                "Leadership",
                "Information Broker",
                "Agility",
                "Climbing",
                "Stealth",
                "Negotiation",
                "Two-handed Sword",
                "One-handed Axe",
                "Spears ou Swords", // Mantido como está, pois "ou" é parte da descrição
                "Flight",
                "Healing Magic",
                "Support Magic",
                "Illusion Magic",
                "Dark Magic",
                "Paralysis Spells",
                "Sniper",
                "Marksmanship",
                "Poison Bullets",
                "Assassination",
                "Mother's Rosario Sword Skill",
                "Blue Rose Sword",
                "Sword Skills", // Habilidade de espada genérica, já que há Sword Skills em outros contextos
                "Sacred Arts",
                "Osmanthus Sword",
                "Soul Manipulation",
                "Enhanced Strength"
        ));

        List<String> copia = new ArrayList<>(habilidades);



        ArrayAdapter adpter = ArrayAdapter.createFromResource(getContext(),R.array.guildas_do_sao, android.R.layout.simple_spinner_item);
        binding.guidas.setAdapter(adpter);
        adpter = ArrayAdapter.createFromResource(getContext(),R.array.armas_de_sao, android.R.layout.simple_spinner_item);
        binding.armas.setAdapter(adpter);

        ArrayAdapter<String> hb = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,new ArrayList<>(habilidades));
        binding.spinnerH.setAdapter(hb);

        db =AppDataBase.getDataBase(requireContext());
        binding.imageButton.setImageResource(R.drawable.player);


        Bundle ags = getArguments();
        if(ags != null) {
            binding.titulo.setText("ATUALIZA JOGADOR");
            binding.menos.setVisibility(View.VISIBLE);
            valor = ags.getInt("id", -1);
            Jogador jogador = db.jogadorDao().busca(valor);
            binding.nome.setText(jogador.getNome());
            binding.nick.setText(jogador.getNick());
            binding.idade.setText(String.valueOf(jogador.getIdade()));
            binding.level.setText(String.valueOf(jogador.getNivel()));
            if(jogador.getGenero().equals(binding.masculino.getText().toString())){
                binding.masculino.setChecked(true);
            }else{
                binding.feminino.setChecked(true);
            }
            for(int i=0;i < binding.armas.getAdapter().getCount(); i++){
                if(jogador.getArma().equals(binding.armas.getAdapter().getItem(i))){
                    binding.armas.setSelection(i);
                    break;
                }
            }
            for(int i=0;i < binding.guidas.getAdapter().getCount(); i++){
                if(jogador.getGuida().equals(binding.guidas.getAdapter().getItem(i))){
                    binding.guidas.setSelection(i);
                    break;
                }
            }
            String[] h = jogador.getHabilidades();
            if(h.length > 0){
                binding.habilidades.removeView(binding.spinnerH);
                for (int j = 0; j < h.length; j++) {
                    Spinner novo = new Spinner(getContext());
                    for(int c=0;c<habilidades.size();c++){
                        if (habilidades.get(c).equals(h[j])) {
                            ArrayAdapter<String> hb_h = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new ArrayList<>(habilidades));
                            novo.setAdapter(hb_h);
                            novo.setSelection(c);
                            habilidades.remove(h[j]);
                            break;
                        }
                    }
                    binding.habilidades.addView(novo);
                }
                cont_h = h.length-1;
            }
        }

        System.out.println(habilidades);

        binding.adiciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!habilidades.isEmpty()) {
                        Spinner novo = new Spinner(getContext());
                        View spiners = binding.habilidades.getChildAt(cont_h);
                        Spinner filhos = (Spinner) spiners;
                        habilidades.remove(filhos.getSelectedItem().toString());
                        ArrayAdapter<String> hb = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new ArrayList<>(habilidades));
                        novo.setAdapter(hb);
                        binding.habilidades.addView(novo);
                        cont_h++;
                    } else {
                        Toast.makeText(requireContext(), "Não tem mais Habilidades", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                }
            }
        });

        binding.menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(cont_h > 0){
                        binding.habilidades.removeViewAt(cont_h);
                        cont_h --;
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        binding.masculino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.feminino.setChecked(false);
            }
        });

        binding.feminino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.masculino.setChecked(false);
            }
        });

        binding.salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String nome = binding.nome.getText().toString();
                    String nick = binding.nick.getText().toString();
                    String genero = "";
                    int idade = 0;
                    int nivel = -1;
                    try {
                        if (!binding.idade.getText().toString().isEmpty()) {
                            idade = Integer.parseInt(binding.idade.getText().toString());
                        }
                        if (!binding.level.getText().toString().isEmpty()) {
                            nivel = Integer.parseInt(binding.level.getText().toString());
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(requireContext(), "Idade e Nível devem ser números válidos.", Toast.LENGTH_LONG).show();
                        return; // Sai do método se a conversão falhar
                    }

                    String guida = binding.guidas.getSelectedItem().toString();
                    String arma = binding.armas.getSelectedItem().toString();
                    try {
                        if (binding.masculino.isChecked()) {
                            genero = binding.masculino.getText().toString();
                        } else if (binding.feminino.isChecked()) {
                            genero = binding.feminino.getText().toString();
                        }
                        if(nivel <= 100) {
                            if (!nome.equals("") && !nick.equals("") && idade != 0 && !genero.equals("") && !guida.equals("") && cont_h > -1 && nivel != -1) {
                                String[] habilidades_s = new String[cont_h + 1];
                                for (int i = 0; i < habilidades_s.length; i++) {
                                    View spiners = binding.habilidades.getChildAt(i);
                                    Spinner filhos = (Spinner) spiners;
                                    habilidades_s[i] = filhos.getSelectedItem().toString();
                                }
                                Jogador jogador = new Jogador(nome, nick, idade, genero, habilidades_s, nivel, guida, arma);
                                if (!db.jogadorDao().pesquisaNICK(nick) && valor == 0) {
                                    db.jogadorDao().insertAll(jogador);
                                    Toast.makeText(requireContext(), "Jogador adicionado com sucesso", Toast.LENGTH_LONG).show();
                                    binding.nome.setText("");
                                    binding.nick.setText("");
                                    binding.level.setText("");
                                    binding.idade.setText("");
                                    if (genero.equals("Masculino")) {
                                        binding.masculino.setChecked(false);
                                    } else {
                                        binding.feminino.setChecked(false);
                                    }
                                    for (int i = cont_h; 0 < i; i--) {
                                        binding.habilidades.removeViewAt(i);
                                    }
                                    habilidades.clear();
                                    habilidades.addAll(copia);
                                    cont_h = 0;
                                } else if (valor != 0) {
                                    jogador.setIDJogador(valor);
                                    db.jogadorDao().upgrade(jogador);
                                    Toast.makeText(requireContext(), "Jogador atualizado", Toast.LENGTH_LONG).show();
                                } else {
                                    //Mensagem falando q ja tem gente com esse nome
                                    Toast.makeText(requireContext(), "Nome ou Nick ja existente", Toast.LENGTH_LONG).show();
                                }
                            }
                        }else{
                            Toast.makeText(requireContext(), "O Nivel só vai até 100", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        //Mensagem falando que todos os campos devem estar preenchidos
                        Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_LONG).show();
                    }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
