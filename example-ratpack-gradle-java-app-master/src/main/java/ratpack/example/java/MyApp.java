package ratpack.example.java;

import ratpack.guice.Guice;
import ratpack.handling.Chain;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

public class MyApp {

	/* a Method for starting the Ratpack server. Post and Get requests are passed to Handlers */

	public static void main(String[] args) throws Exception {
		RatpackServer.start(s -> s.serverConfig(c -> c.baseDir(BaseDir.find()))
				.registry(Guice.registry(b -> b.module(MyModule.class)))
				.handlers(chain -> chain.get("switches", SmartAppGetHandler.class)
						.post("setLevel", SmartAppPostHandler.class).prefix("static",
								nested -> nested.fileSystem("assets/images", Chain::files))));
	}
}
