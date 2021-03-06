package jp.tf_web.hikariboxsample01;

import java.net.InetAddress;
import java.net.UnknownHostException;

import jp.tf_web.fukuon.AudioConfig;
import jp.tf_web.fukuon.RecvAudioRunnable;
import jp.tf_web.fukuon.SendAudioRunnable;
import android.app.Activity;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

public class CameraActivity extends Activity implements SurfaceHolder.Callback {
	private static final String LOG_TAG = "MainActivity";
	
	private Camera myCamera;

	//音声受信スレッド
	private Thread audioThrd;
	private RecvAudioRunnable recvAudioRunnable;
	
	//音声送信スレッド
	private SendAudioRunnable sendAudioRunnable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);

		SurfaceView mySurfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		SurfaceHolder holder = mySurfaceView.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		setIpAddress( (TextView) findViewById(R.id.txtAddress2) );
		
		//送信先アドレスを設定する
		InetAddress addr = null;
		try {
			addr = InetAddress.getByName("192.168.1.1");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			Log.e(LOG_TAG, e.toString());
		}
		Log.e(LOG_TAG, "addr:"+addr);
		
		//オーディオ送信 Runnable		
		sendAudioRunnable = new SendAudioRunnable(addr);
		Log.e(LOG_TAG, "sendAudioRunnable" + sendAudioRunnable);
		
		//オーディオ送信
		Thread thrd = new Thread(sendAudioRunnable);
		thrd.start();
		
		//副音声再生
		recvAudioPlay();
	}

	//IPアドレスを画面に設定
	private void setIpAddress(final TextView txtView){
		Thread thrd = new Thread(new Runnable() {
			@Override
			public void run() {
				String addr = AudioConfig.getIPAddress();
				txtView.setText(addr);
			}
		});
		thrd.start();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		//ふくおん停止
		recvAudioStop();
		
		if(sendAudioRunnable != null){
			//録音停止
			sendAudioRunnable.stopRecording();
		}
		
		if(myCamera != null){
			myCamera.release();
			myCamera = null;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if(sendAudioRunnable != null){
			//録音停止
			sendAudioRunnable.stopRecording();
		}
		
		//ふくおん停止
		recvAudioStop();
		
		if(myCamera != null){
			myCamera.release();
			myCamera = null;
		}
	}
	
	//副音声 再生
	private void recvAudioPlay(){
		recvAudioStop();
		// オーディオ受信
		this.audioThrd = new Thread(new RecvAudioRunnable(this));
		this.audioThrd.start();
	}
	
	//副音声 停止
	private void recvAudioStop(){
		if(this.recvAudioRunnable != null){
			//副音声スレッド停止
			this.recvAudioRunnable.stopPlay();
			this.recvAudioRunnable = null;
			this.audioThrd = null;
		}
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		//Camera.Parameters parameters = myCamera.getParameters();
		//parameters.setPreviewSize(width, height);
		//myCamera.setParameters(parameters);
		myCamera.startPreview();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		myCamera = Camera.open();
		try {
			myCamera.setPreviewDisplay(holder);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		if(myCamera == null) return;
		myCamera.release();
		myCamera = null;
	}
}
