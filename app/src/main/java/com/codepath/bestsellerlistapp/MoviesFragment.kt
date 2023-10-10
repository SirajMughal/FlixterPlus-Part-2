package com.codepath.bestsellerlistapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONException

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

/*
 * The class for the only fragment in the app, which contains the progress bar,
 * recyclerView, and performs the network calls to the NY Times API.
 */
class MoviesFragment : Fragment(), OnListFragmentInteractionListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
     */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api-key"] = API_KEY


        // Using the client, perform the HTTP request
        client[
            "https://api.themoviedb.org/3/tv/top_rated?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed",
            params,
            object : JsonHttpResponseHandler()
        {
            override fun onSuccess(
                statusCode: Int,
                headers: Headers,
                json: JsonHttpResponseHandler.JSON
            ) {
                // The wait for a response is over
                progressBar.hide()

                try {
                    val resultsArray: JSONArray = json.jsonObject.getJSONArray("results")
                    val gson = Gson()
                    val arrayMovieType = object : TypeToken<List<EachMovie>>() {}.type
                    val movies: List<EachMovie> = gson.fromJson(resultsArray.toString(), arrayMovieType)
                    recyclerView.adapter = MoviesRecyclerViewAdapter(movies, this@MoviesFragment)
                    Log.d("YourFragment", "Response successful")
                } catch (e: JSONException) {
                    Log.e("YourFragment", "Error parsing JSON: ${e.message}")
                }
            }


            /*
             * The onFailure function gets called when
             * HTTP response status is "4XX" (eg. 401, 403, 404)
             */
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                t: Throwable?
            ) {
                // The wait for a response is over
                progressBar.hide()

                // If the error is not null, log it!
                t?.message?.let {
                    Log.e("MovieFragment", errorResponse)
                }
            }
        }]

    }

    /*
     * What happens when a particular book is clicked.
     */
    override fun onItemClick(item: EachMovie) {
        // Create an intent to launch MovieDetailsActivity
        val intent = Intent(context, MovieDetailsActivity::class.java).apply {
            putExtra("title", item.title)
            putExtra("backdrop_path", item.backdrop_path)
            putExtra("overview", item.overview)
            putExtra("airDate", item.airDate) // Add this line to pass release date
        }
        context?.startActivity(intent)
    }

}
