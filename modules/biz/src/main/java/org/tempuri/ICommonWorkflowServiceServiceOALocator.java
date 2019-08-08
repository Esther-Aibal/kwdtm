/**
 * ICommonWorkflowServiceServiceOALocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

import com.movitech.mbox.common.config.Global;

public class ICommonWorkflowServiceServiceOALocator extends org.apache.axis.client.Service implements org.tempuri.ICommonWorkflowServiceServiceOA {

    public ICommonWorkflowServiceServiceOALocator() {
    }


    public ICommonWorkflowServiceServiceOALocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ICommonWorkflowServiceServiceOALocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ICommonWorkflowServiceServiceOASoap
    private java.lang.String ICommonWorkflowServiceServiceOASoap_address = Global.getConfig("oa_submitted_url");

    public java.lang.String getICommonWorkflowServiceServiceOASoapAddress() {
        return ICommonWorkflowServiceServiceOASoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ICommonWorkflowServiceServiceOASoapWSDDServiceName = "ICommonWorkflowServiceServiceOASoap";

    public java.lang.String getICommonWorkflowServiceServiceOASoapWSDDServiceName() {
        return ICommonWorkflowServiceServiceOASoapWSDDServiceName;
    }

    public void setICommonWorkflowServiceServiceOASoapWSDDServiceName(java.lang.String name) {
        ICommonWorkflowServiceServiceOASoapWSDDServiceName = name;
    }

    public org.tempuri.ICommonWorkflowServiceServiceOASoap getICommonWorkflowServiceServiceOASoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ICommonWorkflowServiceServiceOASoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getICommonWorkflowServiceServiceOASoap(endpoint);
    }

    public org.tempuri.ICommonWorkflowServiceServiceOASoap getICommonWorkflowServiceServiceOASoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.ICommonWorkflowServiceServiceOASoapStub _stub = new org.tempuri.ICommonWorkflowServiceServiceOASoapStub(portAddress, this);
            _stub.setPortName(getICommonWorkflowServiceServiceOASoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setICommonWorkflowServiceServiceOASoapEndpointAddress(java.lang.String address) {
        ICommonWorkflowServiceServiceOASoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.tempuri.ICommonWorkflowServiceServiceOASoap.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.ICommonWorkflowServiceServiceOASoapStub _stub = new org.tempuri.ICommonWorkflowServiceServiceOASoapStub(new java.net.URL(ICommonWorkflowServiceServiceOASoap_address), this);
                _stub.setPortName(getICommonWorkflowServiceServiceOASoapWSDDServiceName());
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
        if ("ICommonWorkflowServiceServiceOASoap".equals(inputPortName)) {
            return getICommonWorkflowServiceServiceOASoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "ICommonWorkflowServiceServiceOA");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "ICommonWorkflowServiceServiceOASoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ICommonWorkflowServiceServiceOASoap".equals(portName)) {
            setICommonWorkflowServiceServiceOASoapEndpointAddress(address);
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
