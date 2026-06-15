<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Questions QCM" />
<jsp:include page="/WEB-INF/views/fragments/header.jspf" />
<div class="row gy-4">
  <div class="col-lg-5">
    <div class="card shadow-sm">
      <div class="card-body">
        <h5 class="card-title">Ajouter une question</h5>
        <form method="post" action="${pageContext.request.contextPath}/qcm">
          <div class="mb-3">
            <label class="form-label">Numéro question</label>
            <input name="num_quest" class="form-control" required />
          </div>
          <div class="mb-3">
            <label class="form-label">Question</label>
            <input name="question" class="form-control" required />
          </div>
          <div class="mb-3"><label class="form-label">Réponse A</label><input name="reponse1" class="form-control" required /></div>
          <div class="mb-3"><label class="form-label">Réponse B</label><input name="reponse2" class="form-control" required /></div>
          <div class="mb-3"><label class="form-label">Réponse C</label><input name="reponse3" class="form-control" required /></div>
          <div class="mb-3"><label class="form-label">Réponse D</label><input name="reponse4" class="form-control" required /></div>
          <div class="mb-3"><label class="form-label">Catégorie</label><input name="categorie" class="form-control" /></div>
          <div class="mb-3"><label class="form-label">Bonne réponse (reponse1..reponse4)</label><input name="bonne" class="form-control" required /></div>
          <button type="submit" class="btn btn-primary">Ajouter</button>
        </form>
      </div>
    </div>
  </div>
  <div class="col-lg-7">
    <div class="card shadow-sm">
      <div class="card-body">
        <h5 class="card-title">Liste des questions</h5>
        <div class="table-responsive">
          <table class="table table-striped">
            <thead>
              <tr><th>#</th><th>Question</th><th>Catégorie</th><th>Bonne</th><th>Actions</th></tr>
            </thead>
            <tbody>
              <c:forEach var="q" items="${qcms}">
                <tr>
                  <td>${q.numQuest}</td>
                  <td>${q.question}</td>
                  <td>${q.categorie}</td>
                  <td>${q.bonne}</td>
                  <td>
                    <form method="post" action="${pageContext.request.contextPath}/qcm" style="display:inline">
                      <input type="hidden" name="action" value="delete" />
                      <input type="hidden" name="num_quest" value="${q.numQuest}" />
                      <button type="submit" class="btn btn-sm btn-danger">Supprimer</button>
                    </form>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
        <nav aria-label="Pagination questions">
          <ul class="pagination justify-content-center mt-3">
            <c:forEach var="i" begin="1" end="${pages}">
              <li class="page-item ${i == currentPage ? 'active' : ''}">
                <a class="page-link" href="${pageContext.request.contextPath}/qcm?page=${i}">${i}</a>
              </li>
            </c:forEach>
          </ul>
        </nav>
      </div>
    </div>
  </div>
</div>
<jsp:include page="/WEB-INF/views/fragments/footer.jspf" />