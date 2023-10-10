package com.codepath.bestsellerlistapp

import com.google.gson.annotations.SerializedName

/**
 *
 *
 * SerializedName tags MUST match the JSON response for the
 * object to correctly parse with the gson library.
 */
class EachMovie {
    @JvmField
    @SerializedName("poster_path")
    var path = "https://image.tmdb.org/t/p/w500/"

    @JvmField
    @SerializedName("overview")
    var overview: String? = null

    @JvmField
    @SerializedName("name")
    var title: String? = null

    @JvmField
    @SerializedName("first_air_date")
    var airDate: String? = null

    @SerializedName("backdrop_path")
    var backdrop_path = "https://image.tmdb.org/t/p/w500/"

}