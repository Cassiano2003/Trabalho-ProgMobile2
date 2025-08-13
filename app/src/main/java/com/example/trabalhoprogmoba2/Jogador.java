package com.example.trabalhoprogmoba2;

import android.media.Image;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity(tableName = "jogador")
public class Jogador {
    @PrimaryKey(autoGenerate = true)
    public int IDJogador;
    @ColumnInfo(name = "nome")
    public String nome;
    @ColumnInfo(name = "nick")
    public String nick;
    @ColumnInfo(name = "idade")
    public int idade;
    @ColumnInfo(name = "genero")
    public String genero;
    @ColumnInfo(name = "habilidades")
    public String[] habilidades;
    @ColumnInfo(name = "nivel")
    public int nivel;
    @ColumnInfo(name = "giuda")
    public String guida;
    @ColumnInfo(name = "arma")
    public String arma;

    public Jogador(String nome, String nick, int idade, String genero, String[] habilidades, int nivel, String guida,String arma) {
        this.nome = nome;
        this.nick = nick;
        this.idade = idade;
        this.genero = genero;
        this.habilidades = habilidades;
        this.nivel = nivel;
        this.guida = guida;
        this.arma = arma;
    }

    public int getIDJogador() {
        return IDJogador;
    }

    public void setIDJogador(int IDJogador) {
        this.IDJogador = IDJogador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String[] getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(String[] habilidades) {
        this.habilidades = habilidades;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getGuida() {
        return guida;
    }

    public void setGuida(String guida) {
        this.guida = guida;
    }

    public String getArma() {
        return arma;
    }

    public void setArma(String arma) {
        this.arma = arma;
    }

    @Override
    public String toString() {
        return IDJogador + " : " + nick + "  "+ nivel + "  " + guida;
    }
}
