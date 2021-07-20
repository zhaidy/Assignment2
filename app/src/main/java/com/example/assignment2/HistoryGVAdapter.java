package com.example.assignment2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class HistoryGVAdapter extends ArrayAdapter<HistoryModel> {
    public HistoryGVAdapter(@NonNull Context context, ArrayList<HistoryModel> historyModelArrayList) {
        super(context, 0, historyModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.history_item, parent, false);
        }
        HistoryModel historyModel = getItem(position);

        TextView txtType = listitemView.findViewById(R.id.txtType);
        txtType.setText(historyModel.getType());

        TextView txtCode = listitemView.findViewById(R.id.txtCode);
        txtCode.setText(historyModel.getCode());

        ImageView imgImage = listitemView.findViewById(R.id.imgImage);
        imgImage.setImageBitmap(historyModel.getImg());

        TextView txtDate = listitemView.findViewById(R.id.txtDate);
        txtDate.setText(historyModel.getDate());

        return listitemView;
    }
}
