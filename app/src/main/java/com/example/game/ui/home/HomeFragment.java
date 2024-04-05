package com.example.game.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.game.R;

public class HomeFragment extends Fragment {

    private ColorPicker colorPickerFragment;
    private DrawingFragment drawingFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        if (savedInstanceState == null) {
            colorPickerFragment = new ColorPicker();
            drawingFragment = new DrawingFragment();

            FragmentManager fragmentManager = getChildFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.color_picker_fragment, colorPickerFragment)
                    .add(R.id.drawingFragment, drawingFragment)
                    .commitNow(); // Use commitNow to ensure fragments are immediately created

            setupListeners();
        }

        return view;
    }

    private void setupListeners() {
        if (colorPickerFragment != null && drawingFragment != null) {
            colorPickerFragment.setColorChangeListener(drawingFragment::setColor);

            colorPickerFragment.setEraserListener(isErasing -> drawingFragment.setEraseMode(isErasing));

            colorPickerFragment.setClearListener(() -> drawingFragment.ClearScreen());
        }
    }
}
