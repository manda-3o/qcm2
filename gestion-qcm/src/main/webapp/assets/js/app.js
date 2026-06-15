document.addEventListener('DOMContentLoaded', function () {
  const searchInput = document.querySelector('#student-search');
  const tableBody = document.querySelector('#students-body');
  const statusText = document.querySelector('#search-status');

  if (!searchInput || !tableBody) {
    return;
  }

  let timeout = null;
  const context = document.querySelector('meta[name="context-path"]');
  const basePath = context ? context.getAttribute('content') : window.location.origin;

  function renderRows(students) {
    tableBody.innerHTML = '';
    if (!students || students.length === 0) {
      tableBody.innerHTML = '<tr><td colspan="6" class="text-center">Aucun étudiant trouvé</td></tr>';
      statusText.textContent = '0 résultats';
      return;
    }
    students.forEach(function (student) {
      const row = document.createElement('tr');
      row.innerHTML = '<td>' + student.numEtudiant + '</td>' +
        '<td>' + student.nom + '</td>' +
        '<td>' + student.prenoms + '</td>' +
        '<td>' + student.niveau + '</td>' +
        '<td>' + student.adrEmail + '</td>' +
        '<td><form method="post" action="' + basePath + '/etudiants" style="display:inline"><input type="hidden" name="action" value="delete" /><input type="hidden" name="num_etudiant" value="' + student.numEtudiant + '" /><button type="submit" class="btn btn-sm btn-danger">Supprimer</button></form></td>';
      tableBody.appendChild(row);
    });
    statusText.textContent = students.length + ' résultat(s)';
  }

  function fetchStudents() {
    const keyword = searchInput.value.trim();
    fetch(basePath + '/api/etudiants/search?keyword=' + encodeURIComponent(keyword), { headers: { 'Accept': 'application/json' } })
      .then(function (response) {
        return response.json();
      })
      .then(function (data) {
        renderRows(data);
      })
      .catch(function () {
        statusText.textContent = 'Erreur de recherche';
      });
  }

  searchInput.addEventListener('input', function () {
    if (timeout) {
      clearTimeout(timeout);
    }
    timeout = setTimeout(fetchStudents, 250);
  });

  fetchStudents();
});
