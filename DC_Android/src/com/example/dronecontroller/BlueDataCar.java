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
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class BlueDataCar extends Activity implements OnClickListener, OnSeekBarChangeListener,SensorListener{


	
	BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    TextView myLabel,SpeedStatus;
    EditText myTextbox;
    String msg;
    Button left,right,up,down,stop;
    SeekBar speed;
    AlertDialog.Builder diaexit;
   
    
 // For smotion detection.
    private SensorManager sensorMgr;
    private long lastUpdate = -1;
    private float x, y, z;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 800;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blue_data_car);
		
		myLabel = (TextView)findViewById(R.id.myLabel);
		left = (Button)findViewById(R.id.left);
		right = (Button)findViewById(R.id.right);
		up = (Button)findViewById(R.id.up);
		down = (Button)findViewById(R.id.down);
		stop = (Button)findViewById(R.id.stop);
		speed = (SeekBar)findViewById(R.id.speed);
		SpeedStatus= (TextView)findViewById(R.id.SpeedStatus);
		
		left.setOnClickListener(this);
		right.setOnClickListener(this);
		down.setOnClickListener(this);
		up.setOnClickListener(this);
		stop.setOnClickListener(this);
        speed.setOnSeekBarChangeListener(this);
        
        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        boolean accelSupported = sensorMgr.registerListener(this,
            SensorManager.SENSOR_ACCELEROMETER,
            SensorManager.SENSOR_DELAY_GAME);

        if (!accelSupported) {
            // on accelerometer on this device
            sensorMgr.unregisterListener(this,
                    SensorManager.SENSOR_ACCELEROMETER);
        }
        
       
        
        speed.setMax(100);
		
		SpeedStatus.setText("Value: " + speed.getProgress());
		
		try {
			openBT();
		} catch (IOException e) {
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
		SpeedStatus.setText("Value: " + speed.getProgress());
		try {
			if (mmOutputStream != null) {
				mmOutputStream.write(progress);
		        myLabel.setText("SPEED code send");
			} 
			else
			{
				Toast.makeText(this, "SPEED_NULL", Toast.LENGTH_LONG).show();
			}
		} catch (IOException e) {
			Toast.makeText(this, "Error on SPEED", Toast.LENGTH_LONG).show();
		}
		
		}

		
		public void onStopTrackingTouch(SeekBar sb){
			SpeedStatus.setTypeface(null);
		}

		public void onStartTrackingTouch(SeekBar sb){
	    SpeedStatus.setTypeface(null);
	    
		}
	/*	public void ExNotFoundDialog()
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
		
		public void ExNotFoundDialog()
		{
			diaexit.setTitle("Exit");
			diaexit.setMessage("Bluetooth Adapter not found/nExiting... ??");
			diaexit.setCancelable(false);
			diaexit.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int id) {
					finish();
					System.exit(0);
		           }
			});
						diaexit.setNeutralButton("OK", new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
					           }
						});
						AlertDialog alert = diaexit.create();
		                alert.show();
		                
						
		}
		*/


		protected void onPause() {
		    if (sensorMgr != null) {
		        sensorMgr.unregisterListener(this,
		                SensorManager.SENSOR_ACCELEROMETER);
		        sensorMgr = null;
		        }
		    super.onPause();
		    }

		    public void onAccuracyChanged(int arg0, int arg1) {
		    // TODO Auto-generated method stub
		    }

		    public void onSensorChanged(int sensor, float[] values) {
		    if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
		        long curTime = System.currentTimeMillis();
		        // only allow one update every 100ms.
		        if ((curTime - lastUpdate) > 100) {
		        long diffTime = (curTime - lastUpdate);
		        lastUpdate = curTime;

		        x = values[SensorManager.DATA_X];
		        y = values[SensorManager.DATA_Y];
		        z = values[SensorManager.DATA_Z];

		        if(Round(x,4)>5.0000){
		            
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
		        else if(Round(x,4)<-5.0000){
		            
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

		        float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;

		  
		        if (speed > SHAKE_THRESHOLD) {
		        }
		        last_x = x;
		        last_y = y;
		        last_z = z;
		        }
		    }
		    }

		    public static float Round(float Rval, int Rpl) {
		    float p = (float)Math.pow(10,Rpl);
		    Rval = Rval * p;
		    float tmp = Math.round(Rval);
		    return (float)tmp/p;
		    }

}



