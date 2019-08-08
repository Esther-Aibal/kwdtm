package com.movitech.mbox.common.utils;


import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import java.net.URL;
import java.util.Objects;

public class CXFUtil {
	/**
	 * @param
	 */
	/*private static JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory
			.newInstance();
	private static Map<String, Client> clients = new HashMap<String, Client>();
	private static Object lock = new Object();

	public static Object[] wsAction (String wsUrl,String wsMethod, Object... inputPar) {
		*//*JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
		Client client = clientFactory.createClient("http://localhost:9090/helloWorldService?wsdl");
		try {
			Object[] result = client.invoke("wsMethod", "KEVIN");
			return  result;
		}catch (Exception e){
			e.printStackTrace();
		}
        return  null;*//*
		Client client = clients.get(wsUrl);
		boolean needBackup = false;
		if (client == null) {
			client = dcf.createClient(wsUrl);

			// 设置超时单位为毫秒
            *//**
			 * •ConnectionTimeout -
			 * WebService以TCP连接为基础,这个属性可以理解为TCP握手时的时间设置,超过设置的时间就认为是连接超时
			 * .以毫秒为单位,默认是30000毫秒,即30秒。 •ReceiveTimeout -
			 * 这个属性是发送WebService的请求后等待响应的时间
			 * ,超过设置的时长就认为是响应超时.以毫秒为单位,默认是60000毫秒,即60秒.
			 * *//*


			HTTPConduit http = (HTTPConduit) client.getConduit();
			HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
			httpClientPolicy.setConnectionTimeout(60 * 1000); // 连接超时
			//httpClientPolicy.setAllowChunking(false); // 取消块编码
			httpClientPolicy.setReceiveTimeout(160 * 1000); // 响应超时
			http.setClient(httpClientPolicy);


			needBackup = true;
		}
		Object[] outputPar = null;

		try {
			outputPar = client.invoke(wsMethod, inputPar);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (needBackup) {
			synchronized (lock) {
				clients.put(wsUrl, client);
			}
		}

		return outputPar;
	}*/
	/*public static Object[] wsAction (String wsUrl,String wsMethod, Object[] objects){
		try {
			// 指出service所在完整的URL
			//String endpoint = "http://ip:端口号/项目名/webservice/sei(即webservice接口名)?wsdl";
			//调用接口的targetNamespace
			String targetNamespace = "http://services.oa.jzy.cust.mmd";
			//所调用接口的方法method
			//String method = "所要调用的方法名";
			// 创建一个服务(service)调用(call)
			Service service = new Service();
			Call call = (Call) service.createCall();// 通过service创建call对象
			// 设置service所在URL
			call.setTargetEndpointAddress(new java.net.URL(wsUrl));
			call.setOperationName(new QName(targetNamespace, wsMethod));
			call.setUseSOAPAction(true);
			//变量最好只是用String类型，其他类型会报错
			call.addParameter(new QName(targetNamespace, "userid"), org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);//设置参数名 state  第二个参数表示String类型,第三个参数表示入参
			call.addParameter(new QName(targetNamespace, "dpsid"), org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);//设置参数名 state  第二个参数表示String类型,第三个参数表示入参
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
			String jsonString = (String) call.invoke(objects);//此处为数组，有几个变量传几个变量
		}catch (Exception e){
		    e.printStackTrace();
		}
		return null;
	}*/
	public static Object wsAction (String wsUrl,String wsMethod, Object[] objects) {
		try {
			Call call = (Call)new Service().createCall();
			call.setTargetEndpointAddress(wsUrl);
			Object obj = call.invoke("http://services.oa.jzy.cust.mmd",wsMethod, objects);
			return obj;
		}catch (Exception e){
			e.printStackTrace();
		}

		return null;
	}

}
