package br.com.flashstudy.flashstudy_mobile.offline.dao;

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
    void salvar(HorarioOff horarioOff);

    //Insere lista
    @Insert
    void salvarLista(List<HorarioOff> horarioOffs);

    //Deleta
    @Delete
    void deletar(HorarioOff horarioOff);

    //Deleta lista
    @Delete
    void deletarLista(List<HorarioOff> horarioOffs);

    //Atualiza
    @Update
    void atualizar(HorarioOff horarioOff);

    //Procura Horarios de um Usuario
    @Query("SELECT * FROM horario WHERE usuario_codigo = :codigo ORDER BY tempo ASC")
    List<HorarioOff> getAllHorariosByUsuario(long codigo);
}
