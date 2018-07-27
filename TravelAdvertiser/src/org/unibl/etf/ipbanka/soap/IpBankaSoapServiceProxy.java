package org.unibl.etf.ipbanka.soap;

public class IpBankaSoapServiceProxy implements org.unibl.etf.ipbanka.soap.IpBankaSoapService {
  private String _endpoint = null;
  private org.unibl.etf.ipbanka.soap.IpBankaSoapService ipBankaSoapService = null;
  
  public IpBankaSoapServiceProxy() {
    _initIpBankaSoapServiceProxy();
  }
  
  public IpBankaSoapServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initIpBankaSoapServiceProxy();
  }
  
  private void _initIpBankaSoapServiceProxy() {
    try {
      ipBankaSoapService = (new org.unibl.etf.ipbanka.soap.IpBankaSoapServiceServiceLocator()).getIpBankaSoapService();
      if (ipBankaSoapService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)ipBankaSoapService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)ipBankaSoapService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (ipBankaSoapService != null)
      ((javax.xml.rpc.Stub)ipBankaSoapService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.unibl.etf.ipbanka.soap.IpBankaSoapService getIpBankaSoapService() {
    if (ipBankaSoapService == null)
      _initIpBankaSoapServiceProxy();
    return ipBankaSoapService;
  }
  
  public boolean payTotal(java.lang.String userMail, java.lang.String name, java.lang.String surname, java.lang.String cardNumber, java.lang.String type, java.lang.String month, java.lang.String year, java.lang.String cvc, java.lang.String total) throws java.rmi.RemoteException{
    if (ipBankaSoapService == null)
      _initIpBankaSoapServiceProxy();
    return ipBankaSoapService.payTotal(userMail, name, surname, cardNumber, type, month, year, cvc, total);
  }
  
  
}