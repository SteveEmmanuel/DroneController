package com.example.dronecontroller;import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Choose extends Activity implements OnClickListener{
	
    Button car,copter;
	AlertDialog.Builder diaexit;
	BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose);
		diaexit= new AlertDialog.Builder(this);
		car = (Button)findViewById(R.id.car);
		copter = (Button)findViewById(R.id.copter);

		car.setOnClickListener(this);
		copter.setOnClickListener(this);
	
	}

	

	@Override
	public void onClick(View v) {
		if(v==car)
		{
			Intent bluedata = new Intent(this,BlueDataCar.class);
			startActivity(bluedata);
			finish();
		}
		else if(v==copter)
		{
			Intent Icopter = new Intent(this,BlueDataCopter.class);
			startActivity(Icopter);
		}
			
		
	}
	public void exdialog()
	{
		diaexit.setTitle("Exit");
		diaexit.setMessage("Are you sure you want\nto exit ??");
		diaexit.setCancelable(false);
		diaexit.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id) {
				finish();
				System.exit(0);
	           }
		});
					diaexit.setNegativeButton("No", new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog, int id) {
				               try {
									closeBT();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							dialog.dismiss();
				           }
					});
					
					AlertDialog alert = diaexit.create();
	                alert.show();
	 
					}
	
	
	public void onBackPressed()
    {
    exdialog();
    }
	void closeBT() throws IOException
    {
        mmOutputStream.close();
        mmSocket.close();
        
    }

}
