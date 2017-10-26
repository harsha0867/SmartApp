package ratpack.example.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public class SmartAppGetHandler implements Handler {

	@Override
	public void handle(Context ctx) throws Exception {
		HttpClient httpclient = null;
		String responseString = "Did not work!";
		try {
			httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(Constants.smartAppEndpointURL + Constants.getSwitchesPath);
			httpGet.setHeader("Authorization", Constants.authorizationKey);
			HttpResponse response = httpclient.execute(httpGet);
			InputStream iStream = response.getEntity().getContent();
			if (iStream != null)
				responseString = convertInputStreamToString(iStream);

		} catch (Exception e) {
			e.getLocalizedMessage();
		} finally {
		}
		ctx.render(responseString);
	}

	private String convertInputStreamToString(InputStream iStream) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(iStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		iStream.close();
		return result;

	}

}
