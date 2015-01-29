package androidrubick.overlayanywhere;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
        final AROverlay overlay = AROverlayBuilder.from(MainActivity.this)
                .style(R.style.AppContentLoading)
                .view(R.layout.test)
                .animationStyle(android.R.style.Animation_InputMethod)
                .bindBoundView(v)
                .build();

        Toast.makeText(this, "" + overlay.findViewById(R.id.tv), Toast.LENGTH_SHORT).show();;

        overlay.show();
    }

}
