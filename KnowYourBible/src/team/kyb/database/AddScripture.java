package team.kyb.database;



import java.util.ArrayList;
import java.util.List;

import team.kyb.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddScripture extends Activity {
	
	private long rowID;
	private EditText passage;
	private String userBook, userChapter, userVerse;	
//	private EditText book;
//	private EditText chapter;
//	private EditText verse;
	
	private Spinner spbook, spchapter, spverse;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.scripture_add);	
		
		passage = (EditText) findViewById(R.id.passagetext);
//		book = (EditText) findViewById(R.id.booktext);
//		chapter = (EditText) findViewById(R.id.chaptertext);
//		verse = (EditText) findViewById(R.id.versetext);
		
		spbook = (Spinner) findViewById(R.id.sp_scripturebook);
		spchapter = (Spinner) findViewById(R.id.sp_scripturechapter);
		spverse = (Spinner) findViewById(R.id.sp_scriptureverse);		
		
		
		// populating spinners
		addItemsOnSpinnerBook();
		addItemsOnSpinnerChapter();
		addItemsOnSpinnerVerse();		
		
		// set event listener for the Save Scripture Button
		Button saveScriptureButton = (Button) findViewById(R.id.saveScriptureButton);
		
		saveScriptureButton.setOnClickListener(saveScriptureButtonClicked);
		
	}; // end of public void onCreate(Bundle savedInstanceState) {
		
	//responds to event generated when user clicks the Save button
	OnClickListener saveScriptureButtonClicked = new OnClickListener(){
		
		@Override
		public void onClick(View v){
			

			
			if(passage.getText().length() == 0){
				AlertDialog.Builder vpassage = new AlertDialog.Builder(AddScripture.this);
				
				//set dialog title and message, and provide the button to dismiss
				vpassage.setTitle(R.string.vpassageTitle);
				vpassage.setMessage(R.string.vpassageMessage);
				vpassage.setPositiveButton(R.string.vButton, null);
				vpassage.show();
					
			} else {
				
				AsyncTask<Object, Object, Object> saveScriptureTask = 
						new AsyncTask<Object, Object, Object>() {

					@Override
					protected Object doInBackground(Object... params) { 
						saveScripture(); 
						return null;
					} 

					@Override
					protected void onPostExecute(Object result) { 
						finish(); 
					} 
				}; 

				// save the scripture to the database using a separate thread
				saveScriptureTask.execute((Object[]) null); 				
				
			}
			
		}
	};  // end of 	OnClickListener saveScriptureButtonClicked = new OnClickListener(){
	
	private void saveScripture(){
		
		//get DatabaseConnector to interact with the SQLite database
		DatabaseConnector databaseConnector = new DatabaseConnector(this);
		
		userBook = String.valueOf(spbook.getSelectedItem());
		userChapter = String.valueOf(spchapter.getSelectedItem());
		userVerse = String.valueOf(spverse.getSelectedItem());		
		
		if (getIntent().getExtras() == null){
			//insert the scripture info into the database
			databaseConnector.insertScripture(
					passage.getText().toString(), 
					userBook, 
					Integer.parseInt(userChapter), 
					Integer.parseInt(userVerse)
			);
		}
		
	} // end of private void saveScripture(){
	
	private void addItemsOnSpinnerBook()
	{
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.all_books_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spbook.setAdapter(adapter);

	}
	
	private void addItemsOnSpinnerChapter()
	{
		List<String> list = new ArrayList<String>();

		for (int i = 1; i<=150; i++)
		{
			list.add(String.valueOf(i));
		}
	
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spchapter.setAdapter(dataAdapter);
	}
	
	private void addItemsOnSpinnerVerse()
	{
		List<String> list = new ArrayList<String>();

		for (int i = 1; i<=176; i++)
		{
			list.add(String.valueOf(i));
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spverse.setAdapter(dataAdapter);
	}
		

} // end of public class AddEditScripture extends Activity {
