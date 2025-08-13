package com.example.trabalhoprogmoba2;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabalhoprogmoba2.databinding.FragmentFiveBinding;

import java.util.ArrayList;
import java.util.List;

public class FiveFragment extends Fragment {

    private AppDataBase db;
    private List<Jogador> jogadorList;
    private Jogador jogador;
    private int valor;
    public FragmentFiveBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFiveBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        db =AppDataBase.getDataBase(requireContext());
        try {
            int imagem = R.drawable.player;
            valor = args.getInt("valor", -1);
            jogador = db.jogadorDao().busca(valor);
            binding.idJogador.setText("ID JOGADOR "+String.format("%d",jogador.getIDJogador())+"###");
                binding.nomeJogador.setText("Nome: "+jogador.getNome());
                binding.nickJogador.setText("Nick: "+jogador.getNick());
                binding.idadeJogador.setText("Idade: "+String.format("%d",jogador.getIdade()));
                binding.levelJogador.setText("Level: "+String.format("%d",jogador.getNivel()));
                binding.generoJogador.setText("Genero: "+jogador.getGenero());
                binding.armaJogador.setText("Arma: "+jogador.getArma());
                binding.guidaJogador.setText("Guilda: "+jogador.getGuida());
                String[] habilidades = jogador.getHabilidades();
                for(int i=0;i<habilidades.length;i++){
                    TextView novo = new TextView(getContext());
                    novo.setText(habilidades[i]);
                    novo.setTextColor(Color.WHITE);
                    try {
                        Typeface customFont = ResourcesCompat.getFont(requireContext(), R.font.saouibold);
                        if (customFont != null) {
                            novo.setTypeface(customFont);
                        } else {
                            novo.setTypeface(Typeface.DEFAULT);
                        }
                    } catch (Exception e) {
                        novo.setTypeface(Typeface.DEFAULT);
                    }
                    novo.setTextSize(20);
                    binding.habilidadesJogadores.addView(novo);
                }
                binding.imageJogador.setImageResource(imagem);
                List<Duelo> d1 = db.dueloDao().buscaIDplayer1(jogador.getIDJogador());
                List<Duelo> d2 = db.dueloDao().buscaIDplayer2(jogador.getIDJogador());
                List<Duelo> tudo = new ArrayList<>();
                if(d1 != null){
                    tudo.addAll(d1);
                }
                if(d2 != null){
                    tudo.addAll(d2);
                }
                ArrayAdapter<Duelo> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_activated_1, tudo);
                binding.duelosJogadores.setAdapter(adapter);
        } catch (Exception e) {
                Toast.makeText(requireContext(), "Jogador invalido", Toast.LENGTH_LONG).show();
            throw new RuntimeException(e);
        }

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Indicar que este fragmento tem opções de menu para a ActionBar/Toolbar
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Excluir) { // Exemplo de um item de menu
            AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
            dlg.setMessage("Deseja mesmo apagar esse Jogador?").setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    List<Duelo> d1 = db.dueloDao().buscaIDplayer1(jogador.getIDJogador());
                    List<Duelo> d2 = db.dueloDao().buscaIDplayer2(jogador.getIDJogador());
                    List<Duelo> tudo = new ArrayList<>();
                    if(d1 != null){
                        tudo.addAll(d1);
                    }
                    if(d2 != null){
                        tudo.addAll(d2);
                    }
                    for(int i=0; i < tudo.size();i++){
                        Duelo d = tudo.get(i);
                        db.dueloDao().delete(d);
                    }
                    db.jogadorDao().delete(jogador);
                    binding.idJogador.setText("ID JOGADOR ");
                    binding.nomeJogador.setText("Nome: ");
                    binding.nickJogador.setText("Nick: ");
                    binding.idadeJogador.setText("Idade: ");
                    binding.levelJogador.setText("Level: ");
                    binding.generoJogador.setText("Genero: ");
                    binding.armaJogador.setText("Arma: ");
                    binding.guidaJogador.setText("Guilda: ");
                    binding.habilidadesJogadores.removeAllViews();
                }
            });
            dlg.setNeutralButton("cancelar",null);
            dlg.show();
            return true;
        } else if (id == R.id.Atualizar) {
            Bundle bundle = new Bundle();
            bundle.putInt("id",jogador.getIDJogador());
            NavHostFragment.findNavController(FiveFragment.this)
                    .navigate(R.id.action_fiveFragment_to_threeFragment,bundle);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu); // Infla o menu aqui
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }
}
