/**
 * IpBankaSoapService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.ipbanka.soap;

public interface IpBankaSoapService extends java.rmi.Remote {
    public boolean payTotal(java.lang.String userMail, java.lang.String name, java.lang.String surname, java.lang.String cardNumber, java.lang.String type, java.lang.String expirationDate, java.lang.String cvc, java.lang.String total) throws java.rmi.RemoteException;
    public boolean validateData(java.lang.String userMail, java.lang.String name, java.lang.String surname, java.lang.String cardNumber, java.lang.String type, java.lang.String expirationDate, java.lang.String cvc, java.lang.String total) throws java.rmi.RemoteException;
}
