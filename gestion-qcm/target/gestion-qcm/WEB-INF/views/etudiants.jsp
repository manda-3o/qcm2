<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Étudiants" />
<jsp:include page="/WEB-INF/views/fragments/header.jspf" />
<div class="row mb-4">
  <div class="col-md-8">
    <h1>Étudiants</h1>
  </div>
  <div class="col-md-4 text-end">
    <div class="search-box">
      <input id="student-search" type="search" class="form-control" placeholder="Recherche par numéro, nom ou email" />
      <div id="search-status" class="form-text">Résultats en temps réel</div>
    </div>
  </div>
</div>
<div class="row gy-4">
  <div class="col-lg-5">
    <div class="card shadow-sm">
      <div class="card-body">
        <h5 class="card-title">Ajouter ou modifier</h5>
        <form method="post" action="${pageContext.request.contextPath}/etudiants" enctype="multipart/form-data">
          <input type="hidden" name="action" value="save" />
          <div class="mb-3"><label class="form-label">Numéro</label><input name="num_etudiant" class="form-control" required/></div>
          <div class="mb-3"><label class="form-label">Nom</label><input name="nom" class="form-control" required/></div>
          <div class="mb-3"><label class="form-label">Prénoms</label><input name="prenoms" class="form-control"/></div>
          <div class="mb-3"><label class="form-label">Niveau</label><input name="niveau" class="form-control"/></div>
          <div class="mb-3"><label class="form-label">Email</label><input name="adr_email" type="email" class="form-control"/></div>
          <div class="mb-3"><label class="form-label">Photo</label><input id="photo-upload" name="photo" type="file" accept="image/png,image/jpeg,image/jpg" class="form-control"/></div>
          <img id="photo-preview" class="d-none img-fluid rounded mb-3" alt="Aperçu photo" />
          <button type="submit" class="btn btn-primary">Enregistrer</button>
        </form>
      </div>
    </div>
  </div>
  <div class="col-lg-7">
    <div class="card shadow-sm">
      <div class="card-body">
        <h5 class="card-title">Liste des étudiants</h5>
        <div class="table-responsive">
          <table class="table table-striped table-hover">
            <thead>
              <tr><th>#</th><th>Nom</th><th>Prénoms</th><th>Niveau</th><th>Email</th><th>Photo</th><th>Actions</th></tr>
            </thead>
            <tbody id="students-body">
              <c:forEach var="e" items="${etudiants}">
                <tr>
                  <td><c:out value="${e.numEtudiant}"/></td>
                  <td><c:out value="${e.nom}"/></td>
                  <td><c:out value="${e.prenoms}"/></td>
                  <td><c:out value="${e.niveau}"/></td>
                  <td><c:out value="${e.adrEmail}"/></td>
                  <td>
                    <c:choose>
                      <c:when test="${not empty e.photo}">
                        <img src="${pageContext.request.contextPath}/uploads/${e.photo}" class="img-thumbnail" style="width:60px;height:60px;object-fit:cover;" alt="Photo" />
                      </c:when>
                      <c:otherwise>—</c:otherwise>
                    </c:choose>
                  </td>
                  <td>
                    <form method="post" action="${pageContext.request.contextPath}/etudiants" style="display:inline">
                      <input type="hidden" name="action" value="delete" />
                      <input type="hidden" name="num_etudiant" value="${e.numEtudiant}" />
                      <button type="submit" class="btn btn-sm btn-danger">Supprimer</button>
                    </form>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
          <nav aria-label="Pagination étudiants">
            <ul class="pagination justify-content-center">
              <c:forEach var="i" begin="1" end="${pages}">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                  <a class="page-link" href="${pageContext.request.contextPath}/etudiants?page=${i}">${i}</a>
                </li>
              </c:forEach>
            </ul>
          </nav>
        </div>
      </div>
    </div>
  </div>
</div>
<jsp:include page="/WEB-INF/views/fragments/footer.jspf" />