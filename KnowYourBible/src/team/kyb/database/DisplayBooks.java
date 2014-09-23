package team.kyb.database;

import team.kyb.R;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class DisplayBooks extends ListActivity {

	private ListView bookListView; 
	private CursorAdapter bookAdapter; 	
	
	// called when the Activity is first started
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		
	
	//	setContentView(R.layout.display_scriptures);
		
		bookListView = getListView();
	//	scriptureListView.setOnItemClickListener(viewScriptureListener); 		
		
		// map each scripture passage to a ListView
		// in the ListView layout
	//	String passagedetail = "passage";
		String[] from = new String[] {"book"};
		int[] to = new int[] { 
				R.id.bookView,
				};
		bookAdapter = new SimpleCursorAdapter(
				DisplayBooks.this, 
				R.layout.book_list, null, 
				from, to);
		// public SimpleCursorAdapter (Context context, 
		// int layout, Cursor c, 
		// String[] from, int[] to)

		setListAdapter(bookAdapter); 	
		
		
	}	// end of public void onCreate(Bundle savedInstanceState) {
	
	

	
	
	
	@Override
	protected void onResume() {
		super.onResume(); 

		// create new GetScripturesTask and execute it 
		new GetScripturesTask().execute((Object[]) null);
	} 	
	
	@Override
	protected void onStop() {
		Cursor cursor = bookAdapter.getCursor(); 

		if (cursor != null) 
			cursor.deactivate(); // deactivate it

		bookAdapter.changeCursor(null); // adapter now has no Cursor
		super.onStop();
	} 	
	
	// performs database query outside GUI thread
	private class GetScripturesTask extends AsyncTask<Object, Object, Cursor> {
		DatabaseConnector databaseConnector = 
				new DatabaseConnector(DisplayBooks.this);

		// perform the database access
		@Override
		protected Cursor doInBackground(Object... params) {
			databaseConnector.open();

			return databaseConnector.getAllBooks(); 
		} 

		// use the Cursor returned from the doInBackground method
		@Override
		protected void onPostExecute(Cursor result) {
		
			bookAdapter.changeCursor(result); 
			databaseConnector.close();
		} 
	} // end class GetScripturesTask		
} // end of public class DisplayBooks extends ListActivity {
