package com.example.catan;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	ImageView dice1, dice2;
	SoundPool sp;
	int rollSound = 0;
	int endTurn = 0;
	TextView timer;
	boolean clicked = false;
	int gameCounter = 0;
	long total;
	CheckBox robber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button roll = (Button) findViewById(R.id.bRoll);
		Button pause = (Button) findViewById(R.id.bPause);
		timer = (TextView) findViewById(R.id.etTimer);
		dice1 = (ImageView) findViewById(R.id.dice1);
		dice2 = (ImageView) findViewById(R.id.dice2);
		robber = (CheckBox) findViewById(R.id.cbRobber);
		sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		rollSound = sp.load(this, R.raw.dicesound, 1);
		endTurn = sp.load(this, R.raw.horn, 1);
		roll.setOnClickListener(this);
		pause.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.bRoll:
		dice();
		countTimer();
		clicked = true;
		gameCounter++;
		break;
	case R.id.bPause:
		timeLeft();
		break;
	}
	}
	
	public void dice() {
		int[] dice = { 
			R.drawable.one, R.drawable.two, R.drawable.three, 
			R.drawable.four, R.drawable.five, R.drawable.six };
		Random r = new Random();
		int numOne = r.nextInt(dice.length);
		int numTwo = r.nextInt(dice.length);
		dice1.setImageResource(dice[numOne]);
		dice2.setImageResource(dice[numTwo]);
		sp.play(rollSound, 1, 1, 0, 0, 1);
		if ((1 + numOne) + (1 + numTwo) == 7 && robber.isChecked()) {
			robber();
		}
	}

	public void countTimer() {
		new CountDownTimer(30000*4, 1000) {

			public void onTick(long millisUntilFinished) {
				timer.setText("");
				total = millisUntilFinished;
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				timer.setText("YOU SLOW!");
				sp.play(endTurn, 1, 1, 0, 0, 1);
			}

		}.start();
	}
	
	public void robber() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("OH SNAPS, THE ROBBER YO!");
		builder
			.setMessage("If you have more than 7 resource cards, you must discard half to bank")
			.setCancelable(false)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
			AlertDialog ad = builder.create();
			ad.show();
	}
	
	public void timeLeft() {
		long result = total - 0;
		int mills = (int) result;
		int second = (int) result / 1000;
		int minute = second / 60;
		mills = mills % 100;
		second = second % 60;
		if (clicked = true) {
				timer.setText(String.format("%d:%02d:%02d", minute, second, mills));
		}
	}
}	

