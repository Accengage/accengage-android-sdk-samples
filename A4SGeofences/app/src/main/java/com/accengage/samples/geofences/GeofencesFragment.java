package com.accengage.samples.geofences;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.contract.A4SContract;

public class GeofencesFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String[] PROJECTION = {
            A4SContract.Geofences._ID,
            A4SContract.Geofences.SERVER_ID,
            A4SContract.Geofences.EXTERNAL_ID,
            A4SContract.Geofences.NAME,
            A4SContract.Geofences.LATITUDE,
            A4SContract.Geofences.LONGITUDE,
            A4SContract.Geofences.RADIUS,
            A4SContract.Geofences.DETECTED_TIME,
            A4SContract.Geofences.NOTIFIED_TIME,
            A4SContract.Geofences.DETECTED_COUNT,
            A4SContract.Geofences.DEVICE_LATITUDE,
            A4SContract.Geofences.DEVICE_LONGITUDE,
            A4SContract.Geofences.DISTANCE
    };

    // Identifies a particular Loader being used in this component
    private static final int URL_LOADER = 0;

    private GeofenceViewAdapter mAdapter;
    private String mSortingField = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new GeofenceViewAdapter(getActivity());

        // Initializes the CursorLoader
        getLoaderManager().initLoader(URL_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View layout = inflater.inflate(R.layout.fragment_geofences, container, false);
        ListView lv = (ListView) layout.findViewById(R.id.lv_geofences);
        lv.setAdapter(mAdapter);
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        String sortingField = getGeofenceSortingField();
        if (!mSortingField.equals(sortingField)) {
            getLoaderManager().restartLoader(URL_LOADER, null, this);
        }
    }

    @Override
    public void onDetach() {
        // Destroys variables and references, and catches Exceptions
        try {
            getLoaderManager().destroyLoader(URL_LOADER);
            if (mAdapter != null) {
                mAdapter.changeCursor(null);
                mAdapter = null;
            }
        } catch (Throwable localThrowable) {
        }
        super.onDetach();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        mSortingField = getGeofenceSortingField();
        Log.debug("GeofencesFragment|onCreateLoader sorting by " + mSortingField);
        switch (loaderId) {
            case URL_LOADER:
                return new CursorLoader(getActivity(),
                        A4SContract.Geofences.getContentUri(getContext()), PROJECTION,
                        null, null, mSortingField);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        /*
         *  Changes the adapter's Cursor to be the results of the load. This forces the View to
         *  redraw.
         */

        mAdapter.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Sets the Adapter's backing data to null. This prevents memory leaks.
        mAdapter.changeCursor(null);
    }

    private String getGeofenceSortingField() {
        SharedPreferences preferences = getActivity().getSharedPreferences(
                DBPreferencesActivity.DBVIEW_PREFERENCES_FILE_NAME,
                Context.MODE_PRIVATE);
        String keyword = preferences.getString(getResources().getString(R.string.geofence_sorting_keyword), "");
        boolean descOrder = preferences.getBoolean(getResources().getString(R.string.geofence_sorting_order), false);
        if (descOrder) keyword += " DESC";
        return keyword;
    }

    private class GeofenceViewAdapter extends CursorAdapter {

        public GeofenceViewAdapter(Context context) {
            super(context, null, false);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            return inflater.inflate(R.layout.geofence_item, null);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView tv = (TextView) view.findViewById(R.id.tv_geofence_id);
            tv.setText(cursor.getString(0));

            tv = (TextView) view.findViewById(R.id.tv_geofence_server_id);
            tv.setText(cursor.getString(1));

            tv = (TextView) view.findViewById(R.id.tv_geofence_extrernal_id);
            tv.setText(cursor.getString(2));

            tv = (TextView) view.findViewById(R.id.tv_geofence_name);
            tv.setText(cursor.getString(3));

            tv = (TextView) view.findViewById(R.id.tv_geofence_latitude);
            tv.setText(cursor.getString(4));

            tv = (TextView) view.findViewById(R.id.tv_geofence_longitude);
            tv.setText(cursor.getString(5));

            tv = (TextView) view.findViewById(R.id.tv_geofence_radius);
            tv.setText(cursor.getString(6));

            tv = (TextView) view.findViewById(R.id.tv_geofence_detected_time);
            tv.setText(cursor.getString(7));

            tv = (TextView) view.findViewById(R.id.tv_geofence_notified_time);
            tv.setText(cursor.getString(8));

            tv = (TextView) view.findViewById(R.id.tv_geofence_detected_count);
            tv.setText(String.valueOf(cursor.getInt(9)));

            Double distance  = cursor.getDouble(12);
            tv = (TextView) view.findViewById(R.id.tv_geofence_distance);
            tv.setText(String.format("%.02f", distance));
        }
    }
}
