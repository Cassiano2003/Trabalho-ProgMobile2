package com.example.trabalhoprogmoba2;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {Jogador.class,Duelo.class},version = 2)
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase INSTANCE;

    public static AppDataBase getDataBase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,"SAODataBase").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
    public abstract JogadorDao jogadorDao();
    public abstract DueloDao dueloDao();
}