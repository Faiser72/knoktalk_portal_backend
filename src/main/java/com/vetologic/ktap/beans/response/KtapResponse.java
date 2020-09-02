package com.vetologic.ktap.beans.response;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class KtapResponse {
	private boolean success;
	private String message;
	private String statusCode;
	private Object object;
	private List<?> listObject;
	private Map<String, ?> mapObject;
	private byte[] byteArray;
}
