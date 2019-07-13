package com.coop.model.util;

import java.io.Serializable;

public class ChangeStateMessage<T> implements Serializable  {
	
	public static String TYPE_GRAPH_DATA="GRAPH_DATA";

	public ChangeStateMessage(String type, T payload) {
		super();
		this.type = type;
		this.payload = payload;
	}
	public T getPayload() {
		return payload;
	}
	public void setPayload(T payload) {
		this.payload = payload;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	private static final long serialVersionUID = 5707277538370528269L;
	private T payload;
	private String type;
}
