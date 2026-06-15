document.addEventListener('DOMContentLoaded', function () {
  const themeToggle = document.getElementById('themeToggle');
  const body = document.body;
  const stored = localStorage.getItem('gestionqcm-theme');

  function applyTheme(theme) {
    body.classList.remove('theme-light', 'theme-dark');
    body.classList.add(theme === 'dark' ? 'theme-dark' : 'theme-light');
    localStorage.setItem('gestionqcm-theme', theme);
    if (themeToggle) {
      themeToggle.innerHTML = theme === 'dark' ? '<i class="fas fa-sun"></i><span class="ms-2">Clair</span>' : '<i class="fas fa-moon"></i><span class="ms-2">Sombre</span>';
    }
  }

  applyTheme(stored === 'dark' ? 'dark' : 'light');

  if (themeToggle) {
    themeToggle.addEventListener('click', function () {
      applyTheme(body.classList.contains('theme-dark') ? 'light' : 'dark');
    });
  }

  const toastEl = document.getElementById('gestionqcm-toast');
  if (toastEl) {
    const toast = new bootstrap.Toast(toastEl);
    toast.show();
  }

  const photoInput = document.querySelector('#photo-upload');
  const photoPreview = document.querySelector('#photo-preview');
  if (photoInput && photoPreview) {
    photoInput.addEventListener('change', function () {
      const file = photoInput.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = function (event) {
          photoPreview.src = event.target.result;
          photoPreview.classList.remove('d-none');
        };
        reader.readAsDataURL(file);
      }
    });
  }

  const examTimer = document.querySelector('#exam-timer');
  const progressBar = document.querySelector('#exam-progress');
  if (examTimer && progressBar) {
    let remaining = parseInt(examTimer.dataset.seconds, 10) || 600;
    const total = remaining;
    const interval = setInterval(function () {
      if (remaining <= 0) {
        clearInterval(interval);
        const form = examTimer.closest('form');
        if (form) {
          form.submit();
        }
        return;
      }
      remaining -= 1;
      examTimer.textContent = new Date(remaining * 1000).toISOString().substr(14, 5);
      progressBar.style.width = `${Math.floor(((total - remaining) / total) * 100)}%`;
      progressBar.textContent = `${Math.floor(((total - remaining) / total) * 100)}%`;
    }, 1000);

    window.addEventListener('popstate', function () {
      history.pushState(null, '', location.href);
    });
    history.pushState(null, '', location.href);
    window.addEventListener('beforeunload', function (e) {
      e.preventDefault();
      e.returnValue = '';
    });
  }
});
