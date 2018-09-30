package br.com.flashstudy.flashstudy_mobile.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.flashstudy.flashstudy_mobile.R;
import br.com.flashstudy.flashstudy_mobile.offline.model.FlashcardOff;
import br.com.flashstudy.flashstudy_mobile.online.model.Flashcard;

public class FlashcardListAdapter extends ArrayAdapter<FlashcardOff> {

    private List<FlashcardOff> flashcards;
    private Context context;

    public FlashcardListAdapter(@NonNull Context context, List<FlashcardOff> flashcards) {
        super(context, R.layout.activity_flashcard, flashcards);
        this.flashcards = flashcards;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.flashcard_layout, parent, false);

        FlashcardOff flashcard = this.flashcards.get(position);

        TextView textViewTitulo = view.findViewById(R.id.textViewTitulo);
        textViewTitulo.setText(flashcard.getTitulo());

        return view;

    }
}
