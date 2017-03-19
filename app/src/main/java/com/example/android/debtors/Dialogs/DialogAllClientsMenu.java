package com.example.android.debtors.Dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.debtors.R;

/**
 * Created by admin on 19.03.2017.
 */

public class DialogAllClientsMenu extends DialogFragment {

    TextView textView = null;

    public static DialogAllClientsMenu newInstance(int num) {
        DialogAllClientsMenu f = new DialogAllClientsMenu();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    public DialogAllClientsMenu() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_allclients_menu, container, false);
        textView = (TextView) v.findViewById(R.id.chujdupa);


        return v;
    }
}
