package com.xsens.dot.android.example.views;

import com.xsens.dot.android.sdk.events.XsensDotData;
import com.xsens.dot.android.sdk.interfaces.XsensDotRecordingCallback;
import com.xsens.dot.android.sdk.models.XsensDotRecordingFileInfo;
import com.xsens.dot.android.sdk.models.XsensDotRecordingState;

import java.util.ArrayList;

public class RecordingFragment implements XsensDotRecordingCallback {
    public void onXsensDotRecordingNotification(String address, boolean
            isEnabled) {
    }
    public void onXsensDotEraseDone(String address, boolean isSuccess) {

    }
    public void onXsensDotRequestFlashInfoDone(String address, int
            usedFlashSpace, int totalFlashSpace) {

    }
    public void onXsensDotRecordingAck(String address, int recordingId,
                                       boolean isSuccess, XsensDotRecordingState recordingState) {

    }
    public void onXsensDotGetRecordingTime(String address, int
            startUTCSeconds, int totalRecordingSeconds, int
                                                   remainingRecordingSeconds) {

    }
    public void onXsensDotRequestFileInfoDone(String address,
                                              ArrayList<XsensDotRecordingFileInfo> list, boolean isSuccess) {

    }
    public void onXsensDotDataExported(String address,
                                       XsensDotRecordingFileInfo fileInfo, XsensDotData exportedData) {

    }
    public void onXsensDotDataExported(String address,
                                       XsensDotRecordingFileInfo fileInfo) {

    }
    public void onXsensDotAllDataExported(String address) {

    }
    public void onXsensDotStopExportingData(String address) {

    }

}


