package com.example.anakku;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anakku.adapters.ForumAdapter;
import com.example.anakku.viewmodels.ForumViewModel;
import com.example.anakku.viewmodels.ForumViewModelFactory;

import java.sql.Timestamp;
import java.util.Date;

public class ForumFragment extends Fragment {

    private ForumViewModel forumViewModel;
    private TextView judulArtikelTextView;
    private RecyclerView forumRecyclerView;
    private ForumAdapter forumAdapter;
    private RelativeLayout loadingPanel;
    private EditText newDiscussionEditText;
    private Button addDiscussionButton;

    private String articleDocumentId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        articleDocumentId = bundle.getString("documentId");
        forumViewModel = new ViewModelProvider(this, new ForumViewModelFactory(this.getActivity().getApplication(), articleDocumentId)).get(ForumViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum, container, false);

        judulArtikelTextView = view.findViewById(R.id.textViewJudulArtikel);
        forumViewModel.getArticleMutableLiveData().observe(getViewLifecycleOwner(), article -> {
            judulArtikelTextView.setText(judulArtikelTextView.getText().toString() + article.getJudul());
        });

        loadingPanel = view.findViewById(R.id.loadingPanel);
        loadingPanel.setVisibility(View.VISIBLE);

        forumRecyclerView = view.findViewById(R.id.recyclerViewForum);
        forumRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        forumRecyclerView.setHasFixedSize(true);

        forumViewModel.getDiscussionListMutableLiveData().observe(getViewLifecycleOwner(), discussions -> {
            loadingPanel.setVisibility(View.GONE);
            forumAdapter = new ForumAdapter(discussions);
            forumRecyclerView.setAdapter(forumAdapter);
            forumAdapter.notifyDataSetChanged();
        });

        newDiscussionEditText = view.findViewById(R.id.editTextNewDiscussion);
        addDiscussionButton = view.findViewById(R.id.buttonAddDiscussion);
        addDiscussionButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {
                if(newDiscussionEditText.getText().length() == 0) {
                    Toast.makeText(getContext(), "Diskusi baru tidak boleh kosong", Toast.LENGTH_SHORT);
                } else {
                    Date currentDate = new Date();
                    Timestamp currentTimeStamp = new Timestamp(currentDate.getTime());
                    forumViewModel.addNewDiscussion(newDiscussionEditText.getText().toString(), currentTimeStamp);
                    newDiscussionEditText.setText("");
                    Toast.makeText(getContext(), "Diskusi baru berhasil ditambahkan", Toast.LENGTH_SHORT);
                }

                try {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                } catch (Exception e) {

                }
                newDiscussionEditText.clearFocus();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}