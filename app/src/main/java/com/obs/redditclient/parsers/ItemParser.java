package com.obs.redditclient.parsers;

import com.obs.redditclient.bbdd.ItemsDBHandler;
import com.obs.redditclient.models.Bresponse;
import com.obs.redditclient.models.Item;
import com.obs.redditclient.utils.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class ItemParser {

    public static void process(Bresponse result) {
        try {
            JSONObject jresult = new JSONObject(result.getResult().toString());
            String sresult = jresult.getString("result");
            JSONObject jsonObject = new JSONObject(sresult);
            JSONObject jsonData = jsonObject.getJSONObject("data");

            JSONArray jsonArray = jsonData.getJSONArray("children");
            if(jsonArray.length()>0){
                ItemsDBHandler.getInstance().deleteOlders();
            }

            for(int i=0;i<jsonArray.length();i++){
                Item element = new Item();
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                JSONObject jelement = jsonObject1.getJSONObject("data");

                element.idItem = jelement.getString("id");
                element.banner_img = jelement.getString("banner_img");

                String display_name = jelement.getString("display_name");
                if(display_name ==null){ display_name="-"; }
                element.display_name = display_name;

                String header_title = jelement.getString("header_title");
                if(header_title.equals("null")){ header_title="-"; }
                element.header_title = header_title;

                element.header_img = jelement.getString("header_img");
                element.description = jelement.getString("description_html");
                element.display_name_prefixed = jelement.getString("display_name_prefixed");
                element.public_description = jelement.getString("public_description");
                element.submit_text = jelement.getString("submit_text");
                element.icon_img = jelement.getString("icon_img");
                element.created = jelement.getLong("created")*1000;

                ItemsDBHandler.getInstance().deleteBefore(element);
                element.save();
            }
        } catch (Exception e) {
            Log.e("E - process item parser: " + e.toString());
        }
    }
}
