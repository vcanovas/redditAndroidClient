package com.obs.redditclient.bbdd;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.obs.redditclient.models.Item;
import com.obs.redditclient.utils.Log;

import java.util.ArrayList;
import java.util.List;


public class ItemsDBHandler {
    private static ItemsDBHandler dbh;


    public ItemsDBHandler() {
        dbh = this;
    }

    public static ItemsDBHandler getInstance() {
        if (dbh == null) {
            dbh = new ItemsDBHandler();
        }
        return dbh;
    }

    public int getSize(){
        try {
            return new Select().from(Item.class).count();
        }catch (Exception e){
            return 0;
        }
    }

    public List<Item> getAll() {
        try {
            return new Select()
                    .from(Item.class)
                    .execute();
        }catch (Exception e){
            Log.e(e.toString());
            return new ArrayList<>();
        }
    }

    public Item getElementById(long id) {
        return new Select()
                .from(Item.class)
                .where("id = ?", id)
                .executeSingle();
    }

    public List<Item> getElementByIdServidor(String id) {
        return new Select()
                .from(Item.class)
                .where("idItem = ?", id)
                .execute();
    }


    public void deleteBefore(Item item) {
        List<Item> elements = getElementByIdServidor(item.idItem);
        for(int i=0;i<elements.size();i++){
            Item aux = elements.get(i);
            aux.delete();
        }
    }

    public void deleteOlders() {
        new Delete().from(Item.class).execute();
    }
}
