package com.cutingedge.cut.radio;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by michael on 02/04/15.
 */
public class menu1_Fragment extends Fragment {
    View rootview;


    private Button buttonPlayStop;

    private button_state buttonState;

    String strAudioLink = "cutradio";

    private boolean isOnline;
    private boolean boolMusicPlaying = false;
    TelephonyManager telephonyManager;
    PhoneStateListener listener;

    static Context context;
    static boolean isPlaying=false;
    Intent streamService;
    SharedPreferences prefs;

    // Progress dialogue and broadcast receiver variables
    boolean mBufferBroadcastIsRegistered;
    private ProgressDialog pdBuff = null;


    @Override
    public void onResume() {
        super.onResume();


        if(isPlaying){

            buttonPlayStop.setBackgroundResource(R.mipmap.pause);
//            Toast.makeText(getActivity(),"true!",Toast.LENGTH_SHORT).show();

        }
        else
        {

            buttonPlayStop.setBackgroundResource(R.mipmap.play);
//            Toast.makeText(getActivity(),"false!",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.menu1_layout, container, false);

        buttonState=button_state.PLAY;
        try {
            streamService = new Intent(getActivity(), radioService.class);
            initViews();
            setListeners();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(),
                    e.getClass().getName() + " " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        return rootview;
    }



    private void stopMusic() {
//        getActivity().stopService(new Intent(getActivity(),radioService.class));
        try {
            getActivity().stopService(new Intent(getActivity(), radioService.class));

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(),
                    e.getClass().getName() + " " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
        buttonState=buttonState.STOP;
        isPlaying=false;

    }

    private void startMusic() {


        checkConnectivity();
        if (isOnline) {
            stopMusic();

            streamService.putExtra("sentAudioLink", strAudioLink);

            try {
                getActivity().startService(streamService);
            } catch (Exception e) {

                e.printStackTrace();
                Toast.makeText(

                        getActivity().getApplicationContext(),

                        e.getClass().getName() + " " + e.getMessage(),

                        Toast.LENGTH_LONG).show();
            }

            buttonState=buttonState.STOP;
            isPlaying=true;

        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Network Not Connected...");
            alertDialog.setMessage("Please connect to a network and try again");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // here you can add functions
                }
            });
//            alertDialog.setIcon(R.drawable.icon);
            alertDialog.show();
        }

    }
    private void checkConnectivity() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo ni = cm.getActiveNetworkInfo();
        if(ni==null)
            isOnline=false;
        else
            isOnline=true;
    }

    public void setListeners(){

        buttonPlayStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                buttonStateChanged();
            }
        });

    }
    public enum button_state {
        PLAY, STOP;
    }

    public void buttonStateChanged(){

        if(buttonState==buttonState.PLAY){

            startMusic();
            buttonState=buttonState.STOP;
            buttonPlayStop.setBackgroundResource(R.mipmap.pause);

        }
        else
        {
            stopMusic();
            buttonState=buttonState.PLAY;
            buttonPlayStop.setBackgroundResource(R.mipmap.play);
        }
    }

    private void initViews(){

        buttonPlayStop = (Button) rootview.findViewById(R.id.btnPlayStop);

        buttonPlayStop.setBackgroundResource(R.mipmap.play);

        //***Uncomment for background in fragment
        //rootview.findViewById(R.id.relativeView1).setBackground(getActivity().getResources().getDrawable(R.drawable.background));

    }

    // Handle progress dialogue for buffering...
    private void showPD(Intent bufferIntent) {
        String bufferValue = bufferIntent.getStringExtra("buffering");
        int bufferIntValue = Integer.parseInt(bufferValue);

        // When the broadcasted "buffering" value is 1, show "Buffering"
        // progress dialogue.
        // When the broadcasted "buffering" value is 0, dismiss the progress
        // dialogue.

        switch (bufferIntValue) {
            case 0:
                // Log.v(TAG, "BufferIntValue=0 RemoveBufferDialogue");
                // txtBuffer.setText("");
                if (pdBuff != null) {
                    pdBuff.dismiss();
                }
                break;

            case 1:
                BufferDialogue();
                break;

            // Listen for "2" to reset the button to a play button
            case 2:
                break;

        }
    }

    // Progress dialogue...
    private void BufferDialogue() {

        pdBuff = ProgressDialog.show(getActivity(), "Buffering...",
                "Acquiring song...", true);
    }

    // Set up broadcast receiver
    private BroadcastReceiver broadcastBufferReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent bufferIntent) {
            showPD(bufferIntent);
        }
    };



}
