package com.example.hci_onfitapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hci_onfitapp.R;
import com.example.hci_onfitapp.RoutineCardData;
import com.example.hci_onfitapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    ArrayList<RoutineCardData> lista = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentHomeBinding binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        //Esto es el slider de imagenes. No funciona
//        ImageSlider imageSlider = view.findViewById(R.id.home_slider);
//        List<SlideModel> slideModels = new ArrayList<>();
//        slideModels.add(new SlideModel("https://images.unsplash.com/photo-1500468756762-a401b6f17b46?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80", "Popular"));
//        slideModels.add(new SlideModel("https://images.unsplash.com/photo-1560233026-ad254fa8da38?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=610&q=80", "Favoritos"));
//        slideModels.add(new SlideModel("https://images.unsplash.com/photo-1567740034541-1ff8b618a370?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80", "Historia"));
//        imageSlider.setImageList(slideModels, true);
//        imageSlider.setItemClickListener(i -> {
//            NavController navController = Navigation.findNavController(view);
//            switch (i) {
//                case 0:
//                    new ViewModelProvider(getActivity()).get(RoutinesViewModel.class).setOrderById(1);
//                    navController.navigate(HomeFragmentDirections.actionHomeFragmentToRoutinesFragment());
//                    break;
//
//                case 1:
//                    navController.navigate(HomeFragmentDirections.actionHomeFragmentToMeFragment());
//                    break;
//
//                case 2:
//                    navController.navigate(HomeFragmentDirections.actionHomeFragmentToMyActivityFragment());
//                    break;x
//            }
//        });

        View viewReturn = inflater.inflate(R.layout.fragment_home, container, false);

        fillList();

        recyclerView = (RecyclerView) viewReturn.findViewById(R.id.recyclerViewHor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // Inflate the layout for this fragment
        return view;
//        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    //Aca podria usar la api para llenar los elementos de la lista a mostrar
    private void fillList() {
        lista.add(new RoutineCardData("primero", 10));
        lista.add(new RoutineCardData("segundo", 20));
        lista.add(new RoutineCardData("tercero", 30));
    }
}