package androidrubick.progressanywhere;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv).setOnClickListener(this);
        findViewById(R.id.btn).setOnClickListener(this);
        findViewById(R.id.iv).setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("yytest", "onPause");
    }

    @Override
    public void onClick(View v) {
        ARProgressBuilder.from(MainActivity.this)
                .style(R.style.AppContentLoading)
                .bindBoundView(v)
                .cancelOnTouchOutside(true)
                .build().show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean res = super.dispatchTouchEvent(ev);
        Log.e("yytest", "" + res);
        return res;
    }
}
