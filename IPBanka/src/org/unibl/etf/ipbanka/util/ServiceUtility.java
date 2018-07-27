package org.unibl.etf.ipbanka.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ServiceUtility {
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("org.unibl.etf.ipbanka.config.IPBankaConfig");
	private static final String redisHost = BUNDLE.getString("redis.Host");
	public static final String VISA = BUNDLE.getString("user.Visa");
	public static final String MASTERCARD = BUNDLE.getString("user.MasterCard");
	public static final String AMERICAN_EXPRESS = BUNDLE.getString("user.AmericanExpress");
	public static final String NAME = BUNDLE.getString("user.Name");
	public static final String SURNAME = BUNDLE.getString("user.Surname");
	public static final String NUMBER = BUNDLE.getString("user.CardNumber");
	public static final String EXIRATION_DATE = BUNDLE.getString("user.CardExpirationDate");
	public static final String CVC = BUNDLE.getString("user.CardCvc");
	public static final String TYPE = BUNDLE.getString("user.CardType");
	public static final String BALANCE = BUNDLE.getString("user.AccountBalance");
	private static final String MONEY_PATTERN = BUNDLE.getString("bank.MoneyPattern");
	private static final String FOUR_DIGITS_PATTERN = BUNDLE.getString("bank.FourDigitsPattern");
	private static final String EXPIRE_PATTERN = BUNDLE.getString("bank.ExpirePattern");
	private static final String CARD_NUMBER_PATTERN = BUNDLE.getString("bank.CardNumberPattern");

	public static void init() {
		JedisPool pool = new JedisPool(redisHost);
		try (Jedis jedis = pool.getResource()) {
			Map<String, String> mapa = new HashMap<>();
			mapa.put(NAME, "Jovan");
			mapa.put(SURNAME, "Danilovic");
			mapa.put(TYPE, VISA);
			mapa.put(NUMBER, "1234-1234-1234-1234");
			mapa.put(EXIRATION_DATE, "07/19");
			mapa.put(CVC, "1234");
			mapa.put(BALANCE, "100.00");
			jedis.hmset("joco-95@hotmail.com", mapa);
			mapa = new HashMap<>();
			mapa.put(NAME, "Marko");
			mapa.put(SURNAME, "Malinovic");
			mapa.put(TYPE, AMERICAN_EXPRESS);
			mapa.put(NUMBER, "8888-8888-8888-8888");
			mapa.put(EXIRATION_DATE, "10/18");
			mapa.put(CVC, "1237");
			mapa.put(BALANCE, "100.00");
			jedis.hmset("marko-95@hotmail.com", mapa);
			mapa = new HashMap<>();
			mapa.put(NAME, "Petar");
			mapa.put(SURNAME, "Stojanovic");
			mapa.put(TYPE, MASTERCARD);
			mapa.put(NUMBER, "6666-6666-6666-6666");
			mapa.put(EXIRATION_DATE, "08/19");
			mapa.put(CVC, "2237");
			mapa.put(BALANCE, "100.00");
			jedis.hmset("petar-95@hotmail.com", mapa);
			mapa.put(NAME, "Milan");
			mapa.put(SURNAME, "Danilovic");
			mapa.put(TYPE, MASTERCARD);
			mapa.put(NUMBER, "7777-7777-7777-7777");
			mapa.put(EXIRATION_DATE, "05/19");
			mapa.put(CVC, "8569");
			mapa.put(BALANCE, "1500.00");
			jedis.hmset("mdanilovic50@hotmail.com", mapa);
			mapa.put(NAME, "Zlatan");
			mapa.put(SURNAME, "Ibrahimovic");
			mapa.put(TYPE, VISA);
			mapa.put(NUMBER, "9999-9999-9999-9999");
			mapa.put(EXIRATION_DATE, "10/99");
			mapa.put(CVC, "9999");
			mapa.put(BALANCE, "15000000.00");
			jedis.hmset("zlatan@internet.com", mapa);
		}
		pool.close();
		System.out.println("IPBank database fullfilled...");
	}

	public static Boolean doesExist(String userMail) {
		JedisPool pool = new JedisPool(redisHost);
		Boolean result = false;
		try (Jedis jedis = pool.getResource()) {
			result = jedis.exists(userMail);
		}
		pool.close();
		return result;
	}

	public static Boolean validateData(String userMail, String name, String surname, String cardNumber, String type,
			String expirationDate, String cvc, String price) {
		if (userMail != null && name != null && surname != null && cardNumber != null && type != null
				&& expirationDate != null && cvc != null && price != null && userMail.length() > 0 && name.length() > 0
				&& surname.length() > 0 && cardNumber.matches(CARD_NUMBER_PATTERN) && type.length() > 0
				&& expirationDate.matches(EXPIRE_PATTERN) && cvc.matches(FOUR_DIGITS_PATTERN)
				&& price.matches(MONEY_PATTERN)) {
			return true;
		}
		return false;
	}

	public static Boolean verifyData(String userMail, String name, String surname, String cardNumber, String type,
			String expirationDate, String cvc, String total) {
		JedisPool pool = new JedisPool(redisHost);
		Boolean result = false;
		try (Jedis jedis = pool.getResource()) {
			if (validateData(userMail, name, surname, cardNumber, type, expirationDate, cvc, total)) {
				List<String> values = jedis.hmget(userMail, NAME, SURNAME, NUMBER, TYPE, EXIRATION_DATE, CVC, BALANCE);
				if (name != null && surname != null && cardNumber != null && type != null && expirationDate != null
						&& cvc != null && total != null) {
					Boolean valid = true;
					Integer index = 0;
					valid &= name.equals(values.get(index++));
					valid &= surname.equals(values.get(index++));
					valid &= cardNumber.equals(values.get(index++));
					valid &= type.equals(values.get(index++));
					valid &= expirationDate.equals(values.get(index++));
					valid &= cvc.equals(values.get(index++));
					valid &= total.matches(MONEY_PATTERN);
					result = valid;
				}
			}
		}
		pool.close();
		return result;
	}

	public static Boolean payTotal(String userMail, String name, String surname, String cardNumber, String type,
			String expirationDate, String cvc, String total) {
		Boolean result = false;
		Boolean verified = verifyData(userMail, name, surname, cardNumber, type, expirationDate, cvc, total);
		if (verified) {
			JedisPool pool = new JedisPool(redisHost);
			try (Jedis jedis = pool.getResource()) {
				List<String> values = jedis.hmget(userMail, BALANCE);
				BigDecimal balance = BigDecimal.valueOf(Double.valueOf(values.get(0)));
				BigDecimal totalCash = BigDecimal.valueOf(Double.valueOf(total));
				if (balance.compareTo(totalCash) >= 0) {
					System.out.println("BALANCE: "+balance+" TO PAY:"+totalCash);
					balance = balance.subtract(totalCash);
					balance.setScale(2, BigDecimal.ROUND_CEILING);
					Map<String, String> map = new HashMap<>();
					map.put(BALANCE, balance.toPlainString());
					jedis.hmset(userMail, map);
					values = jedis.hmget(userMail, BALANCE);
					BigDecimal balanceAfter = BigDecimal.valueOf(Double.valueOf(values.get(0)));
					if (balanceAfter.compareTo(balance) == 0) {
						result = true;
					}
				}
			}
			pool.close();
		}
		return result;
	}
}
