package dev.twittoapkvk.twito;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import dev.twittoapkvk.twito.R;

public class BottomSheetF2 extends BottomSheetDialogFragment {

    TextView cardView,cardView2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.bottomsheet_f2,null);
        cardView = view.findViewById(R.id.related_f2);
        cardView2 = view.findViewById(R.id.your_Question_f2);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ShavedQuestionActivity.class));
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),YourQuestions.class));
            }
        });

        return view;

    }
}
