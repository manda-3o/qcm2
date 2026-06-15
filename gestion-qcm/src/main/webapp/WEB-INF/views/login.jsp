<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="pageTitle" value="Connexion" />
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>GestionQCM - Connexion</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&amp;display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" crossorigin="anonymous" referrerpolicy="no-referrer" />
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
  <style>
    body { min-height: 100vh; display: flex; align-items: center; justify-content: center; background: radial-gradient(circle at top, #1f2a56 0%, #0f172a 55%); color: #f8fbff; }
    .login-card { max-width: 420px; width: 100%; backdrop-filter: blur(16px); background: rgba(255,255,255,0.08); border: 1px solid rgba(255,255,255,0.18); box-shadow: 0 24px 60px rgba(0,0,0,.24); }
    .login-card .form-control { background: rgba(255,255,255,0.12); color: #f8fbff; border: 1px solid rgba(255,255,255,0.18); }
    .login-card .form-control:focus { background: rgba(255,255,255,0.18); color: #ffffff; }
    .login-card .form-check-label { color: #dbe2ff; }
    .login-card .btn-primary { background: #7c5cff; border-color: #7c5cff; }
    .login-title { letter-spacing: .05em; }
  </style>
</head>
<body>
  <div class="card login-card p-4">
    <div class="card-body">
      <div class="text-center mb-4">
        <h2 class="fw-bold login-title">GestionQCM</h2>
        <p class="text-muted mb-0">Connexion administrateur</p>
      </div>
      <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert">${fn:escapeXml(error)}</div>
      </c:if>
      <c:if test="${param.message != null}">
        <div class="alert alert-info" role="alert">${fn:escapeXml(param.message)}</div>
      </c:if>
      <form method="post" action="${pageContext.request.contextPath}/login" novalidate>
        <div class="mb-3">
          <label class="form-label">Nom d'utilisateur</label>
          <input type="text" name="username" class="form-control" value="${fn:escapeXml(username)}" required autofocus>
        </div>
        <div class="mb-3">
          <label class="form-label">Mot de passe</label>
          <input type="password" name="password" class="form-control" required>
        </div>
        <div class="mb-3 form-check text-white-50">
          <input class="form-check-input" type="checkbox" name="remember" id="rememberMe">
          <label class="form-check-label" for="rememberMe">Se souvenir de moi</label>
        </div>
        <button type="submit" class="btn btn-primary w-100">Se connecter</button>
      </form>
    </div>
  </div>
</body>
</html>
