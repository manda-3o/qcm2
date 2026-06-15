<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="pageTitle" value="Niveaux" />
<jsp:include page="/WEB-INF/views/fragments/header.jspf" />
<div class="row gy-4">
  <div class="col-lg-4">
    <div class="card shadow-sm">
      <div class="card-body">
        <h5 class="card-title">Filtrer par niveau</h5>
        <ul class="list-group">
          <c:forEach var="level" items="${levels}">
            <li class="list-group-item d-flex justify-content-between align-items-center">
              <c:url var="levelUrl" value="/niveaux">
                <c:param name="niveau" value="${level.niveau}" />
              </c:url>
              <a href="${pageContext.request.contextPath}${levelUrl}">${fn:escapeXml(level.niveau)}</a>
              <span class="badge bg-primary rounded-pill">${level.studentCount}</span>
            </li>
          </c:forEach>
        </ul>
        <c:if test="${not empty selectedLevel}">
          <a class="btn btn-sm btn-secondary mt-3" href="${pageContext.request.contextPath}/niveaux">Afficher tous</a>
        </c:if>
      </div>
    </div>
  </div>
  <div class="col-lg-8">
    <div class="card shadow-sm">
      <div class="card-body">
        <h5 class="card-title">Étudiants</h5>
        <c:if test="${not empty selectedLevel}">
          <p class="text-muted">Niveau sélectionné: <strong>${fn:escapeXml(selectedLevel)}</strong></p>
        </c:if>
        <div class="table-responsive">
          <table class="table table-striped">
            <thead>
              <tr><th>#</th><th>Nom</th><th>Prénoms</th><th>Email</th></tr>
            </thead>
            <tbody>
              <c:forEach var="e" items="${etudiants}">
                <tr>
                  <td>${fn:escapeXml(e.numEtudiant)}</td>
                  <td>${fn:escapeXml(e.nom)}</td>
                  <td>${fn:escapeXml(e.prenoms)}</td>
                  <td>${fn:escapeXml(e.adrEmail)}</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>
<jsp:include page="/WEB-INF/views/fragments/footer.jspf" />