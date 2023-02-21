package com.phunware.analytics.sample

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import com.phunware.analytics.sdk.Analytics
import androidx.core.app.NavUtils
import java.util.HashMap

internal class SecondActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_second)
        Analytics.sendEvent("SECOND_ACTIVITY_LOADED")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this)
                val eventParams: MutableMap<String, String> = HashMap()
                eventParams["param1"] = "value1"
                Analytics.sendEvent("NAVIGATED_HOME", eventParams)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}