package org.unibl.etf.ipbanka.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ServiceUtility {
	private static final ResourceBundle BUNDLE=ResourceBundle.getBundle("org.unibl.etf.ipbanka.config.IPBankaConfig");
	private static final String redisHost=BUNDLE.getString("redis.Host");
	public static final String VISA = BUNDLE.getString("user.Visa");
	public static final String MASTERCARD = BUNDLE.getString("user.MasterCard");
	public static final String AMERICAN_EXPRESS = BUNDLE.getString("user.AmericanExpress");
	public static final String NAME = BUNDLE.getString("user.Name");
	public static final String SURNAME = BUNDLE.getString("user.Surname");
	public static final String NUMBER = BUNDLE.getString("user.CardNumber");
	public static final String MONTH = BUNDLE.getString("user.CardMonth");
	public static final String YEAR = BUNDLE.getString("user.CardYear");
	public static final String CVC = BUNDLE.getString("user.CardCvc");
	public static final String TYPE = BUNDLE.getString("user.CardType");
	public static final String BALANCE = BUNDLE.getString("user.AccountBalance");
	private static final String MONEY_PATTERN = BUNDLE.getString("bank.MoneyPattern");
	private static final String FOUR_DIGITS_PATTERN = BUNDLE.getString("bank.FourDigitsPattern");
	private static final String MONTH_PATTERN = BUNDLE.getString("bank.MonthPattern");
	private static final String CARD_NUMBER_PATTERN = BUNDLE.getString("bank.CardNumberPattern");
	
	
	public static void init() {
		JedisPool pool = new JedisPool(redisHost);
		try (Jedis jedis = pool.getResource()) {
			Map<String, String> mapa = new HashMap<>();
			mapa.put(NAME, "Jovan");
			mapa.put(SURNAME, "Danilovic");
			mapa.put(TYPE, VISA);
			mapa.put(NUMBER, "1234-5678-9123-4567");
			mapa.put(MONTH, "07");
			mapa.put(YEAR, "2019");
			mapa.put(CVC, "1234");
			mapa.put(BALANCE, "100.00");
			jedis.hmset("joco-95@hotmail.com", mapa);
			mapa = new HashMap<>();
			mapa.put(NAME, "Marko");
			mapa.put(SURNAME, "Malinovic");
			mapa.put(TYPE, AMERICAN_EXPRESS);
			mapa.put(NUMBER, "1234-8586-9123-4567");
			mapa.put(MONTH, "july");
			mapa.put(YEAR, "07");
			mapa.put(CVC, "1237");
			mapa.put(BALANCE, "100.00");
			jedis.hmset("marko-95@hotmail.com", mapa);
			mapa = new HashMap<>();
			mapa.put(NAME, "Petar");
			mapa.put(SURNAME, "Stojanovic");
			mapa.put(TYPE, MASTERCARD);
			mapa.put(NUMBER, "1234-8586-9343-4567");
			mapa.put(MONTH, "08");
			mapa.put(YEAR, "2019");
			mapa.put(CVC, "2237");
			mapa.put(BALANCE, "100.00");
			jedis.hmset("petar-95@hotmail.com", mapa);
			mapa.put(NAME, "Milan");
			mapa.put(SURNAME, "Danilovic");
			mapa.put(TYPE, MASTERCARD);
			mapa.put(NUMBER, "1234-9978-9145-4567");
			mapa.put(MONTH, "05");
			mapa.put(YEAR, "2019");
			mapa.put(CVC, "8569");
			mapa.put(BALANCE, "150.00");
			jedis.hmset("mdanilovic50@hotmail.com", mapa);
			mapa.put(NAME, "Zlatan");
			mapa.put(SURNAME, "Ibrahimovic");
			mapa.put(TYPE, VISA);
			mapa.put(NUMBER, "9999-9999-9999-9999");
			mapa.put(MONTH, "10");
			mapa.put(YEAR, "2099");
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
			String month, String year, String cvc, String total) {
			if (userMail != null && 
					name != null && 
					surname != null &&
					cardNumber != null && 
					type != null && 
					month != null &&
					year != null && 
					cvc != null &&
					total != null &&
					userMail.length()>0 &&
					name.length()>0 &&
					surname.length()>0 &&
					cardNumber.matches(CARD_NUMBER_PATTERN) &&
					type.length()>0 &&
					month.matches(MONTH_PATTERN) &&
					year.matches(FOUR_DIGITS_PATTERN) &&
					cvc.matches(FOUR_DIGITS_PATTERN) && 
					total.matches(MONEY_PATTERN)
					) {
						return true;
				}
		return false;
	}
	public static Boolean verifyData(String userMail, String name, String surname, String cardNumber, String type,
			String month, String year, String cvc, String total) {
		JedisPool pool = new JedisPool(redisHost);
		Boolean result = false;
		try (Jedis jedis = pool.getResource()) {
			if (validateData(userMail, name, surname, cardNumber, type, month, year, cvc, total)) {
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
		Boolean verified = verifyData(userMail, name, surname, cardNumber, type, month, year, cvc, total);
		if (verified) {
			JedisPool pool = new JedisPool(redisHost);
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
