package asim.net.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by asimaltwijry on 4/19/17.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    private String mUrl;
    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public List<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        Log.i("API", "loadInBackground: "+mUrl);
        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Book> books = API.fetchBookData(mUrl);
        return books;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }



}
