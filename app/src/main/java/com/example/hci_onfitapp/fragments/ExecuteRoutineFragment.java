package com.example.hci_onfitapp.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import java.util.Locale;

public class ExecuteRoutineFragment extends Fragment {
    private RoutineViewModel viewModel;
    private ExerciseViewModel exerciseViewModel;

    private @NonNull
    FragmentsExecuteRoutineBinding binding;
    private RoutineData routineData;

    private static final int WARMUP_CYCLE = 0;
    private static final int MAIN_CYCLE = 1;
    private static final int COOLDOWN_CYCLE = 2;
    private CycleData currentCycle;
    private boolean finished = false;

    private PagedList<CycleExerciseData> currentCycleEx;
    List<CycleData> routineCyclesList = new ArrayList<>();
    private List<List<CycleExerciseData>> cycleExerciseList = new ArrayList<>();
    private TextView routineTitle;
    private TextView cycleTextView;
    private String cycleTitle;
    private TextView exTitle;
    private TextView exDetail;
    private TextView cycleRepes;
    private String repesText;
    private Button detallado;
    private Button breve;
    private FloatingActionButton play;
    private CountDownTimer countDownTimer;
    private String[] title = {"ENTRADA EN CALOR", "PRINCIPAL", "ELONGACIÓN"};
    private String[] titleEN = {"WARM-UP", "MAIN", "STRETCHING"};
    private App app;
    View view;

    private @NonNull
    int routineId;
    private int currentExerciseIndex;
    private boolean isFinished;
    private boolean isFinishedCycle;
    private int currentCycleIndex = -1;
    private int currentCycleRep = 0;

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
        cycleRepes = binding.repesCiclo;
        play = binding.playExe;
        if(Locale.getDefault().getLanguage().equals("en")){
            repesText = "Remaining cycle repetitions: ";
        } else
            repesText = "Repeticiones restantes del ciclo: ";

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
        play.setOnClickListener(v -> start());

    }

    private void start(){
        play.setVisibility(View.GONE);
        playRout(currentCycle = getCurrentCycle());
    }

    private void playRout(CycleData current){
        finished = false;
        isFinished = false;
        isFinishedCycle = false;
        if(currentCycleRep == 0) {
            currentCycleRep = current.getRepetitions();
        }
        playCycle(current);
    }
    private void playCycle(CycleData current) {
        currentCycleEx = current.getCycleExercises();
        hasExercises();
        playExercise(currentCycleEx, 0);

    }
    private void playExercise(PagedList<CycleExerciseData> content, int index) {
        if(index == content.getTotalCount())
            return;
        CycleExerciseData cycleExerciseData = content.getContent().get(index);
        ExerciseData exerciseData = cycleExerciseData.getExercise();

        cycleRepes.setText(repesText + String.valueOf(currentCycleRep));

        binding.exerciseTitleInExecutionList.setText(exerciseData.getName());
        if (exerciseData.getDetail().equals("")) {
            binding.ExerciseDescription.setText(new String("No hay descripcion"));
            binding.ExerciseDescription.setTextColor(Color.GRAY);
        } else
            binding.ExerciseDescription.setText(exerciseData.getDetail());
        if (countDownTimer != null)
            countDownTimer.cancel();
        if (cycleExerciseData.getDuration() != 0) {
            binding.progressBar.setMax(cycleExerciseData.getDuration());
            countDownTimer = new CountDownTimer(cycleExerciseData.getDuration() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    binding.progressBar.setProgress((int) (millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    playExercise(currentCycleEx, index + 1);
                    if(index + 1 == content.getTotalCount()) {
                        if(--currentCycleRep == 0)
                            currentCycle = getCurrentCycle();
                        if (finished) {
                            countDownTimer.cancel();
                            end();
                            return;
                        }
                        currentCycleEx = currentCycle.getCycleExercises();
                        playRout(currentCycle);
                    }
                }
            }.start();
        } else {
            int repes = cycleExerciseData.getRepetitions() * 5;
            binding.progressBar.setMax(repes);
            countDownTimer = new CountDownTimer(repes * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    binding.progressBar.setProgress((int) (millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    playExercise(currentCycleEx, index + 1);

                    if(index + 1 == content.getTotalCount()) {
                        if(--currentCycleRep == 0)
                            currentCycle = getCurrentCycle();
                        if (finished){
                            countDownTimer.cancel();
                            end();
                            return;
                        }
                        currentCycleEx = currentCycle.getCycleExercises();
                        playRout(currentCycle);
                    }
                }
            }.start();
        }


    }

    private void hasExercises(){
        if(currentCycleEx.getTotalCount() == 0){
            currentCycle = getCurrentCycle();
            if (finished){
                end();
                return;
            }
            currentCycleEx = currentCycle.getCycleExercises();
            playRout(currentCycle);
        }
    }

    private CycleData getCurrentCycle() {
        if(currentCycleIndex <= routineCyclesList.size()){
            if(currentCycleIndex == COOLDOWN_CYCLE){
                finished = true;
                return null;
            }
            currentCycleIndex++;

            if(Locale.getDefault().getLanguage().equals("en")){
                cycleTitle = titleEN[currentCycleIndex];
            } else
                cycleTitle = title[currentCycleIndex];
            binding.routineCycleTitleInExecutionExercise.setText(cycleTitle);
        }
        return routineCyclesList.get(currentCycleIndex);
    }

    private void end(){
        NavDirections action = ExecuteRoutineFragmentDirections.actionExecuteRoutineFragmentToEndRoutineFragment().setRoutineId(routineId);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(countDownTimer != null)
            countDownTimer.cancel();
    }
}
