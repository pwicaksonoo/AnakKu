package com.example.anakku;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.anakku.viewmodels.ArtikelViewModel;
import com.example.anakku.viewmodels.ArtikelViewModelFactory;

public class ArtikelTumbuhKembangAnakFragment extends Fragment {

    private ArtikelViewModel artikelViewModel;
    private ImageView articleImageView;

    private final String artikelDocumentId = "axvgNijGlUVZJ2VtshXP";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        artikelViewModel = new ViewModelProvider(this, new ArtikelViewModelFactory(this.getActivity().getApplication(), artikelDocumentId)).get(ArtikelViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artikel_tumbuh_kembang_anak, container, false);

        articleImageView = view.findViewById(R.id.imageViewArticle);

        artikelViewModel.getArticleMutableLiveData().observe(getViewLifecycleOwner(), article -> {
            articleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("documentId", article.getDocumentId());
                    Navigation.findNavController(getView()).navigate(R.id.action_artikelTumbuhKembangAnakFragment_to_forumFragment, bundle);
                }
            });
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
        artikelViewModel.stopArtikelListener();
    }
}