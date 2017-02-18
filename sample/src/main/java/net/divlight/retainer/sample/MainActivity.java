package net.divlight.retainer.sample;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.divlight.retainer.Retainer;
import net.divlight.retainer.annotation.Retain;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    @Retain
    int retainedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retainer.onCreate(this);

        retainedValue++;

        final TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText(String.format(Locale.ENGLISH, "Retained value : %d", retainedValue));

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MainDialogFragment().show(getSupportFragmentManager(), MainDialogFragment.TAG);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Retainer.onDestroy(this);
    }

    public static class MainDialogFragment extends DialogFragment {
        public static final String TAG = MainDialogFragment.class.getSimpleName();

        @Retain
        int retainedValue;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Retainer.onCreate(this);

            retainedValue++;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Retainer.onDestroy(this);
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getContext())
                    .setMessage(String.format(Locale.ENGLISH, "Retained value : %d", retainedValue))
                    .setPositiveButton(android.R.string.ok, null)
                    .create();
        }
    }
}
