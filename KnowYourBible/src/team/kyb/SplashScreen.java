package team.kyb;

import team.kyb.database.DatabaseConnector;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashScreen extends Activity {

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 2500;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        

 
        new Handler().postDelayed(new Runnable() {
 
            
         //    * Showing splash screen with a timer. This will be useful when you
         //    * want to show case your app logo / company
             
 
            @Override
            public void run() {
                // This method will be executed once the timer is over
        		// pre populate the scriptures to the database using a separate thread
        		preScriptureTask.execute((Object[]) null); 	
            }
        }, SPLASH_TIME_OUT);
    }
    
	AsyncTask<Object, Object, Object> preScriptureTask = 
			new AsyncTask<Object, Object, Object>() {

		@Override
		protected Object doInBackground(Object... params) { 

			preInsertScripture(); 
			return null;
		} 

		@Override
		protected void onPostExecute(Object result) { 
            // Start your app main activity
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);

            // close this activity
            finish();
			//databaseConnector.close();
		} 
	}; 

	private void preInsertScripture(){
		
		//get DatabaseConnector to interact with the SQLite database
		DatabaseConnector databaseConnector = new DatabaseConnector(this);
		
		boolean dbExist = databaseConnector.checkDataBase();	
		
		if (dbExist){
	    	  // do nothing database already exists
				Log.d("dbExist", "True");	
	    } else {
	    	  Log.d("dbExist", "False");
	    	 
  				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a1),
						getResources().getString(R.string.passage_b1), 
						Integer.parseInt(getResources().getString(R.string.passage_c1)), 
						Integer.parseInt(getResources().getString(R.string.passage_d1))
				);
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a2),
						getResources().getString(R.string.passage_b2), 
						Integer.parseInt(getResources().getString(R.string.passage_c2)), 
						Integer.parseInt(getResources().getString(R.string.passage_d2))
				);
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a3),
						getResources().getString(R.string.passage_b3), 
						Integer.parseInt(getResources().getString(R.string.passage_c3)), 
						Integer.parseInt(getResources().getString(R.string.passage_d3))
				);
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a4),
						getResources().getString(R.string.passage_b4), 
						Integer.parseInt(getResources().getString(R.string.passage_c4)), 
						Integer.parseInt(getResources().getString(R.string.passage_d4))
				);								
  				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a5),
						getResources().getString(R.string.passage_b5), 
						Integer.parseInt(getResources().getString(R.string.passage_c5)), 
						Integer.parseInt(getResources().getString(R.string.passage_d5))
				);
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a6),
						getResources().getString(R.string.passage_b6), 
						Integer.parseInt(getResources().getString(R.string.passage_c6)), 
						Integer.parseInt(getResources().getString(R.string.passage_d6))
				);
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a7),
						getResources().getString(R.string.passage_b7), 
						Integer.parseInt(getResources().getString(R.string.passage_c7)), 
						Integer.parseInt(getResources().getString(R.string.passage_d7))
				);
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a8),
						getResources().getString(R.string.passage_b8), 
						Integer.parseInt(getResources().getString(R.string.passage_c8)), 
						Integer.parseInt(getResources().getString(R.string.passage_d8))
				);					
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a9),
						getResources().getString(R.string.passage_b9), 
						Integer.parseInt(getResources().getString(R.string.passage_c9)), 
						Integer.parseInt(getResources().getString(R.string.passage_d9))
				);								
  				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a10),
						getResources().getString(R.string.passage_b10), 
						Integer.parseInt(getResources().getString(R.string.passage_c10)), 
						Integer.parseInt(getResources().getString(R.string.passage_d10))
				);
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a11),
						getResources().getString(R.string.passage_b11), 
						Integer.parseInt(getResources().getString(R.string.passage_c11)), 
						Integer.parseInt(getResources().getString(R.string.passage_d11))
				);
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a12),
						getResources().getString(R.string.passage_b12), 
						Integer.parseInt(getResources().getString(R.string.passage_c12)), 
						Integer.parseInt(getResources().getString(R.string.passage_d12))
				);
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a13),
						getResources().getString(R.string.passage_b13), 
						Integer.parseInt(getResources().getString(R.string.passage_c13)), 
						Integer.parseInt(getResources().getString(R.string.passage_d13))
				);	
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a14),
						getResources().getString(R.string.passage_b14), 
						Integer.parseInt(getResources().getString(R.string.passage_c14)), 
						Integer.parseInt(getResources().getString(R.string.passage_d14))
				);								
  				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a15),
						getResources().getString(R.string.passage_b15), 
						Integer.parseInt(getResources().getString(R.string.passage_c15)), 
						Integer.parseInt(getResources().getString(R.string.passage_d15))
				);
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a16),
						getResources().getString(R.string.passage_b16), 
						Integer.parseInt(getResources().getString(R.string.passage_c16)), 
						Integer.parseInt(getResources().getString(R.string.passage_d16))
				);
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a17),
						getResources().getString(R.string.passage_b17), 
						Integer.parseInt(getResources().getString(R.string.passage_c17)), 
						Integer.parseInt(getResources().getString(R.string.passage_d17))
				);
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a18),
						getResources().getString(R.string.passage_b18), 
						Integer.parseInt(getResources().getString(R.string.passage_c18)), 
						Integer.parseInt(getResources().getString(R.string.passage_d18))
				);					
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a19),
						getResources().getString(R.string.passage_b19), 
						Integer.parseInt(getResources().getString(R.string.passage_c19)), 
						Integer.parseInt(getResources().getString(R.string.passage_d19))
				);								
  				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a20),
						getResources().getString(R.string.passage_b20), 
						Integer.parseInt(getResources().getString(R.string.passage_c20)), 
						Integer.parseInt(getResources().getString(R.string.passage_d20))
				);		
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a21),
						getResources().getString(R.string.passage_b21), 
						Integer.parseInt(getResources().getString(R.string.passage_c21)), 
						Integer.parseInt(getResources().getString(R.string.passage_d21))
				);								
  				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a22),
						getResources().getString(R.string.passage_b22), 
						Integer.parseInt(getResources().getString(R.string.passage_c22)), 
						Integer.parseInt(getResources().getString(R.string.passage_d22))
				);		
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a23),
						getResources().getString(R.string.passage_b23), 
						Integer.parseInt(getResources().getString(R.string.passage_c23)), 
						Integer.parseInt(getResources().getString(R.string.passage_d23))
				);	
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a24),
						getResources().getString(R.string.passage_b24), 
						Integer.parseInt(getResources().getString(R.string.passage_c24)), 
						Integer.parseInt(getResources().getString(R.string.passage_d24))
				);								
  				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a25),
						getResources().getString(R.string.passage_b25), 
						Integer.parseInt(getResources().getString(R.string.passage_c25)), 
						Integer.parseInt(getResources().getString(R.string.passage_d25))
				);
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a26),
						getResources().getString(R.string.passage_b26), 
						Integer.parseInt(getResources().getString(R.string.passage_c26)), 
						Integer.parseInt(getResources().getString(R.string.passage_d26))
				);
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a27),
						getResources().getString(R.string.passage_b27), 
						Integer.parseInt(getResources().getString(R.string.passage_c27)), 
						Integer.parseInt(getResources().getString(R.string.passage_d27))
				);
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a28),
						getResources().getString(R.string.passage_b28), 
						Integer.parseInt(getResources().getString(R.string.passage_c28)), 
						Integer.parseInt(getResources().getString(R.string.passage_d28))
				);					
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a29),
						getResources().getString(R.string.passage_b29), 
						Integer.parseInt(getResources().getString(R.string.passage_c29)), 
						Integer.parseInt(getResources().getString(R.string.passage_d29))
				);								
  				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a30),
						getResources().getString(R.string.passage_b30), 
						Integer.parseInt(getResources().getString(R.string.passage_c30)), 
						Integer.parseInt(getResources().getString(R.string.passage_d30))
				);		
				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a31),
						getResources().getString(R.string.passage_b31), 
						Integer.parseInt(getResources().getString(R.string.passage_c31)), 
						Integer.parseInt(getResources().getString(R.string.passage_d31))
				);								
  				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a32),
						getResources().getString(R.string.passage_b32), 
						Integer.parseInt(getResources().getString(R.string.passage_c32)), 
						Integer.parseInt(getResources().getString(R.string.passage_d32))
				);	  				
  				databaseConnector.insertScripture(
						getResources().getString(R.string.passage_a33),
						getResources().getString(R.string.passage_b33), 
						Integer.parseInt(getResources().getString(R.string.passage_c33)), 
						Integer.parseInt(getResources().getString(R.string.passage_d33))
				);	  				
				
	    } // end of 		if (dbExist){

	} // end of private void preInsertScripture(){
	
	    
	
} // end of public class SplashScreen extends Activity {
