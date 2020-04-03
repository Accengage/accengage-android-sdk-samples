package com.accengage.samples.beacons;

import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.LinkedHashMap;
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
    public void onBindViewHolder(BeaconAdapter.ViewHolder holder, int position) {
        holder.getCell().bind(getItem(position));
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
