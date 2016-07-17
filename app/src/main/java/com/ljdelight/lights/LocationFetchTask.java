package com.ljdelight.lights;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ljdelight.lights.db.LightsDBHelper;
import com.ljdelight.lights.generated.Center;
import com.ljdelight.lights.generated.Lights;
import com.ljdelight.lights.generated.TaggedLocationWithMeta;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljdelight on 4/12/16.
 */
public class LocationFetchTask extends AsyncTask<Center, Void, List<TaggedLocationWithMeta>> {
    private final String TAG = getClass().getSimpleName();
    private LocationFetchTask.NotifyOnTaskCompleteCommand mCommand;
    private Context mContext;

    interface Listener {
        void onFetchFinished(List<TaggedLocationWithMeta> locations);
    }
    public LocationFetchTask(Context context, LocationFetchTask.Listener listener) {
        mCommand = new NotifyOnTaskCompleteCommand(listener);
        mContext = context;
    }

    @Override
    protected void onPostExecute(List<TaggedLocationWithMeta> locations) {
        mCommand.mLocations = locations;
        mCommand.postExec();
    }

    public static class NotifyOnTaskCompleteCommand implements Command {
        LocationFetchTask.Listener mListener;
        List<TaggedLocationWithMeta> mLocations;

        public NotifyOnTaskCompleteCommand(LocationFetchTask.Listener listener) {
            mListener = listener;
        }

        @Override
        public void postExec() {
            mListener.onFetchFinished(mLocations);
        }
    }

    @Override
    protected List<TaggedLocationWithMeta> doInBackground(Center... params) {
        Log.d(TAG, "In LocationFetchTask.doInBackground");
        List<TaggedLocationWithMeta> locations = null;
        String host = "lights.ljdelight.com";
        int port = 12345;
        try {
            LightsDBHelper helper = new LightsDBHelper(mContext);

            if (!helper.hasValidSQLCache()) {
                Log.d(TAG, "The cache was not valid. Fetching data from the remote server");
                TTransport transport = new TFramedTransport(new TSocket(host, port));
                transport.open();

                TProtocol protocol = new TBinaryProtocol(transport);
                Lights.Client client = new Lights.Client(protocol);

                Log.d(TAG, "Calling getLocationsWithMetaNear");
                locations = client.getLocationsWithMetaNear(params[0]);
                transport.close();
                Log.d(TAG, "Finished and closed getLocationsWithMetaNear");

                // helper.insertAll(locations);
            } else {
                Log.d(TAG, "The cache is valid and loading those locations");
                locations = helper.getLocationsWithMeta();
            }

            Log.d(TAG, "Found " + locations.size() + " near " + params[0].toString());
        } catch (TTransportException x) {
            Log.e(TAG, "TTransportException: COULD NOT GET LOCATIONS");
        } catch (TException x) {
            Log.e(TAG, "TException: COULD NOT GET LOCATIONS");
        } finally {
            if (locations == null) {
                // Return empty list instead of null.
                return new ArrayList<>();
            }
        }
        return locations;
    }
}
