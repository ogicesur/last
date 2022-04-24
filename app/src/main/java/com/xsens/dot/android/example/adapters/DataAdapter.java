//  Copyright (c) 2003-2020 Xsens Technologies B.V. or subsidiaries worldwide.
//  All rights reserved.
//
//  Redistribution and use in source and binary forms, with or without modification,
//  are permitted provided that the following conditions are met:
//
//  1.      Redistributions of source code must retain the above copyright notice,
//           this list of conditions, and the following disclaimer.
//
//  2.      Redistributions in binary form must reproduce the above copyright notice,
//           this list of conditions, and the following disclaimer in the documentation
//           and/or other materials provided with the distribution.
//
//  3.      Neither the names of the copyright holders nor the names of their contributors
//           may be used to endorse or promote products derived from this software without
//           specific prior written permission.
//
//  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
//  EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
//  MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
//  THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
//  SPECIAL, EXEMPLARY OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
//  OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
//  HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY OR
//  TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
//  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.THE LAWS OF THE NETHERLANDS
//  SHALL BE EXCLUSIVELY APPLICABLE AND ANY DISPUTES SHALL BE FINALLY SETTLED UNDER THE RULES
//  OF ARBITRATION OF THE INTERNATIONAL CHAMBER OF COMMERCE IN THE HAGUE BY ONE OR MORE
//  ARBITRATORS APPOINTED IN ACCORDANCE WITH SAID RULES.
//

package com.xsens.dot.android.example.adapters;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xsens.dot.android.example.NextPreviousButtons;
import com.xsens.dot.android.example.R;
import com.xsens.dot.android.example.Random_Task_Sorter;
import com.xsens.dot.android.example.SpinnerManagement;
import com.xsens.dot.android.example.views.RecordingFragment;
import com.xsens.dot.android.sdk.events.XsensDotData;
import com.xsens.dot.android.sdk.interfaces.XsensDotRecordingCallback;
import com.xsens.dot.android.sdk.models.XsensDotDevice;
import com.xsens.dot.android.sdk.recording.XsensDotRecordingManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * A view adapter for item view to present data.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    int[] item_on_the_combination_spinner = {0};
    int[] item_on_the_tasks_spinner = {1};

    NextPreviousButtons nextpreviousbuttons = new NextPreviousButtons();
    Random_Task_Sorter randomtasksorter = new Random_Task_Sorter();
    SpinnerManagement spinnerManagement  = new SpinnerManagement();

    private static final String TAG = DataAdapter.class.getSimpleName();

    // The keys of HashMap
    public static final String KEY_ADDRESS = "address", KEY_TAG = "tag", KEY_DATA = "data";

    // The application context
    private Context mContext;

    // Put all data from sensors into one list
    private ArrayList<HashMap<String, Object>> mDataList;

    private XsensDotRecordingManager mManager;



    /**
     * Default constructor.
     *
     * @param context  The application context
     * @param dataList A list contains tag and data
     */
    public DataAdapter(Context context, ArrayList<HashMap<String, Object>> dataList) {

        mContext = context;
        mDataList = dataList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    @SuppressLint("DefaultLocale")
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {

        String tag = (String) mDataList.get(position).get(KEY_TAG);
        XsensDotData xsData = (XsensDotData) mDataList.get(position).get(KEY_DATA);


        //mManager.enableDataRecordingNotification();


        holder.sensorName.setText(tag);

        double[] eulerAngles = xsData.getEuler();
        String eulerAnglesStr =
                String.format("%.6f", eulerAngles[0]) + ", " +
                String.format("%.6f", eulerAngles[1]) + ", " +
                String.format("%.6f", eulerAngles[2]);
        holder.orientationData.setText(eulerAnglesStr);

        float[] freeAcc = xsData.getFreeAcc();
        String freeAccStr =
                String.format("%.6f", freeAcc[0]) + ", " +
                String.format("%.6f", freeAcc[1]) + ", " +
                String.format("%.6f", freeAcc[2]);
        holder.freeAccData.setText(freeAccStr);

        participant_spinner(holder.participantSpinner, holder.environmentSpinner, holder.taskOrderSpinner, mContext);
        holder.nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                nextpreviousbuttons.next_button_method(item_on_the_tasks_spinner,item_on_the_combination_spinner, holder.environmentSpinner, holder.taskOrderSpinner );
            }
        });

        holder.previousButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                nextpreviousbuttons.previous_button_method(item_on_the_tasks_spinner,item_on_the_combination_spinner, holder.environmentSpinner, holder.taskOrderSpinner );
            }
        });

        holder.stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mManager.stopRecording();
            }
        });

        holder.startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mManager.startRecording();
            }
        });

    }

    public void participant_spinner(Spinner participantSpinner, Spinner environmentSpinner, Spinner taskOrderSpinner,  Context mContext){
        final int[] participant_id_on_array = {0};

        String[] participants_id = mContext.getResources().getStringArray(R.array.participants);
        ArrayAdapter itemsAdapter = new ArrayAdapter(mContext, R.layout.dropdown_item, participants_id);
        participantSpinner.setAdapter(itemsAdapter);

        participantSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                participant_id_on_array[0] = position + 1;
                int spinnerets = Integer.parseInt((String) participantSpinner.getSelectedItem());
                combination_spinner(participant_id_on_array[0], spinnerets, environmentSpinner, taskOrderSpinner, mContext);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void combination_spinner(int participant_id_on_array, int combination_number, Spinner environmentSpinner, Spinner taskOrderSpinner, Context mContext) {
        String[] combination_id = mContext.getResources().getStringArray(spinnerManagement.switch_for_combinations_strings(combination_number));
        ArrayAdapter itemsAdapter_combination = new ArrayAdapter(mContext, R.layout.dropdown_item, combination_id);
        environmentSpinner.setAdapter(itemsAdapter_combination);
        environmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                item_on_the_combination_spinner[0] = position;
                task_combination_spinner(participant_id_on_array, item_on_the_combination_spinner[0], environmentSpinner, taskOrderSpinner, mContext);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void task_combination_spinner(int participant_id, int combination_id, Spinner environmentSpinner, Spinner taskOrderSpinner, Context mContext){
        String[] tasks_id = randomtasksorter.UniqueRandomNumbers(participant_id, combination_id);
        ArrayAdapter itemsAdapter_tasks = new ArrayAdapter(mContext, R.layout.dropdown_item, tasks_id);
        taskOrderSpinner.setAdapter(itemsAdapter_tasks);

        taskOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                item_on_the_tasks_spinner[0] = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    @Override
    public int getItemCount() {

        return mDataList == null ? 0 : mDataList.size();
    }

    /**
     * A Customized class for ViewHolder of RecyclerView.
     */
    static class DataViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        TextView sensorName;
        TextView orientationData;
        TextView freeAccData;
        Spinner participantSpinner;
        Spinner environmentSpinner;
        Spinner taskOrderSpinner;
        Button previousButton;
        Button nextButton;
        Button startButton;
        Button stopButton;

        DataViewHolder(View v) {

            super(v);


            rootView = v;
            sensorName = v.findViewById(R.id.sensor_name);
            orientationData = v.findViewById(R.id.orientation_data);
            freeAccData = v.findViewById(R.id.free_acc_data);

            participantSpinner = v.findViewById(R.id.participantSpinner);
            environmentSpinner = v.findViewById(R.id.environmentSpinner);
            taskOrderSpinner = v.findViewById(R.id.taskOrderSpinner);

            previousButton = v.findViewById(R.id.previous_button);
            nextButton = v.findViewById(R.id.next_button);
            startButton = v.findViewById(R.id.start_button);
            stopButton = v.findViewById(R.id.stop_button);
/*
            //participant_spinner(participantSpinner, environmentSpinner, taskOrderSpinner, v);

            participantSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                   String index = adapterView.getItemAtPosition(position).toString();
                   String arrayName = String.format("Participant_%i", index);
                   int resourceId = v.getContext().getResources().getIdentifier(arrayName, "id", v.getContext().getPackageName());
                   List<String> stringArray = Arrays.asList(v.getContext().getResources().getStringArray(resourceId));
                   ArrayAdapter<String> adapter = new ArrayAdapter<>(v.getContext(), R.layout.dropdown_item, stringArray);
                   environmentSpinner.setAdapter(adapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            environmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    List<String> entries = createRandomArray();
                    ArrayAdapter adapter = new ArrayAdapter(v.getContext(), R.layout.dropdown_item, entries);
                    taskOrderSpinner.setAdapter(adapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            previousButton.setOnClickListener(view -> {
                taskOrderSpinner.setSelection(taskOrderSpinner.getSelectedItemPosition() + 1);
                System.out.println("Button clicked");
//                v.notifyAll();
            });
*/
        }
/*
        private List<String> createRandomArray() {
            List<String> entries = new ArrayList<>();
            while(entries.size() < 11){
                Integer r = (int) Math.floor(Math.random() * 10) + 1;
                if(entries.indexOf(r) == -1) {
                    entries.add(r.toString());
                }
            }
            return entries;


        }*/
    }
}
