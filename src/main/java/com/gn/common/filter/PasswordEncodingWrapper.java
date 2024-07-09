package com.gn.common.filter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class PasswordEncodingWrapper extends HttpServletRequestWrapper{
	
	public PasswordEncodingWrapper(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public String getParameter(String name) {
		String result = super.getParameter(name);
		
		if("user_pw".equals(name)) {
			String ori = super.getParameter(name);
			System.out.println("before : "+ ori);
			String enc = getSHA512(ori);
			System.out.println("after : " + enc);
			result = enc;
		}
		return result;
	}
	
	// 매개변수로 문자열을 받아서 암호화된 문자열을 리턴해 줌.
	private String getSHA512(String oriStr) {
		// Java에서 제공하는 암호화 처리 클래스
		MessageDigest md = null;
		try {
			// 적용할 알고리즘 선택하여 인스턴스화
			md = MessageDigest.getInstance("SHA-512");
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// String을 byte 배열로 자름.
		byte[] oriData = oriStr.getBytes();
		// 자른 데이터를 암호화 처리
		md.update(oriData);
		byte[] encryptData = md.digest();
		return Base64.getEncoder().encodeToString(encryptData);
	}
}
