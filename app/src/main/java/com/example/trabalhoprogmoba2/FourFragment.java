package com.example.trabalhoprogmoba2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trabalhoprogmoba2.databinding.FragmentFourBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FourFragment extends Fragment {

    private FragmentFourBinding binding;
    private AppDataBase db;
    private int valor = -1;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFourBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db =AppDataBase.getDataBase(requireContext());

        binding.txVencedor.setVisibility(View.INVISIBLE);

        binding.tempo.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getContext(),
                    (timePicker, selectedHour, selectedMinute) -> {
                        String selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                        binding.tempo.setText(selectedTime);
                    },
                    hour, minute, true);
            timePickerDialog.show();
        });

        binding.data.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (datePickerView, year1, month1, dayOfMonth) -> {
                        String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                        binding.data.setText(selectedDate);
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        ArrayAdapter adpter = ArrayAdapter.createFromResource(getContext(),R.array.tipos_duelos, android.R.layout.simple_spinner_item);
        binding.tipoDuelos.setAdapter(adpter);

        binding.tipoDuelos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        binding.imageDuelos.setImageResource(R.drawable.simple);
                        break;
                    case 1:
                        binding.imageDuelos.setImageResource(R.drawable.time);
                        break;
                    case 2:
                        binding.imageDuelos.setImageResource(R.drawable.death);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] nomes = db.jogadorDao().buscaNICK();
        ArrayAdapter<String> pl1 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, nomes);
        binding.plauer01.setAdapter(pl1);
        ArrayAdapter<String> pl2 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, nomes);
        binding.plauer02.setAdapter(pl2);


        Bundle args = getArguments();
        if(args != null){
            binding.txVencedor.setVisibility(View.VISIBLE);
            binding.titulo2.setText("ATUALIZA DUELO");
            valor = args.getInt("idd");
            Duelo duelo = db.dueloDao().busca(valor);
            binding.pontosP1.setText(String.valueOf(duelo.getPontosP1()));
            binding.pontosP2.setText(String.valueOf(duelo.getPontosP2()));
            binding.txVencedor.setText("Vencedor: "+duelo.getVencedor());
            binding.data.setText(duelo.getData());
            binding.tempo.setText(duelo.getTempo());
            for(int i=0; i < binding.tipoDuelos.getCount(); i++){
                if(binding.tipoDuelos.getAdapter().getItem(i).equals(duelo.getTipo())){
                    binding.tipoDuelos.setSelection(i);
                    break;
                }
            }
            for(int i=0; i < binding.plauer01.getCount();i++){
                if(binding.plauer01.getAdapter().getItem(i).equals(db.jogadorDao().buscaJogadorNick(duelo.getIDPlayer1()))){
                    binding.plauer01.setSelection(i);
                    break;
                }
            }
            for(int i=0; i < binding.plauer02.getCount();i++){
                if(binding.plauer02.getAdapter().getItem(i).equals(db.jogadorDao().buscaJogadorNick(duelo.getIDPlayer2()))){
                    binding.plauer02.setSelection(i);
                    break;
                }
            }
        }

        binding.salvar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String p1 = binding.plauer01.getSelectedItem().toString();
                    String p2 = binding.plauer02.getSelectedItem().toString();
                    if (p1 != p2) {
                        String tipo = binding.tipoDuelos.getSelectedItem().toString();
                        int pn1 = Integer.parseInt(binding.pontosP1.getText().toString());
                        int pn2 = Integer.parseInt(binding.pontosP2.getText().toString());
                        String data = binding.data.getText().toString();
                        String tempo = binding.tempo.getText().toString();
                        String venncedor = "";
                        if (pn1 > pn2) {
                            venncedor = p1;
                        } else if (pn2 > pn1) {
                            venncedor = p2;
                        } else if (pn2 == pn1) {
                            venncedor = "Empate";
                        }
                        binding.txVencedor.setText("Vencedor: " + venncedor);
                        if (pn1 <= 100 && pn2 <= 100) {
                            Duelo duelo = new Duelo(data, tipo, db.jogadorDao().buscanickID(p1), db.jogadorDao().buscanickID(p2), pn1, pn2, tempo);
                            if ((!(p1.equals("") && p2.equals("") && tipo.equals("") && venncedor.equals("") && pn1 > 0 && pn2 > 0 && data.equals("") && tempo.equals(""))) && valor == -1) {
                                if (!binding.tipoDuelos.getSelectedItem().toString().equals("Duelo Contra o Relogio")) {
                                    duelo.setTempo("Duelo sem tempo");
                                }
                                duelo.setVencedor(venncedor);
                                db.dueloDao().insertAll(duelo);
                                Toast.makeText(requireContext(), "Duelo adicionado com sucesso", Toast.LENGTH_LONG).show();
                                binding.pontosP1.setText("");
                                binding.pontosP2.setText("");
                                binding.txVencedor.setText("");
                                binding.data.setText("");
                                binding.tempo.setText("");
                                binding.tipoDuelos.setSelection(0);
                                binding.plauer01.setSelection(0);
                                binding.plauer02.setSelection(0);
                            } else if (valor > 0) {
                                if (!binding.tipoDuelos.getSelectedItem().toString().equals("Duelo Contra o Relogio")) {
                                    duelo.setTempo("Duelo sem tempo");
                                }
                                duelo.setVencedor(venncedor);
                                duelo.setIDDuelo(valor);
                                db.dueloDao().upgrade(duelo);
                                Toast.makeText(requireContext(), "Duelo atualizado com sucesso", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(requireContext(), "A pontuaçao só vai até 100", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(requireContext(), "Nao pode ser o mesmo jogador", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(requireContext(), "Duelo nao adicionado", Toast.LENGTH_LONG).show();
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
