package team.kyb.iknow;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import team.kyb.MainActivity;
import team.kyb.R;
import team.kyb.animationAPI.LoseEffect;
import team.kyb.animationAPI.WinEffect;
import team.kyb.database.DatabaseConnector;
import team.kyb.database.Scriptures;
import team.kyb.extragames.ScriptureForGameHelper;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SimpleCursorAdapter;
import android.widget.CursorAdapter;
import android.database.Cursor;

public class IKnowGame extends Activity implements TextToSpeech.OnInitListener {

//	private Spinner spinnerBook = (Spinner) findViewById(R.id.spinner_book);
//	private Spinner spinnerChapter = (Spinner) findViewById(R.id.spinner_chapter);
//	private Spinner spinnerVerse = (Spinner) findViewById(R.id.spinner_verse);
	
	DatabaseConnector database = new DatabaseConnector(this);
	
	private TextToSpeech tts;
	private ImageButton btnSpeak;
	
	private String userBook, userChapter, userVerse;
	private String correctBook, correctChapter, correctVerse;
	private Spinner spinnerBook, spinnerChapter, spinnerVerse;
	private TextView tv_game_status, tv_numCorrect, tv_numAttempts, tv_numHighScore;
	
	private String verseToDisplay;
	private TextView tv_displayVerse;
	private ArrayList<String> listVerses;
	private int numVerses;
	private Button buttonNextIKnow;
	
	private int numCorrect = 0;
	private int numAttempts = 0;
	private int numHighScore = 0;
	private int numGoalSetting = 4; //make this adjustable later in settings. chh 12/2/2013
	private int numWrongSetting = 3;
	private boolean mSoundOn = true;

		
	
	private SharedPreferences mPrefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.iknow_layout);
		
		tts = new TextToSpeech(this, this);
		
		// find r ids
		spinnerBook = (Spinner) findViewById(R.id.spinner_book);
		spinnerChapter = (Spinner) findViewById(R.id.spinner_chapter);
		spinnerVerse = (Spinner) findViewById(R.id.spinner_verse);
		
		buttonNextIKnow = (Button) findViewById(R.id.next_iknow);
		
		tv_numCorrect = (TextView) findViewById(R.id.numCorrect);
		tv_numAttempts = (TextView) findViewById(R.id.numAttempts);
		tv_numHighScore = (TextView) findViewById(R.id.numhighscore);
		
		
		btnSpeak = (ImageButton) findViewById(R.id.imageButton);

		// initiate, retreive scores
		mPrefs = getSharedPreferences("ttt_prefs", MODE_PRIVATE);
		numCorrect = mPrefs.getInt("numCorrect",0);
		numAttempts = mPrefs.getInt("numAttempts",0);
		numHighScore = mPrefs.getInt("numHighScore", 0);
		numGoalSetting = mPrefs.getInt("numGoalSetting", 3);
		numWrongSetting = mPrefs.getInt("numWrongSetting", 3);
		mSoundOn = mPrefs.getBoolean("sound", true);
		
		if (mSoundOn){
			btnSpeak.setVisibility(View.VISIBLE);
		} else {
			btnSpeak.setVisibility(View.GONE);
		}
		
		// set scores
		tv_numCorrect.setText(String.valueOf(numCorrect));
		tv_numAttempts.setText(String.valueOf(numAttempts));
		
		// populating spinners
		addItemsOnSpinnerBook();
		addItemsOnSpinnerChapter();
		addItemsOnSpinnerVerse();
		
		// GET RANDOM VERSE !!!
		database.open();
		ScriptureForGameHelper scripture = new ScriptureForGameHelper(database.getRandomScriptureForGame());
		
		verseToDisplay = scripture.getPassage();
		
		// Uncomment when have all spinners populated
		correctBook = String.valueOf(scripture.getBook());
		correctChapter = String.valueOf(scripture.getChapter());
		correctVerse = String.valueOf(scripture.getVerse());
		
		
//		numVerses = database.getAllVerses().getCount();
//		verseToDisplay = String.valueOf(numVerses);
		
//		Cursor cursorVerses = database.getAllVerses();
//		ScriptureForGameHelper verseI = new ScriptureForGameHelper(cursorVerses);
//		verseToDisplay = String.valueOf(verseI.getVerse());
		
		database.close();
		displayVerse(verseToDisplay);
		
        // button on click event
        btnSpeak.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View view) {
                //speakOut();
            	speakOut2(verseToDisplay);
            }
 
        });		
		
		// Delete when have uncomment above and have all spinners populated --------
		// Get correct book, chapter, verse to check user's answer
//		correctBook = "book 0";
//		correctChapter = "chapter 0";
//		correctVerse = "verse 0";
		// --------------------------------------------------------------------------

		buttonNextIKnow.setEnabled(false);
		checkUserAnswer(correctBook, correctChapter, correctVerse);
		nextListener();
		
	}  // end of 	protected void onCreate(Bundle savedInstanceState) {
	
    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }	
    
    @Override
    public void onInit(int status) {
    	
 	   String mylocale = java.util.Locale.getDefault().getLanguage();   	
 
        if (status == TextToSpeech.SUCCESS) {
 
        	int result = tts.setLanguage(Locale.US);
        	
		 	if (mylocale.equals("es")){   
		 		Locale locSpanish = new Locale("spa", "MEX");
		 		result = tts.setLanguage(locSpanish);
		 	} else if (mylocale.equals("en")){
		 		result = tts.setLanguage(Locale.US);		 		
		 	}

        	//int result = tts.setLanguage(Locale.getDefault());  

 
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                btnSpeak.setEnabled(true);
                
                if(mSoundOn){
                	speakOut();
                }
            }
 
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
 
    }   // end of  public void onInit(int status) {
    
    private void speakOut() {
    	 
		TextView textview_text = (TextView)  findViewById(R.id.textview_text);
	  	
    	
       String text = textview_text.getText().toString();
	
		//String text = "Hello there, I'm here and working";
		tts.setSpeechRate((float) 0.8);		
		Log.i("TTS", text);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }   
    
    private void speakOut2(String mystring) {
   	 
	//	TextView textview_text = (TextView)  findViewById(R.id.textview_text);
	  	
    	
       String text = mystring;
	
		//String text = "Hello there, I'm here and working";
		
		Log.i("TTS", text);
		tts.setSpeechRate((float) 0.8);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }      
    

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.iknow_menu, menu);
		return true;
	}

	// handle choice from options menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {

		case R.id.reset:
			numCorrect = 0;
			numAttempts = 0;
			numHighScore = 0;
			tv_numCorrect.setText(String.valueOf(numCorrect));
			tv_numAttempts.setText(String.valueOf(numAttempts));
			tv_numHighScore.setText(String.valueOf(numHighScore));
			
			return true;
		case R.id.exit:
			// create a new Intent to launch
			Intent exitIKnow = 
					new Intent(this, MainActivity.class);
			startActivity(exitIKnow); 

		}
		return false;
	} 	

	public void displayVerse( String verseToDisplay)
	{
		TextView textview_text = (TextView)  findViewById(R.id.textview_text);
		textview_text.setText(verseToDisplay);
	}
	
	private void addItemsOnSpinnerBook()
	{
//		Spinner spinnerBook = (Spinner) findViewById(R.id.spinner_book);
//		List<String> list = new ArrayList<String>();
//		list.add("book 0");
//		list.add("book 1");
//		list.add("book 2");
//		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
		
//		Resources res = getResources();
//		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,res.getStringArray(R.array.all_books_array));
//		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinnerBook.setAdapter(dataAdapter);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.all_books_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerBook.setAdapter(adapter);
		
//		spinnerBook.setAdapter(
//			      new NothingSelectedSpinnerAdapter(
//			            adapter,
//			            R.layout.contact_spinner_row_nothing_selected,
//			            // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
//			            this));
	}
	
	private void addItemsOnSpinnerChapter()
	{
//		Spinner spinnerChapter = (Spinner) findViewById(R.id.spinner_chapter);
		List<String> list = new ArrayList<String>();
//		list.add("chapter 0");
//		list.add("chapter 1");
//		list.add("chapter 2");
		for (int i = 1; i<=150; i++)
		{
			list.add(String.valueOf(i));
		}
	
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerChapter.setAdapter(dataAdapter);
	}
	
	private void addItemsOnSpinnerVerse()
	{
//		Spinner spinnerVerse = (Spinner) findViewById(R.id.spinner_verse);
		List<String> list = new ArrayList<String>();
//		list.add("verse 0");
//		list.add("verse 1");
//		list.add("verse 2");
		for (int i = 1; i<=176; i++)
		{
			list.add(String.valueOf(i));
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerVerse.setAdapter(dataAdapter);
	}
	
	private void checkUserAnswer(final String correctBook, final String correctChapter, final String correctVerse)
	{
//		final Spinner spinnerBook = (Spinner) findViewById(R.id.spinner_book);
//		final Spinner spinnerChapter = (Spinner) findViewById(R.id.spinner_chapter);
//		final Spinner spinnerVerse = (Spinner) findViewById(R.id.spinner_verse);
		
		final Button buttonSubmit = (Button) findViewById(R.id.button_submit);
		

		
		
		buttonSubmit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
//				Toast.makeText(MainActivity.this, 
//						"OnClickListener : " +
////								"\nSpinnerBook : "+ String.valueOf(spinnerBook.getSelectedItem()) + 
////								"\nSpinnerChapter : "+ String.valueOf(spinnerChapter.getSelectedItem()) + 
////								"\nSpinnerVerse : "+ String.valueOf(spinnerVerse.getSelectedItem()) 
//								"\nSpinnerBook : "+ String.valueOf(spinnerBook.getSelectedItem()) + 
//								"\nSpinnerChapter : "+ String.valueOf(spinnerChapter.getSelectedItem()) + 
//								"\nSpinnerVerse : "+ String.valueOf(spinnerVerse.getSelectedItem()) 
//								, Toast.LENGTH_SHORT).show();
				

				
				userBook = String.valueOf(spinnerBook.getSelectedItem());
				userChapter = String.valueOf(spinnerChapter.getSelectedItem());
				userVerse = String.valueOf(spinnerVerse.getSelectedItem());
				
				tv_game_status = (TextView) findViewById(R.id.game_status);
				tv_numCorrect = (TextView) findViewById(R.id.numCorrect);
				tv_numAttempts = (TextView) findViewById(R.id.numAttempts);
				tv_numHighScore = (TextView) findViewById(R.id.numhighscore);
				
				
				
				if ( correctBook.equals(userBook) && correctChapter.equals(userChapter) && correctVerse.equals(userVerse))
				{
//					Toast.makeText(IKnowGame.this, 
//							"Correct: " +
//									"\nSpinnerBook : "+ String.valueOf(spinnerBook.getSelectedItem()) +  
//									"\nSpinnerChapter : "+ String.valueOf(spinnerChapter.getSelectedItem()) + 
//									"\nSpinnerVerse : "+ String.valueOf(spinnerVerse.getSelectedItem()) 
//									, Toast.LENGTH_SHORT).show();
					tv_game_status.setText(R.string.correct_label2);
					
					if(mSoundOn){	
						speakOut2(getString(R.string.correct_label2));
					}
					
					numCorrect++;
					tv_numCorrect.setText(String.valueOf(numCorrect));
					tv_numAttempts.setText(String.valueOf(numAttempts));
					buttonNextIKnow.setEnabled(true);
					buttonSubmit.setEnabled(false);
					 
					Log.i("highscore = ", String.valueOf(numHighScore));	
					Log.i("numCorrect = ", String.valueOf(numCorrect));	

					if (numCorrect > numHighScore){
						
						numHighScore = numCorrect;
						tv_numHighScore.setText(String.valueOf(numHighScore));						
						
						numCorrect = 0;
						numAttempts = 0;
						tv_numCorrect.setText(String.valueOf(numCorrect));
						tv_numAttempts.setText(String.valueOf(numAttempts));						
						
						Log.i("highscore = ", String.valueOf(numHighScore));
						
						
						 winEffect(); 
						
					}
					
			//		if (numCorrect > numGoalSetting){
			//			winEffect();
			//		}
				}

				else
				{
					buttonSubmit.setEnabled(false);
					
					numAttempts++;					
					//getString(R.string.hello)
					//String answer = "Wrong! Answer is " + correctBook + " " + correctChapter + ":" + correctVerse ;
					String answer = getString(R.string.wronganswer) + " " + correctBook + " " + correctChapter + " : " + correctVerse ;
					
					if(mSoundOn){
						speakOut2(answer);
					}
					tv_game_status.setText(answer);
					tv_numAttempts.setText(String.valueOf(numAttempts));
					buttonNextIKnow.setEnabled(true);
					if (numAttempts > numWrongSetting){
						
						numCorrect = 0;
						numAttempts = 0;
						tv_numCorrect.setText(String.valueOf(numCorrect));
						tv_numAttempts.setText(String.valueOf(numAttempts));
						
					
						
						loseEffect();
						//reset score back to zeros.
					}
				
				}

			}
		});
		
		
	}	
	
	private void nextListener()
	{
//		Button buttonNextIKnow = (Button) findViewById(R.id.next_iknow);
		
		
		buttonNextIKnow.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// GET RANDOM VERSE !!!
				database.open();
				ScriptureForGameHelper scripture = new ScriptureForGameHelper(database.getRandomScriptureForGame());
				verseToDisplay = scripture.getPassage();

				correctBook = String.valueOf(scripture.getBook());
				correctChapter = String.valueOf(scripture.getChapter());
				correctVerse = String.valueOf(scripture.getVerse());
				database.close();
				
				Button buttonSubmit = (Button) findViewById(R.id.button_submit);
				if (!buttonSubmit.isEnabled())
				{
					buttonSubmit.setEnabled(true);
				}
				
				displayVerse(verseToDisplay);
				tv_game_status = (TextView) findViewById(R.id.game_status);
				if (tv_game_status.toString() != null)
				{
					tv_game_status.setText("");
				}
				
				checkUserAnswer(correctBook, correctChapter, correctVerse);
				buttonNextIKnow.setEnabled(false);
	
			}
		});
		
		
	}
	
	@Override
	protected void onStop() 
	{
		super.onStop();
		
		// save current scores
		SharedPreferences.Editor ed = mPrefs.edit();
		ed.putInt("numCorrect", numCorrect);
		ed.putInt("numAttempts", numAttempts);
		ed.putInt("numHighScore", numHighScore);
		ed.putInt("numGoalSetting", numGoalSetting);
		ed.putInt("numWrongSetting", numWrongSetting);
		ed.putBoolean("sound", mSoundOn);
		ed.commit();
	}
	
	public void loseEffect() {
		Intent intent = new Intent(this, LoseEffect.class);
		startActivity(intent);
	}

	public void winEffect() {
		Intent intent = new Intent(this, WinEffect.class);
		startActivity(intent);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RESULT_CANCELED) {
			// Apply potentially new settings
			mSoundOn = mPrefs.getBoolean("sound", true);
			Log.i("sound on = ", String.valueOf(mSoundOn));
			
		}
	}	
	
	
	
}
