package com.example.perceptordeinseguridad;

import androidx.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * A {@link Request} subclass.
 *
 * This modified class is used to get a {@link JSONObject}
 * response through a http POST request.
 */
public class CustomRequest extends Request<JSONObject> {

    /** The listener to convert the request response to a JSONObject. */
    private Listener<JSONObject> listener;

    /** The parameters of the POST request*/
    private Map<String, String> parameters;

    /**
     * Constructor method
     *
     * @param method request method.
     * @param url web service url.
     * @param params request parameters.
     * @param responseListener {@link JSONObject} listener.
     * @param errorListener {@link ErrorListener} listener.
     */
    public CustomRequest(int method, String url, Map<String, String> params,
                         Listener<JSONObject> responseListener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = responseListener;
        this.parameters = params;
    }

    /**
     * Overridden {@link Request#parseNetworkResponse} method to parse the raw network response
     * and return an appropriate response type. The response
     * will not be delivered if it returns null.
     * */
    @Override
    protected Response<JSONObject> parseNetworkResponse(@NonNull NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JSONException e) {
            return Response.error(new ParseError(e));
        }
    }

    /** An overridden  {@link Request#deliverResponse(Object)} */
    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }

    /** Method to get request parameters */
    protected Map<String, String> getParams() {
        return parameters;
    }
}