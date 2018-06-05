package org.unibl.etf.ipbanka.soap;

import org.unibl.etf.ipbanka.util.ServiceUtility;

public class IpBankaSoapService {
	public Boolean isValid(String userMail, String name, String surname, String cardNumber, String type,
			String month, String year, String cvc, String total) {
		return ServiceUtility.isValid(userMail, name, surname, cardNumber, type, month, year, cvc, total);
	}
	public Boolean payTotal(String userMail, String name, String surname, String cardNumber, String type,
			String month, String year, String cvc, String total) {
		return ServiceUtility.payTotal(userMail, name, surname, cardNumber, type, month, year, cvc, total);
	}
}
