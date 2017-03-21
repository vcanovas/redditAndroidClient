package com.obs.redditclient.task;

/**
 * 
 * @author Pedro Cánovas
 * @mail wcanovas@gmail.com
 * 
 */

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.obs.redditclient.R;
import com.obs.redditclient.connections.Connector;
import com.obs.redditclient.delegates.BDelegate;
import com.obs.redditclient.models.Bresponse;
import com.obs.redditclient.utils.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BTask extends AsyncTask<Void, Integer, String> {


	public BDelegate delegate = null;

	private Dialog dialog;
	private Context context = null;
	private String action;
	private String operation;

	public BTask(Context p, String action, String operation) {
		super();
		delegate = (BDelegate) p;
		context = p;

		this.operation = operation;
		this.action = action;
	}

	@Override
	protected void onPreExecute() {
		try {
			dialog = ProgressDialog.show(context, context.getString(R.string.app_name), context.getString(R.string.procesando), true, true);
			dialog.show();
		} catch (Exception e) {
			Log.e("Error." + e.toString());
		}
	}

	@Override
	protected String doInBackground(Void... params) {
		return Connector.call_get(action);
	}

	@Override
	protected void onPostExecute(String result) {
		try {
			dialog.dismiss();
		}catch (Exception e){}


		delegate.result(processResult(result));
	}

	public Bresponse processResult(String c) {
		boolean error = true;

		Map<String, Object> map = new HashMap<String, Object>();
		Bresponse bresponse = new Bresponse();
		bresponse.setOperation(operation);

		if (c != null) {
			try {
				JSONObject obj = new JSONObject(c);
				map.put("result", obj);
				bresponse.setResult(map);
				error=false;
			} catch (JSONException e) {
				Log.e("Error processResult: " + e.toString());
			}
		}

		if (error) {
			bresponse.setErrorCode(-1);
			bresponse.setErrorMessage("Error exception app");
		}
		return bresponse;
	}
}
