package idv.yurenju.prismflipper;

import idv.yurenju.prismflipper.widget.PrismFlipper;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PrismFlipperActivity extends Activity {
	private int mPosition = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final PrismFlipper flipper = (PrismFlipper)findViewById(R.id.viewFlipper1);
		final String[] texts = new String[] { "Refresh", "go to last read position",
				"last read post" };
        flipper.setFrontText(texts[0]);
        flipper.setBackground(new ColorDrawable(0xff3465a4));
        flipper.setTextColor(Color.WHITE);
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				flipper.showNext(texts[mPosition], false);
				mPosition = (mPosition + 1) % texts.length;
			}
		});
        
        btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				flipper.showNext(texts[mPosition], true);
				mPosition = (mPosition + 1) % texts.length;
			}
		});
    }
}