package com.example.anakku.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anakku.R;
import com.example.anakku.models.Child;
import com.example.anakku.settings.SharedPref;

import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder> {
    List<Child> childList;

    public ChildAdapter(List<Child> childList) {
        this.childList = childList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_child_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String nama = "+";
        if(childList.get(position) != null) {
            nama = childList.get(position).getNama();
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPref.write(SharedPref.ACTIVE_CHILD, childList.get(position).getDocumentId());
                    Navigation.findNavController(view).navigate(R.id.action_pickChildFragment_to_homeFragment);
                }
            });
        } else {
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(R.id.action_pickChildFragment_to_registerChildFragment);
                }
            });
        }
        holder.namaAnakTextView.setText(nama);
    }

    @Override
    public int getItemCount() {
        if(childList != null) return childList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView namaAnakTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            this.namaAnakTextView = view.findViewById(R.id.textViewNamaAnak);
        }
    }
}
