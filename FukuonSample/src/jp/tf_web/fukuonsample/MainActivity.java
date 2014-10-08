package jp.tf_web.fukuonsample;

import java.net.InetAddress;
import java.net.UnknownHostException;

import jp.tf_web.fukuon.AudioConfig;
import jp.tf_web.fukuon.RecvAudioRunnable;
import jp.tf_web.fukuon.SendAudioRunnable;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private static final String LOG_TAG = "MainActivity";
	
	private SendAudioRunnable sendAudioRunnable;
	private RecvAudioRunnable recvAudioRunnable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//送信先アドレスを設定する
		InetAddress addr = null;
		try {
			addr = InetAddress.getByName("192.168.0.5");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			Log.e(LOG_TAG, e.toString());
		}
		Log.e(LOG_TAG, "addr:"+addr);
		
		//オーディオ送信 Runnable		
		sendAudioRunnable = new SendAudioRunnable(addr);
		Log.e(LOG_TAG, "sendAudioRunnable" + sendAudioRunnable);
		
		Button btnRec = (Button) findViewById(R.id.btnRec);
		btnRec.setOnClickListener(clickBtnRec);
		
		Button btnPlay = (Button) findViewById(R.id.btnPlay);
		btnPlay.setOnClickListener(clickBtnPlay);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
    @Override
    protected void onPause() {
        super.onPause();
        //録音停止
        sendAudioRunnable.stopRecording();
        //再生停止
        recvAudioRunnable.stopPlay();
    }
    
	private OnClickListener clickBtnRec = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			//オーディオ送信
			Thread thrd = new Thread(sendAudioRunnable);
			thrd.start();
		}
	};
	
	private OnClickListener clickBtnPlay = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			//オーディオ再生
			Thread thrd = new Thread(recvAudioRunnable);
			thrd.start();
		}
	};
	
}
