package com.example.trabalhoprogmoba2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "duelos")
public class Duelo {
    @PrimaryKey(autoGenerate = true)
    public int IDDuelo;
    @ColumnInfo(name = "data")
    public String data;
    @ColumnInfo(name = "tipo")
    public String tipo;
    @ColumnInfo(name = "idplayer1")
    public int IDPlayer1;
    @ColumnInfo(name = "idplayer2")
    public int IDPlayer2;
    @ColumnInfo(name = "pontosp1")
    public int pontosP1;
    @ColumnInfo(name = "pontosp2")
    public int pontosP2;
    @ColumnInfo(name = "tempo")
    public String tempo;
    @ColumnInfo(name = "vencedor")
    public String vencedor;

    public Duelo(String data, String tipo, int IDPlayer1, int IDPlayer2, int pontosP1, int pontosP2, String tempo) {
        this.data = data;
        this.tipo = tipo;
        this.IDPlayer1 = IDPlayer1;
        this.IDPlayer2 = IDPlayer2;
        this.pontosP1 = pontosP1;
        this.pontosP2 = pontosP2;
        this.tempo = tempo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIDPlayer1() {
        return IDPlayer1;
    }

    public void setIDPlayer1(int IDPlayer1) {
        this.IDPlayer1 = IDPlayer1;
    }

    public int getIDPlayer2() {
        return IDPlayer2;
    }

    public void setIDPlayer2(int IDPlayer2) {
        this.IDPlayer2 = IDPlayer2;
    }

    public int getPontosP1() {
        return pontosP1;
    }

    public void setPontosP1(int pontosP1) {
        this.pontosP1 = pontosP1;
    }

    public int getPontosP2() {
        return pontosP2;
    }

    public void setPontosP2(int pontosP2) {
        this.pontosP2 = pontosP2;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getVencedor() {
        return vencedor;
    }

    public void setVencedor(String vencedor) {
        this.vencedor = vencedor;
    }

    public int getIDDuelo() {
        return IDDuelo;
    }

    public void setIDDuelo(int IDDuelo) {
        this.IDDuelo = IDDuelo;
    }

    @Override
    public String toString() {
        return IDDuelo +
                    " : " + data +
                    "   " + tipo +
                    "   " + IDPlayer1 +
                    " VS " + IDPlayer2 +
                    "   " + tempo;
    }
}
