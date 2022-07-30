package com.example.androidmusicbrainz.resultPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmusicbrainz.R;
import com.example.androidmusicbrainz.model.Result;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder>{
    private List<Result> list;
    private ResultAdapter.RecyclerViewClickListener clickListener;

    public ResultAdapter(List<Result> list, ResultAdapter.RecyclerViewClickListener clickListener) {
        this.list = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.result_item, parent, false);

        ResultAdapter.ViewHolder viewHolder = new ResultAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResultAdapter.ViewHolder holder, int position) {
        Result result = this.list.get(position);

        TextView text = holder.text;
        text.setText(result.getText());

        TextView subtext = holder.subtext;
        subtext.setText(result.getSubtext());
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text, subtext;
        CardView layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.text = itemView.findViewById(R.id.text);
            this.subtext = itemView.findViewById(R.id.subtext);

            this.layout = itemView.findViewById(R.id.layout);
            this.layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.recyclerViewListClicked(view, this.getLayoutPosition());
        }
    }

    public interface RecyclerViewClickListener {
        void recyclerViewListClicked(View v, int position);
    }
}
