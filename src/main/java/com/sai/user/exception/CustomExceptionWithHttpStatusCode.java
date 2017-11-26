package com.sai.user.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomExceptionWithHttpStatusCode {

	public ResponseEntity<Object> errorHandle(String resMsg,int id,String valError){
		Map<String,String> mp = new HashMap<>();
		mp.put("resMsg", resMsg);
		mp.put("userId", Integer.toString(id));
		mp.put("valErrors", valError);
		return new ResponseEntity<Object>(mp,HttpStatus.CONFLICT);
	}
}