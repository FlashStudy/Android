package br.com.flashstudy.flashstudy_mobile.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import br.com.flashstudy.flashstudy_mobile.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AjudaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnAjudaCiclo)
    public void ajudaCiclo() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("1. Na tela Ciclo de Estudos clique no botão 'editar' \n\n");
        stringBuilder.append("2. Preencha os campos com os dias da semana e quantidade de matérias que serão estudadas por dia \n\n");
        stringBuilder.append("3. Clique em confirmar e o sistema irá gerar um ciclo para você! \n\n\n");
        stringBuilder.append("P.S. É necessário que você já tenha criado um Cronograma de Estudos para que o siclo seja gerado!");

        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle(R.string.lbl_btn_ajuda_ciclo);
        dlg.setMessage(stringBuilder.toString());

        dlg.setNeutralButton("OK!", null);
        dlg.show();
    }

    @OnClick(R.id.btnAjudaCronograma)
    public void ajudaCronograma() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("1. Na tela Cronograma clique no botão 'editar' \n\n");
        stringBuilder.append("2. Preencha os campos com o período de validade do cronograma e algumas matérias iniciais \n\n");
        stringBuilder.append("3. Clique em confirmar e o sistema irá salvar o seu Cronograma! \n\n\n");
        stringBuilder.append("P.S. Você pode alterar tanto o seu Cronograma quanto as suas Matérias nessa mesma tela!");

        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle(R.string.lbl_btn_ajuda_cronograma);
        dlg.setMessage(stringBuilder.toString());

        dlg.setNeutralButton("OK!", null);
        dlg.show();

    }

    @OnClick(R.id.btnAjudaFlashcard)
    public void ajudaFlashcard() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("1. Na tela Flashcards clique no botão flutuante ou navegue pelo menu Flashcards -> Novo \n\n");
        stringBuilder.append("2. Preencha os campos com título, pergunta e resposta do Flashcard \n\n");
        stringBuilder.append("3. Clique em salvar e pronto! \n\n\n");
        stringBuilder.append("P.S. Agora você pode ter acesso aos seus flashcards da página inicial de Flashcards!");

        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle(R.string.lbl_btn_ajuda_flashcard);
        dlg.setMessage(stringBuilder.toString());

        dlg.setNeutralButton("OK!", null);
        dlg.show();

    }
}