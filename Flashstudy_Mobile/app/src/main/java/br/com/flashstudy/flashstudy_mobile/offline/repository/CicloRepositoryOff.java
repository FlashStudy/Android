package br.com.flashstudy.flashstudy_mobile.offline.repository;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.offline.database.AppDatabase;
import br.com.flashstudy.flashstudy_mobile.offline.model.CicloOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.DiaDaSemanaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.DisciplinaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.HorarioOff;

public class CicloRepositoryOff {

    public static CicloOff buscarPorUsario(long codigo, Context context){
        try{
            return AppDatabase.getAppDatabase(context).cicloDao().getCicloByUsuario(codigo);
        }catch (Exception e){
            Log.i("ERRO BUSCAR CICLO", e.getMessage());
            return null;
        }
    }

    public static boolean salvar(CicloOff cicloOff, Context context){
        try{
            Random rand = new Random();

            CicloOff c = new CicloOff(cicloOff.getCodigo(), cicloOff.getNumMaterias(), cicloOff.getUsuarioCodigo());

            List<DiaDaSemanaOff> dias = cicloOff.getDias();

            List<DisciplinaOff> disciplinas = DisciplinaRepositoryOff.listarDisciplinas(Util.getLocalUserCodigo(context), context);

            int arrLength = disciplinas.size();

            c.setDias(new ArrayList<DiaDaSemanaOff>());

            for (DiaDaSemanaOff dia : dias){
                for (int i = 0; i< cicloOff.getNumMaterias(); i++){
                    HorarioOff horarioOff = new HorarioOff(i, disciplinas.get(rand.nextInt(arrLength)).getCodigo(), cicloOff.getUsuarioCodigo());
                    dia.addHorario(horarioOff);
                }

                dia.setUsuarioCodigo(cicloOff.getUsuarioCodigo());
                c.addDiaDaSemana(dia);
            }

            AppDatabase.getAppDatabase(context).cicloDao().insert(cicloOff);

            return true;
        }catch (Exception e){
            Log.i("ERRO SALVAR CICLO", e.getMessage());
            return false;
        }
    }

    public static CicloOff atualizar(long codigo, Context context){
        try{
            return AppDatabase.getAppDatabase(context).cicloDao().getCicloByUsuario(codigo);
        }catch (Exception e){
            Log.i("ERRO BUSCAR CICLO", e.getMessage());
            return null;
        }
    }
}
