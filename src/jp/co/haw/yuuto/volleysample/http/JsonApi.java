package jp.co.haw.yuuto.volleysample.http;

import jp.co.haw.yuuto.volleysample.model.Prefectures;
import jp.co.haw.yuuto.volleysample.model.JsonRootContent;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

public class JsonApi {

	public enum Api {
		PREFECTURES("http://express.heartrails.com/api/json?method=getPrefectures", Prefectures.class),
		OTHER(null,null),
		;
		
		private String uri;
		private Class<? extends JsonRootContent> clazz;
		private Api(String uri, Class<? extends JsonRootContent> clazz) {
			this.uri = uri;
			this.clazz = clazz;
		}
		
		public static Api valueOf(Class<? extends JsonRootContent> clazz) {
			Api[] values = Api.values();
			for (Api api : values) {
				if (api.clazz == clazz) {
					return api;
				}
			}
			
			return OTHER;
		}
	}
	
	public static <T extends JsonRootContent> JsonPojoRequest<T> createRequest(Listener<T> listener, T... content) {
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) content.getClass().getComponentType();
		Api api = Api.valueOf(clazz);
		JsonPojoRequest<T> request = new JsonPojoRequest<T>(Method.GET, api.uri, null,
				listener, 
				new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						// Ç±Ç±Ç≈ã§í ÉGÉâÅ[èàóù
					}
				}, clazz);
		return request;
	}
}
