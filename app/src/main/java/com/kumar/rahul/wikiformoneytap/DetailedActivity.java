package com.kumar.rahul.wikiformoneytap;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kumar.rahul.wikiformoneytap.pojo.WikiResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rahul Kumar on 08.07.2018.
 * @version 1.0
 *
 **/


public class DetailedActivity extends AppCompatActivity {
    TextView tvTitleDetail,tvDetail;
    String query,pageid,url,webUrl;
    WebView wvWiki;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        tvTitleDetail=(TextView)findViewById(R.id.tv_titleDetail);
        tvDetail=(TextView) findViewById(R.id.tv_detail);
        wvWiki=(WebView) findViewById(R.id.wv_wiki);

        query= getIntent().getStringExtra("title");
        pageid= getIntent().getStringExtra("pageid");
        Toast.makeText(this,"Loading.."+query,Toast.LENGTH_LONG).show();
        url="https://en.wikipedia.org/w/api.php?action=query&prop=info&pageids="+pageid+"&inprop=url&format=json";
        loadData();
    }




    private void loadData(){
        tvTitleDetail.setText(query);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            Log.d("responseVolley detail",""+obj.toString());

                            webUrl=obj.getJSONObject("query").getJSONObject("pages").getJSONObject(""+pageid).get("fullurl").toString();
                            loadWebView(webUrl);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }
    private void loadWebView(String pageUrl){

        tvDetail.setText(pageUrl);
        Log.d("responseVolley fullurl",""+webUrl);
        wvWiki.setWebViewClient(new WebViewClient());
        wvWiki.loadUrl(""+pageUrl);
        WebSettings webSettings = wvWiki.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            webSettings.setSafeBrowsingEnabled(false);
        }
        //wvWiki.setWebViewClient(new MyWebViewClient());
    }


    private class MyWebViewClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            final Uri uri = Uri.parse(url);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }
}
