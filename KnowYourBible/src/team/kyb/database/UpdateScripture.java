package team.kyb.database;


import team.kyb.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class UpdateScripture extends Activity {

	private long rowID;
	private TextView passage;
	private TextView book;
	private TextView chapter;
	private TextView verse;
	
	private TextView allpassage;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.scripture_update);	
		passage = (TextView) findViewById(R.id.passageTextView);
		book = (TextView) findViewById(R.id.bookTextView);		
		chapter = (TextView) findViewById(R.id.chapterTextView);
		verse = (TextView) findViewById(R.id.verseTextView);	
		
		allpassage = (TextView) findViewById(R.id.allpassageTextView);
		
		//get the selected scripture's row id
		Bundle extras = getIntent().getExtras();
		rowID = extras.getLong(Scriptures.ROW_ID);
		
	} // end of public void onCreate(Bundle savedInstanceState){
	
	//called when the activity is first created
	@Override
	protected void onResume(){
		super.onResume();
		
		//create new LoadScriptureTask and execute it
		new LoadScriptureTask().execute(rowID);
	}
	
	// performs database query outside GUI thread
	private class LoadScriptureTask extends AsyncTask<Long, Object, Cursor> {
		
		DatabaseConnector databaseConnector = new DatabaseConnector(UpdateScripture.this);
		
		// perform the database access
		@Override
		protected Cursor doInBackground(Long...params){
			databaseConnector.open();
			
			//get a cursor containing all data on given entry
			return databaseConnector.getOneScripture(params[0]);
			
		}
		
		// use the Cursor returned from the doInBackground method
		@Override
		protected void onPostExecute(Cursor result) {
			super.onPostExecute(result);

			result.moveToFirst();
			
			// get the column index for each data item
			int passageIndex = result.getColumnIndex("passage");
			int bookIndex = result.getColumnIndex("book");
			int chapterIndex = result.getColumnIndex("chapter");
			int verseIndex = result.getColumnIndex("verse");
			
			String p = result.getString(passageIndex);
			String b = result.getString(bookIndex);
			String c = result.getString(chapterIndex);
			String v = result.getString(verseIndex);

			
			String fulltext = "<b>" + p + "</b>" + "<br>" +
					"<br>" + b + " " + c
					+ " : " + v;			
			
			//fill TextViews with the retrieved data
			
			allpassage.setText(Html.fromHtml(fulltext));
			
			
			passage.setText(result.getString(passageIndex));
			
	
			book.setText(result.getString(bookIndex));
			chapter.setText(result.getString(chapterIndex));
			verse.setText(result.getString(verseIndex));
			
			result.close();
			databaseConnector.close();
			
		}
		
	} // end of 	private class LoadScriptureTask extends AsyncTask<Long, Object, Cursor> {
	
	//create the menu for the update scripture
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		
		inflater.inflate(R.menu.update_menu, menu);
		/*inflater.inflate(R.menu.scriptures_menu, menu);	*/
		
		return true;
	}

	
	//handle choice from options menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
		case R.id.editscripture:
			// create an Intent to launch the AddEditScripture Activity
			Intent EditScripture = new Intent(this, EditScripture.class);
			
			EditScripture.putExtra(Scriptures.ROW_ID, rowID);
			EditScripture.putExtra("passage", passage.getText());
			EditScripture.putExtra("book", book.getText());
			EditScripture.putExtra("chapter", chapter.getText());
			EditScripture.putExtra("verse", verse.getText());
			
			Log.d("edit scripture", "row id:" + rowID);	
			Log.d("edit scripture", "passage:" + passage.getText());	
			Log.d("edit scripture", "book:" + book.getText());
			Log.d("edit scripture", "chapter:" + chapter.getText());
			Log.d("edit scripture", "verse:" + verse.getText());  
		
			
			startActivity(EditScripture);
			return true;
		case R.id.deletescripture:
			
			Log.d("delete scripture", "delete");  			
			deleteScripture();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void deleteScripture() {
		// TODO Auto-generated method stub
		
		Log.d("inside delete scripture", "delete");  		
		AlertDialog.Builder builder = new AlertDialog.Builder(UpdateScripture.this);
		
		builder.setTitle(R.string.confirmDelete);
		builder.setMessage(R.string.confirmDeleteMessage);
		
		// provide an OK button
		builder.setPositiveButton(R.string.button_delete, 
				new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int button) {

						final DatabaseConnector databaseConnector = new DatabaseConnector(UpdateScripture.this);
						
						// create an AsyncTask that deletes the scripture in another 
						// thread, then calls finish after the deletion
						AsyncTask<Long, Object, Object> deleteTask = new AsyncTask<Long, Object, Object>() {	
							
							@Override
							protected Object doInBackground(Long... params){
								databaseConnector.deleteScripture(params[0]);
								return null;
							}
							
							@Override
							protected void onPostExecute(Object result){
								finish();
							}
						}; // end of AsyncTask<Long, Object, Object> deleteTask = new AsyncTask<Long, Object, Object>() {	
						
						deleteTask.execute(new Long[] { rowID });
						
					} // end of public void onClick(DialogInterface dialog, int button) {
				} // end of new DialogInterface.OnClickListener() {
		); // end of builder.setPositiveButton(R.string.button_delete, 
		
		builder.setNegativeButton(R.string.button_cancel, null);
		builder.show();
	} // end of private void deleteScripture() {
	
}  // end of public class UpdateScripture extends Activity {
