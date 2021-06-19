package com.example.hci_onfitapp.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.hci_onfitapp.App;
import com.example.hci_onfitapp.api.data.CycleData;
import com.example.hci_onfitapp.api.data.CycleExerciseData;
import com.example.hci_onfitapp.api.data.ExerciseData;
import com.example.hci_onfitapp.api.data.RoutineData;
import com.example.hci_onfitapp.api.model.PagedList;
import com.example.hci_onfitapp.api.model.Status;
import com.example.hci_onfitapp.databinding.FragmentsExecuteRoutineBinding;
import com.example.hci_onfitapp.viewModel.ExerciseViewModel;
import com.example.hci_onfitapp.viewModel.RoutineViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ExecuteRoutineFragment extends Fragment {
    private RoutineViewModel viewModel;
    private ExerciseViewModel exerciseViewModel;

    private @NonNull
    FragmentsExecuteRoutineBinding binding;
    private RoutineData routineData;

    private static final int WARMUP_CYCLE = 0;
    private static final int MAIN_CYCLE = 1;
    private static final int COOLDOWN_CYCLE = 2;
    private int currentCycle = -1;
    private boolean finished = false;

    private PagedList<CycleExerciseData> currentCycleEx;
    List<CycleData> routineCyclesList = new ArrayList<>();
    private List<List<CycleExerciseData>> cycleExerciseList = new ArrayList<>();
    private TextView routineTitle;
    private TextView cycleTextView;
    private String cycleTitle;
    private TextView exTitle;
    private TextView exDetail;
    private Button detallado;
    private Button breve;
    private FloatingActionButton play;
    private CountDownTimer countDownTimer;
    private String[] title = {"ENTRADA EN CALOR", "PRINCIPAL", "ELONGACIÓN"};
    private App app;
    View view;

    private @NonNull
    int routineId;
    private int currentExerciseIndex;

    public ExecuteRoutineFragment() {

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentsExecuteRoutineBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        routineTitle = binding.routineNameTitleInExecutionExercise;
        cycleTextView = binding.routineCycleTitleInExecutionExercise;
        exTitle = binding.exerciseTitleInExecutionList;
        exDetail = binding.ExerciseDescription;
        breve = binding.executionBar.breve;
        detallado = binding.executionBar.exe;
        play = binding.playExe;

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState); //routine
        Activity activity = requireActivity();
        app = (App) activity.getApplication();
        if (getArguments() != null) {
            routineId = getArguments().getInt("routineId");
        }
        app.getRoutineRepository().getRoutine(routineId).observe(requireActivity(),r->{
            if(r.getStatus() == Status.SUCCESS){
                routineData = r.getData();
                binding.routineNameTitleInExecutionExercise.setText(routineData.getName());
                breve.setOnClickListener(v -> {
                    NavDirections action = ExecuteRoutineFragmentDirections.actionExecuteRoutineFragmentToExecuteShortFragment().setRoutineId(routineId);
                    Navigation.findNavController(v).navigate(action);
                });
                app.getRoutineRepository().getRoutineCycles(routineId).observe(requireActivity(),c->{
                    if(c.getStatus() == Status.SUCCESS){
                        routineCyclesList = c.getData().getContent();
                        System.out.println(c.getData());
                        cycleExerciseList = new ArrayList<>();
                        for(int i=0;i<routineCyclesList.size();i++){
                            CycleData cycle = routineCyclesList.get(i);
                            cycleExerciseList.add(new ArrayList<>());
                            app.getRoutineRepository().getCycleExercise(cycle.getId()).observe(requireActivity(), e->{
                                if(e.getStatus() == Status.SUCCESS){
                                    cycle.setCycleExercises(e.getData());
                                }
                            });
                        }
                    }
                });
            }

        });
        play.setOnClickListener(v -> playExe());

    }

    private void playExe() {
        finished = false;
        ExerciseData exerciseData = null;
        while (!finished){
            currentCycleEx = getCurrentCycleEx();
            System.out.println("holis");
            if(currentCycleEx == null)
                continue;
            for (CycleExerciseData ex : currentCycleEx.getContent()) {
                System.out.println("este for for ");
                exerciseData = ex.getExercise();
                binding.exerciseTitleInExecutionList.setText(exerciseData.getName());
                if(exerciseData.getDetail().equals("")){
                    binding.ExerciseDescription.setText(new String("No hay descripcion"));
                    binding.ExerciseDescription.setTextColor(Color.GRAY);
                } else
                    binding.ExerciseDescription.setText(exerciseData.getDetail());
                if (countDownTimer != null)
                    countDownTimer.cancel();
                if(ex.getDuration() != 0){
                    binding.progressBar.setMax(ex.getDuration());
                    countDownTimer = new CountDownTimer(ex.getDuration(), 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            binding.progressBar.setProgress((int) (millisUntilFinished / 1000));
                        }

                        @Override
                        public void onFinish() {

                        }
                    };
                } else  {
                    int repes = ex.getRepetitions()*5;
                    binding.progressBar.setMax(repes);
                    countDownTimer = new CountDownTimer(repes, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            System.out.println("ticker");
                            binding.progressBar.setProgress((int) (millisUntilFinished / 1000));
                        }

                        @Override
                        public void onFinish() {

                        }
                    };
                }
            }
        }
    }

    private PagedList<CycleExerciseData> getCurrentCycleEx() {
        if(currentCycle <= routineCyclesList.size()){
            System.out.println(currentCycle);
            if(currentCycle == COOLDOWN_CYCLE){
                finished = true;
                return null;
            }
            currentCycle++;
            cycleTitle = title[currentCycle];
            binding.routineCycleTitleInExecutionExercise.setText(cycleTitle);
            currentExerciseIndex = 0;
        }
        System.out.println(routineCyclesList.get(currentCycle));
        return routineCyclesList.get(currentCycle).getCycleExercises();
    }

}
