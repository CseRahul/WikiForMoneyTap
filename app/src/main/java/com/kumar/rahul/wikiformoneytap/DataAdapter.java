package com.kumar.rahul.wikiformoneytap;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kumar.rahul.wikiformoneytap.pojo.WikiResult;

import java.util.ArrayList;
/**
 * @author Rahul Kumar on 08.07.2018.
 * @version 1.0
 *
 **/
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> implements Filterable {
    private ArrayList<WikiResult> mArrayList;
    private ArrayList<WikiResult> mFilteredList;
   private ItemClickListener mClickListener;

    public DataAdapter(ArrayList<WikiResult> arrayList) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wiki_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.tv_title.setText(mFilteredList.get(i).getTitle());
        viewHolder.tv_desc.setText(mFilteredList.get(i).getDescription());
        viewHolder.tv_pageid.setText(mFilteredList.get(i).getPageid());
        if (mFilteredList.get(i).getThumbnail()!=null){
            Uri uri = Uri.parse(""+mFilteredList.get(i).getThumbnail());
            viewHolder.draweeView.setImageURI(uri);
        }
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mArrayList;
                } else {

                    ArrayList<WikiResult> filteredList = new ArrayList<>();

                    for (WikiResult wikiResult : mArrayList) {
                        if(wikiResult.getTitle()!=null){
                            if(wikiResult.getTitle().toLowerCase().contains(charString)){
                                filteredList.add(wikiResult);
                            }
                        }
                        if(wikiResult.getDescription()!=null){
                            if(wikiResult.getDescription().toLowerCase().contains(charString)){
                                filteredList.add(wikiResult);
                            }
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<WikiResult>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tv_title,tv_desc,tv_pageid;
        private CardView cardView;
        private SimpleDraweeView draweeView;



        public ViewHolder(final View view) {
            super(view);
            cardView=(CardView) view.findViewById(R.id.cv_item);
            tv_title = (TextView)view.findViewById(R.id.tv_title);
            tv_desc = (TextView)view.findViewById(R.id.tv_desc);
            tv_pageid = (TextView)view.findViewById(R.id.tv_pageid);
            draweeView =(SimpleDraweeView) view.findViewById(R.id.ivDrawee);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("clicks ","clicked--"+ tv_title.getText().toString());
                    Intent intent=new Intent(view.getContext(),DetailedActivity.class);
                    intent.putExtra("title",tv_title.getText().toString());
                    intent.putExtra("pageid",tv_pageid.getText().toString());
                    view.getContext().startActivity(intent);
                }
            });
        }


        @Override
        public void onClick(View v) {

            Log.d("clicks ","clicked--");
            /*if (mClickListener != null)
                mClickListener.onItemClick(v, getAdapterPosition());*/
        }
    }
    String getItem(int id) {
        return mFilteredList.get(id).getTitle();
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}