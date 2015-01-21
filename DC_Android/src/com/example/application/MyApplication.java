package com.example.application;


import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;

@ReportsCrashes(formKey = "dGZvUXVXdWdUZWJ3blNycmJrTk1uVFE6MQ")
public class MyApplication extends Application{
	

	@Override
	public void onCreate() {
		
	    super.onCreate();

	    // The following line triggers the initialization of ACRA
	    ACRA.init(this);
	}
}