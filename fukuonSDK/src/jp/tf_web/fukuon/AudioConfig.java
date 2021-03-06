package jp.tf_web.fukuon;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.apache.http.conn.util.InetAddressUtils;

import android.util.Log;

public class AudioConfig {
	private static final String TAG = "AudioConfig";
	public static final int AUDIO_PORT = 2048;
	public static final int SAMPLE_RATE = 8000;
	public static final int SAMPLE_INTERVAL = 20; // milliseconds
	public static final int SAMPLE_SIZE = 2; // bytes per sample
		
	//ホストのアドレスを取得する
	public static String getIPAddress(){
	    Enumeration<NetworkInterface> interfaces = null;
		try {
			interfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        
	    while(interfaces.hasMoreElements()){
	        NetworkInterface network = interfaces.nextElement();
	        Enumeration<InetAddress> addresses = network.getInetAddresses();
	            
	        while(addresses.hasMoreElements()){
	            String address = addresses.nextElement().getHostAddress();	                
	            //127.0.0.1と0.0.0.0以外のアドレスが見つかったらそれを返す
	            //IPv4の場合だけ返す
	            if(InetAddressUtils.isIPv4Address(address)){
	            	if(!"127.0.0.1".equals(address) && !"0.0.0.0".equals(address)){
	                	return address;
	            	}
	            }
	        }
	    }
	        
	    return "127.0.0.1";
	}
}
