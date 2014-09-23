package team.kyb.database;

import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DatabaseConnector {

	   String mylocale = java.util.Locale.getDefault().getLanguage();
		
	   private static final String DATABASE_NAME = "dbScriptures";	 
	   private static final String DATABASE_NAME_ES = "dbScripturesES";
  
	   
	   private static final String DB_PATH = "/data/data/team.kyb/databases/";
		 
	   private SQLiteDatabase database; 
	   private DatabaseOpenHelper databaseOpenHelper; 	
	
	   public DatabaseConnector(Context context) {
		   
		 	if (mylocale.equals("en")){
			      databaseOpenHelper = 
					         new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);		 		
		 	} else if (mylocale.equals("es")){
			      databaseOpenHelper = 
					         new DatabaseOpenHelper(context, DATABASE_NAME_ES, null, 1);		 		
		 	} else {
		 		
			      databaseOpenHelper = 
					         new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);		 		
		 	}
		   

	   }	   
	   
	   public void open() throws SQLException {
	   
	//	   boolean dbExist = checkDataBase();
		   
			// create or open a database for reading/writing
		    database = databaseOpenHelper.getWritableDatabase();	
		   
		    
	   
/*		      if (dbExist){
		    	  // do nothing database already exists
 					Log.d("dbExist", "True");	
		      } else {
		    	  Log.d("dbExist", "False");	*/

		    	  // create or open a database for reading/writing
			   //   database = databaseOpenHelper.getWritableDatabase(); 
			      
	/*		      preInsertScripture(
			    		  "Throw all your anxiety on him, because he cares for you.",
			    		  "1 Peter", 5, 7);*/
  
		  //    }				   
	   } 	   


	public void preInsertScripture(String passage, String book, int chapter, 
		      int verse) {
		   
		      ContentValues newScripture = new ContentValues();
		      newScripture.put("passage", passage);
		      newScripture.put("book", book);
		      newScripture.put("chapter", chapter);
		      newScripture.put("verse", verse);
		      open();
		      database.insert("scriptures", null, newScripture);
		      close();
	}

	public boolean checkDataBase() {
    	  
      	SQLiteDatabase checkDB = null;
      	 
      	Log.d("localization = ", mylocale);

      	if (mylocale.equals("en")){
          	Log.d("localization = ", "english");
        	try{
         		String myPath = DB_PATH + DATABASE_NAME;	
    			Log.d("DB_Path", myPath);	
        		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
         	} 
        	catch(SQLiteException e) {
         		//database does't exist yet.
         	}	     	
 		
      	} else if (mylocale.equals("es")){
        	Log.d("localization = ", "spanish");
        	try{
         		String myPath = DB_PATH + DATABASE_NAME_ES;	
    			Log.d("DB_Path", myPath);	
        		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
         	} 
        	catch(SQLiteException e) {
         		//database does't exist yet.
         	}	     	
	
      	} else {
        	Log.d("localization = ", "default");
        	try{
         		String myPath = DB_PATH + DATABASE_NAME;	
    			Log.d("DB_Path", myPath);	
        		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
         	} 
        	catch(SQLiteException e) {
         		//database does't exist yet.
         	}	     	

      	} // end of      	if (locale == "en"){

    	if(checkDB != null){
     		checkDB.close();
     	}
 
    	return checkDB != null ? true : false;
	}

	public void close() {
		      if (database != null)
		         database.close();
	   } 	   
	   
	   // inserts a new scripture into the database
	   public void insertScripture(String passage, String book, int chapter, 
	      int verse) {
		   
	      ContentValues newScripture = new ContentValues();
	      newScripture.put("passage", passage);
	      newScripture.put("book", book);
	      newScripture.put("chapter", chapter);
	      newScripture.put("verse", verse);

	      open();
	      database.insert("scriptures", null, newScripture);
	      close();
	   }        	   
	   
	   // updates a scripture in the database
	   public void updateScripture(long id, String passage, String book, int chapter, 
	      int verse) {
		   
	      ContentValues editScripture = new ContentValues();
	      editScripture.put("passage", passage);
	      editScripture.put("book", book);
	      editScripture.put("chapter", chapter);
	      editScripture.put("verse", verse);


	      open(); 
	      database.update("scriptures", editScripture, "_id=" + id, null);
	      close(); 
	   } 	   
	   
	   public Cursor getAllScriptures() {
		      return database.query(
		    		  "scriptures", 
		    		  new String[] {"_id", "passage", "book", "chapter", "verse"}, 
		    		  null, null, null, null, 
		    		  "book");
		      // query(String table, 
		      // String[] columns, String selection, String[] selectionArgs, 
		      // String groupBy, String having, String orderBy)
	   }
	   
	    // use rawQuery to get all books from the scriptures table
	   public Cursor getAllBooks() {
		   
/*		      return database.query(
		    		  "scriptures", 
		    		  new String[] {"_id","book"}, 
		    		  null, null, null, null, 
		    		  "book");	*/
		      
		      return database.query(true, "scriptures", 
		    		  new String[] { "_id" ,"book"}, 
		    		  null, null, "book", null, null, null);		      
		   
		 //  return database.rawQuery("select distinct book from scriptures order by book", new String[] {"book"});
	   }	   
	   
	    // use rawQuery to get all chapters from the scriptures table
	   public Cursor getAllChapters() {
		      return database.query(true, "scriptures", 
		    		  new String[] { "_id" ,"chapter"}, 
		    		  null, null, "chapter", null, null, null);	
	   }
	   
	    // use rawQuery to get all chapters from the scriptures table
	   public Cursor getAllVerses() {
		      return database.query(true, "scriptures", 
		    		  new String[] { "_id" ,"verse"}, 
		    		  null, null, "verse", null, null, null);	
	   }
	   	   
	   

	   //SELECT * FROM table ORDER BY RANDOM() LIMIT 1
//"RANDOM() limit 1"
	   
	   public Cursor getRandomScripture() {
		   
		      return database.query(
		    		  "scriptures", 
		    		  new String[] {"_id", "passage", "book", "chapter", "verse"}, 
		    		  null, null, null, null, 
		    		  "RANDOM() limit 1");		   
		   
/*		      return database.query(
		 	         "scriptures", null, "_id=" + id, null, null, null, "RANDOM() limit 1");*/
	   }	   
	   
	   public Cursor getRandomScriptureForGame() {
		    Random rd = new Random();
		    Cursor cursor = getAllScriptures();
		    int totalScriptures = cursor.getCount();
            cursor.moveToPosition(rd.nextInt(totalScriptures - 1));
			return cursor;
	   }

	   public Cursor getVerseAtPositionI()
	   {
		   Cursor cursor = getAllVerses();
		   cursor.moveToPosition(2);
		   return cursor;
	   }
	   
	   // get a Cursor containing all information about the movie specified
	   // by the given id
	   public Cursor getOneScripture(long id) {
	      return database.query(
	         "scriptures", null, "_id=" + id, null, null, null, null);
	      
	      // public Cursor query (String table, String[] columns, 
	      // String selection, String[] selectionArgs, String groupBy, 
	      // String having, String orderBy, String limit)
	   }	   
	   
	   // delete the scripture specified by the given id
	   public void deleteScripture(long id) {
	      open(); 
	      database.delete("Scriptures", "_id=" + id, null);
	      close();
	   } 	   
	   
	   private class DatabaseOpenHelper extends SQLiteOpenHelper {


		      public DatabaseOpenHelper(Context context, String name,
		         CursorFactory factory, int version) {
		         super(context, name, factory, version);
		      }
 

		      // creates the scriptures table when the database is created
		      @Override   
		      public void onCreate(SQLiteDatabase db) {
		    	  
			         // query to create a new table named ratings
			         String createQuery = "CREATE TABLE scriptures" +
			            "(_id INTEGER PRIMARY KEY autoincrement, " +
			            "passage TEXT, " +
			            "book TEXT, " +
			            "chapter INTEGER, " +
			            "verse INTEGER);";
			                  
			         db.execSQL(createQuery);	
		      };	         
			         

		      @Override
		      public void onUpgrade(SQLiteDatabase db, int oldVersion, 
		          int newVersion) 
		      { /* nothing to do*/ }
		      
	   } 	// end of 	   private class DatabaseOpenHelper extends SQLiteOpenHelper {   
	   
	   
} // end of public class DatabaseConnector {
