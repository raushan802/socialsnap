package com.cmsc436.socialsnap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.widget.Toast;


public class DatabaseTask extends AsyncTask<Void, Void, Integer> {


	private Activity mActivity;
	private Location mLocation;
	private String mImgurUrl;
	private String comment;

	public DatabaseTask(String imgurURL, Location location, String comment, Activity activity) {
		this.mActivity = activity;
		this.mLocation = location;
		this.mImgurUrl = imgurURL;
		this.comment = comment;
	}

	@Override
	protected Integer doInBackground(Void... params) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cmsc436socialsnapapp.appspot.com");
		Double latitude = mLocation.getLatitude();
		Double longitude = mLocation.getLongitude();
		int statuscode = -99;
		try{
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("latitude",latitude.toString() ));
			nameValuePairs.add(new BasicNameValuePair("longitude", longitude.toString()));
			nameValuePairs.add(new BasicNameValuePair("image_url", mImgurUrl));
			nameValuePairs.add(new BasicNameValuePair("comment", comment));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			statuscode = response.getStatusLine().getStatusCode();
			

	    } catch (IOException e) {
	       return statuscode;
	    }
		
		return statuscode;
	}

	@Override
	protected void onPostExecute(Integer output) {
		// Display the results of the lookup.
		//
		
		if(output == 200){
			Toast.makeText(mActivity.getApplicationContext(), "Upload Sucessful", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(mActivity.getApplicationContext(), "Upload Failed", Toast.LENGTH_LONG).show();
		}
	}
	
}

