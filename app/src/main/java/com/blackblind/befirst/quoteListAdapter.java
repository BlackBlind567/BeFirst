package com.blackblind.befirst;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class quoteListAdapter extends RecyclerView.Adapter<quoteListAdapter.ViewHolder> {


    private List<quote> quoteList;
    quoteListAdapter(List<quote> quoteList){

        this.quoteList = quoteList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.quoteText.setText(quoteList.get(position).getQuote());
        holder.authorText.setText(quoteList.get(position).getAuthor());

    }

    @Override
    public int getItemCount() {
        return quoteList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        View mView;

        TextView quoteText;
        TextView authorText;



        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            quoteText = mView.findViewById(R.id.tv_quote);
            authorText = mView.findViewById(R.id.tv_author);



        }
    }
}