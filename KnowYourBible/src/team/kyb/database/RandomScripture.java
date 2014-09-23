package team.kyb.database;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import team.kyb.R;


public class RandomScripture extends ListActivity {

	public static final String ROW_ID = "row_id"; // Intent extra key
	private ListView scriptureListView; 
	private CursorAdapter scriptureAdapter; 	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		scriptureListView = getListView();
		
		String[] from = new String[] {"passage","book", "chapter", "verse"};
		int[] to = new int[] { 
				R.id.passageLView, 
				R.id.bookLView,
				R.id.chapterLView,
				R.id.verseLView
				};
		scriptureAdapter = new SimpleCursorAdapter(
				RandomScripture.this, 
				R.layout.scripture_random, null, 
				from, to);
		// public SimpleCursorAdapter (Context context, 
		// int layout, Cursor c, 
		// String[] from, int[] to)

		setListAdapter(scriptureAdapter); 	
		
	} // end of public void onCreate(Bundle savedInstanceState){
	@Override
	protected void onResume() {
		super.onResume(); 

		// create new GetScripturesTask and execute it 
		new GetScripturesTask().execute((Object[]) null);
	} 	
	
	@Override
	protected void onStop() {
		Cursor cursor = scriptureAdapter.getCursor(); 

		if (cursor != null) 
			cursor.deactivate(); // deactivate it

		scriptureAdapter.changeCursor(null); // adapter now has no Cursor
		super.onStop();
	} 	
	
	// performs database query outside GUI thread
	private class GetScripturesTask extends AsyncTask<Object, Object, Cursor> {
		DatabaseConnector databaseConnector = 
				new DatabaseConnector(RandomScripture.this);

		// perform the database access
		@Override
		protected Cursor doInBackground(Object... params) {
			databaseConnector.open();

			return databaseConnector.getRandomScripture(); 
		} 

		// use the Cursor returned from the doInBackground method
		@Override
		protected void onPostExecute(Cursor result) {
		
			scriptureAdapter.changeCursor(result); 
			databaseConnector.close();
		} 
	} // end class GetScriptureTask	
	
} // end of public class RandomScripture extends Activity {
