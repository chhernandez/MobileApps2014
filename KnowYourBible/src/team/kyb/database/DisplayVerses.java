package team.kyb.database;

import team.kyb.R;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class DisplayVerses extends ListActivity {
	
	private ListView verseListView; 
	private CursorAdapter verseAdapter; 	
	
	// called when the Activity is first started
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		

		
		verseListView = getListView();

		String[] from = new String[] {"verse"};
		int[] to = new int[] { 
				R.id.verseView,
				};
		verseAdapter = new SimpleCursorAdapter(
				DisplayVerses.this, 
				R.layout.verse_list, null, 
				from, to);
		// public SimpleCursorAdapter (Context context, 
		// int layout, Cursor c, 
		// String[] from, int[] to)

		setListAdapter(verseAdapter); 		
		
	}	// end of public void onCreate(Bundle savedInstanceState) {
	
	@Override
	protected void onResume() {
		super.onResume(); 

		// create new GetScripturesTask and execute it 
		new GetScripturesTask().execute((Object[]) null);
	} 	
	
	@Override
	protected void onStop() {
		Cursor cursor = verseAdapter.getCursor(); 

		if (cursor != null) 
			cursor.deactivate(); // deactivate it

		verseAdapter.changeCursor(null); // adapter now has no Cursor
		super.onStop();
	} 	
	
	// performs database query outside GUI thread
	private class GetScripturesTask extends AsyncTask<Object, Object, Cursor> {
		DatabaseConnector databaseConnector = 
				new DatabaseConnector(DisplayVerses.this);

		// perform the database access
		@Override
		protected Cursor doInBackground(Object... params) {
			databaseConnector.open();

			return databaseConnector.getAllVerses(); 
		} 

		// use the Cursor returned from the doInBackground method
		@Override
		protected void onPostExecute(Cursor result) {
		
			verseAdapter.changeCursor(result); 
			databaseConnector.close();
		} 
	} // end class GetScripturesTask		
	

} // end of public class DisplayVerses extends ListActivity {
