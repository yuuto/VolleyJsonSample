package jp.co.haw.yuuto.volleysample.http;

import java.io.UnsupportedEncodingException;

import jp.co.haw.yuuto.volleysample.model.JsonRootContent;
import net.arnx.jsonic.JSON;

import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

public class JsonPojoRequest<T extends JsonRootContent> extends JsonRequest<T> {
	private Class<T> clazz;
	
	public JsonPojoRequest(int method, String url, JSONObject jsonRequest,
			Listener<T> listener, ErrorListener errorListener, Class<T> clazz) {
		super(method, url, (jsonRequest == null) ? null : jsonRequest
				.toString(), listener, errorListener);
		this.clazz = clazz;//(Class<T>) content.getClass().getComponentType();
	}

	public JsonPojoRequest(String url, JSONObject jsonRequest,
			Listener<T> listener, ErrorListener errorListener, Class<T> clazz) {
		this(jsonRequest == null ? Method.GET : Method.POST, url, jsonRequest,
				listener, errorListener, clazz);
	}
	
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			// FIXME なんかしらんけどcontent-type が帰ってこないんでとりあえず明示的にUTF-8指定。
//			String jsonString = new String(response.data,
//					HttpHeaderParser.parseCharset(response.headers));
			String jsonString = new String(response.data, HTTP.UTF_8);
			T content = JSON.decode(jsonString, clazz);
			return Response.success(content,
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		}
	}
}
