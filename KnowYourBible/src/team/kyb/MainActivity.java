package team.kyb;


import team.kyb.R;
import team.kyb.database.DatabaseConnector;
import team.kyb.database.Scriptures;
import team.kyb.extragames.HangmanMain;
import team.kyb.extragames.MoreGames;
import team.kyb.iknow.IKnowGame;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	static final int DIALOG_HELP_ID = 0;
	static final int DIALOG_ABOUT_ID = 1;
	
//	private TextView tv_contactinfo;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//String mycontactinfo = "";
		//String mycontactinfo2 = "";
		
/*		// pre populate the scriptures to the database using a separate thread
		preScriptureTask.execute((Object[]) null); 	*/	
	
	//	tv_contactinfo = (TextView) findViewById(R.id.contactinfo);
	//	mycontactinfo = "<b>" + "<a href=\"http://www.7kplanet.com/kyb.html\">Know Your Bible Webpage</a>" + "</b>" + "<br/>" +
	//			"<br/>" + "<a href=\"mailto:carloshernandez@utexas.edu?Subject=KYB\">Send Mail</a>";	
			
		//tv_contactinfo.setText(Html.fromHtml(mycontactinfo));		
	//	tv_contactinfo.setText(getText(R.string.mycontactinfo));	
	}

	public void startIKnow(View v) {
		startIKnowGame();
	}	
	

	private void startIKnowGame() {
		// TODO Auto-generated method stub
		// create a new Intent to launch
		Intent iknowgame = 
				new Intent(this, IKnowGame.class);
		startActivity(iknowgame);	
		
	}

	public void startScriptures(View v) {
		startScripturesDatabase();
	}	
	

	private void startScripturesDatabase() {
		// TODO Auto-generated method stub
		// create a new Intent to launch
		Intent listScriptures = 
				new Intent(this, Scriptures.class);
		startActivity(listScriptures);	
		
	}	
	
	public void startGames(View v) {
		startMoreGames();
	}	
	

	private void startMoreGames() {
		// TODO Auto-generated method stub
		Intent moreGames = 
				new Intent(this, MoreGames.class);
		startActivity(moreGames);		
	}			
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	// handle choice from options menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()){
		case R.id.scriptures:			
		
			// create a new Intent to launch
			Intent listScriptures = 
					new Intent(this, Scriptures.class);
			startActivity(listScriptures); 
			return true;
		case R.id.settings:
			startActivityForResult(new Intent(this, Settings.class), 0);
			return true;
		case R.id.help:
			showDialog(DIALOG_HELP_ID);
			return true;
/*		case R.id.exit:
			
			return true;*/
		case R.id.about:
			showDialog(DIALOG_ABOUT_ID);
			return true;
		default:		
			return super.onOptionsItemSelected(item); 
			
		}
		
	} 	// End of 	public boolean onOptionsItemSelected(MenuItem item) {
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		
		if (requestCode == RESULT_CANCELED){
			
		}
	}

	@Override
	protected Dialog onCreateDialog(int id){
		Dialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		switch(id){
		case DIALOG_HELP_ID:
			dialog = createHelpDialog(builder);
			break;
		case DIALOG_ABOUT_ID:
			dialog = createAboutDialog(builder);
			break;
		
		}
		
		if(dialog == null)
			Log.d("Dialog Info:", "Uh oh! Dialog is null");
		else
			Log.d("Dialog Info:", "Dialog created: " + id + ", dialog: " + dialog);
		return dialog;    
		
	} // end of 	protected Dialog onCreateDialog(int id){

	private Dialog createAboutDialog(Builder builder) {
		Context context = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.about_dialog, null); 		
		builder.setView(layout);
		builder.setTitle(R.string.about_label);
		builder.setPositiveButton(R.string.vButton, null);	
		return builder.create();
	}

	private Dialog createHelpDialog(Builder builder) {
		Context context = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.help_dialog, null); 		
		builder.setView(layout);
		builder.setTitle(R.string.help_label);
		builder.setPositiveButton(R.string.vButton, null);	
		return builder.create();
	}
	
} // end of public class MainActivity extends Activity {
