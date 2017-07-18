package com.accengage.samples.beacons;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.accengage.samples.beacons.activities.DetailActivity;
import com.accengage.samples.beacons.activities.MainActivity;
import com.ad4screen.sdk.Log;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


public class BeaconAdapter extends RecyclerView.Adapter<BeaconAdapter.ViewHolder> {

    private LinkedHashMap<String, Beacon> mDataset;

    public BeaconAdapter(LinkedHashMap<String, Beacon> dataset) {
        mDataset = dataset;
    }

    @Override
    public BeaconAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new BeaconView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(final BeaconAdapter.ViewHolder holder, final int position) {
        holder.getCell().bind(getItem(position));
        holder.mCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("beacon", mDataset.get(getItemKey(holder.getAdapterPosition())));
                v.getContext().startActivity(intent);
                Log.warn("" + mDataset.get(getItemKey(holder.getAdapterPosition())).getDate());
            }
        });
    }

    private Beacon getItem(int position) {
        return mDataset.get(getItemKey(position));
    }

    private String getItemKey(int position) {
        Set<String> keys = mDataset.keySet();
        int i = 0;
        for (String key : keys) {
            if (i == position) {
                return key;
            }
            i++;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private BeaconView mCell;

        public ViewHolder(BeaconView v) {
            super(v);
            mCell = v;

        }

        public BeaconView getCell() {
            return mCell;
        }
    }


}
