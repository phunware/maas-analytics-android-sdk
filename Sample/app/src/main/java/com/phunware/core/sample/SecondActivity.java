package com.phunware.core.sample;

import android.app.Activity;
import android.os.Bundle;
import androidx.core.app.NavUtils;
import android.view.MenuItem;
import com.phunware.analytics.sdk.Analytics;
import java.util.HashMap;
import java.util.Map;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Analytics.sendEvent("SECOND_ACTIVITY_LOADED");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                Map<String, String> eventParams = new HashMap<>();
                eventParams.put("param1", "value1");
                Analytics.sendEvent("NAVIGATED_HOME", eventParams);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}