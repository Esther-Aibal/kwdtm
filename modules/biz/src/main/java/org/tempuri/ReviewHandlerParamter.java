/**
 * ReviewHandlerParamter.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class ReviewHandlerParamter  implements java.io.Serializable {
    private java.lang.String docCreator;

    private java.lang.String docStatus;

    private java.lang.String docSubject;

    private java.lang.String fdId;

    private java.lang.String fdOtherSystemId;

    private java.lang.String fdTemplateKeyword;

    private java.lang.String flowParam;

    private java.lang.String formValues;

    public ReviewHandlerParamter() {
    }

    public ReviewHandlerParamter(
           java.lang.String docCreator,
           java.lang.String docStatus,
           java.lang.String docSubject,
           java.lang.String fdId,
           java.lang.String fdOtherSystemId,
           java.lang.String fdTemplateKeyword,
           java.lang.String flowParam,
           java.lang.String formValues) {
           this.docCreator = docCreator;
           this.docStatus = docStatus;
           this.docSubject = docSubject;
           this.fdId = fdId;
           this.fdOtherSystemId = fdOtherSystemId;
           this.fdTemplateKeyword = fdTemplateKeyword;
           this.flowParam = flowParam;
           this.formValues = formValues;
    }


    /**
     * Gets the docCreator value for this ReviewHandlerParamter.
     * 
     * @return docCreator
     */
    public java.lang.String getDocCreator() {
        return docCreator;
    }


    /**
     * Sets the docCreator value for this ReviewHandlerParamter.
     * 
     * @param docCreator
     */
    public void setDocCreator(java.lang.String docCreator) {
        this.docCreator = docCreator;
    }


    /**
     * Gets the docStatus value for this ReviewHandlerParamter.
     * 
     * @return docStatus
     */
    public java.lang.String getDocStatus() {
        return docStatus;
    }


    /**
     * Sets the docStatus value for this ReviewHandlerParamter.
     * 
     * @param docStatus
     */
    public void setDocStatus(java.lang.String docStatus) {
        this.docStatus = docStatus;
    }


    /**
     * Gets the docSubject value for this ReviewHandlerParamter.
     * 
     * @return docSubject
     */
    public java.lang.String getDocSubject() {
        return docSubject;
    }


    /**
     * Sets the docSubject value for this ReviewHandlerParamter.
     * 
     * @param docSubject
     */
    public void setDocSubject(java.lang.String docSubject) {
        this.docSubject = docSubject;
    }


    /**
     * Gets the fdId value for this ReviewHandlerParamter.
     * 
     * @return fdId
     */
    public java.lang.String getFdId() {
        return fdId;
    }


    /**
     * Sets the fdId value for this ReviewHandlerParamter.
     * 
     * @param fdId
     */
    public void setFdId(java.lang.String fdId) {
        this.fdId = fdId;
    }


    /**
     * Gets the fdOtherSystemId value for this ReviewHandlerParamter.
     * 
     * @return fdOtherSystemId
     */
    public java.lang.String getFdOtherSystemId() {
        return fdOtherSystemId;
    }


    /**
     * Sets the fdOtherSystemId value for this ReviewHandlerParamter.
     * 
     * @param fdOtherSystemId
     */
    public void setFdOtherSystemId(java.lang.String fdOtherSystemId) {
        this.fdOtherSystemId = fdOtherSystemId;
    }


    /**
     * Gets the fdTemplateKeyword value for this ReviewHandlerParamter.
     * 
     * @return fdTemplateKeyword
     */
    public java.lang.String getFdTemplateKeyword() {
        return fdTemplateKeyword;
    }


    /**
     * Sets the fdTemplateKeyword value for this ReviewHandlerParamter.
     * 
     * @param fdTemplateKeyword
     */
    public void setFdTemplateKeyword(java.lang.String fdTemplateKeyword) {
        this.fdTemplateKeyword = fdTemplateKeyword;
    }


    /**
     * Gets the flowParam value for this ReviewHandlerParamter.
     * 
     * @return flowParam
     */
    public java.lang.String getFlowParam() {
        return flowParam;
    }


    /**
     * Sets the flowParam value for this ReviewHandlerParamter.
     * 
     * @param flowParam
     */
    public void setFlowParam(java.lang.String flowParam) {
        this.flowParam = flowParam;
    }


    /**
     * Gets the formValues value for this ReviewHandlerParamter.
     * 
     * @return formValues
     */
    public java.lang.String getFormValues() {
        return formValues;
    }


    /**
     * Sets the formValues value for this ReviewHandlerParamter.
     * 
     * @param formValues
     */
    public void setFormValues(java.lang.String formValues) {
        this.formValues = formValues;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReviewHandlerParamter)) return false;
        ReviewHandlerParamter other = (ReviewHandlerParamter) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.docCreator==null && other.getDocCreator()==null) || 
             (this.docCreator!=null &&
              this.docCreator.equals(other.getDocCreator()))) &&
            ((this.docStatus==null && other.getDocStatus()==null) || 
             (this.docStatus!=null &&
              this.docStatus.equals(other.getDocStatus()))) &&
            ((this.docSubject==null && other.getDocSubject()==null) || 
             (this.docSubject!=null &&
              this.docSubject.equals(other.getDocSubject()))) &&
            ((this.fdId==null && other.getFdId()==null) || 
             (this.fdId!=null &&
              this.fdId.equals(other.getFdId()))) &&
            ((this.fdOtherSystemId==null && other.getFdOtherSystemId()==null) || 
             (this.fdOtherSystemId!=null &&
              this.fdOtherSystemId.equals(other.getFdOtherSystemId()))) &&
            ((this.fdTemplateKeyword==null && other.getFdTemplateKeyword()==null) || 
             (this.fdTemplateKeyword!=null &&
              this.fdTemplateKeyword.equals(other.getFdTemplateKeyword()))) &&
            ((this.flowParam==null && other.getFlowParam()==null) || 
             (this.flowParam!=null &&
              this.flowParam.equals(other.getFlowParam()))) &&
            ((this.formValues==null && other.getFormValues()==null) || 
             (this.formValues!=null &&
              this.formValues.equals(other.getFormValues())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getDocCreator() != null) {
            _hashCode += getDocCreator().hashCode();
        }
        if (getDocStatus() != null) {
            _hashCode += getDocStatus().hashCode();
        }
        if (getDocSubject() != null) {
            _hashCode += getDocSubject().hashCode();
        }
        if (getFdId() != null) {
            _hashCode += getFdId().hashCode();
        }
        if (getFdOtherSystemId() != null) {
            _hashCode += getFdOtherSystemId().hashCode();
        }
        if (getFdTemplateKeyword() != null) {
            _hashCode += getFdTemplateKeyword().hashCode();
        }
        if (getFlowParam() != null) {
            _hashCode += getFlowParam().hashCode();
        }
        if (getFormValues() != null) {
            _hashCode += getFormValues().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReviewHandlerParamter.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "reviewHandlerParamter"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docCreator");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "docCreator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "docStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docSubject");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "docSubject"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fdId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "fdId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fdOtherSystemId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "fdOtherSystemId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fdTemplateKeyword");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "fdTemplateKeyword"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flowParam");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "flowParam"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("formValues");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "formValues"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
