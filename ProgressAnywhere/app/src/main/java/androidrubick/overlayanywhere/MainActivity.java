package androidrubick.overlayanywhere;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidrubick.progressanywhere.R;

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
        AROverlayBuilder.from(MainActivity.this)
                .style(R.style.AppContentLoading)
                .bindBoundView(v)
                .build().show();
    }

}
