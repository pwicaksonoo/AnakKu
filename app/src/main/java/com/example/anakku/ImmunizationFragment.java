package com.example.anakku;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.anakku.viewmodels.ImmunizationViewModel;

import java.util.HashMap;
import java.util.Map;

public class ImmunizationFragment extends Fragment {

    private ImmunizationViewModel immunizationViewModel;
    private CheckBox cat1BCGCheckBox, cat1RotavirusCheckBox, cat1PolioCheckBox,
            cat1PCVCheckBox, cat1HepatitisBCheckBox, cat1CampakCheckBox, cat1DPTCheckBox;
    private CheckBox cat2DPTCheckBox, cat2TifoidCheckBox, cat2PolioCheckBox,
            cat2HepatitisACheckBox, cat2PCVCheckBox, cat2VariselaCheckBox, cat2MMRCheckBox;
    private CheckBox cat3DPTCheckbox, cat3TifoidCheckBox, cat3PolioCheckBox,
            cat3HepatitisACheckBox, cat3PCVCheckBox, cat3VariselaCheckBox,
            cat3MMRCheckBox, cat3InfluenzaCheckBox, cat3CampakCheckBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        immunizationViewModel = new ViewModelProvider(this).get(ImmunizationViewModel.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_immunization, container, false);

        cat1BCGCheckBox = view.findViewById(R.id.checkBoxCat1BCG);
        cat1RotavirusCheckBox = view.findViewById(R.id.checkBoxCat1Rotavirus);
        cat1PolioCheckBox = view.findViewById(R.id.checkBoxCat1Polio);
        cat1PCVCheckBox = view.findViewById(R.id.checkBoxCat1PCV);
        cat1HepatitisBCheckBox = view.findViewById(R.id.checkBoxCat1HepatitisB);
        cat1CampakCheckBox = view.findViewById(R.id.checkBoxCat1Campak);
        cat1DPTCheckBox = view.findViewById(R.id.checkBoxCat1DPT);

        cat2DPTCheckBox = view.findViewById(R.id.checkBoxCat2DPT);
        cat2TifoidCheckBox = view.findViewById(R.id.checkBoxCat2Tifoid);
        cat2PolioCheckBox = view.findViewById(R.id.checkBoxCat2Polio);
        cat2HepatitisACheckBox = view.findViewById(R.id.checkBoxCat2HepatitisA);
        cat2PCVCheckBox = view.findViewById(R.id.checkBoxCat2PCV);
        cat2VariselaCheckBox = view.findViewById(R.id.checkBoxCat2Varisel);
        cat2MMRCheckBox = view.findViewById(R.id.checkBoxCat2MMR);

        cat3DPTCheckbox = view.findViewById(R.id.checkBoxCat3DPT);
        cat3TifoidCheckBox = view.findViewById(R.id.checkBoxCat3Tifoid);
        cat3PolioCheckBox = view.findViewById(R.id.checkBoxCat3Polio);
        cat3HepatitisACheckBox = view.findViewById(R.id.checkBoxCat3HepatitisA);
        cat3PCVCheckBox = view.findViewById(R.id.checkBoxCat3PCV);
        cat3VariselaCheckBox = view.findViewById(R.id.checkBoxCat3Varisela);
        cat3MMRCheckBox = view.findViewById(R.id.checkBoxCat3MMR);
        cat3InfluenzaCheckBox = view.findViewById(R.id.checkBoxCat3Influenza);
        cat3CampakCheckBox = view.findViewById(R.id.checkBoxCat3Campak);

        immunizationViewModel.getImmunizationMutableLiveData().observe(getViewLifecycleOwner(), immunization -> {
            if(immunization != null) {
                if(immunization.getCategory1() != null) {
                    cat1BCGCheckBox.setChecked(immunization.getCategory1().getOrDefault("BCG", false));
                    cat1RotavirusCheckBox.setChecked(immunization.getCategory1().getOrDefault("Rotavirus", false));
                    cat1PolioCheckBox.setChecked(immunization.getCategory1().getOrDefault("Polio", false));
                    cat1PCVCheckBox.setChecked(immunization.getCategory1().getOrDefault("PCV", false));
                    cat1HepatitisBCheckBox.setChecked(immunization.getCategory1().getOrDefault("HepatitisB", false));
                    cat1CampakCheckBox.setChecked(immunization.getCategory1().getOrDefault("Campak", false));
                    cat1DPTCheckBox.setChecked(immunization.getCategory1().getOrDefault("DPT", false));
                }

                if(immunization.getCategory2() != null) {
                    cat2DPTCheckBox.setChecked(immunization.getCategory2().getOrDefault("DPT", false));
                    cat2TifoidCheckBox.setChecked(immunization.getCategory2().getOrDefault("Tifoid", false));
                    cat2PolioCheckBox.setChecked(immunization.getCategory2().getOrDefault("Polio", false));
                    cat2HepatitisACheckBox.setChecked(immunization.getCategory2().getOrDefault("HepatitisA", false));
                    cat2PCVCheckBox.setChecked(immunization.getCategory2().getOrDefault("PCV", false));
                    cat2VariselaCheckBox.setChecked(immunization.getCategory2().getOrDefault("Varisela", false));
                    cat2MMRCheckBox.setChecked(immunization.getCategory2().getOrDefault("MMR", false));
                }

                if(immunization.getCategory3() != null) {
                    cat3DPTCheckbox.setChecked(immunization.getCategory3().getOrDefault("DPT", false));
                    cat3TifoidCheckBox.setChecked(immunization.getCategory3().getOrDefault("Tifoid", false));
                    cat3PolioCheckBox.setChecked(immunization.getCategory3().getOrDefault("Polio", false));
                    cat3HepatitisACheckBox.setChecked(immunization.getCategory3().getOrDefault("HepatitisA", false));
                    cat3PCVCheckBox.setChecked(immunization.getCategory3().getOrDefault("PCV", false));
                    cat3VariselaCheckBox.setChecked(immunization.getCategory3().getOrDefault("Varisela", false));
                    cat3MMRCheckBox.setChecked(immunization.getCategory3().getOrDefault("MMR", false));
                    cat3InfluenzaCheckBox.setChecked(immunization.getCategory3().getOrDefault("Influenza", false));
                    cat3CampakCheckBox.setChecked(immunization.getCategory3().getOrDefault("Campak", false));
                }
            }
        });

        initListenerCheckBoxCategory1();
        initListenerCheckBoxCategory2();
        initListenerCheckBoxCategory3();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



    public void initListenerCheckBoxCategory1() {
        cat1BCGCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory1() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory1(newMap);
                    }

                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory1().put("BCG", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
        cat1RotavirusCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory1() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory1(newMap);
                    }


                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory1().put("Rotavirus", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
        cat1PolioCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory1() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory1(newMap);
                    }

                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory1().put("Polio", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
        cat1PCVCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory1() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory1(newMap);
                    }

                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory1().put("PCV", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
        cat1HepatitisBCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory1() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory1(newMap);
                    }

                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory1().put("HepatitisB", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
        cat1CampakCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory1() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory1(newMap);
                    }

                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory1().put("Campak", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
        cat1DPTCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory1() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory1(newMap);
                    }

                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory1().put("DPT", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
    }

    private void initListenerCheckBoxCategory2() {
        cat2DPTCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory2() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory2(newMap);
                    }
                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory2().put("DPT", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
        cat2TifoidCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory2() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory2(newMap);
                    }

                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory2().put("Tifoid", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
        cat2PolioCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory2() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory2(newMap);
                    }

                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory2().put("Polio", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
        cat2HepatitisACheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory2() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory2(newMap);
                    }


                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory2().put("HepatitisA", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
        cat2PCVCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory2() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory2(newMap);
                    }

                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory2().put("PCV", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
        cat2VariselaCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory2() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory2(newMap);
                    }

                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory2().put("Varisela", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
        cat2MMRCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory2() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory2(newMap);
                    }

                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory2().put("MMR", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
    }

    private void initListenerCheckBoxCategory3() {
        cat3DPTCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory3() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory3(newMap);
                    }

                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory3().put("DPT", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
        cat3TifoidCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory3() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory3(newMap);
                    }

                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory3().put("Tifoid", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
        cat3PolioCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory3() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory3(newMap);
                    }

                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory3().put("Polio", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
        cat3HepatitisACheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory3() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory3(newMap);
                    }

                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory3().put("HepatitisA", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
        cat3PCVCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory3() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory3(newMap);
                    }

                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory3().put("PCV", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
        cat3VariselaCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory3() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory3(newMap);
                    }

                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory3().put("Varisela", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
        cat3MMRCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory3() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory3(newMap);
                    }

                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory3().put("MMR", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
        cat3InfluenzaCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory3() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory3(newMap);
                    }

                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory3().put("Influenza", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
        cat3CampakCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(immunizationViewModel.getImmunizationMutableLiveData().getValue() != null) {
                    if(immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory3() == null) {
                        Map<String, Boolean> newMap = new HashMap<>();
                        immunizationViewModel.getImmunizationMutableLiveData().getValue().setCategory3(newMap);
                    }

                    immunizationViewModel.getImmunizationMutableLiveData().getValue().getCategory3().put("Campak", b);
                    immunizationViewModel.changeImmunizationStatus(immunizationViewModel.getImmunizationMutableLiveData().getValue());
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        immunizationViewModel.stopImmunizationListener();
    }
}