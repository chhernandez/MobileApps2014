package team.kyb;

import java.text.SimpleDateFormat;
import java.util.Random;

import team.kyb.database.DatabaseConnector;
import team.kyb.extragames.ScriptureForGameHelper;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.text.Html;
import android.util.Log;
import android.widget.RemoteViews;
import team.kyb.R;

public class DailyScriptureWidget extends AppWidgetProvider{

	public static final String DEBUG_TAG = "kyb widget";
	
	//DatabaseConnector database = new DatabaseConnector(DailyScriptureWidget.this);		
	
	private String wPassage, wBook, wChapter, wVerse;	
	String mylocale = java.util.Locale.getDefault().getLanguage();	
	
	
	@Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		

/*		database.open();
		ScriptureForGameHelper scripture = new ScriptureForGameHelper(database.getRandomScriptureForGame());
		
		
		wPassage = scripture.getPassage();
		wBook = scripture.getBook();
		wChapter = String.valueOf(scripture.getChapter());
		wVerse = String.valueOf(scripture.getVerse());*/
	
		
		long date = System.currentTimeMillis(); 

		SimpleDateFormat sdf = new SimpleDateFormat("EEEE MMM d, yyyy");
		String dateString = sdf.format(date);   		
		

		Resources res = context.getResources();
		
		wPassage = res.getString(R.string.passage_a2);
		wBook = res.getString(R.string.passage_b2);
		wChapter = res.getString(R.string.passage_c2);
		wVerse = res.getString(R.string.passage_d2);
	
		
/*		String fulltext = "<b>" + wPassage + "</b>" + "<br>" +
				"<br>" + wBook + " " + wChapter
				+ " : " + wVerse;	*/		
		
		String[] mytextarray = new String[33];
		
		mytextarray[0] = "<b>" + res.getString(R.string.passage_a1) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b1) + " " + res.getString(R.string.passage_c1)
				+ " : " + res.getString(R.string.passage_d1);
		mytextarray[1] = "<b>" + res.getString(R.string.passage_a2) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b2) + " " + res.getString(R.string.passage_c2)
				+ " : " + res.getString(R.string.passage_d2);		
		mytextarray[2] = "<b>" + res.getString(R.string.passage_a3) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b3) + " " + res.getString(R.string.passage_c3)
				+ " : " + res.getString(R.string.passage_d3);	
		mytextarray[3] = "<b>" + res.getString(R.string.passage_a4) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b4) + " " + res.getString(R.string.passage_c4)
				+ " : " + res.getString(R.string.passage_d4);	
		mytextarray[4] = "<b>" + res.getString(R.string.passage_a5) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b5) + " " + res.getString(R.string.passage_c5)
				+ " : " + res.getString(R.string.passage_d5);
		mytextarray[5] = "<b>" + res.getString(R.string.passage_a6) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b6) + " " + res.getString(R.string.passage_c6)
				+ " : " + res.getString(R.string.passage_d6);		
		mytextarray[6] = "<b>" + res.getString(R.string.passage_a7) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b7) + " " + res.getString(R.string.passage_c7)
				+ " : " + res.getString(R.string.passage_d7);	
		mytextarray[7] = "<b>" + res.getString(R.string.passage_a8) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b8) + " " + res.getString(R.string.passage_c8)
				+ " : " + res.getString(R.string.passage_d8);
		mytextarray[8] = "<b>" + res.getString(R.string.passage_a9) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b9) + " " + res.getString(R.string.passage_c9)
				+ " : " + res.getString(R.string.passage_d9);
		mytextarray[9] = "<b>" + res.getString(R.string.passage_a10) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b10) + " " + res.getString(R.string.passage_c10)
				+ " : " + res.getString(R.string.passage_d10);		
		mytextarray[10] = "<b>" + res.getString(R.string.passage_a11) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b11) + " " + res.getString(R.string.passage_c11)
				+ " : " + res.getString(R.string.passage_d11);	
		mytextarray[11] = "<b>" + res.getString(R.string.passage_a12) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b12) + " " + res.getString(R.string.passage_c12)
				+ " : " + res.getString(R.string.passage_d12);	
		mytextarray[12] = "<b>" + res.getString(R.string.passage_a13) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b13) + " " + res.getString(R.string.passage_c13)
				+ " : " + res.getString(R.string.passage_d13);
		mytextarray[13] = "<b>" + res.getString(R.string.passage_a14) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b14) + " " + res.getString(R.string.passage_c14)
				+ " : " + res.getString(R.string.passage_d14);		
		mytextarray[14] = "<b>" + res.getString(R.string.passage_a15) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b15) + " " + res.getString(R.string.passage_c15)
				+ " : " + res.getString(R.string.passage_d15);	
		mytextarray[15] = "<b>" + res.getString(R.string.passage_a16) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b16) + " " + res.getString(R.string.passage_c16)
				+ " : " + res.getString(R.string.passage_d16);
		mytextarray[16] = "<b>" + res.getString(R.string.passage_a17) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b17) + " " + res.getString(R.string.passage_c17)
				+ " : " + res.getString(R.string.passage_d17);	
		mytextarray[17] = "<b>" + res.getString(R.string.passage_a18) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b18) + " " + res.getString(R.string.passage_c18)
				+ " : " + res.getString(R.string.passage_d18);
		mytextarray[18] = "<b>" + res.getString(R.string.passage_a19) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b19) + " " + res.getString(R.string.passage_c19)
				+ " : " + res.getString(R.string.passage_d19);		
		mytextarray[19] = "<b>" + res.getString(R.string.passage_a20) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b20) + " " + res.getString(R.string.passage_c20)
				+ " : " + res.getString(R.string.passage_d20);	
		mytextarray[20] = "<b>" + res.getString(R.string.passage_a21) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b21) + " " + res.getString(R.string.passage_c21)
				+ " : " + res.getString(R.string.passage_d21);
		mytextarray[21] = "<b>" + res.getString(R.string.passage_a22) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b22) + " " + res.getString(R.string.passage_c22)
				+ " : " + res.getString(R.string.passage_d22);		
		mytextarray[22] = "<b>" + res.getString(R.string.passage_a23) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b23) + " " + res.getString(R.string.passage_c23)
				+ " : " + res.getString(R.string.passage_d23);	
		mytextarray[23] = "<b>" + res.getString(R.string.passage_a24) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b24) + " " + res.getString(R.string.passage_c24)
				+ " : " + res.getString(R.string.passage_d24);	
		mytextarray[24] = "<b>" + res.getString(R.string.passage_a25) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b25) + " " + res.getString(R.string.passage_c25)
				+ " : " + res.getString(R.string.passage_d25);
		mytextarray[25] = "<b>" + res.getString(R.string.passage_a26) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b26) + " " + res.getString(R.string.passage_c26)
				+ " : " + res.getString(R.string.passage_d26);
		mytextarray[26] = "<b>" + res.getString(R.string.passage_a27) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b27) + " " + res.getString(R.string.passage_c27)
				+ " : " + res.getString(R.string.passage_d27);	
		mytextarray[27] = "<b>" + res.getString(R.string.passage_a28) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b28) + " " + res.getString(R.string.passage_c28)
				+ " : " + res.getString(R.string.passage_d28);
		mytextarray[28] = "<b>" + res.getString(R.string.passage_a29) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b29) + " " + res.getString(R.string.passage_c29)
				+ " : " + res.getString(R.string.passage_d29);		
		mytextarray[29] = "<b>" + res.getString(R.string.passage_a30) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b30) + " " + res.getString(R.string.passage_c30)
				+ " : " + res.getString(R.string.passage_d30);	
		mytextarray[30] = "<b>" + res.getString(R.string.passage_a31) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b31) + " " + res.getString(R.string.passage_c31)
				+ " : " + res.getString(R.string.passage_d31);
		mytextarray[31] = "<b>" + res.getString(R.string.passage_a32) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b32) + " " + res.getString(R.string.passage_c32)
				+ " : " + res.getString(R.string.passage_d32);		
		mytextarray[32] = "<b>" + res.getString(R.string.passage_a33) + "</b>" + "<br>" +
				"<br>" + res.getString(R.string.passage_b33) + " " + res.getString(R.string.passage_c33)
				+ " : " + res.getString(R.string.passage_d33);			
		
	
		
		int idx = new Random().nextInt(mytextarray.length);	
		
		String fulltext = (mytextarray[idx]);		
		
		//fill TextViews with the retrieved data
		
		// allpassage.setText(Html.fromHtml(fulltext));		
		
		// loop through all instances of this widget
		for (int appWidgetId : appWidgetIds){
			
			Log.d("KYB Widget", "Updating widget " + appWidgetId);
			
            // Create an Intent to launch KYB from spash screen
            Intent intent = new Intent(context, SplashScreen.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			
			
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.kyb_widget);
			views.setTextViewText(R.id.dateTextView, dateString);
			views.setTextViewText(R.id.allpassageTextView, Html.fromHtml(fulltext));
	        views.setOnClickPendingIntent(R.id.kyb_icon, pendingIntent);
	        
            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
            
		}// end of for (int appWidgetId : appWidgetIds){
		
	//	database.close();		
		
	}
	
}
