package jp.tf_web.fukuon;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioSource;
import android.util.Log;

/**　録音データをUDPで送信
 * 
 * @author furukawanobuyuki
 *
 */
public class SendAudioRunnable implements Runnable {
	private static final String LOG_TAG = "SendAudioRunnable";
	private DatagramSocket sock;

	private AudioRecord audioRec = null;
	private int bufSize;
	private boolean isRecording = false;
	int samplingRate;
	
	//送信先IPアドレス
	private InetAddress addr;
	
	public SendAudioRunnable(InetAddress addr) {
		try {
			//送信先IPアドレス
			this.addr = addr;
			
			// バッファサイズの計算
			bufSize = AudioRecord.getMinBufferSize(AudioConfig.SAMPLE_RATE,
					AudioFormat.CHANNEL_IN_MONO,
					AudioFormat.ENCODING_PCM_16BIT);// * AudioConfig.SAMPLE_SIZE;

			// AudioRecordの作成
			audioRec = new AudioRecord(AudioSource.DEFAULT,
					AudioConfig.SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,
					AudioFormat.ENCODING_PCM_16BIT, bufSize);
			
			this.sock = new DatagramSocket();
		} catch (SocketException se) {
			Log.e(LOG_TAG, "SocketException: " + se.toString());
		} catch (IOException ie) {
			Log.e(LOG_TAG, "IOException" + ie.toString());
		}
	}

	@Override
	public void run() {
		Log.e(LOG_TAG, "run");
		audioRec.startRecording();
		isRecording = true;
		byte[] buf = new byte[bufSize];
		while (isRecording) {
			// 録音データを取得
			int bytes_read = audioRec.read(buf, 0, buf.length);
			Log.e(LOG_TAG, "buf length:"+buf.length);
			try {
				//UDPで送信
				DatagramPacket pack = new DatagramPacket(buf, bytes_read, addr, AudioConfig.AUDIO_PORT);
                sock.send(pack);                
				Log.d(LOG_TAG, "send pack: " + pack.getLength());
				
				// 録音データを貯める為にスリープ
				Thread.sleep(AudioConfig.SAMPLE_INTERVAL,0);
			} catch (IOException e) {
				e.printStackTrace();
				Log.d(LOG_TAG, e.toString());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d(LOG_TAG, e.toString());
			}
		}
		Log.d(LOG_TAG,"stop");
		audioRec.stop();
	}

	public void stopRecording() {
		isRecording = false;
	}
}
