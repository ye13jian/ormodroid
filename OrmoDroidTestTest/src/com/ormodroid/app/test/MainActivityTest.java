package com.ormodroid.app.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ormodroid.app.MainActivity;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	private MainActivity mMainActivity;
	private TextView mHelloWorldTextView;
	private EditText mEventTitle;
	private Button mInsertButton;
	private Button mReadButton;

	public MainActivityTest() {
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mMainActivity = getActivity();
		mHelloWorldTextView = (TextView) mMainActivity.findViewById(com.ormodroid.app.R.id.hello_world_text_view);
		mInsertButton = (Button) mMainActivity.findViewById(com.ormodroid.app.R.id.insert_button);
		mReadButton = (Button) mMainActivity.findViewById(com.ormodroid.app.R.id.read_button);
		mEventTitle = (EditText) mMainActivity.findViewById(com.ormodroid.app.R.id.event_title);
	}
	
	public void testPreconditions() {
	    assertNotNull(mMainActivity);
	    assertNotNull(mHelloWorldTextView);
	    assertNotNull(mInsertButton);
	    assertNotNull(mReadButton);
	    assertNotNull(mEventTitle);
	}
	
	public void testHelloWorldTextViewText() {
		String expected = mMainActivity.getString(com.ormodroid.app.R.string.hello_world);
		String actual = mHelloWorldTextView.getText().toString();
		assertEquals(expected, actual);
		
		final ViewGroup.LayoutParams mHelloWorldTextViewLayoutParams = mHelloWorldTextView.getLayoutParams();
		assertNotNull(mHelloWorldTextViewLayoutParams);
		assertEquals(mHelloWorldTextViewLayoutParams.width, WindowManager.LayoutParams.WRAP_CONTENT);
		assertEquals(mHelloWorldTextViewLayoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
	}
	
	public void testInsertButton() {
		String expected = mMainActivity.getString(com.ormodroid.app.R.string.btn_insert);
		String actual = mInsertButton.getText().toString();
		assertEquals(expected, actual);
		
		final ViewGroup.LayoutParams mHelloWorldButtonLayoutParams = mInsertButton.getLayoutParams();
		assertNotNull(mHelloWorldButtonLayoutParams);
		assertEquals(mHelloWorldButtonLayoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
		assertEquals(mHelloWorldButtonLayoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
	}
	
	@MediumTest
	public void testHelloWorldLayout() {
		final View decorView = mMainActivity.getWindow().getDecorView();
		ViewAsserts.assertOnScreen(decorView, mInsertButton);
		ViewAsserts.assertOnScreen(decorView, mReadButton);
		ViewAsserts.assertOnScreen(decorView, mHelloWorldTextView);
	}
	
	@MediumTest
	public void testInsertAndReadButtonClick() {
		String expected = mMainActivity.getString(com.ormodroid.app.R.string.btn_insert);
		TouchUtils.clickView(this, mInsertButton);
		assertEquals(expected, mHelloWorldTextView.getText());
		
		TouchUtils.clickView(this, mReadButton);
		assertEquals("my-test", mHelloWorldTextView.getText().toString());
	}
	
}
