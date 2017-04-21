package asim.net.booklisting;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>>{

    @BindView(R.id.list)
    ListView listView;
    ListAdapter adapter;

    @BindView(R.id.noBookFound)
    TextView noBookFoundView;

    @BindView(R.id.noBookFoundFrame)
    FrameLayout noBookFoundFrame;

    @BindView(R.id.searchBox)
    EditText searchBox;

    @BindView(R.id.loading_indicator) View
            loadingIndicator;


    /** URL for earthquake data from the USGS dataset */
    private static String BOOK_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=";


    @OnClick(R.id.searchButton)
    public void submit(){
        noBookFoundFrame.setVisibility(View.GONE);



        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.

            loadingIndicator.setVisibility(View.VISIBLE);

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
          //  loaderManager.initLoader(1, null, this);

            if (getLoaderManager().getLoader(1) !=null){
                getLoaderManager().restartLoader(1,null,this);

            }else{
                getLoaderManager().initLoader(1,null,this);

            }

        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            loadingIndicator.setVisibility(View.GONE);

            noBookFoundFrame.setVisibility(View.VISIBLE);
            // Update empty state with no connection error message
            noBookFoundView.setText(R.string.no_internet);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new ListAdapter(this, new ArrayList<Book>());
        listView.setAdapter(adapter);
        listView.setEmptyView(noBookFoundFrame);


        if (savedInstanceState != null) {
            // If it exists, init with null arguments (since they won't
            // be used) to reconnect the callbacks
            if (getLoaderManager().getLoader(1) != null) {
                getLoaderManager().initLoader(1, null, this);
            }
        }
        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected book.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Book currentBook = adapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentBook.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });


    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        String searchWord = searchBox.getText().toString();

        Log.i("API", "onCreateLoader: "+ BOOK_REQUEST_URL+searchWord );
        return new BookLoader(this, BOOK_REQUEST_URL+searchWord);

    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // Hide loading indicator because the data has been loaded
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No earthquakes found."
        noBookFoundView.setText(R.string.no_book_found);

        // Clear the adapter of previous earthquake data
        adapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            adapter.addAll(books);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        // Loader reset, so we can clear out our existing data.
        adapter.clear();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }


}
