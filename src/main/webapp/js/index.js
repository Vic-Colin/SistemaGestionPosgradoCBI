document.addEventListener("DOMContentLoaded", function () {

    const form = document.getElementById("loginForm");
    const modal = document.getElementById("errorModal");
    const modalMessage = document.getElementById("modalMessage");
    const closeModal = document.getElementById("closeModalerror");

    if (!form) {
        console.error("Formulario no encontrado");
        return;
    }

    form.addEventListener("submit", function (event) {

        event.preventDefault(); // 🔴 Detiene SIEMPRE el envío tradicional

        const formData = new URLSearchParams(new FormData(form));

        fetch(form.action, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: formData
        })
        .then(response => response.json())
        .then(data => {

            console.log("Respuesta del servidor:", data);

            if (data.success === true) {

                window.location.href = form.action.replace("LoginServlet", "jsp/dashboard.jsp");

            } else {

                modalMessage.textContent = data.message;
                modal.style.display = "flex";
            }

        })
        .catch(error => {
            console.error("Error en fetch:", error);
        });

    });

    if (closeModal) {
        closeModal.addEventListener("click", function () {
            modal.style.display = "none";
            form.reset();
            
            form.querySelector("input[name='usuario']").focus();
        });
    }
    
    window.addEventListener("click", function (event) {
    if (event.target === modal) {

        modal.style.display = "none";
        form.reset();
        form.querySelector("input[name='usuario']").focus();

    }
});


});






