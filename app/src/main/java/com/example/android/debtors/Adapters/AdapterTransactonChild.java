package com.example.android.debtors.Adapters;

import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.example.android.debtors.R;

/**
 * Created by admin on 13.03.2017.
 */

public class AdapterTransactonChild extends ChildViewHolder {

    private TextView textViewQuantity, textViewProductValue, textViewEntryPayment, textViewDetails;
    private LinearLayout linearLayout;

    public AdapterTransactonChild(View itemView) {
        super(itemView);

        textViewQuantity = (TextView) itemView.findViewById(R.id.paychild)
    }


}
