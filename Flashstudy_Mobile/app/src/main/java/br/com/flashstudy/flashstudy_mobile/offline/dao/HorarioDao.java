package br.com.flashstudy.flashstudy_mobile.offline.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.offline.model.HorarioOff;

@Dao
public interface HorarioDao {

    //Insere
    @Insert
    void insert(HorarioOff horario);

    //Insere lista
    @Insert
    void insertLista(List<HorarioOff> horarios);

    //Deleta
    @Delete
    void delete(HorarioOff horario);

    //Deleta lista
    @Delete
    void deleteLista(List<HorarioOff> horario);

    //Atualiza
    @Update
    void update(HorarioOff horario);

    //Procura Horarios de um Usuario
    @Query("SELECT * FROM horario WHERE usuario_codigo = :codigo ORDER BY codigo ASC")
    LiveData<List<HorarioOff>> getAllHorariosByUsuario(int codigo);
}
