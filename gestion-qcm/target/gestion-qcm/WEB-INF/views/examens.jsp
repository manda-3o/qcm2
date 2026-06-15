<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="pageTitle" value="Examens" />
<jsp:include page="/WEB-INF/views/fragments/header.jspf" />
<c:if test="${not empty examResult}">
  <div class="alert alert-success">Examen soumis avec succès. Note obtenue : <strong>${examResult}</strong> / 10.</div>
</c:if>
<div class="row gy-4">
  <div class="col-lg-6">
    <div class="card shadow-sm">
      <div class="card-body">
        <h5 class="card-title">Lancer un examen</h5>
        <form method="get" action="${pageContext.request.contextPath}/examens" class="row g-3 align-items-end">
          <input type="hidden" name="action" value="quiz" />
          <div class="col-12">
            <label class="form-label">Étudiant</label>
            <select name="num_etudiant" class="form-select" required>
              <c:forEach var="e" items="${etudiants}">
                <option value="${e.numEtudiant}">${fn:escapeXml(e.nom)} ${fn:escapeXml(e.prenoms)} (${fn:escapeXml(e.niveau)})</option>
              </c:forEach>
            </select>
          </div>
          <div class="col-md-6">
            <label class="form-label">Année universitaire</label>
            <input type="text" name="annee" class="form-control" value="2024-2025" required />
          </div>
          <div class="col-md-6 text-end">
            <button type="submit" class="btn btn-primary">Générer le questionnaire</button>
          </div>
        </form>
      </div>
    </div>
  </div>
  <c:if test="${not empty questions}">
    <div class="col-12">
      <div class="card shadow-sm">
        <div class="card-body">
          <div class="d-flex justify-content-between align-items-center mb-3">
            <h5 class="card-title mb-0">Questionnaire pour ${fn:escapeXml(selectedStudent.nom)} ${fn:escapeXml(selectedStudent.prenoms)}</h5>
            <div>
              <span class="badge bg-info text-dark me-2">Temps restant : <span id="exam-timer">10:00</span></span>
              <span class="badge bg-secondary">Durée prévue : ${examDuration} min</span>
            </div>
          </div>
          <form method="post" action="${pageContext.request.contextPath}/examens">
            <input type="hidden" name="action" value="start" />
            <input type="hidden" name="duration" value="${examDuration}" />
            <div class="mb-3">
              <span class="badge bg-secondary">Élève :</span> ${fn:escapeXml(selectedStudent.nom)} ${fn:escapeXml(selectedStudent.prenoms)}
              <span class="badge bg-secondary ms-2">Année :</span> ${fn:escapeXml(selectedYear)}
            </div>
            <c:forEach var="q" items="${questions}" varStatus="status">
              <input type="hidden" name="questionId" value="${q.numQuest}" />
              <div class="mb-4">
                <p class="fw-semibold">Question ${status.index + 1} : ${fn:escapeXml(q.question)}</p>
                <div class="form-check">
                  <input class="form-check-input" type="radio" name="answer_${q.numQuest}" id="answer_${q.numQuest}_1" value="reponse1" required>
                  <label class="form-check-label" for="answer_${q.numQuest}_1">${fn:escapeXml(q.reponse1)}</label>
                </div>
                <div class="form-check">
                  <input class="form-check-input" type="radio" name="answer_${q.numQuest}" id="answer_${q.numQuest}_2" value="reponse2">
                  <label class="form-check-label" for="answer_${q.numQuest}_2">${fn:escapeXml(q.reponse2)}</label>
                </div>
                <div class="form-check">
                  <input class="form-check-input" type="radio" name="answer_${q.numQuest}" id="answer_${q.numQuest}_3" value="reponse3">
                  <label class="form-check-label" for="answer_${q.numQuest}_3">${fn:escapeXml(q.reponse3)}</label>
                </div>
                <div class="form-check">
                  <input class="form-check-input" type="radio" name="answer_${q.numQuest}" id="answer_${q.numQuest}_4" value="reponse4">
                  <label class="form-check-label" for="answer_${q.numQuest}_4">${fn:escapeXml(q.reponse4)}</label>
                </div>
              </div>
            </c:forEach>
            <div class="d-flex justify-content-between">
              <button type="submit" class="btn btn-success">Soumettre l'examen</button>
              <a href="${pageContext.request.contextPath}/examens?action=cancel" class="btn btn-outline-secondary">Annuler l'examen</a>
            </div>
          </form>
        </div>
      </div>
    </div>
  </c:if>
</div>
<div class="card shadow-sm mt-4">
  <div class="card-body">
    <h5 class="card-title">Historique des examens</h5>
    <div class="table-responsive">
      <table class="table table-striped">
        <thead>
          <tr><th>Étudiant</th><th>Note</th><th>Année</th></tr>
        </thead>
        <tbody>
          <c:forEach var="ex" items="${examens}">
            <tr>
              <td>${fn:escapeXml(ex.numEtudiant)}</td>
              <td>${ex.note}</td>
              <td>${fn:escapeXml(ex.anneeUniv)}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</div>
<jsp:include page="/WEB-INF/views/fragments/footer.jspf" />