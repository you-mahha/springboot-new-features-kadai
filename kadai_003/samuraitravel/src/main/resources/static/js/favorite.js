document.addEventListener("DOMContentLoaded", () => {
  const btn = document.getElementById("favorite-btn");
  if (!btn) return;

  btn.addEventListener("click", () => {
    const houseId = btn.dataset.houseId;

    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

    fetch("/favorites/toggle", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      headers: {
        [csrfHeader]: csrfToken
      },
      body: `houseId=${houseId}`
    })
    .then(res => res.json())
    .then(isFavorite => {
      btn.dataset.favorite = isFavorite;

      btn.innerHTML = isFavorite
        ? '<span style="color:red;">&#9829;</span>'
        : '<span>&#9825;</span>';
    });
  });
});
