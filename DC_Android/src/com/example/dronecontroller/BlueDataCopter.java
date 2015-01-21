package com.example.dronecontroller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class BlueDataCopter extends Activity implements OnClickListener, OnSeekBarChangeListener{


	
	BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    TextView myLabel,ThrustStatus;
    EditText myTextbox;
    String msg;
    Button left,right,up,down,stop;
    SeekBar Thrust;
    AlertDialog.Builder diaexit;
    int flag=0;
    
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blue_data_copter);
		
		myLabel = (TextView)findViewById(R.id.myLabel);
		left = (Button)findViewById(R.id.left);
		right = (Button)findViewById(R.id.right);
		up = (Button)findViewById(R.id.up);
		down = (Button)findViewById(R.id.down);
		stop = (Button)findViewById(R.id.stop);
		Thrust = (SeekBar)findViewById(R.id.Thrust);
		ThrustStatus= (TextView)findViewById(R.id.ThrustStatus);
		
		left.setOnClickListener(this);
		right.setOnClickListener(this);
		down.setOnClickListener(this);
		up.setOnClickListener(this);
		stop.setOnClickListener(this);
        Thrust.setOnSeekBarChangeListener(this);
        
        Thrust.setMax(100);
		
		ThrustStatus.setText("Thrust: " + Thrust.getProgress());
		
		try {
			openBT();
		} catch (IOException e) {
			if(flag==0)
	    	{
	    		Toast.makeText(this, "EXIT DIALOG", Toast.LENGTH_LONG).show();
	    		ExNotFoundDialog();
	    	}
			e.printStackTrace();
		}
		
		
		
	}
	

    void openBT() throws IOException
    {
    	if (mmOutputStream == null)
    	{		
    	mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    	Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
    	

    	
    	for(BluetoothDevice device : pairedDevices)
        {
            if(device.getName().equals("linvor")) 
            {
            	
                mmDevice = device;
                flag=1;
                break;
                
                
            }
       }
    	
    	
    	
    	UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);        
        mmSocket.connect();
        mmOutputStream = mmSocket.getOutputStream();
    	}

    }
    

	@Override
	public void onClick(View v) {
		
		if(v==up)
		{
				try {
					if (mmOutputStream != null) {
						mmOutputStream.write("e".getBytes());
				        myLabel.setText("UP code send");
					} 
					else
					{
						Toast.makeText(this, "UP_NULL", Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {
					Toast.makeText(this, "Error on UP", Toast.LENGTH_LONG).show();
				}
				
			}
		if(v==down)
		{
				try {
					if (mmOutputStream != null) {
						mmOutputStream.write("f".getBytes());
				        myLabel.setText("DOWN code send");
					} 
					else
					{
						Toast.makeText(this, "DOWN_NULL", Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {
					Toast.makeText(this, "Error on DOWN", Toast.LENGTH_LONG).show();
				}
				
			}
		if(v==left)
		{
				try {
					if (mmOutputStream != null) {
						mmOutputStream.write("g".getBytes());
				        myLabel.setText("LEFT code send");
					} 
					else
					{mmOutputStream.write("L".getBytes());
						Toast.makeText(this, "LEFT_NULL", Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {
					Toast.makeText(this, "Error on LEFT", Toast.LENGTH_LONG).show();
				}
				
			}
		if(v==right)
		{
				try {
					if (mmOutputStream != null) {
						mmOutputStream.write("h".getBytes());
				        myLabel.setText("RIGHT code send");
					} 
					else
					{
						Toast.makeText(this, "RIGHT_NULL", Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {
					Toast.makeText(this, "Error on RIGHT", Toast.LENGTH_LONG).show();
				}
				
			}
		if(v==stop)
		{
				try {
					if (mmOutputStream != null) {
						mmOutputStream.write("i".getBytes());
				        myLabel.setText("STOP code send");
					} 
					else
					{
						Toast.makeText(this, "STOP_NULL", Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {
					Toast.makeText(this, "Error on STOP", Toast.LENGTH_LONG).show();
				}
				
			}
		}


	public void onProgressChanged(SeekBar sb, int progress, boolean fromUser){
		ThrustStatus.setText("Thrust: " + Thrust.getProgress());
		try {
			if (mmOutputStream != null) {
				mmOutputStream.write(progress);
		        myLabel.setText("Thrust code send");
			} 
			else
			{
				Toast.makeText(this, "Thrust_NULL", Toast.LENGTH_LONG).show();
			}
		} catch (IOException e) {
			Toast.makeText(this, "Error on Thrust", Toast.LENGTH_LONG).show();
		}
		
		}

		
		public void onStopTrackingTouch(SeekBar sb){
			ThrustStatus.setTypeface(null);
		}

		public void onStartTrackingTouch(SeekBar sb){
	    ThrustStatus.setTypeface(null);
	    
		}
		public void ExNotFoundDialog()
		{
			diaexit.setTitle("Exit");
			diaexit.setMessage("Bluetooth Adapter not found/nExiting... ??");
			diaexit.setCancelable(false);
			
		    diaexit.setNeutralButton("OK", new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
								finish();
								System.exit(0);
							
					           }
						});
						AlertDialog alert = diaexit.create();
		                alert.show();
		                
						
		}
		

}


