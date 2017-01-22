/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.backend.service.core;

/**
 *
 * @author MumbaiZone
 */
    import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class UniqueIdHelper {
	private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";// "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final long BASE = 36;

	private String encode(long num) {
		StringBuilder sb = new StringBuilder();

		while (num > 0) {
			sb.append(ALPHABET.charAt((int) (num % BASE)));
			num /= BASE;
		}

		return sb.reverse().toString();
	}

	public String getId() {
		Date date = new Date();
		String id = encode(date.getTime());
		return id;
	}
	
}

