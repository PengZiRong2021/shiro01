<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String scheme = request.getScheme();
    String serverName = request.getServerName();
    int serverPort = request.getServerPort();
    String contextPath = request.getContextPath();
    String ctx=scheme+"://"+serverName+":"+serverPort+contextPath;
    request.setAttribute("ctx",ctx);
%>