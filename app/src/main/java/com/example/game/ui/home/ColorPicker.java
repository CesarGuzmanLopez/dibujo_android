package com.example.game.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.game.R;

public class ColorPicker extends Fragment {

    private View colorPreview;
    private SeekBar seekBarRed, seekBarGreen, seekBarBlue;
    private ToggleButton toggleEraser;
    private Button clearButton;
    private ColorChangeListener colorChangeListener;
    private EraserListener eraserListener;
    private ClearListener clearListener;

    private int currentColor; // Para rastrear el color actual

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color_picker, container, false);

        colorPreview = view.findViewById(R.id.color_preview);
        seekBarRed = view.findViewById(R.id.seek_bar_red);
        seekBarGreen = view.findViewById(R.id.seek_bar_green);
        seekBarBlue = view.findViewById(R.id.seek_bar_blue);
        toggleEraser = view.findViewById(R.id.toggle_eraser);
        clearButton = view.findViewById(R.id.btn_clear);

        seekBarRed.setOnSeekBarChangeListener(seekBarChangeListener);
        seekBarGreen.setOnSeekBarChangeListener(seekBarChangeListener);
        seekBarBlue.setOnSeekBarChangeListener(seekBarChangeListener);

        toggleEraser.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (eraserListener != null) {
                eraserListener.onEraserToggle(isChecked);
            }
        });

        clearButton.setOnClickListener(v -> {
            if (clearListener != null) {
                clearListener.onClear();
            }
        });

        updateColorPreview();

        return view;
    }

    private void updateColorPreview() {
        int red = seekBarRed.getProgress();
        int green = seekBarGreen.getProgress();
        int blue = seekBarBlue.getProgress();

        currentColor = Color.rgb(red, green, blue);
        colorPreview.setBackgroundColor(currentColor);
    }

    public void setColorChangeListener(ColorChangeListener listener) {
        this.colorChangeListener = listener;
    }

    public void setEraserListener(EraserListener listener) {
        this.eraserListener = listener;
    }

    public void setClearListener(ClearListener listener) {
        this.clearListener = listener;
    }

    private final SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int newColor = Color.rgb(seekBarRed.getProgress(), seekBarGreen.getProgress(), seekBarBlue.getProgress());

            if (currentColor != newColor) {
                updateColorPreview();
                currentColor = newColor;

                if (colorChangeListener != null) {
                    colorChangeListener.onColorChanged(currentColor);
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    public interface ColorChangeListener {
        void onColorChanged(int color);
    }

    public interface EraserListener {
        void onEraserToggle(boolean isActive);
    }

    public interface ClearListener {
        void onClear();
    }
}
