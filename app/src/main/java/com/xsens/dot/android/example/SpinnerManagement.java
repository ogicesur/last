package com.xsens.dot.android.example;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SpinnerManagement extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.item_data);
    }

    public int switch_for_combinations_strings(int combination) {
        switch (combination) {
            case 1:
                return R.array.Participant_1;

            case 2:
                return R.array.Participant_2;

            case 3:
                return R.array.Participant_3;

            case 4:
                return R.array.Participant_4;

            case 5:
                return R.array.Participant_5;

            case 6:
                return R.array.Participant_6;

            case 7:
                return R.array.Participant_7;

            case 8:
                return R.array.Participant_8;

            case 9:
                return R.array.Participant_9;

            case 10:
                return R.array.Participant_10;

            case 11:
                return R.array.Participant_11;

            case 12:
                return R.array.Participant_12;

            case 13:
                return R.array.Participant_13;

            case 14:
                return R.array.Participant_14;

            case 15:
                return R.array.Participant_15;

            case 16:
                return R.array.Participant_16;

            case 17:
                return R.array.Participant_17;

            case 18:
                return R.array.Participant_18;
        }
        return R.array.Participant_1;
    }
}