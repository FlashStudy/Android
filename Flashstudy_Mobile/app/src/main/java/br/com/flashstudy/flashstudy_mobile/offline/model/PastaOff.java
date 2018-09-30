package br.com.flashstudy.flashstudy_mobile.offline.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "pasta",
        foreignKeys = {@ForeignKey(entity = UsuarioOff.class,
                parentColumns = "codigo",
                childColumns = "usuario_codigo")},
        indices = {@Index(value = {"codigo"}), @Index(value = {"usuario_codigo"})})
public class PastaOff {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int codigo;

    @NonNull
    private String nome;

    @NonNull
    @ColumnInfo(name = "usuario_codigo")
    private int usuarioCodigo;

    @Ignore
    private List<FlashcardOff> flashcards = new ArrayList<>();

    public PastaOff() {
    }

    @Ignore
    public PastaOff(@NonNull String nome, @NonNull int usuarioCodigo, List<FlashcardOff> flashcards) {
        this.nome = nome;
        this.usuarioCodigo = usuarioCodigo;
        this.flashcards = flashcards;
    }

    @NonNull
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(@NonNull int codigo) {
        this.codigo = codigo;
    }

    @NonNull
    public String getNome() {
        return nome;
    }

    public void setNome(@NonNull String nome) {
        this.nome = nome;
    }

    @NonNull
    public int getUsuarioCodigo() {
        return usuarioCodigo;
    }

    public void setUsuarioCodigo(@NonNull int usuarioCodigo) {
        this.usuarioCodigo = usuarioCodigo;
    }

    public List<FlashcardOff> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<FlashcardOff> flashcards) {
        this.flashcards = flashcards;
    }

    /*public void addFlashcard(FlashcardOff flashcard) {
        flashcards.add(flashcard);
        flashcard.setPastaCodigo(codigo);
    }*/

    @Override
    public String toString() {
        return "PastaOff{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", usuarioCodigo=" + usuarioCodigo +
                ", flashcards=" + flashcards +
                '}';
    }
}