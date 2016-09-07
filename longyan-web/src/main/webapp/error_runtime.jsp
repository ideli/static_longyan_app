<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.xiwa.base.bean.ext.SimpleResponse"%>
<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
	SimpleResponse simpleResponse = new SimpleResponse();
	simpleResponse.setOk(false);
	String exceptionDescription = ((Exception)request.getAttribute("exception")).toString();
	simpleResponse.setMessage(exceptionDescription);
	response.setHeader("Content-Type", "application/json;charset=UTF-8");
	response.getWriter().write(JSONObject.fromObject(simpleResponse).toString());
%>