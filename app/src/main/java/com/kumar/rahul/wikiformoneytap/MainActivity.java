package com.kumar.rahul.wikiformoneytap;

import android.os.Bundle;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.GsonBuilder;
import com.kumar.rahul.wikiformoneytap.pojo.Example;
import com.kumar.rahul.wikiformoneytap.pojo.Page;
import com.kumar.rahul.wikiformoneytap.pojo.WikiResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Rahul Kumar on 08.07.2018.
 * @version 1.0
 *
 **/

public class MainActivity extends AppCompatActivity {

  //  public static final String BASE_URL = "https://api.learn2crack.com";
    public static final String BASE_URL = "https://en.wikipedia.org/";
    private RecyclerView mRecyclerView;
    private ArrayList<WikiResult> mArrayList;
    private DataAdapter mAdapter;
    String searchQuery="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Fresco.initialize(this);

        initViews();

        Bundle extras = getIntent().getExtras();
        if(extras != null ) {
            searchQuery= extras.getString("search_item");
            if(searchQuery.isEmpty() || searchQuery.equals("") || searchQuery.length()==0){
                searchQuery="";
                Toast.makeText(this,"Nothing to search!!",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Searching Please wait!-", Toast.LENGTH_LONG).show();
                //loadJSON(searchQuery);
                loadData(searchQuery);
            }
        } else {
            searchQuery="";
            Toast.makeText(this,"Nothing to search!!",Toast.LENGTH_LONG).show();
        }

    }

    private void initViews(){
        mRecyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void loadData(String queryToBeSearched){
       // String JSON_URL = "https://simplifiedcoding.net/demos/view-flipper/heroes.php";
        String JSON_URL = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=pageimages%7Cpageterms&generator=prefixsearch&formatversion=2&wbptterms=description&gpssearch="+queryToBeSearched;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                 new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            Log.d("responseVolley",""+obj.toString());
                            JSONObject objQuery = obj.getJSONObject("query");
                            Log.d("responseVolleyobj",""+objQuery.toString());
                            JSONArray pagesArray=objQuery.getJSONArray("pages");
                            Log.d("responseVolleyArr",""+pagesArray.toString());

                            List<WikiResult> wikiResultList=new ArrayList<>();
                            for (int i = 0; i < pagesArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject pageObject = pagesArray.getJSONObject(i);
                                String title=null,thumbnail=null,description=null,pageid=null;
                                if (pageObject.has("title"))
                                    title=(pageObject.getString("title"));
                                if (pageObject.has("thumbnail"))
                                    thumbnail=(pageObject.getJSONObject("thumbnail").getString("source").toString());
                                if(pageObject.has("terms"))
                                description=(pageObject.getJSONObject("terms").getJSONArray("description").get(0).toString());
                                if (pageObject.has("pageid"))
                                    pageid=(pageObject.getString("pageid"));
                                WikiResult wikiResult=new WikiResult(title,thumbnail,description,pageid);
                                wikiResultList.add(wikiResult);
                            }

                            for (WikiResult wikiResult : wikiResultList) {
                                System.out.println(wikiResult.getDescription());
                                Log.d("responseList",
                                        "desc:"+wikiResult.getDescription()+
                                                "--title--"+wikiResult.getTitle()+
                                                "--thumb--"+wikiResult.getThumbnail()+
                                                "--pageid--"+wikiResult.getPageid());
                            }


                            mArrayList = new ArrayList<>(wikiResultList);
                            mAdapter = new DataAdapter(mArrayList);
                            mRecyclerView.setAdapter(mAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
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

        Toast.makeText(getApplicationContext(),"Images May Load little slower. ",Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

     /* private void loadJSON( String qry){                         //Failed
        Log.d("response"," i am called");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONWikiResponse> call=request.getJSON(
                "query",
                "json",
                "pageimages|pageterms",
                "prefixsearch",
                "2",
                "description",
                "Rahul Kumar"
        );

      *//*  call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d("response2","--"+call.request().url().toString());

                Log.d("response",new GsonBuilder().setPrettyPrinting().create().toJson(response));
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("response","Failure"+t.getMessage());
            }
        });*//*
        call.enqueue(new Callback<JSONWikiResponse>() {
            @Override
            public void onResponse(Call<JSONWikiResponse> call, Response<JSONWikiResponse> response) {
                Log.d("response2","--"+call.request().url().toString());

                Log.d("response",new GsonBuilder().setPrettyPrinting().create().toJson(response));
            }

            @Override
            public void onFailure(Call<JSONWikiResponse> call, Throwable t) {
                Log.d("response","Failure"+t.getMessage());
            }
        });
       *//*
       //Call call=request.getJSON();
        Call<JSONResponse> call = request.getJSON();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                JSONResponse jsonResponse = response.body();
                mArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getAndroid()));
                mAdapter = new DataAdapter(mArrayList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });*//*
    }*/


}
