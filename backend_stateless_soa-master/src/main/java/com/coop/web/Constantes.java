package com.coop.web;

public final class Constantes {

	public static final String URL_API = "/api";
	public static final String URL_API_VERSION = "/v1";
	public static final String URL_API_BASE = URL_API + URL_API_VERSION;

	public static final String URL_PRODUCTOS = URL_API_BASE + "/productos";
	public static final String URL_USUARIOS = URL_API_BASE + "/usuarios";
	public static final String URL_ARCHIVOS = URL_API_BASE + "/archivos";
	public static final String URL_CORE = URL_API_BASE + "/core";
	public static final String URL_LOGINOK   = "/loginok";
	public static final String URL_AUTHINFO   = "/authinfo";
	public static final String URL_LOGOUT   = "/dologout";
	public static final String URL_DENY   = "/deny";
	public static final String URL_TOKEN   = "/authtoken";
	public static final String URL_WEBSOCKET_ENPOINT = URL_API_BASE + "/ws";
	
	public static final String TOPIC_SEND_WEBSOCKET_GRAPH="/backend/data/graph";
	
	public static final String URL_GRAPH =  URL_API_BASE  + "/graph";
	
	public static final String URL_INTEGRATION =  URL_API_BASE  + "/integration";
}
