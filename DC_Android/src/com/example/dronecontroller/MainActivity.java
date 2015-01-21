package com.example.dronecontroller;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				switchto();
			}
		}, 1500);
	}
		
		void switchto(){
			Intent blue = new Intent(this,BluetoothStart.class);//BluetoothStart enables bluetooth and connects to 'linvor' 
			startActivity(blue);                                //ie. the bluetooth module
			finish();
		}
		



	}