package jp.tf_web.fukuon.network.model;

import java.io.FileNotFoundException;

import org.json.JSONException;
import org.json.JSONObject;

import jp.tf_web.fukuon.CommonUtil;

public class User {
	private int id;
	private String name;
	private String photo;
	private String showName;
	private int showId;
	private String message;
	private long listenerCnt;
	
	public User(){		
	}
	
	public User(int id){
		this.setId(id);
	}
	
	public User(String name,String photo,String showName,int showId,String message,long listenerCnt){
		this.setName( name );
		this.setPhoto( photo );
		this.setShowName(showName);
		this.setShowId( showId );
		this.setMessage(message);
		this.setListenerCnt( listenerCnt );
	}
	
	public User(JSONObject src){
		try{
			this.setId( src.getInt("id") );
			this.setName( src.getString("name") );
			this.setPhoto( src.getString("photo")  );
			this.setShowName( src.getString("show_name") );
			this.setShowId( src.getInt("show_id") );
			this.setMessage( src.getString("message") );
			this.setListenerCnt( src.getLong("listener_cnt") );
		}
		catch (JSONException e) {
		
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	
	public String getPhotoBase64() {
		try {
			return CommonUtil.convertJpgStringFromBitmap(photo);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public int getShowId() {
		return showId;
	}

	public void setShowId(int showId) {
		this.showId = showId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getListenerCnt() {
		return listenerCnt;
	}

	public void setListenerCnt(long listenerCnt) {
		this.listenerCnt = listenerCnt;
	}
}
