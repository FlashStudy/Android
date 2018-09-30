package br.com.flashstudy.flashstudy_mobile.offline.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.com.flashstudy.flashstudy_mobile.offline.dao.AssuntoDao;
import br.com.flashstudy.flashstudy_mobile.offline.dao.CicloDao;
import br.com.flashstudy.flashstudy_mobile.offline.dao.CronogramaDao;
import br.com.flashstudy.flashstudy_mobile.offline.dao.DiaDaSemanaDao;
import br.com.flashstudy.flashstudy_mobile.offline.dao.DisciplinaDao;
import br.com.flashstudy.flashstudy_mobile.offline.dao.FlashcardDao;
import br.com.flashstudy.flashstudy_mobile.offline.dao.HorarioDao;
import br.com.flashstudy.flashstudy_mobile.offline.dao.PastaDao;
import br.com.flashstudy.flashstudy_mobile.offline.dao.UsuarioDao;
import br.com.flashstudy.flashstudy_mobile.offline.model.AssuntoOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.CicloOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.CronogramaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.DiaDaSemanaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.DisciplinaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.FlashcardOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.HorarioOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.PastaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.UsuarioOff;


@Database(entities = {AssuntoOff.class, CicloOff.class,
        CronogramaOff.class, DiaDaSemanaOff.class,
        DisciplinaOff.class, FlashcardOff.class,
        HorarioOff.class, PastaOff.class, UsuarioOff.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AssuntoDao assuntoDao();
    public abstract CicloDao cicloDao();
    public abstract CronogramaDao cronogramaDao();
    public abstract DiaDaSemanaDao diaDaSemanaDao();
    public abstract DisciplinaDao disciplinaDao();
    public abstract FlashcardDao flashcardDao();
    public abstract HorarioDao horarioDao();
    public abstract PastaDao pastaDao();
    public abstract UsuarioDao usuarioDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database")
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
