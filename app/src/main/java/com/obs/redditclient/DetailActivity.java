package com.obs.redditclient;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.obs.redditclient.bbdd.ItemsDBHandler;
import com.obs.redditclient.models.Item;
import com.obs.redditclient.utils.Key;

/**
 * Created by pedro on 17/3/17.
 */

public class DetailActivity  extends AppCompatActivity{

    private Context context;
    private Item item;

    private TextView author, title, description;
    private ImageView image;
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        context = DetailActivity.this;

        setTitle(getString(R.string.detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        author = (TextView) findViewById(R.id.user);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        image = (ImageView) findViewById(R.id.image);


        Bundle bundle = getIntent().getExtras();
        long idItem=0;
        try{
            if(bundle.containsKey(Key.ID_ITEM)){
                idItem=bundle.getLong(Key.ID_ITEM);
            }
        }catch (Exception e){
            notifyError();
        }

        item = ItemsDBHandler.getInstance().getElementById(idItem);

        if(item!=null){
            author.setText(item.display_name);
            title.setText(item.header_title);
            String desc = item.description.replace("&lt;", "<");
            desc = desc.replace("&gt;", ">");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                description.setText(Html.fromHtml(desc,Html.FROM_HTML_MODE_LEGACY));
            } else {
                description.setText(Html.fromHtml(desc));
            }

            Glide
                    .with(context)
                    .load(item.banner_img)
                    .centerCrop()
                    .placeholder(R.drawable.ic_photo_camera_black)
                    .crossFade()
                    .into(image);

        }else{
            notifyError();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void notifyError(){
        Toast.makeText(context, getString(R.string.error_item), Toast.LENGTH_LONG).show();
    }

}
