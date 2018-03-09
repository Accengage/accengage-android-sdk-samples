package com.accengage.samples.events

import com.google.gson.annotations.SerializedName

class MyEvent {
    @SerializedName("time_elapsed") private val mInt = 2906
    @SerializedName("watch_count") private val mInt2 = 777
    @SerializedName("video_name") private val mString1 = "Alberto"
    @SerializedName("from") private val mString2 = "Google"
    @SerializedName("subscribed") private val mBool = true
    @SerializedName("date") private val mDate: Long = 1520523280
}
