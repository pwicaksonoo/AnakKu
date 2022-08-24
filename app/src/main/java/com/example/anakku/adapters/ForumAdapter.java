package com.example.anakku.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anakku.R;
import com.example.anakku.models.Discussion;
import com.example.anakku.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ViewHolder> {

    List<Discussion> discussionList;
    private FirebaseFirestore db;

    public ForumAdapter(List<Discussion> discussionList) {
        this.discussionList = discussionList;
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_discussion_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.namaTextView.setText(discussionList.get(position).getUser().getNama());
        holder.textTextView.setText(discussionList.get(position).getText());

        db.collection("users")
                .document(discussionList.get(position).getUserDocumentId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            User user = task.getResult().toObject(User.class);
                            if(user != null) holder.namaTextView.setText(user.getNama());
                            else holder.namaTextView.setText("User");
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        if(discussionList != null) return discussionList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView namaTextView;
        TextView textTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            this.namaTextView = view.findViewById(R.id.textViewNama);
            this.textTextView = view.findViewById(R.id.textViewText);
        }
    }
}
