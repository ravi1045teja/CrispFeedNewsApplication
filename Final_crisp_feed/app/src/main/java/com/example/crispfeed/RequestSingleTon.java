package com.example.crispfeed;

//WORK CITED FROM MOBILE COMPUTING CURRENCY_LAB_FALL2019 FROM BRIGHTSPACE
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestSingleTon {
    private static RequestSingleTon mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private RequestSingleTon(Context context){
        mCtx=context.getApplicationContext();
        mRequestQueue=getRequestQueue();
    }
    public static  synchronized RequestSingleTon getInstance(Context context){
        //check if instance is created for class, if null, then only initialize the class
        if(mInstance==null){
            mInstance=new RequestSingleTon(context.getApplicationContext());

        }
        return  mInstance;

    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue==null){
            //check for request queue, so that instead of causing memory leaks every time , we are creating a single request and will be pushing it to queue
            mRequestQueue= Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req){getRequestQueue().add(req);}
}
