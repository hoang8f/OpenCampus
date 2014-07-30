package com.colorcloud.wifichat;

import static com.colorcloud.wifichat.Constants.*;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
	
	public static final String TAG = "PTP_ChatAct";

    public static final String KEY_COLOR_CODE = "COLOR_CODE";

    public static final int COLOR_1 = 1;
    public static final int COLOR_2 = 2;
    public static final int COLOR_3 = 3;
    public static final int COLOR_4 = 4;

    public static final int ANIMATION_POP_CENTER = 1;
    public static final int ANIMATION_LEFT_TO_RIGHT = 2;
    public static final int ANIMATION_RIGHT_TO_LEFT = 3;

    Fragment fragment1;
    Fragment fragment2;
    Fragment fragment3;
    Fragment fragment4;
	
	WiFiDirectApp mApp = null;
//	ChatFragment mChatFrag = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);

        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);

		Intent i = getIntent();
		String initMsg = i.getStringExtra("FIRST_MSG");
		
		mApp = (WiFiDirectApp)getApplication(); 
//		initFragment(initMsg);
	}
	
	/**
	 * init fragement with possible recvd start up message.
	 */

    /*
	public void initFragment(String initMsg) {
    	// to add fragments to your activity layout, just specify which viewgroup to place the fragment.
    	final FragmentTransaction ft = getFragmentManager().beginTransaction();
    	if( mChatFrag == null ){
    		//mChatFrag = ChatFragment.newInstance(this, ConnectionService.getInstance().mConnMan.mServerAddr);
    		mChatFrag = ChatFragment.newInstance(this, null, initMsg);
    	}
    	
    	Log.d(TAG, "initFragment : show chat fragment..." + initMsg);
    	// chat fragment on top, do not do replace, as frag_detail already hard coded in layout.
    	ft.add(R.id.container, mChatFrag, "chat_frag");
    	ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    	ft.commit();
    }
    */
	
	@Override
	public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: chat activity resume, register activity to connection service !");
        registerActivityToService(true);
    }
	
	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause: chat activity closed, de-register activity from connection service !");
		registerActivityToService(false);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, " onDestroy: nothing... ");
	}
	
	/**
	 * set listview weight works, only when layout has only one LinearLayout.
	 */
	@Deprecated
	private void testWithListViewWeight() {
		List<String> mMessageList;   // a list of chat msgs.
	    ArrayAdapter<String> mAdapter;
	    
		mMessageList = new ArrayList<String>(200);
		for(int i=0;i<100;i++)
			mMessageList.add("User logged in");
        mAdapter = new ChatMessageAdapter(this, mMessageList);
        
        //setListAdapter(mAdapter);  // list fragment data adapter
        mAdapter.notifyDataSetChanged();  // notify the attached observer and views to refresh.
	}

    @Override
    public void onClick(View view) {
        FragmentTransaction ft;
        switch (view.getId()) {
            case R.id.button1:
                showColor1();
                sendColorMessage(COLOR_1);
                break;
            case R.id.button2:
                showColor2();
                sendColorMessage(COLOR_2);
                break;
            case R.id.button3:
                showColor3();
                sendColorMessage(COLOR_3);
                break;
            case R.id.button4:
                showColor4();
                sendColorMessage(COLOR_4);
                break;
            default:
                break;
        }
    }

    private void showColor4() {
        FragmentTransaction ft;
        if (fragment4 == null) {
            fragment4 = new PlaceholderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_COLOR_CODE, COLOR_4);
            fragment4.setArguments(bundle);
        }
        ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, 0, 0);
        ft.replace(R.id.container, fragment4).commit();
    }

    private void showColor3() {
        FragmentTransaction ft;
        if (fragment3 == null) {
            fragment3 = new PlaceholderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_COLOR_CODE, COLOR_3);
            fragment3.setArguments(bundle);
        }
        ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, 0, 0);
        ft.replace(R.id.container, fragment3).commit();
    }

    private void showColor2() {
        FragmentTransaction ft;
        if (fragment2 == null) {
            fragment2 = new PlaceholderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_COLOR_CODE, COLOR_2);
            fragment2.setArguments(bundle);
        }
        ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, 0, 0);
        ft.replace(R.id.container, fragment2).commit();
    }

    private void showColor1() {
        FragmentTransaction ft;
        if (fragment1 == null) {
            fragment1 = new PlaceholderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_COLOR_CODE, COLOR_1);
            fragment1.setArguments(bundle);
        }
        ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, 0, 0);
        ft.replace(R.id.container, fragment1).commit();
    }


    /**
     * list view adapter for this activity when testing without using fragment!
     * deprecated, as we should always use fragment, template.
     */
	@Deprecated
    final class ChatMessageAdapter extends ArrayAdapter<String> {

    	private LayoutInflater mInflater;
    	
		public ChatMessageAdapter(Context context, List<String> objects){
			super(context, 0, objects);
            mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
		
		@Override
        public int getItemViewType(int position) {
			return IGNORE_ITEM_VIEW_TYPE;   // do not care			
		}
		
		/**
		 * assemble each row view in the list view.
		 */
		@Override
        public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;  // old view to re-use if possible. Useful for Heterogeneous list with diff item view type.
            String item = this.getItem(position);
            
            if( view == null ){
            	view = mInflater.inflate(R.layout.msg_row, null);
            }
            
            TextView msgRow = (TextView)view.findViewById(R.id.msg_row);
            msgRow.setText(item);
            
            return view;
		}
    }
    
    /**
     * register this activity to service process, so that service later can update list view.
     * In AsyncTask, the activity itself is passed to the constructor of asyncTask, hence later
     * onPostExecute() can do mActivity.mCommentsAdapter.notifyDataSetChanged();
     * Just need to be careful of reference to avoid mem leak. 
     */
    protected void registerActivityToService(boolean register){
    	if( ConnectionService.getInstance() != null ){
	    	Message msg = ConnectionService.getInstance().getHandler().obtainMessage();
	    	msg.what = MSG_REGISTER_ACTIVITY;
	    	msg.obj = this;
	    	msg.arg1 = register ? 1 : 0;
	    	ConnectionService.getInstance().getHandler().sendMessage(msg);
    	}
    }

    private void sendColorMessage(int colorCode) {
        // send the chat text in current line to the server
        String inputMsg = colorCode + "";
        MessageRow row = new MessageRow(mApp.mDeviceName, inputMsg, null);
        String jsonMsg = mApp.shiftInsertMessage(row);
        this.pushOutMessage(jsonMsg);
    }
    
    /**
     * post send msg to service to handle it in background.
     */
    public void pushOutMessage(String jsonstring) {
    	Log.d(TAG, "pushOutMessage : " + jsonstring);
    	Message msg = ConnectionService.getInstance().getHandler().obtainMessage();
    	msg.what = MSG_PUSHOUT_DATA;
    	msg.obj = jsonstring;
    	ConnectionService.getInstance().getHandler().sendMessage(msg);
    }
    
    /**
     * show the msg in chat fragment, update view must be from ui thread.
     */
    public void showMessage(final MessageRow row){
    	runOnUiThread(new Runnable() {
    		@Override public void run() {
    			Log.d(TAG, "showMessage : " + row.mMsg);
//    			if( mChatFrag != null ){
//    				mChatFrag.appendChatMessage(row);
//    			}
                try {
                    int colorCode = Integer.parseInt(row.mMsg);
                    switch (colorCode) {
                        case COLOR_1:
                            showColor1();
                            break;
                        case COLOR_2:
                            showColor2();
                            break;
                        case COLOR_3:
                            showColor3();
                            break;
                        case COLOR_4:
                            showColor4();
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
    		}
    	});
    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                int color = bundle.getInt(KEY_COLOR_CODE, COLOR_1);
                switch (color) {
                    case COLOR_1:
                        rootView.setBackgroundColor(getResources().getColor(R.color.fbutton_color_green_sea));
                        break;
                    case COLOR_2:
                        rootView.setBackgroundColor(getResources().getColor(R.color.fbutton_color_peter_river));
                        break;
                    case COLOR_3:
                        rootView.setBackgroundColor(getResources().getColor(R.color.fbutton_color_alizarin));
                        break;
                    case COLOR_4:
                        rootView.setBackgroundColor(getResources().getColor(R.color.fbutton_color_sun_flower));
                        break;
                    default:
                        //Do nothing
                }
            }
            return rootView;
        }
    }
}
