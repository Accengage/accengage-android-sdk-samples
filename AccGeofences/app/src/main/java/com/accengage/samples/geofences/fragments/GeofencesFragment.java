package com.accengage.samples.geofences.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.cursoradapter.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.accengage.samples.geofences.R;
import com.accengage.samples.geofences.Utils;
import com.accengage.samples.geofences.activities.GeofencesDetailsActivity;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.contract.A4SContract;

import java.util.ArrayList;

public class GeofencesFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final String[] PROJECTION = {
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
        initialiseClick(lv);
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        String sortingField = Utils.getGeofenceSortingField(getActivity());
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
        mSortingField = Utils.getGeofenceSortingField(getActivity());
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

    private class GeofenceViewAdapter extends CursorAdapter {

        private GeofenceViewAdapter(Context context) {
            super(context, null, false);

        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            return inflater.inflate(R.layout.geofence_item, null);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView tv = (TextView) view.findViewById(R.id.tv_geofence_id_number);
            tv.setText(cursor.getString(0));

            tv = (TextView) view.findViewById(R.id.tv_geofence_server_id_name);
            tv.setText(cursor.getString(1));

            tv = (TextView) view.findViewById(R.id.tv_geofence_extrernal_id);
            tv.setText(cursor.getString(2));

            tv = (TextView) view.findViewById(R.id.tv_geofence_name_value);
            tv.setText(cursor.getString(3));
        }

        @Override
        public Object getItem(int position) {
            return super.getItem(position);
        }
    }

    public void initialiseClick(ListView lv) {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);

                ArrayList<String> cursorToSend = new ArrayList<>();
                cursor.moveToPosition(position);
                System.out.println(cursor.getColumnCount());
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    cursorToSend.add(cursor.getString(i));

                }

                Intent intent = new Intent(getContext(), GeofencesDetailsActivity.class);
                intent.putStringArrayListExtra("cursor", cursorToSend);
                startActivity(intent);
            }
        });
    }
}
