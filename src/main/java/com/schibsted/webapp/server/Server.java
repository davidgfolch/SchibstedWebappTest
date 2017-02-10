package com.schibsted.webapp.server;

import static com.schibsted.webapp.di.DIFactory.inject;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.schibsted.webapp.server.annotation.ContextHandler;
import com.schibsted.webapp.server.handler.HandlerFactory;
import com.schibsted.webapp.server.handler.MVCHandler;
import com.schibsted.webapp.server.helper.ReflectionHelper;
import com.schibsted.webapp.server.helper.ServerConfigHelper;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import rx.Observable;

@SuppressWarnings("restriction")
public class Server implements ILogger {

	public static final String LOGIN_PATH = "login.path";
	private static final String PORT = "port";

	private final Config config = inject(Config.class);
	private final ServerConfigHelper serverConfigHelper = inject(ServerConfigHelper.class);
	private final ReflectionHelper reflectionHelper = inject(ReflectionHelper.class);
	private final HandlerFactory handlerFactory = inject(HandlerFactory.class);

	private HttpServer serverInstance;

	public void startServer() throws IOException {
		createInstance().subscribe(instance -> {
			this.serverInstance = instance;
			reflectionHelper.getContextHandlers().stream() //
					.map(serverConfigHelper::createControllerInstance) //
					.forEach(observable -> observable.subscribe(obj -> {
						setHandler(obj);
						MVCHandler.getControllers().add(obj);
					}));
			serverInstance.start();
		});
	}

	private Observable<HttpServer> createInstance() throws IOException {
		try {
			logger().info("Starting server on port " + config.get(PORT));
			return Observable.just(HttpServer.create(new InetSocketAddress(config.getInt(PORT)), 0));
		} catch (IOException e) {
			logger().error("Cannot start server...", e);
			throw e;
		}
	}

	public void setHandler(IController ctrl) {
		ContextHandler handlerAnnotation=reflectionHelper.getContextHandler(ctrl.getClass());
		String contextPath = reflectionHelper.getContextPath(ctrl.getClass());
		HttpContext ctx = registerHandler(contextPath, handlerFactory.get(handlerAnnotation.contextHandler()));
		setHandlerController(ctx, ctrl);
	}

	public HttpContext registerHandler(String contextPath, HttpHandler handler) {
		HttpContext ctx = serverInstance.createContext(contextPath);
		ctx.setHandler(handler);
		return ctx;
	}

	public void setHandlerController(HttpContext ctx, IController ctrl) {
		serverConfigHelper.setHandlerController(ctx, ctrl);
	}

}
