package com.tumblr.ccphan.threadinghandlerusingmessage;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView txt;
	// Our handler.
	// Processes Messages and Runnable objects associated with the current
	// thread MessageQueue. the message queue holds the tasks to be executed in
	// FIFO (First In First Out) manner. You will need only one Handler per
	// activity where the background thread will communicate with to update the
	// UI.
	// For Messages (as oppose to Runnables) the handler is responsible
	// implementing the response via the callback method.
	// For Runnables the 'sender' is responsible for the implementing the
	// response.
	Handler handler = new Handler() {
		@Override
		// This is the callback method that will process the messages as they
		// are received
		public void handleMessage(Message msg) {
			// get the bundle and extract data by key
			Bundle b = msg.getData();
			String key = b.getString("My Key");
			txt.setText(txt.getText() + "Item " + key
					+ System.getProperty("line.separator"));
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txt = (TextView) findViewById(R.id.txt);
	}

	@Override
	protected void onStart() {
		super.onStart();
		// create a new thread that runs in the background
		Thread background = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					try {
						Thread.sleep(1000);

						Message msg = new Message();

						// need to pass data into bundle for the callback method
						// to access
						Bundle b = new Bundle();
						b.putString("My Key", "My Value: " + String.valueOf(i));
						msg.setData(b);

						// send message to the handler with the current message
						// handler
						handler.sendMessage(msg);
					} catch (Exception e) {
						Log.v("Error", e.toString());
					}
				}
			}
		});

		background.start();
	}

}
