package com.schibsted.webapp.controller.web;

import com.schibsted.webapp.server.annotation.ContextHandler;
import com.schibsted.webapp.server.handler.HandlerFactory.CONTEXT_HANDLER;

@ContextHandler(value = "/", contextHandler = CONTEXT_HANDLER.WEB_HANDLER)
public abstract class BaseWebController extends BaseController {

}
