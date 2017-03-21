package com.obs.redditclient.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.obs.redditclient.R;
import com.obs.redditclient.components.OnItemClickListener;
import com.obs.redditclient.models.Item;
import com.obs.redditclient.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by pedrocanovas on 10/7/16.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> elements;
    private Context contexto;
    private final OnItemClickListener listener;


    public ItemAdapter(Context context, List<Item> elements, OnItemClickListener listener) {
        this.elements = elements;
        this.contexto = context;
        this.listener = listener;
    }

    // Create new views
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final int pos = position;


        Item item = elements.get(position);
        viewHolder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout contenido;

        public TextView tvTitulo, tvDesc, tvFecha, tvName;
        public ImageView imagen;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);



            contenido = (LinearLayout) itemView.findViewById(R.id.header);

            tvName = (TextView) itemLayoutView.findViewById(R.id.nombre);
            tvTitulo = (TextView) itemLayoutView.findViewById(R.id.tv_titulo);
            tvDesc = (TextView) itemLayoutView.findViewById(R.id.tv_desc);
            tvFecha = (TextView) itemLayoutView.findViewById(R.id.tv_tiempo);

            imagen = (ImageView) itemLayoutView.findViewById(R.id.profile_image);
        }

        public void bind(final Item item, final OnItemClickListener listener) {

            contenido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickItem(elements.get(getAdapterPosition()));
                }
            });

            tvName.setText(item.display_name);
            tvTitulo.setText(item.header_title);
            tvDesc.setText(item.public_description);
            tvFecha.setText(Utils.getDate(item.created));
            
            Glide
                    .with(contexto)
                    .load(item.icon_img)
                    .centerCrop()
                    .placeholder(R.drawable.ic_photo_camera_black)
                    .crossFade()
                    .into(imagen);

        }

    }

    public void setElements(ArrayList<Item> m) {
        elements = m;
        notifyDataSetChanged();
    }
}