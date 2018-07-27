package org.unibl.etf.ipbanka.soap;

import org.unibl.etf.ipbanka.util.ServiceUtility;

public class IpBankaSoapService {
	public Boolean payTotal(String userMail, String name, String surname, String cardNumber, String type,
			String expirationDate, String cvc, String total) {
		return ServiceUtility.payTotal(userMail, name, surname, cardNumber, type,expirationDate, cvc, total);
	}
	public Boolean validateData(String userMail, String name, String surname, String cardNumber, String type,
			String expirationDate, String cvc, String total) {
		return ServiceUtility.verifyData(userMail, name, surname, cardNumber, type, expirationDate, cvc, total);
	}
}
