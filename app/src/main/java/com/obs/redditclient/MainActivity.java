package com.obs.redditclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.activeandroid.Model;
import com.keiferstone.nonet.Monitor;
import com.keiferstone.nonet.NoNet;
import com.obs.redditclient.adapters.ItemAdapter;
import com.obs.redditclient.bbdd.ItemsDBHandler;
import com.obs.redditclient.components.OnItemClickListener;
import com.obs.redditclient.connections.BRoute;
import com.obs.redditclient.delegates.BDelegate;
import com.obs.redditclient.models.Bresponse;
import com.obs.redditclient.models.Item;
import com.obs.redditclient.parsers.ItemParser;
import com.obs.redditclient.utils.Key;
import com.obs.redditclient.utils.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements BDelegate {

    private Context context;
    private List<Item> elements;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private ItemAdapter mAdapter;
    private OnItemClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        elements = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycled_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        listener = new OnItemClickListener() {
            @Override
            public void onClickItem(Model item) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(Key.ID_ITEM, item.getId());
                startActivity(intent);
            }
        };

        mAdapter = new ItemAdapter(context, elements, listener);
        mRecyclerView.setAdapter(mAdapter);

        NoNet.check(this)
                .callback(new Monitor.Callback() {
                    @Override
                    public void onConnectionEvent(int connectionStatus) {
                        switch (connectionStatus){
                            case 100:
                                loadFromServer();
                                break;
                            case 101:
                                Toast.makeText(context, getString(R.string.sin_conexion), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .start();

        loadLocale(false);
    }

    private void loadFromServer() {
        try {
            BRoute.callFromServer(this);

        } catch (Exception e) {
            Log.e("loadFromServer e: " + e.toString());
        }
    }

    private void loadLocale(boolean reload) {
        elements = ItemsDBHandler.getInstance().getAll();

        if(elements.size()>0) {
            mAdapter = new ItemAdapter(context, elements, listener);
            mRecyclerView.setAdapter(mAdapter);
        }

        if(reload) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void result(Bresponse result) {
        if(result.getOperation().equals(Key.LISTING)){
            if(result.getErrorCode()>=0){
                ItemParser.process(result);
                loadLocale(true);
            }
        }
    }
}
