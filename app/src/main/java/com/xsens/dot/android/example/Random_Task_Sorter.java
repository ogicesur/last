package com.xsens.dot.android.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Random_Task_Sorter {

    //int i = 0;
    Random randomizer = new Random();
    int[][][] three_dimensional_task_order = new int[18][9][10];

/*
    public void Random_Task_Array_Creater(int participant_id, int combination_id) {
        int random_task_int = randomizer.nextInt(8);
        int check = 0;
        while (i <= 9)
            if (i == 9) {
                i = 0;
                return_task_order_as_array(participant_id, combination_id);
            } else if (three_dimensional_task_order[participant_id][combination_id][i] != 0) {
                i++;
            } else {
                while (check <= i) {
                    if (three_dimensional_task_order[participant_id][combination_id][check] == random_task_int + 1) {
                        Random_Task_Array_Creater(participant_id, combination_id);
                    } else check++;
                }
                three_dimensional_task_order[participant_id][combination_id][i] = random_task_int + 1;
                check = 0;
                random_task_int = randomizer.nextInt(8);
            }
    }*/
/*
    public String[] return_task_order_as_array(int participant_id, int combination_id) {
        String[] task_order = new String[9];
        int order = 0;
        while (order <= 8) {
            task_order[order] = String.valueOf(three_dimensional_task_order[participant_id][combination_id][order]);
            order++;
        }
        return task_order;

    }
*/

    public String[] UniqueRandomNumbers(int participant_id, int combination_id) {
        if (three_dimensional_task_order[participant_id][combination_id][0] != 0) ;
        else {
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int i = 1; i < 10; i++) {
                list.add(i + 1);
            }
            Collections.shuffle(list);
            for (int i = 0; i < 9; i++) {
                three_dimensional_task_order[participant_id][combination_id][i] = list.get(i);
            }
        }
        String[] task_order = new String[10];
        int order = 0;
        while (order <= 9) {
            task_order[order] = String.valueOf(three_dimensional_task_order[participant_id][combination_id][order]);
            order++;
            ;
        }
        return task_order;
    }
}