package team.kyb.database;


import team.kyb.R;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Scriptures extends ListActivity {

	public static final String ROW_ID = "row_id"; // Intent extra key
	private ListView scriptureListView; 
	private CursorAdapter scriptureAdapter; 	
	
	// called when the Activity is first started
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		
	
	//	setContentView(R.layout.display_scriptures);
		
		scriptureListView = getListView();
		scriptureListView.setOnItemClickListener(viewScriptureListener); 		
		
		// map each scripture passage to a ListView
		// in the ListView layout
	//	String passagedetail = "passage";
		String[] from = new String[] {"passage","book", "chapter", "verse"};
		int[] to = new int[] { 
				R.id.passageListView, 
				R.id.bookListView,
				R.id.chapterListView,
				R.id.verseListView
				};
		scriptureAdapter = new SimpleCursorAdapter(
				Scriptures.this, 
				R.layout.scripture_list, null, 
				from, to);
		// public SimpleCursorAdapter (Context context, 
		// int layout, Cursor c, 
		// String[] from, int[] to)

		setListAdapter(scriptureAdapter); 		
		
	}
	
	/*	ArrayList<String> mArrayList = new ArrayList<String>();
	mCursor.moveToFirst();
	while(!mCursor.isAfterLast()) {
	     mArrayList.add(mCursor.getString(mCursor.getColumnIndex(dbAdapter.KEY_NAME))); //add the item
	     mCursor.moveToNext();
	}	*/	
	
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
				new DatabaseConnector(Scriptures.this);

		// perform the database access
		@Override
		protected Cursor doInBackground(Object... params) {
			databaseConnector.open();

			return databaseConnector.getAllScriptures(); 
		} 

		// use the Cursor returned from the doInBackground method
		@Override
		protected void onPostExecute(Cursor result) {
		
			scriptureAdapter.changeCursor(result); 
			databaseConnector.close();
		} 
	} // end class GetScriptureTask	
	
	// create the Activity's menu from a menu resource XML file
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		
		inflater.inflate(R.menu.scriptures_menu, menu);
		
		return true;
	} 


	// handle choice from options menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()){
		case R.id.addscripture:		
		
			// create a new Intent to launch
			Intent addNew = 
					new Intent(this, AddScripture.class);
			startActivity(addNew); 
			return true;
		case R.id.listbooks:
			
			// create a new Intent to launch
			Intent listBooks = 
					new Intent(this, DisplayBooks.class);
			startActivity(listBooks); 
			return true;
		case R.id.listchapters:
			
			// create a new Intent to launch
			Intent listChapters = 
					new Intent(this, DisplayChapters.class);
			startActivity(listChapters); 

			return true;	
		case R.id.listverses:
			
			// create a new Intent to launch
			Intent listVerses = 
					new Intent(this, DisplayVerses.class);
			startActivity(listVerses); 

			return true;
		case R.id.randomscripture:
			
			// create a new Intent to launch
			Intent randompassage = 
					new Intent(this, RandomScripture.class);
			startActivity(randompassage); 

			return true;				
		default:		
			return super.onOptionsItemSelected(item); 
		}
	} 	
	
	
	// event listener that responds to the user touching a passage
	// in the ListView
	OnItemClickListener viewScriptureListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			Log.d("Scripture", "postion: " + position + ", id: " + id);
			// create an Intent to launch the ViewRating Activity
			Intent viewScripture = 
					new Intent(Scriptures.this, UpdateScripture.class);

			// pass the selected contact's row ID as an extra with the Intent
			viewScripture.putExtra(ROW_ID, id);
			startActivity(viewScripture);
		} 
	}; 	
	
	
	
} // end of public class Scriptures extends Activity {
