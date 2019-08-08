package org.tempuri;

public class ICommonWorkflowServiceServiceOASoapProxy implements org.tempuri.ICommonWorkflowServiceServiceOASoap {
  private String _endpoint = null;
  private org.tempuri.ICommonWorkflowServiceServiceOASoap iCommonWorkflowServiceServiceOASoap = null;
  
  public ICommonWorkflowServiceServiceOASoapProxy() {
    _initICommonWorkflowServiceServiceOASoapProxy();
  }
  
  public ICommonWorkflowServiceServiceOASoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initICommonWorkflowServiceServiceOASoapProxy();
  }
  
  private void _initICommonWorkflowServiceServiceOASoapProxy() {
    try {
      iCommonWorkflowServiceServiceOASoap = (new org.tempuri.ICommonWorkflowServiceServiceOALocator()).getICommonWorkflowServiceServiceOASoap();
      if (iCommonWorkflowServiceServiceOASoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iCommonWorkflowServiceServiceOASoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iCommonWorkflowServiceServiceOASoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iCommonWorkflowServiceServiceOASoap != null)
      ((javax.xml.rpc.Stub)iCommonWorkflowServiceServiceOASoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.ICommonWorkflowServiceServiceOASoap getICommonWorkflowServiceServiceOASoap() {
    if (iCommonWorkflowServiceServiceOASoap == null)
      _initICommonWorkflowServiceServiceOASoapProxy();
    return iCommonWorkflowServiceServiceOASoap;
  }
  
  public java.lang.String helloWorld() throws java.rmi.RemoteException{
    if (iCommonWorkflowServiceServiceOASoap == null)
      _initICommonWorkflowServiceServiceOASoapProxy();
    return iCommonWorkflowServiceServiceOASoap.helloWorld();
  }
  
  public java.lang.String addReview(org.tempuri.ReviewHandlerParamter args) throws java.rmi.RemoteException{
    if (iCommonWorkflowServiceServiceOASoap == null)
      _initICommonWorkflowServiceServiceOASoapProxy();
    return iCommonWorkflowServiceServiceOASoap.addReview(args);
  }
  
  public java.lang.String restartReview(org.tempuri.ReviewHandlerParamter args) throws java.rmi.RemoteException{
    if (iCommonWorkflowServiceServiceOASoap == null)
      _initICommonWorkflowServiceServiceOASoapProxy();
    return iCommonWorkflowServiceServiceOASoap.restartReview(args);
  }
  
  public java.lang.String abandonReview(java.lang.String fdId) throws java.rmi.RemoteException{
    if (iCommonWorkflowServiceServiceOASoap == null)
      _initICommonWorkflowServiceServiceOASoapProxy();
    return iCommonWorkflowServiceServiceOASoap.abandonReview(fdId);
  }
  
  public java.lang.String deleteReview(java.lang.String fdId) throws java.rmi.RemoteException{
    if (iCommonWorkflowServiceServiceOASoap == null)
      _initICommonWorkflowServiceServiceOASoapProxy();
    return iCommonWorkflowServiceServiceOASoap.deleteReview(fdId);
  }
  
  public org.tempuri.ReviewHandlerParamter getReviewHandlerParamter(java.lang.String fdTemplateKeyword, java.lang.String fdId) throws java.rmi.RemoteException{
    if (iCommonWorkflowServiceServiceOASoap == null)
      _initICommonWorkflowServiceServiceOASoapProxy();
    return iCommonWorkflowServiceServiceOASoap.getReviewHandlerParamter(fdTemplateKeyword, fdId);
  }
  
  
}