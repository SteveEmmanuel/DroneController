package com.example.dronecontroller;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import java.io.OutputStream;
import java.util.Set;


public class BluetoothStart extends Activity 

{
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    String msg;
    Editor editor;
	SharedPreferences prefs;
	AlertDialog.Builder diaexit;
	TextView myLabel;
	
	
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_start);
        
        myLabel = (TextView)findViewById(R.id.label);
       
        
       mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
       
        if(mBluetoothAdapter == null)
        {
        	exdialog();
        }
        
        else if(!mBluetoothAdapter.isEnabled())
        {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }
        else
        {
        	doBT();
        }
   
 
    }
    
    
    

      void findBT()
    {
    	Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0)
        {
            for(BluetoothDevice device : pairedDevices)
            {
                if(device.getName().equals("linvor")) 
                {
                	
                    mmDevice = device;
                    break;
                    
                    
                }
            }
        }
        myLabel.setText("Bluetooth Device Found");
    }
           
    @Override 
    //called automatically when startActivityForResult(enableBluetooth, 0); executes.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//So that the connection will initiate only after the adapter is enabled
  
    	if (resultCode == RESULT_OK){
   Toast.makeText(getApplicationContext(), "BlueTooth is now Enabled", Toast.LENGTH_LONG).show();
   doBT();
   }
  //If any error in enabling the adapter
    if(resultCode == RESULT_CANCELED){
   Toast.makeText(getApplicationContext(), "Error occured while enabling.Leaving the application..", Toast.LENGTH_LONG).show();
   finish();
   System.exit(0);
   ///finish();
    }
    }
    
    public void exdialog()
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
    
  
    void doBT()
    {
    	   findBT();
    	   Intent I=new Intent(this,Choose.class);
    	   startActivity(I);
    	   finish();
    		
    }
    
   
	
}



