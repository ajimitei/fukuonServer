package jp.tf_web.hikariboxsample01;

import jp.tf_web.fukuon.CommonUtil;
import jp.tf_web.fukuon.network.model.User;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/** ユーザー表示部分のボタン
 * 
 * @author furukawanobuyuki
 *
 */
public class UserButton extends RelativeLayout {

	private View layout;
	private User user;
	private Button btnUserButton1;
	
	public UserButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.layout = LayoutInflater.from(context).inflate(R.layout.userbutton, this);
	}

	public UserButton(Context context, User user) {
        super(context);
        this.layout = LayoutInflater.from(context).inflate(R.layout.userbutton, this);
        setUser( user );
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		
		btnUserButton1 = (Button)layout.findViewById(R.id.btnUserButton1);
		btnUserButton1.setTag( Integer.valueOf( this.user.getId() ));
		
		//ユーザー情報を画面に反映する
		final TextView txtUserButtonName = (TextView)layout.findViewById(R.id.txtUserButtonName);
		txtUserButtonName.setText( this.user.getName() );

		final TextView txtUserButtonListenerCnt = (TextView)layout.findViewById(R.id.txtUserButtonListenerCnt);
		txtUserButtonListenerCnt.setText( String.valueOf( this.user.getListenerCnt() ) );
		
		final TextView txtUserButtonMessage = (TextView)layout.findViewById(R.id.txtUserButtonMessage);
		txtUserButtonMessage.setText( this.user.getMessage() );
		
		final TextView txtUserButtonShowName = (TextView)layout.findViewById(R.id.txtUserButtonShowName);
		txtUserButtonShowName.setText( this.user.getShowName() );
		
		final ImageView imgUserPhoto = (ImageView)layout.findViewById(R.id.imgUserPhoto);
		String b64img = this.user.getPhoto();
		imgUserPhoto.setImageBitmap(  CommonUtil.convertBitmapFromJpeg(b64img) );
		//imgUserPhoto.setScaleType(ImageView.ScaleType.FIT_CENTER);
		
	}

	public Button getBtnUserButton1() {
		return btnUserButton1;
	}

	public void setBtnUserButton1(Button btnUserButton1) {
		this.btnUserButton1 = btnUserButton1;
	}
}
