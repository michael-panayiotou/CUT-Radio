package com.cutingedge.cut.radio;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by michael on 02/04/15.
 */
public class menu3_Fragment extends Fragment {
    View rootview;
    WebView webView;
    Button btnGr;
    Button btnEn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.menu3_layout, container, false);
        // get our html content
              // used by WebView
        try {
            initViews();
            setListeners();
            String htmlAsString = getString(R.string.html_gr);
            webView.loadDataWithBaseURL(null, htmlAsString, "text/html", "utf-8", null);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(),
                    e.getClass().getName() + " " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        return rootview;
    }
    public void initViews()
    {
        webView = (WebView) rootview.findViewById(R.id.webView);
        btnGr = (Button) rootview.findViewById(R.id.btnGr);
        btnEn = (Button) rootview.findViewById(R.id.btnEn);
    }
    public void setListeners(){

        btnGr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String htmlAsString = getString(R.string.html_gr);
                webView.loadDataWithBaseURL(null,htmlAsString,"text/html","utf-8", null);
            }
        });

        btnEn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String htmlAsString = getString(R.string.html_en);
                webView.loadDataWithBaseURL(null,htmlAsString,"text/html","utf-8", null);
            }
        });

    }

}
