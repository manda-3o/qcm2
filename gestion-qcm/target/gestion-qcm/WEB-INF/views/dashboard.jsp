<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="pageTitle" value="Dashboard" />
<jsp:include page="/WEB-INF/views/fragments/header.jspf" />
<div class="row g-4 mb-4">
  <div class="col-md-3">
    <div class="card card-dashboard shadow-sm">
      <div class="card-body">
        <h5 class="card-title">Étudiants</h5>
        <p class="fs-3">${dashboard.totalStudents}</p>
        <p class="text-muted">Niveaux actifs: ${dashboard.levelCount}</p>
      </div>
    </div>
  </div>
  <div class="col-md-3">
    <div class="card card-dashboard shadow-sm">
      <div class="card-body">
        <h5 class="card-title">Questions</h5>
        <p class="fs-3">${dashboard.totalQuestions}</p>
        <p class="text-muted">Banque de questions</p>
      </div>
    </div>
  </div>
  <div class="col-md-3">
    <div class="card card-dashboard shadow-sm">
      <div class="card-body">
        <h5 class="card-title">Examens</h5>
        <p class="fs-3">${dashboard.totalExams}</p>
        <p class="text-muted">Passages enregistrés</p>
      </div>
    </div>
  </div>
  <div class="col-md-3">
    <div class="card card-dashboard shadow-sm">
      <div class="card-body">
        <h5 class="card-title">Moyenne</h5>
        <p class="fs-3">${dashboard.averageScore}</p>
        <p class="text-muted">Moyenne générale</p>
      </div>
    </div>
  </div>
</div>
<div class="row">
  <div class="col-lg-6 mb-4">
    <div class="card shadow-sm h-100">
      <div class="card-body">
        <h5 class="card-title">Résumé</h5>
        <p>Top étudiant: <strong>${fn:escapeXml(dashboard.topStudentName)}</strong></p>
        <p>Meilleure moyenne: <strong>${dashboard.topAverage}</strong></p>
        <p>Modules:</p>
        <ul>
          <li><a href="${pageContext.request.contextPath}/etudiants">Gestion des étudiants</a></li>
          <li><a href="${pageContext.request.contextPath}/qcm">Gestion des QCM</a></li>
          <li><a href="${pageContext.request.contextPath}/examens">Examens</a></li>
          <li><a href="${pageContext.request.contextPath}/classement">Classement</a></li>
        </ul>
      </div>
    </div>
  </div>
  <div class="col-lg-6 mb-4">
    <div class="card shadow-sm h-100">
      <div class="card-body">
        <h5 class="card-title">Classement rapide</h5>
        <table class="table table-sm">
          <thead>
            <tr><th>#</th><th>Étudiant</th><th>Moyenne</th><th>Examens</th></tr>
          </thead>
          <tbody>
            <c:forEach items="${ranking}" var="item" varStatus="status">
              <tr>
                <td>${status.index + 1}</td>
                <td>${fn:escapeXml(item.fullName)}</td>
                <td>${item.averageScore}</td>
                <td>${item.examCount}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>
<div class="footer-note">GestionQCM - Plateforme universitaire avec JSP, Servlets et PostgreSQL.</div>
<jsp:include page="/WEB-INF/views/fragments/footer.jspf" />