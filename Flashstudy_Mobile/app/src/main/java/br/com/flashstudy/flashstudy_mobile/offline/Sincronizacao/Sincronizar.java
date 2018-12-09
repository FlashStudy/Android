package br.com.flashstudy.flashstudy_mobile.offline.Sincronizacao;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.flashstudy.flashstudy_mobile.Util.ConversaoDeClasse;
import br.com.flashstudy.flashstudy_mobile.Util.Util;
import br.com.flashstudy.flashstudy_mobile.offline.model.AssuntoOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.CronogramaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.DisciplinaOff;
import br.com.flashstudy.flashstudy_mobile.offline.model.FlashcardOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.AssuntoRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.CicloRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.CronogramaRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.DisciplinaRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.offline.repository.FlashcardRepositoryOff;
import br.com.flashstudy.flashstudy_mobile.online.model.Assunto;
import br.com.flashstudy.flashstudy_mobile.online.model.Ciclo;
import br.com.flashstudy.flashstudy_mobile.online.model.Cronograma;
import br.com.flashstudy.flashstudy_mobile.online.model.Disciplina;
import br.com.flashstudy.flashstudy_mobile.online.model.Flashcard;
import br.com.flashstudy.flashstudy_mobile.online.repository.AssuntoRepository;
import br.com.flashstudy.flashstudy_mobile.online.repository.CicloRepository;
import br.com.flashstudy.flashstudy_mobile.online.repository.CronogramaRepository;
import br.com.flashstudy.flashstudy_mobile.online.repository.FlashcardRepository;

public class Sincronizar {
    private ProgressDialog progressDialog;
    private Context context;

    public Sincronizar(Context context) {
        this.context = context;

    }

    public boolean sincronizar() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Sincronizando dados!");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.show();


        long codigo_usuario = Util.getLocalUserCodigo(context);

        AssuntoRepository assuntoRepository = new AssuntoRepository();
        AssuntoRepositoryOff assuntoRepositoryOff = new AssuntoRepositoryOff(context);

        CicloRepository cicloRepository = new CicloRepository();
        CicloRepositoryOff cicloRepositoryOff = new CicloRepositoryOff(context);

        //SALVA O CRONOGRAMA E AS DISCIPLINAS
        CronogramaRepository cronogramaRepository = new CronogramaRepository();
        CronogramaRepositoryOff cronogramaRepositoryOff = new CronogramaRepositoryOff(context);

        DisciplinaRepositoryOff disciplinaRepositoryOff = new DisciplinaRepositoryOff(context);

        FlashcardRepository flashcardRepository = new FlashcardRepository();
        FlashcardRepositoryOff flashcardRepositoryOff = new FlashcardRepositoryOff(context);

        try {
            Log.e("BUSCANDO", "CRONOGRAMA");

            Cronograma cronograma = cronogramaRepository.procurar(codigo_usuario);
            if (cronograma != null) {
                CronogramaOff cronogramaOff = cronogramaRepositoryOff.buscarPorUsario(codigo_usuario);

                List<Disciplina> disciplinas = new ArrayList<>();
                List<DisciplinaOff> disciplinaOffs = disciplinaRepositoryOff.listar(codigo_usuario);

                disciplinas.addAll(cronograma.getDisciplinas());

                List<DisciplinaOff> salvar = new ArrayList<>();
                List<DisciplinaOff> atualizar = new ArrayList<>();

                // Anda pelo array de disciplinas
                for (Disciplina d : disciplinas) {

                    boolean encontrado = false;

                    // Anda pelo array de disciplinas local
                    for (DisciplinaOff off : disciplinaOffs) {

                        if (d.getCodigo() == off.getCodigo()) {
                            atualizar.add(ConversaoDeClasse.disciplinaToDisciplinaOff(d));
                            encontrado = true;
                        }

                    }

                    if (!encontrado)
                        salvar.add(ConversaoDeClasse.disciplinaToDisciplinaOff(d));
                }


                if (cronogramaOff != null) {
                    cronogramaOff = ConversaoDeClasse.cronogramaToCronogramaOff(cronograma);
                    cronogramaOff.setDisciplinas(salvar);


                    cronogramaRepositoryOff.atualizar(cronogramaOff);
                    progressDialog.setProgress(progressDialog.getProgress() + 10);

                    disciplinaRepositoryOff.atualizarLista(atualizar);
                    progressDialog.setProgress(progressDialog.getProgress() + 10);

                } else {
                    cronogramaOff = ConversaoDeClasse.cronogramaToCronogramaOff(cronograma);
                    cronogramaOff.setDisciplinas(salvar);

                    cronogramaRepositoryOff.salvar(cronogramaOff);
                    progressDialog.setProgress(progressDialog.getProgress() + 10);

                    disciplinaRepositoryOff.atualizarLista(atualizar);
                    progressDialog.setProgress(progressDialog.getProgress() + 10);
                }


            }

            Log.e("BUSCANDO", "ASSUNTOS");
            List<Assunto> assuntos = assuntoRepository.listar(codigo_usuario);
            List<AssuntoOff> assuntoOffs = assuntoRepositoryOff.listar();

            List<AssuntoOff> salvarAssuntos = new ArrayList<>();
            List<AssuntoOff> atualizarAssuntos = new ArrayList<>();

            if (assuntos != null) {
                for (Assunto assunto : assuntos) {

                    boolean encontrado = false;

                    for (AssuntoOff off : assuntoOffs) {
                        if (assunto.getCodigo() == off.getCodigo()) {
                            atualizarAssuntos.add(ConversaoDeClasse.assuntoToAssuntoOff(assunto));
                            encontrado = true;
                        }
                    }

                    if (!encontrado) {
                        salvarAssuntos.add(ConversaoDeClasse.assuntoToAssuntoOff(assunto));
                    }

                }

                assuntoRepositoryOff.salvarLista(salvarAssuntos);
                progressDialog.setProgress(progressDialog.getProgress() + 10);

                assuntoRepositoryOff.atualizarLista(atualizarAssuntos);
                progressDialog.setProgress(progressDialog.getProgress() + 10);
            }

            Log.e("BUSCANDO", "CICLO");
            Ciclo ciclo = cicloRepository.procurar(codigo_usuario);

            if (ciclo != null) {
                cicloRepositoryOff.salvar(ciclo);
            }

            Log.e("BUSCANDO", "FLASHCARDS");
            List<Flashcard> flashcards = flashcardRepository.listarFlashcards(codigo_usuario);
            List<FlashcardOff> flashcardOffs = flashcardRepositoryOff.listar(codigo_usuario);

            List<FlashcardOff> salvarFlashcards = new ArrayList<>();
            List<FlashcardOff> atualizarFlashcards = new ArrayList<>();


            if (flashcards != null) {
                for (Flashcard flashcard : flashcards) {
                    boolean encontrado = false;

                    for (FlashcardOff off : flashcardOffs) {
                        if (flashcard.getCodigo() == off.getCodigo()) {
                            atualizarFlashcards.add(ConversaoDeClasse.flashcardToFlashcardOff(flashcard));
                            encontrado = true;
                        }
                    }

                    if (!encontrado) {
                        salvarFlashcards.add(ConversaoDeClasse.flashcardToFlashcardOff(flashcard));
                    }

                }

                flashcardRepositoryOff.salvarLista(salvarFlashcards);
                progressDialog.setProgress(progressDialog.getProgress() + 10);

                flashcardRepositoryOff.atualizarLista(atualizarFlashcards);
                progressDialog.setProgress(progressDialog.getProgress() + 10);
            }

            if (progressDialog.isShowing())
                progressDialog.dismiss();

            return true;
        } catch (Exception e) {
            Log.e("ERRO SINCRO", e.getMessage());
            e.printStackTrace();
            if (progressDialog.isShowing()) {

                progressDialog.dismiss();
            }
            return false;
        }
    }
}



