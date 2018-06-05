package org.unibl.etf.ipbanka.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ServiceUtility {
	public static final String VISA = "VISA";
	public static final String MASTERCARD = "MASTERCARD";
	public static final String AMERICAN_EXPRESS = "AMERICAN EXPRESS";
	public static final String NAME = "name";
	public static final String SURNAME = "surname";
	public static final String NUMBER = "card_number";
	public static final String MONTH = "card_month";
	public static final String YEAR = "card_year";
	public static final String CVC = "card_cvc";
	public static final String TYPE = "card_type";
	public static final String BALANCE = "account_balance";
	private static final String MONEY_PATTERN = "[0-9]+[.][0-9][0-9]";

	static {
		init();
	}

	private static void init() {
		JedisPool pool = new JedisPool("localhost");//properti
		try (Jedis jedis = pool.getResource()) {
			Map<String, String> mapa = new HashMap<>();
			mapa.put(NAME, "Jovan");
			mapa.put(SURNAME, "Danilovic");
			mapa.put(TYPE, VISA);
			mapa.put(NUMBER, "1234-5678-9123-4567");
			mapa.put(MONTH, "july");
			mapa.put(YEAR, "2018");
			mapa.put(CVC, "1234");
			mapa.put(BALANCE, "100.00");
			jedis.hmset("joco-95@hotmail.com", mapa);
			mapa = new HashMap<>();
			mapa.put(NAME, "Marko");
			mapa.put(SURNAME, "Malinovic");
			mapa.put(TYPE, AMERICAN_EXPRESS);
			mapa.put(NUMBER, "1234-8586-9123-4567");
			mapa.put(MONTH, "july");
			mapa.put(YEAR, "2018");
			mapa.put(CVC, "1237");
			mapa.put(BALANCE, "100.00");
			jedis.hmset("marko-95@hotmail.com", mapa);
			mapa = new HashMap<>();
			mapa.put(NAME, "Petar");
			mapa.put(SURNAME, "Stojanovic");
			mapa.put(TYPE, MASTERCARD);
			mapa.put(NUMBER, "1234-8586-9343-4567");
			mapa.put(MONTH, "july");
			mapa.put(YEAR, "2018");
			mapa.put(CVC, "2237");
			mapa.put(BALANCE, "100.00");
			jedis.hmset("petar-95@hotmail.com", mapa);
		}
		pool.close();
	}

	public static Boolean doesExist(String userMail) {
		JedisPool pool = new JedisPool("localhost");
		Boolean result = false;
		try (Jedis jedis = pool.getResource()) {
			result = jedis.exists(userMail);
		}
		pool.close();
		return result;
	}

	public static Boolean isValid(String userMail, String name, String surname, String cardNumber, String type,
			String month, String year, String cvc, String total) {
		JedisPool pool = new JedisPool("localhost");
		Boolean result = false;
		try (Jedis jedis = pool.getResource()) {
			if (userMail != null) {
				List<String> values = jedis.hmget(userMail, NAME, SURNAME, NUMBER, TYPE, MONTH, YEAR, CVC, BALANCE);
				if (name != null && surname != null && cardNumber != null && type != null && month != null
						&& year != null && cvc != null && total != null) {
					Boolean valid = true;
					Integer index = 0;
					valid &= name.equals(values.get(index++));
					valid &= surname.equals(values.get(index++));
					valid &= cardNumber.equals(values.get(index++));
					valid &= type.equals(values.get(index++));
					valid &= month.equals(values.get(index++));
					valid &= year.equals(values.get(index++));
					valid &= cvc.equals(values.get(index++));
					if (valid && total.matches(MONEY_PATTERN)) {
						BigDecimal balance = BigDecimal.valueOf(Double.valueOf(values.get(index)));
						BigDecimal totalCash = BigDecimal.valueOf(Double.valueOf(total));
						valid &= (balance.compareTo(totalCash)>=0);
					}
					result = valid;
				}
			}
		}
		pool.close();
		return result;
	}

	public static Boolean payTotal(String userMail, String name, String surname, String cardNumber, String type,
			String month, String year, String cvc, String total) {
		Boolean result = false;
		Boolean valid = isValid(userMail, name, surname, cardNumber, type, month, year, cvc, total);
		if (valid) {
			JedisPool pool = new JedisPool("localhost");//property
			try (Jedis jedis = pool.getResource()) {
				List<String> values=jedis.hmget(userMail, BALANCE);
				BigDecimal balance = BigDecimal.valueOf(Double.valueOf(values.get(0)));
				BigDecimal totalCash = BigDecimal.valueOf(Double.valueOf(total));
				balance=balance.subtract(totalCash);
				balance.setScale(2, BigDecimal.ROUND_CEILING);
				Map<String,String> map=new HashMap<>();
				map.put(BALANCE, balance.toPlainString());
				jedis.hmset(userMail, map);
				values=jedis.hmget(userMail, BALANCE);
				BigDecimal balanceAfter = BigDecimal.valueOf(Double.valueOf(values.get(0)));
				if(balanceAfter.compareTo(balance)==0) {
					result=true;
				}
			}
			pool.close();
		}
		return result;
	}

}
