package com.example.trabalhoprogmoba2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DueloDao {

    @Query("SELECT * FROM duelos")
    List<Duelo> getall();

    @Query("SELECT * FROM duelos WHERE IDDuelo = :id")
    Duelo busca(int id);

    @Query("SELECT * FROM duelos WHERE idplayer1 = :id")
    List<Duelo> buscaIDplayer1(int id);

    @Query("SELECT * FROM duelos WHERE idplayer2 = :id")
    List<Duelo> buscaIDplayer2(int id);

    @Insert
    void insertAll(Duelo... duelos);

    @Update
    void upgrade(Duelo duelos);

    @Delete
    void delete(Duelo duelos);
}
