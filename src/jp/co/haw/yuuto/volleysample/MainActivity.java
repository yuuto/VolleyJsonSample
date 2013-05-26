package jp.co.haw.yuuto.volleysample;

import jp.co.haw.yuuto.volleysample.http.JsonApi;
import jp.co.haw.yuuto.volleysample.http.JsonPojoRequest;
import jp.co.haw.yuuto.volleysample.model.Prefectures;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity {

	private RequestQueue queue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		JsonPojoRequest<Prefectures> request = JsonApi
				.createRequest(new Listener<Prefectures>() {
					@Override
					public void onResponse(Prefectures prefectures) {
						TextView textView = (TextView)MainActivity.this.findViewById(R.id.main_text);
						
						StringBuilder builder = new StringBuilder();
						for(String prefecture : prefectures.getResponse().getPrefecture()) {
							builder.append(prefecture).append(", ");
						}
						textView.setText(builder.toString());
					}
				});

		queue = Volley.newRequestQueue(getApplicationContext());
		queue.add(request);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
