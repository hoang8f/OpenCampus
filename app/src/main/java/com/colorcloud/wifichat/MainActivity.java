package com.colorcloud.wifichat;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
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

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.SoundManager;
import info.hoang8f.android.segmented.SegmentedGroup;

import static com.colorcloud.wifichat.Constants.MSG_PUSHOUT_DATA;
import static com.colorcloud.wifichat.Constants.MSG_REGISTER_ACTIVITY;

public class MainActivity extends Activity implements View.OnClickListener {

    public static final String TAG = "PTP_ChatAct";

    public static final String KEY_COLOR_CODE = "COLOR_CODE";
    public static final String KEY_IS_MUSIC = "IS_MUSIC";

    public static final int COLOR_1 = 1;
    public static final int COLOR_2 = 2;
    public static final int COLOR_3 = 3;
    public static final int COLOR_4 = 4;
    public static final int COLOR_5 = 5;
    public static final int MUSIC_CODE = 6;

    public static final int ANIMATION_POP_CENTER = 1;
    public static final int ANIMATION_LEFT_TO_RIGHT = 2;
    public static final int ANIMATION_RIGHT_TO_LEFT = 3;

    PlaceholderFragment fragment1;
    PlaceholderFragment fragment2;
    PlaceholderFragment fragment3;
    PlaceholderFragment fragment4;
    PlaceholderFragment fragment5;

    //    private static int currentColor = 1;
    SegmentedGroup segmentedGroup;

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
        findViewById(R.id.button_reset).setOnClickListener(this);
        findViewById(R.id.button_music).setOnClickListener(this);
        segmentedGroup = (SegmentedGroup) findViewById(R.id.segmented);

        Intent i = getIntent();
        String initMsg = i.getStringExtra("FIRST_MSG");

        mApp = (WiFiDirectApp) getApplication();
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
        for (int i = 0; i < 100; i++)
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
                showColor1(false);
                sendColorMessage(COLOR_1);
                break;
            case R.id.button2:
                showColor2(false);
                sendColorMessage(COLOR_2);
                break;
            case R.id.button3:
                showColor3(false);
                sendColorMessage(COLOR_3);
                break;
            case R.id.button4:
                showColor4(false);
                sendColorMessage(COLOR_4);
                break;
            case R.id.button_music:
                sendColorMessage(MUSIC_CODE);
                playMusic();
                break;
            case R.id.button_reset:
                showColor5(false);
                sendColorMessage(COLOR_5);
            default:
                break;
        }
    }

    private void showColor5(boolean isMusic) {
        FragmentTransaction ft;
        if (fragment5 == null) {
            fragment5 = new PlaceholderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_COLOR_CODE, COLOR_5);
            bundle.putBoolean(KEY_IS_MUSIC, isMusic);
            fragment5.setArguments(bundle);
        }
        fragment5.setIsMusic(isMusic);
        ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, 0, 0);
        ft.replace(R.id.container, fragment5).commit();
    }


    private void showColor4(boolean isMusic) {
        FragmentTransaction ft;
        if (fragment4 == null) {
            fragment4 = new PlaceholderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_COLOR_CODE, COLOR_4);
            bundle.putBoolean(KEY_IS_MUSIC, isMusic);
            fragment4.setArguments(bundle);
        }
        fragment4.setIsMusic(isMusic);
        ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, 0, 0);
        ft.replace(R.id.container, fragment4).commit();
    }

    private void showColor3(boolean isMusic) {
        FragmentTransaction ft;
        if (fragment3 == null) {
            fragment3 = new PlaceholderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_COLOR_CODE, COLOR_3);
            bundle.putBoolean(KEY_IS_MUSIC, isMusic);
            fragment3.setArguments(bundle);
        }
        fragment3.setIsMusic(isMusic);
        ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, 0, 0);
        ft.replace(R.id.container, fragment3).commit();
    }

    private void showColor2(boolean isMusic) {
        FragmentTransaction ft;
        if (fragment2 == null) {
            fragment2 = new PlaceholderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_COLOR_CODE, COLOR_2);
            bundle.putBoolean(KEY_IS_MUSIC, isMusic);
            fragment2.setArguments(bundle);
        }
        fragment2.setIsMusic(isMusic);
        ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, 0, 0);
        ft.replace(R.id.container, fragment2).commit();
    }

    private void showColor1(boolean isMusic) {
        FragmentTransaction ft;
        if (fragment1 == null) {
            fragment1 = new PlaceholderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_COLOR_CODE, COLOR_1);
            bundle.putBoolean(KEY_IS_MUSIC, isMusic);
            fragment1.setArguments(bundle);
        }
        fragment1.setIsMusic(isMusic);
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

        public ChatMessageAdapter(Context context, List<String> objects) {
            super(context, 0, objects);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

            if (view == null) {
                view = mInflater.inflate(R.layout.msg_row, null);
            }

            TextView msgRow = (TextView) view.findViewById(R.id.msg_row);
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
    protected void registerActivityToService(boolean register) {
        if (ConnectionService.getInstance() != null) {
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
    public void showMessage(final MessageRow row) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "showMessage : " + row.mMsg);
//    			if( mChatFrag != null ){
//    				mChatFrag.appendChatMessage(row);
//    			}
                try {
                    int colorCode = Integer.parseInt(row.mMsg);
                    switch (colorCode) {
                        case COLOR_1:
                            showColor1(false);
                            break;
                        case COLOR_2:
                            showColor2(false);
                            break;
                        case COLOR_3:
                            showColor3(false);
                            break;
                        case COLOR_4:
                            showColor4(false);
                            break;
                        case COLOR_5:
                            showColor5(false);
                            break;
                        case MUSIC_CODE:
                            //Show code
                            playMusic();
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void playMusic() {
        switch (segmentedGroup.getCheckedRadioButtonId()) {
            case R.id.position1:
                showColor1(true);
                SoundManager.getInstance().playDo(this, 500);
                break;
            case R.id.position2:
                showColor2(true);
                SoundManager.getInstance().playRe(this, 1000);
                break;
            case R.id.position3:
                showColor3(true);
                SoundManager.getInstance().playMi(this, 1500);
                break;
            case R.id.position4:
                showColor4(true);
                SoundManager.getInstance().playFa(this, 2000);
                break;
            default:
                //Do nothing
        }
    }

    public static class PlaceholderFragment extends Fragment {

        View rootView;
        TextView notes;
        Boolean isMusic = false;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
            notes = (TextView) rootView.findViewById(R.id.notes);
            return rootView;
        }

        public void setIsMusic(boolean music) {
            isMusic = music;
        }

        @Override
        public void onResume() {
            super.onResume();
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                int color = bundle.getInt(KEY_COLOR_CODE, COLOR_1);
                switch (color) {
                    case COLOR_1:
                        rootView.setBackgroundColor(getResources().getColor(R.color.fbutton_color_green_sea));
                        if(isMusic) {
                            notes.setText("ド");
                        } else {
                            notes.setText("");
                        }
                        break;
                    case COLOR_2:
                        rootView.setBackgroundColor(getResources().getColor(R.color.fbutton_color_peter_river));
                        if(isMusic) {
                            notes.setText("レ");
                        } else {
                            notes.setText("");
                        }
                        break;
                    case COLOR_3:
                        rootView.setBackgroundColor(getResources().getColor(R.color.fbutton_color_alizarin));
                        if(isMusic) {
                            notes.setText("ミ");
                        } else {
                            notes.setText("");
                        }
                        break;
                    case COLOR_4:
                        rootView.setBackgroundColor(getResources().getColor(R.color.fbutton_color_sun_flower));
                        if(isMusic) {
                            notes.setText("ファ");
                        } else {
                            notes.setText("");
                        }
                        break;
                    case COLOR_5:
                        rootView.setBackgroundColor(getResources().getColor(R.color.fbutton_color_silver));
                        if(isMusic) {
                            notes.setText("ファ");
                        } else {
                            notes.setText("");
                        }
                        break;
                    default:
                        //Do nothing
                }
            }
        }
    }
}
