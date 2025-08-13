package com.example.trabalhoprogmoba2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.intellij.lang.annotations.Pattern;

import java.util.List;

@Dao
public interface JogadorDao {

    @Query("SELECT * FROM jogador")
    List<Jogador> getall();

    @Query("SELECT EXISTS (SELECT 1 FROM jogador WHERE nome = :nome)")
    Boolean pesquisaNOME(String nome);

    @Query("SELECT COUNT(*) FROM jogador")
    int quantosJogadores();

    @Query("SELECT EXISTS (SELECT 1 FROM jogador WHERE nick = :nick)")
    Boolean pesquisaNICK(String nick);

    @Query("SELECT * FROM jogador WHERE IDJogador = :id")
    Jogador busca(int id);

    @Query("SELECT nome FROM jogador")
    String[] buscaNOME();

    @Query("SELECT nick FROM jogador")
    String[] buscaNICK();

    @Query("SELECT IDJogador FROM jogador WHERE nome = :nome")
    int buscanomeID(String nome);

    @Query("SELECT IDJogador FROM jogador WHERE nick = :nick")
    int buscanickID(String nick);

    @Query("SELECT * FROM jogador WHERE IDJogador = :id")
    Jogador buscaJogador(int id);

    @Query("SELECT nick FROM jogador WHERE IDJogador = :id")
    String buscaJogadorNick(int id);
    @Insert
    void insertAll(Jogador... jogadores);

    @Update
    void upgrade(Jogador jogadores);

    @Delete
    void delete(Jogador jogadores);
}
