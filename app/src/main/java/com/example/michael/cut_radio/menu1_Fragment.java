package com.example.michael.cut_radio;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.util.Log;
import android.media.AudioManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import java.io.IOException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;

/**
 * Created by michael on 02/04/15.
 */
public class menu1_Fragment extends Fragment {
    View rootview;


    private Button buttonPlay;

    private Button buttonStopPlay;

    static Context context;
    boolean isPlaying;
    Intent streamService;
    SharedPreferences prefs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.menu1_layout, container, false);

        buttonPlay = (Button)rootview.findViewById(R.id.buttonPlay);
        buttonStopPlay= (Button)rootview.findViewById(R.id.buttonStopPlay);

        buttonPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                getActivity().startService(new Intent(getActivity(),StreamService.class));
                buttonPlay.setEnabled(false);
            }
        });

        buttonStopPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                getActivity().stopService(new Intent(getActivity(),StreamService.class));
                buttonPlay.setEnabled(true);
            }
        });
        return rootview;
    }

    public void getPrefs() {
        isPlaying = prefs.getBoolean("isPlaying", false);
        if (isPlaying) buttonPlay.setEnabled(false);
    }









}
