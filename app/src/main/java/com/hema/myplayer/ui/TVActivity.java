package com.hema.myplayer.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.hema.myplayer.R;
import com.hema.myplayer.ui.fragment.OnlineFragment;

public class TVActivity extends ActionBarActivity{
    private ImageView back;
    private TextView title;
	private OnlineFragment online = new OnlineFragment();
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_tv);

	        if (savedInstanceState == null) {
	            getSupportFragmentManager().beginTransaction()
	                    .add(R.id.tv_fl, online)
	                    .commit();
	        }
         back = (ImageView) findViewById(R.id.back);
         title = (TextView) findViewById(R.id.title);
         title.setText("网络电视");
         back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});

	    }
	    @Override
	    public void onBackPressed() {
			if (online.onBackPressed())
				return;
			else
				super.onBackPressed();
	    }
}
