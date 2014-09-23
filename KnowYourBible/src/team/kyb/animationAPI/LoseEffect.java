package team.kyb.animationAPI;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import team.kyb.R;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoseEffect extends Activity {

	Random rd = new Random();

	FallingObject[] objs = new FallingObject[30];

	Handler RedrawHandler = new Handler(); // so redraw occurs in main thread
	Timer mTmr = null;

	TimerTask mTsk = null;
	
	private TextView time;

	int mScrWidth, mScrHeight;
	// android.graphics.PointF mBallPos, mBallVelocity;
	float mPrevXAcc, mPrevYAcc;
	long mPrevTime;

	final float ACC_FUDGE_FACTOR = .5f;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE); // hide title bar
		getWindow()
				.setFlags(
						0xFFFFFFFF,
						LayoutParams.FLAG_FULLSCREEN
								| LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blank_falling_layout2);
		// create pointer to main screen
		final RelativeLayout mainView = (android.widget.RelativeLayout) findViewById(R.id.main_view);

		// get screen dimensions
		Display display = getWindowManager().getDefaultDisplay();
		
		time = (TextView) findViewById(R.id.timeBoxL);
		
		mScrWidth = display.getWidth();
		mScrHeight = display.getHeight();

		mPrevTime = System.currentTimeMillis();
		mPrevXAcc = 0;
		mPrevYAcc = 0;

		for (int i = 0; i < objs.length; i++) {
			objs[i] = creatingFallingObject();
			mainView.addView(objs[i]);
			objs[i].invalidate();
		}

		long currentTime = System.currentTimeMillis();
		long elapsedTime = currentTime - mPrevTime;

		for (int i = 0; i < objs.length; i++) {
			changingSpeedAndPosForObject(objs[i], 0, mScrHeight / 30,
					elapsedTime);
		}

	} // OnCreate

	protected void changingSpeedAndPosForObject(FallingObject obj12,
			float aveXA, float aveYA, long time) {
		// Randomize x-axis velocity
		int rdint = rd.nextInt();
		if (rdint % 2 == 0) {
			obj12.mBallVelocity.x += aveXA * time / 1000 / ACC_FUDGE_FACTOR;
		} else {
			obj12.mBallVelocity.x -= aveXA * time / 1000 / ACC_FUDGE_FACTOR;
		}
		obj12.mBallVelocity.y += aveYA * time / 1000 / ACC_FUDGE_FACTOR;
	}

	private FallingObject creatingFallingObject() {
		android.graphics.PointF mBallPos = new android.graphics.PointF();
		android.graphics.PointF mBallVelocity = new android.graphics.PointF();
		Bitmap bm = BitmapFactory.decodeResource(getResources(),
				R.drawable.raindrop);

		// create variables for ball position and speed
		mBallPos.x = rd.nextInt(mScrWidth);
		mBallPos.y = rd.nextInt(mScrHeight / 5);

		mBallVelocity.x = rd.nextFloat();
		mBallVelocity.y = rd.nextFloat();
		FallingObject a = new FallingObject(this, mBallPos.x, mBallPos.y, 20,
				bm);
		a.mBallPos = mBallPos;
		a.mBallVelocity = mBallVelocity;
		return a;
	}

	// listener for menu button on phone
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Exit"); // only one menu item
		return super.onCreateOptionsMenu(menu);
	}

	// listener for menu item clicked
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		if (item.getTitle() == "Exit") // user clicked Exit
			finish(); // will call onPause
		return super.onOptionsItemSelected(item);
	}

	// For state flow see
	// http://developer.android.com/reference/android/app/Activity.html
	@Override
	public void onPause() // app moved to background, stop background threads
	{
		mTmr.cancel(); // kill\release timer (our only background thread)
		mTmr = null;
		super.onPause();
	}

	@Override
	public void onResume() // app moved to foreground (also occurs at app
							// startup)
	{
		// create timer to move ball to new position
		mTmr = new Timer();
		
		CountDownTimer a = new CountDownTimer(7000, 1000) {

		     public void onTick(long millisUntilFinished) {
		         time.setText("Try again in: " + millisUntilFinished / 1000);
		     }

		     public void onFinish() {
		         time.setText("done!");
		         finish();
		     }
		  }.start();
		
		mTsk = new TimerTask() {
			public void run() {
				for (int i = 0; i < objs.length; i++) {
					final FallingObject objHere = objs[i];

					if (objHere.mBallPos.y + objHere.mR >= mScrHeight) {
						objHere.mBallPos.y = 0;
					}

					if (objHere.mBallPos.x + objHere.mR >= mScrWidth) {
						objHere.mBallPos.x = 0;
					}

					// move ball based on current speed
					int rdint = rd.nextInt();
					if (rdint % 2 == 0) {
						objHere.mBallPos.x += objHere.mBallVelocity.x;
					} else {
						objHere.mBallPos.x -= objHere.mBallVelocity.x;
					}
					objHere.mBallPos.y += objHere.mBallVelocity.y;

					// update ball class instance
					objHere.mX = objHere.mBallPos.x;
					objHere.mY = objHere.mBallPos.y;

					RedrawHandler.post(new Runnable() {
						public void run() {
							objHere.invalidate();
						}
					});
				}
			}
		};
		mTmr.schedule(mTsk, 10, 10); // start timer
		super.onResume();
	} // onResume

	@Override
	public void onDestroy() // main thread stopped
	{
		super.onDestroy();
		System.runFinalizersOnExit(true); // wait for threads to exit before
											// clearing app
		// android.os.Process.killProcess(android.os.Process.myPid()); //remove
		// app from memory
	}

	// listener for config change.
	// This is called when user tilts phone enough to trigger landscape view
	// we want our app to stay in portrait view, so bypass event
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

}