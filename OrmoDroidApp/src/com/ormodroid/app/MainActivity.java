package com.ormodroid.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ormodroid.app.dao.EventDAO;
import com.ormodroid.app.model.Event;
import com.ormodroid.app.schema.DbHelper;
import com.ormodroid.core.exception.ObjectNotFoundException;

public class MainActivity extends Activity {
	private Button mInsertButton;
	private Button mReadButton;
	private TextView mHelloWorldTextView;
	private EditText mEventTitle;
	private DbHelper mEventDbHelper;
	
	private EventDAO mEventDAO;
	
	private long gen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mEventDbHelper = new DbHelper(this);
		mEventDAO = new EventDAO(mEventDbHelper.getWritableDatabase());

		mInsertButton = (Button) findViewById(R.id.insert_button);
		mReadButton = (Button) findViewById(R.id.read_button);
		mHelloWorldTextView = (TextView) findViewById(R.id.hello_world_text_view);
		mEventTitle = (EditText) findViewById(R.id.event_title);
		
		mInsertButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mHelloWorldTextView.setText(getString(R.string.btn_insert));
				String eventTitle = mEventTitle.getText().toString();
				gen = mEventDAO.save(new Event(eventTitle));
			}
		});

		mReadButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mHelloWorldTextView.setText(getString(R.string.btn_read));

				Event event;
				try {
					event = mEventDAO.get(gen);
					mHelloWorldTextView.setText(event.getTitle());
					
				} catch (ObjectNotFoundException e) {
					mHelloWorldTextView.setText(e.getMessage());
					
				}
				
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
