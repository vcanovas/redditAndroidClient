package com.obs.redditclient.connections;

import android.content.Context;

import com.obs.redditclient.task.BTask;
import com.obs.redditclient.utils.Key;

public class BRoute {

	public static void callFromServer(Context context) {

		// no params.
		BTask tr = new BTask(context, Key.accion_listing, Key.LISTING);
		tr.execute();

	}
}
