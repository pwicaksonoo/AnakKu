package com.example.anakku.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anakku.R;
import com.example.anakku.models.ActivityAnak;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    List<ActivityAnak> activityAnakList;
    public ActivityAdapter(List<ActivityAnak> activityAnakList) {
        this.activityAnakList = activityAnakList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_activity_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.activityAnakTextView.setText(activityAnakList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if(activityAnakList != null) return activityAnakList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView activityAnakTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            activityAnakTextView = view.findViewById(R.id.textViewActivityAnak);
        }
    }
}
