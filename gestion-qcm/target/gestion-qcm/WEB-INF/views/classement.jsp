<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="pageTitle" value="Classement" />
<jsp:include page="/WEB-INF/views/fragments/header.jspf" />
<div class="card shadow-sm mb-4">
  <div class="card-body">
    <h1 class="card-title">Classement des meilleurs élèves</h1>
    <p class="text-muted">Classement par moyenne des examens enregistrés.</p>
  </div>
</div>
<div class="card shadow-sm">
  <div class="card-body">
    <div class="table-responsive">
      <table class="table table-hover">
        <thead>
          <tr><th>#</th><th>Étudiant</th><th>Niveau</th><th>Examens</th><th>Moyenne</th></tr>
        </thead>
        <tbody>
          <c:forEach var="item" items="${classement}" varStatus="status">
            <tr>
              <td>${status.index + 1}</td>
              <td>${fn:escapeXml(item.fullName)}</td>
              <td>${fn:escapeXml(item.niveau)}</td>
              <td>${item.examCount}</td>
              <td>${item.averageScore}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</div>
<jsp:include page="/WEB-INF/views/fragments/footer.jspf" />