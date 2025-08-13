package com.example.trabalhoprogmoba2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabalhoprogmoba2.databinding.FragmentSevenBinding;

import java.util.List;

public class SevenFragment extends Fragment {

    private FragmentSevenBinding binding;

    private AppDataBase db;
    private List<Duelo> dueloList;
    private Duelo duelo;
    private int valor;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSevenBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        db =AppDataBase.getDataBase(requireContext());
        try {
            int imagen = R.drawable.player;
            valor = args.getInt("valorD", -1);
            duelo = db.dueloDao().busca(valor);
            Jogador j1 = db.jogadorDao().buscaJogador(duelo.getIDPlayer1());
            Jogador j2 = db.jogadorDao().buscaJogador(duelo.getIDPlayer2());
            binding.idDuelo.setText("ID DUELO "+String.format("%d",duelo.getIDDuelo())+"###");
            binding.dataDuelo.setText("Data: "+duelo.getData());
            binding.tempoDuelo.setText("Tempo: "+duelo.getTempo());
            binding.tipoDuelo.setText("Tipo do Duelo: "+duelo.getTipo());
            binding.guilda1Duelo.setText(j1.getGuida());
            binding.guilda2Duelo.setText(j2.getGuida());
            binding.player1Duelo.setText("Nick: "+j1.getNick());
            binding.player2Duelo.setText("Nick: "+j2.getNick());
            binding.placar.setText("Pontuação: "+String.valueOf(duelo.getPontosP1())+" - "+String.valueOf(duelo.getPontosP2()));
            binding.imageP1.setImageResource(imagen);
            binding.imageP2.setImageResource(imagen);
            switch (binding.tipoDuelo.getText().toString()){
                case "Tipo do Duelo: Duelo Simples":
                    binding.imageView.setImageResource(R.drawable.simple);
                    break;
                case "Tipo do Duelo: Duelo Contra o Relogio":
                    binding.imageView.setImageResource(R.drawable.time);
                    break;
                case "Tipo do Duelo: Morte Subita":
                    binding.imageView.setImageResource(R.drawable.death);
                    break;
                default:
                    break;
            }
            binding.resultado.setText("O vencedor: "+duelo.getVencedor());
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
            dlg.setMessage("Deseja mesmo apagar esse Duelo?").setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.dueloDao().delete(duelo);
                    binding.idDuelo.setText("ID DUELO ");
                    binding.dataDuelo.setText("Data: ");
                    binding.tempoDuelo.setText("Tempo: ");
                    binding.tipoDuelo.setText("Tipo do Duelo: ");
                    binding.guilda1Duelo.setText("Guilda P1: ");
                    binding.guilda2Duelo.setText("Guilda P2: ");
                    binding.player1Duelo.setText("Nome: ");
                    binding.player2Duelo.setText("Nome: ");
                    binding.resultado.setText("O vencedor: ");
                }
            });
            dlg.setNeutralButton("cancelar",null);
            dlg.show();
            return true;
        } else if (id == R.id.Atualizar) {
            Bundle bundle = new Bundle();
            bundle.putInt("idd",duelo.getIDDuelo());
            NavHostFragment.findNavController(SevenFragment.this)
                    .navigate(R.id.action_sevenFragment_to_fourFragment,bundle);
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
