/**
 * IpBankaSoapServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.ipbanka.soap;

public class IpBankaSoapServiceServiceLocator extends org.apache.axis.client.Service implements org.unibl.etf.ipbanka.soap.IpBankaSoapServiceService {

    public IpBankaSoapServiceServiceLocator() {
    }


    public IpBankaSoapServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public IpBankaSoapServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IpBankaSoapService
    private java.lang.String IpBankaSoapService_address = "http://localhost:8080/IPBanka/services/IpBankaSoapService";

    public java.lang.String getIpBankaSoapServiceAddress() {
        return IpBankaSoapService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IpBankaSoapServiceWSDDServiceName = "IpBankaSoapService";

    public java.lang.String getIpBankaSoapServiceWSDDServiceName() {
        return IpBankaSoapServiceWSDDServiceName;
    }

    public void setIpBankaSoapServiceWSDDServiceName(java.lang.String name) {
        IpBankaSoapServiceWSDDServiceName = name;
    }

    public org.unibl.etf.ipbanka.soap.IpBankaSoapService getIpBankaSoapService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IpBankaSoapService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIpBankaSoapService(endpoint);
    }

    public org.unibl.etf.ipbanka.soap.IpBankaSoapService getIpBankaSoapService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.unibl.etf.ipbanka.soap.IpBankaSoapServiceSoapBindingStub _stub = new org.unibl.etf.ipbanka.soap.IpBankaSoapServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getIpBankaSoapServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIpBankaSoapServiceEndpointAddress(java.lang.String address) {
        IpBankaSoapService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.unibl.etf.ipbanka.soap.IpBankaSoapService.class.isAssignableFrom(serviceEndpointInterface)) {
                org.unibl.etf.ipbanka.soap.IpBankaSoapServiceSoapBindingStub _stub = new org.unibl.etf.ipbanka.soap.IpBankaSoapServiceSoapBindingStub(new java.net.URL(IpBankaSoapService_address), this);
                _stub.setPortName(getIpBankaSoapServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("IpBankaSoapService".equals(inputPortName)) {
            return getIpBankaSoapService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://soap.ipbanka.etf.unibl.org", "IpBankaSoapServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://soap.ipbanka.etf.unibl.org", "IpBankaSoapService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("IpBankaSoapService".equals(portName)) {
            setIpBankaSoapServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
