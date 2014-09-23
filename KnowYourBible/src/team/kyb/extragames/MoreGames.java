package team.kyb.extragames;

import team.kyb.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MoreGames extends Activity {
	
	static final int DIALOG_HELP_ID = 0;
	static final int DIALOG_ABOUT_ID = 1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moregames);		
	}
	
	public void startHangman(View v) {
		startHangmanGames();
	}	
	

	private void startHangmanGames() {
		// TODO Auto-generated method stub
		Intent hangManGame = 
				new Intent(this, HangmanMain.class);
		startActivity(hangManGame);		
	}
	
	public void startFillin(View v) {
		startFillinGames();
	}	
	

	private void startFillinGames() {
		// TODO Auto-generated method stub
		Intent fillInGame = 
				new Intent(this, FillInTheBlankMain.class);
		startActivity(fillInGame);		
	}	
}
