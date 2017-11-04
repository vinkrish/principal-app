package com.aanglearning.principalapp.album;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;

import com.aanglearning.principalapp.dao.ImageStatusDao;
import com.aanglearning.principalapp.model.ImageStatus;
import com.aanglearning.principalapp.util.Constants;
import com.aanglearning.principalapp.util.SharedPreferenceUtil;
import com.aanglearning.principalapp.util.Util;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;

import java.io.File;
import java.util.ArrayList;

public class AlbumUploadService extends IntentService {
    private TransferUtility transferUtility;

    public AlbumUploadService() {
        super("AlbumUploadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            transferUtility = Util.getTransferUtility(getApplicationContext());
            ArrayList<ImageStatus> albumImages = ImageStatusDao.getAlbumImages(intent.getLongExtra("albumId", 0));
            for(ImageStatus imageStatus : albumImages) {
                File file = new File(Environment.getExternalStorageDirectory().getPath(),
                        "Shikshitha/Principal/" + SharedPreferenceUtil.getTeacher(this).getSchoolId() + "/" + imageStatus.getName());
                if(file.exists()) {
                    TransferObserver observer = transferUtility.upload(Constants.BUCKET_NAME, file.getName(),
                            file);
                    observer.setTransferListener(new UploadListener(imageStatus.getName()));
                }
            }
        }
    }

    private class UploadListener implements TransferListener {
        private String imageName;

        UploadListener(String name) {
            this.imageName = name;
        }

        // Simply updates the UI list when notified.
        @Override
        public void onError(int id, Exception e) {
            //updateList();
        }

        @Override
        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
            //updateList();
        }

        @Override
        public void onStateChanged(int id, TransferState newState) {
            if (newState.toString().equals("COMPLETED")) {
                ImageStatusDao.delete(imageName);
            } else if (newState.toString().equals("FAILED")) {

            }
        }
    }

}
