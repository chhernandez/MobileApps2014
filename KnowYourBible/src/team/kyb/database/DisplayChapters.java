package team.kyb.database;

import team.kyb.R;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class DisplayChapters extends ListActivity{
	
	private ListView chapterListView; 
	private CursorAdapter chapterAdapter; 	
	
	// called when the Activity is first started
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		

		
		chapterListView = getListView();

		String[] from = new String[] {"chapter"};
		int[] to = new int[] { 
				R.id.chapterView,
				};
		chapterAdapter = new SimpleCursorAdapter(
				DisplayChapters.this, 
				R.layout.chapter_list, null, 
				from, to);
		// public SimpleCursorAdapter (Context context, 
		// int layout, Cursor c, 
		// String[] from, int[] to)

		setListAdapter(chapterAdapter); 		
		
	}	// end of public void onCreate(Bundle savedInstanceState) {
	
	@Override
	protected void onResume() {
		super.onResume(); 

		// create new GetScripturesTask and execute it 
		new GetScripturesTask().execute((Object[]) null);
	} 	
	
	@Override
	protected void onStop() {
		Cursor cursor = chapterAdapter.getCursor(); 

		if (cursor != null) 
			cursor.deactivate(); // deactivate it

		chapterAdapter.changeCursor(null); // adapter now has no Cursor
		super.onStop();
	} 	
	
	// performs database query outside GUI thread
	private class GetScripturesTask extends AsyncTask<Object, Object, Cursor> {
		DatabaseConnector databaseConnector = 
				new DatabaseConnector(DisplayChapters.this);

		// perform the database access
		@Override
		protected Cursor doInBackground(Object... params) {
			databaseConnector.open();

			return databaseConnector.getAllChapters(); 
		} 

		// use the Cursor returned from the doInBackground method
		@Override
		protected void onPostExecute(Cursor result) {
		
			chapterAdapter.changeCursor(result); 
			databaseConnector.close();
		} 
	} // end class GetScripturesTask		

} // end of public class DisplayChapters extends ListActivity{
