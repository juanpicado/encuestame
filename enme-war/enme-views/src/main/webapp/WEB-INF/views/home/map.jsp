<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/map.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/map.jsp"%>
  </c:if>