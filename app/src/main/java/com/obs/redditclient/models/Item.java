package com.obs.redditclient.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


@Table(name = "Item")
public class Item extends Model {

    @Column(name = "IdItem")
    public String idItem;

    @Column(name = "Banner_img")
    public String banner_img;

    @Column(name = "Display_name_prefixed")
    public String display_name_prefixed;

    @Column(name = "Display_name")
    public String display_name;

    @Column(name = "Header_img")
    public String header_img;

    @Column(name = "Submit_text")
    public String submit_text;

    @Column(name = "Title")
    public String title;

    @Column(name = "Icon_img")
    public String icon_img;

    @Column(name = "Header_title")
    public String header_title;

    @Column(name = "Description")
    public String description;

    @Column(name = "Public_description")
    public String public_description;

    @Column(name = "Created")
    public long created;

}