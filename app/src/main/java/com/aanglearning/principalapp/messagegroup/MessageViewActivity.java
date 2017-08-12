package com.aanglearning.principalapp.messagegroup;

import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aanglearning.principalapp.R;
import com.aanglearning.principalapp.model.Message;
import com.aanglearning.principalapp.util.SharedPreferenceUtil;
import com.github.chrisbanes.photoview.PhotoView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageViewActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.shared_image)
    PhotoView sharedImage;
    @BindView(R.id.message)
    TextView messageTV;

    private Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_view);

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            message = (Message) extras.getSerializable("message");
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(message.getSenderName());
        DateTime dateTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S").parseDateTime(message.getCreatedAt());
        getSupportActionBar().setSubtitle(DateTimeFormat.forPattern("dd-MMM, HH:mm").print(dateTime));

        if(!message.getMessageBody().equals("")) {
            messageTV.setVisibility(View.VISIBLE);
            messageTV.setText(message.getMessageBody());
        }

        File file = new File(Environment.getExternalStorageDirectory().getPath(),
                "Shikshitha/Teacher/" + SharedPreferenceUtil.getTeacher(this).getSchoolId() + "/" + message.getImageUrl());
        if (file.exists()) {
            sharedImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        }
    }
}