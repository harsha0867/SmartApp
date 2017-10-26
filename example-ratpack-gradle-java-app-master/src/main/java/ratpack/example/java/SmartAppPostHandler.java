package ratpack.example.java;

import static ratpack.jackson.Jackson.fromJson;

import java.io.InputStream;

import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import ratpack.exec.ExecResult;
import ratpack.exec.Promise;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.test.exec.ExecHarness;

public class SmartAppPostHandler implements Handler {

	public String val = "";

	@Inject
	public SmartAppPostHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(Context ctx) throws Exception {
		Promise<PayLoad> payLoadValue = ctx.parse(fromJson(PayLoad.class));
		ExecResult<String> result1 = ExecHarness.yieldSingle(c -> payLoadValue.map(p -> p.getValue())

		);
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(Constants.smartAppEndpointURL + Constants.setLevlPath + result1.getValue());
		httppost.setHeader("Authorization", Constants.authorizationKey);
		HttpResponse resp = httpclient.execute(httppost);
		resp.getStatusLine();
		HttpEntity entity = resp.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			try {
				// do something useful
			} finally {
				instream.close();
			}
		}
		ctx.render("Success");
	}

}
