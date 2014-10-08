package jp.tf_web.hikariboxsample01;

import jp.tf_web.fukuon.AudioConfig;
import jp.tf_web.fukuon.RecvAudioRunnable;
import jp.tf_web.fukuon.SendAudioRunnable;
import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	private TextView txtAddress;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button btnPlay = (Button) findViewById(R.id.button1);
        btnPlay.setOnClickListener(clickBtnPlay);
        
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        Thread thrd = new Thread(new Runnable(){
        	@Override
        	public void run() {
        		//IPアドレスを画面に表示する
        		String addr = AudioConfig.getIPAddress();
        		txtAddress.setText(addr);        
        	}
        });
        thrd.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
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
    
    //リモコンキー 取得
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
    	/*
    	if(txtSample == null) super.dispatchKeyEvent(e);
    	
    	if(e.getAction() == KeyEvent.ACTION_UP){
    		//キーが離された時
    		switch(e.getKeyCode()){
				case KeyEvent.KEYCODE_DPAD_UP:
					//上キー
					txtSample.setText("KEYCODE_DPAD_UP");
					return true;
				case KeyEvent.KEYCODE_DPAD_DOWN:
					//下キー
					txtSample.setText("KEYCODE_DPAD_DOWN");
					return true;
				case KeyEvent.KEYCODE_DPAD_LEFT:
					//左キー
					txtSample.setText("KEYCODE_DPAD_LEFT");
					return true;
				case KeyEvent.KEYCODE_DPAD_RIGHT:
					//右キー
					txtSample.setText("KEYCODE_DPAD_RIGHT");
					return true;
    			case KeyEvent.KEYCODE_DPAD_CENTER:
    				//十字中央キー
    				txtSample.setText("KEYCODE_DPAD_CENTER");
    				return true;
    			default:
    		}
    	}*/
    	return super.dispatchKeyEvent(e);
    }
        
	private OnClickListener clickBtnPlay = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			//オーディオ送信
			Thread thrd = new Thread(new RecvAudioRunnable());
			thrd.start();
		}
	};
}