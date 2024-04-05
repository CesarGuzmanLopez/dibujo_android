package com.example.game.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.game.R;

public class DrawingFragment extends Fragment {

    private DrawingView drawingView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawing, container, false);
        drawingView = view.findViewById(R.id.drawing_view);
        return view;
    }
    public void  setColor(int color){
        drawingView.setColor(color);
    }
    public void setEraseMode(boolean val){
        drawingView.setEraseMode(val);
    }

    public void ClearScreen(){
        drawingView.clearScreen();
    }


    public DrawingView getDrawingView() {
        return drawingView;
    }
}
