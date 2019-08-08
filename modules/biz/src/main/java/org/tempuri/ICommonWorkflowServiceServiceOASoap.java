/**
 * ICommonWorkflowServiceServiceOASoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface ICommonWorkflowServiceServiceOASoap extends java.rmi.Remote {
    public java.lang.String helloWorld() throws java.rmi.RemoteException;
    public java.lang.String addReview(org.tempuri.ReviewHandlerParamter args) throws java.rmi.RemoteException;
    public java.lang.String restartReview(org.tempuri.ReviewHandlerParamter args) throws java.rmi.RemoteException;
    public java.lang.String abandonReview(java.lang.String fdId) throws java.rmi.RemoteException;
    public java.lang.String deleteReview(java.lang.String fdId) throws java.rmi.RemoteException;
    public org.tempuri.ReviewHandlerParamter getReviewHandlerParamter(java.lang.String fdTemplateKeyword, java.lang.String fdId) throws java.rmi.RemoteException;
}
