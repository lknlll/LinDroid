package com.example.lindroidcode.audio;

import android.Manifest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;
import com.example.lindroidcode.R;
import com.example.lindroidcode.greendaoatoz.beans.AudioBean;
import com.example.lindroidcode.greendaoatoz.common.UserBeanDaoUtils;
import com.example.lindroidcode.permission.PermissionHelper;
import com.example.lindroidcode.utils.DateTimeUtils;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.media.MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED;

public class AudioRecordPlayActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private static final int MSG_PLAYER_UPDATE = 0x10;
    private TextView mCurrentTimeTv , mTotalTimeTv ;
    private ImageView mPlayerActionIv;
    private TextView mTvAudioTips;
    private Button mBtStartStopRecord;
    private static final int MAX_RECORD_TIME = 60 * 1000;
    private boolean isRecording = false;
    private Spinner mSpinnerAudioRecords;
    private Spinner mSpinnerAudioMarks;
    private PlayerHandler mHandler;
    private SeekBar mSeekBar;
    private String currentAudioFilePath;
    private List<Long> currentMarks = new ArrayList<>();
    private long startingPoint;
    private AudioBean selectedAudioBean;
    private UserBeanDaoUtils mUserBeanDaoUtils;

    private static class PlayerHandler extends Handler {
        private WeakReference<AudioRecordPlayActivity> mHost;

        public PlayerHandler(AudioRecordPlayActivity host) {
            mHost = new WeakReference<>(host) ;
        }

        @Override
        public void handleMessage(Message msg) {
            AudioRecordPlayActivity host = mHost.get() ;
            if (host != null) {
                switch (msg.what) {
                    case MSG_PLAYER_UPDATE:
                        host.playerUpdate() ;
                        break ;
                }
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser){
            if (progress >= 0 && progress <= seekBar.getMax()) {
                float percentage = ((float) progress) / seekBar.getMax();
                if (mCurrentTimeTv != null && mMediaPlayer != null && mMediaPlayer.getDuration() != -1){
                    mCurrentTimeTv.setText(DateTimeUtils.formattedTime((int) (mMediaPlayer.getDuration() * percentage)));
                }
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mMediaPlayer != null && mMediaPlayer.getDuration() != -1) {
            if (seekBar.getProgress() >= seekBar.getMax()) {
                mMediaPlayer.seekTo(mMediaPlayer.getDuration());
            }else {
                mMediaPlayer.seekTo(mMediaPlayer.getDuration() * seekBar.getProgress()/seekBar.getMax());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_record_play);
        mTvAudioTips = findViewById(R.id.tv_audio_tips);
        mBtStartStopRecord = findViewById(R.id.bt_start_stop_record);
        mPlayerActionIv = findViewById(R.id.iv_controller_play);
        mSpinnerAudioRecords = findViewById(R.id.spinner_audio_records);
        mSpinnerAudioMarks = findViewById(R.id.spinner_audio_marks);
        mSeekBar = findViewById(R.id.sb_controller);
        mCurrentTimeTv = findViewById(R.id.tv_controller_current);
        mTotalTimeTv = findViewById(R.id.tv_controller_duration);

        mBtStartStopRecord.setOnClickListener(this);
        mPlayerActionIv.setOnClickListener(this);
        findViewById(R.id.bt_mark_record).setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
        initPlayer();
        mHandler = new PlayerHandler(this);

        mUserBeanDaoUtils = new UserBeanDaoUtils(getApplicationContext());

        mPlayerActionIv.setImageResource(R.mipmap.ic_play);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshFiles();
    }

    @Override
    protected void onPause() {
        pausePlayer();
        super.onPause();
    }

    @Override
    protected void onStop() {
        stopRecord();
        stopPlayer();
        super.onStop();
    }

    /**
     * record feature param and api start
     */
    private MediaRecorder mMediaRecorder;
    public void startRecord() {

        currentAudioFilePath = "";
        currentMarks.clear();

        // 开始录音
        /* ①Initial：实例化MediaRecorder对象 */
        if (mMediaRecorder == null)
            mMediaRecorder = new MediaRecorder();
        try {
            /* ②setAudioSource/setVedioSource */
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            mMediaRecorder.setAudioChannels(2);
            /*
             * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
             * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
             */
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样 */
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            String appPath = PathUtils.getExternalAppFilesPath();
            String folderPath = appPath + File.separator +
                    "voice" +
                    File.separator +
                    "data";

            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
            //SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String timeStr = df.format(new Date());
            currentAudioFilePath = folderPath +
                    File.separator + "audio_" +
                    timeStr +
                    ".m4a";

            File folder = new File(folderPath);
            folder.listFiles();

            boolean dirFlag = FileUtils.createOrExistsDir(folderPath);  //不存在则判断是否创建成功

            /* ③准备 */
            mMediaRecorder.setOutputFile(currentAudioFilePath);
            mMediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
                @Override
                public void onError(MediaRecorder mr, int what, int extra) {
                    mTvAudioTips.append("录音遇到问题：" + what);
                }
            });
            mMediaRecorder.setMaxDuration(MAX_RECORD_TIME); //设置录制时长。单位毫秒。
//      mMediaRecorder.setMaxFileSize(); //设置录制的媒体大小。单位字节。
            mMediaRecorder.setOnInfoListener((mr, what, extra) -> {
                if (what == MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                    mTvAudioTips.append("录音已达" + MAX_RECORD_TIME/1000 + "秒");
                    stopRecord();
                    mBtStartStopRecord.setText("开始录音");
                }
            });
            mMediaRecorder.prepare();
            /* ④开始 */
            mMediaRecorder.start();
            isRecording = true;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopRecord() {
        if (mMediaRecorder != null) {
            try {
                mMediaRecorder.stop();
                mMediaRecorder.release();
                mMediaRecorder = null;
                isRecording = false;
            } catch (RuntimeException e) {
                mMediaRecorder.reset();
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
        }
        if (currentAudioFilePath != null && !"".equals(currentAudioFilePath)) {
            mUserBeanDaoUtils.insertAudioBean(new AudioBean(null,currentAudioFilePath,currentMarks));
            currentAudioFilePath = null;
        }
    }

    /**
     * record feature param and api end
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start_stop_record:
                if (PermissionHelper.hasPermissions(this, Manifest.permission.RECORD_AUDIO)) {
                    if (isRecording) {
                        stopRecord();
                        mBtStartStopRecord.setText("开始录音");
                        refreshFiles();
                    }else {
                        startingPoint = System.currentTimeMillis();
                        startRecord();
                        mBtStartStopRecord.setText("结束录音");
                    }
                }else {
                    finish();//Todo apply
                }
                break;
            case R.id.iv_controller_play:
                if (isPlaying()) {
                    mMediaPlayer.pause();
                    mPlayerActionIv.setImageResource(R.mipmap.ic_play);
                }else {
                    mHandler.sendEmptyMessageDelayed(MSG_PLAYER_UPDATE , 0);
                    if (bFirstPlay) {
                        preparePlayer();
                        startPlayer();
                        bFirstPlay = false;
                    } else {
                        resumePlayer();
                    }
                    mPlayerActionIv.setImageResource(R.mipmap.ic_player_pause);
                }
                break;
            case R.id.bt_mark_record:
                currentMarks.add(System.currentTimeMillis() - startingPoint);
                break;
        }
    }

    private MediaPlayer mMediaPlayer;
    private int mPosition;
    private boolean bFirstPlay = true;
    private String mPath;

    private void initPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(mp -> {
            mPlayerActionIv.setImageResource(R.mipmap.ic_play);
            bFirstPlay = true;
        });
    }

    private void pausePlayer(){
        // 先判断是否正在播放
        if (isPlaying()) {
            // 如果正在播放我们就先保存这个播放位置
            mPosition = mMediaPlayer.getCurrentPosition();
            mMediaPlayer.pause();
            mHandler.removeCallbacksAndMessages(null);
        }
    }
    private void resumePlayer(){
        if (mPosition > 0) {
            try {
                mMediaPlayer.start();
                mMediaPlayer.seekTo(mPosition);
                mPosition = 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            startPlayer();
        }
    }
    private void preparePlayer() {
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 设置需要播放的音频
            mMediaPlayer.setDataSource(mPath);
            mMediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mp) {

                }
            });
            mMediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void startPlayer() {
        try {
            //播放
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mTotalTimeTv.setText(DateTimeUtils.formattedTime(mMediaPlayer.getDuration()));
    }

    private void stopPlayer(){
        mMediaPlayer.stop();
        mMediaPlayer.release();
    }

    private boolean isPlaying(){
        return mMediaPlayer !=null && mMediaPlayer.isPlaying();
    }

    private void playerUpdate(){
        float seekTo = 0;
        if (mMediaPlayer != null && mMediaPlayer.getDuration() != -1) {
            seekTo = mMediaPlayer.getCurrentPosition() / (float)mMediaPlayer.getDuration();
        }
        mSeekBar.setProgress((int)(seekTo * 100));
        mCurrentTimeTv.setText(DateTimeUtils.formattedTime(mMediaPlayer.getCurrentPosition()));
        mHandler.sendEmptyMessageDelayed(MSG_PLAYER_UPDATE , 1000);
    }

    private void refreshFiles(){
        String audioFolderPath = PathUtils.getExternalAppFilesPath() + File.separator +
                "voice" +
                File.separator +
                "data";
        File folder = new File(audioFolderPath);
        String[] children = folder.list();
        if (children != null && children.length > 0) {
            mPath = audioFolderPath + File.separator + children[0];
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, children);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //绑定 Adapter到控件
            mSpinnerAudioRecords.setAdapter(adapter);
            mSpinnerAudioRecords.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int pos, long id) {
                    mPath = audioFolderPath + File.separator + (String)parent.getItemAtPosition(pos);
                    Log.e("mPath ",mPath);
                    List<AudioBean> audioBeanList = mUserBeanDaoUtils.queryAudioBeanByPath(mPath);
                    if (audioBeanList != null && audioBeanList.size() > 0) {
                        selectedAudioBean = audioBeanList.get(0);
                    }
                    /*Log.e("selectedAudioBean ",selectedAudioBean.toString());*/
                    refreshMarks();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Another interface callback
                }
            });
            mSpinnerAudioRecords.setEnabled(true);
        }else {
            mSpinnerAudioRecords.setEnabled(false);
        }

    }

    private void refreshMarks(){
        if (selectedAudioBean != null && selectedAudioBean.getMarks() !=null && selectedAudioBean.getMarks().size() > 0) {
            ArrayAdapter<Long> marksAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, selectedAudioBean.getMarks());
            marksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerAudioMarks.setAdapter(marksAdapter);
            mSpinnerAudioMarks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    long process = (Long)parent.getItemAtPosition(position);
                    if (mMediaPlayer != null) {
                        mMediaPlayer.start();
                        mMediaPlayer.seekTo((int)process);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            mSpinnerAudioMarks.setEnabled(true);
        }else {
            mSpinnerAudioMarks.setEnabled(false);
        }
    }
}