package androidrubick.progressanywhere;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ARProgressBuilder.from(MainActivity.this)
                        .bindBoundView(findViewById(R.id.tv))
                        .build().show();
            }
        }, 1000L);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("yytest", "onPause");
    }
}
