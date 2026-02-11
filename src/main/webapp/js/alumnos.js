// ================== INIT GENERAL ==================
document.addEventListener("DOMContentLoaded", init);

function init() {
    configurarCamposDinamicos();
    initAcordeon();
    initModal();
    initTabla();
    initFiltros();
}

// ================== CONFIGURACIÓN DINÁMICA ==================
function configurarCamposDinamicos() {
    const fecha = new Date();
    const anioActualCorto = fecha.getFullYear() % 100;

    const regIngreso = document.getElementById("regIngreso");
    const regPierde = document.getElementById("regPierdeCalidad");

    if (regIngreso) {
        regIngreso.innerHTML = '<option value="" disabled selected>Trimestre Ingreso...</option>';
        for (let y = anioActualCorto; y >= 16; y--) {
            ["O", "P", "I"].forEach(t => {
                regIngreso.add(new Option(`${y}-${t}`, `${y}-${t}`));
            });
        }
    }

    if (regPierde) {
        regPierde.innerHTML = '<option value="" disabled selected>Selecciona...</option>';
        for (let y = anioActualCorto; y <= anioActualCorto + 6; y++) {
            ["O", "P", "I"].forEach(t => {
                regPierde.add(new Option(`${y}-${t}`, `${y}-${t}`));
            });
        }
    }
}

// ================== ACORDEÓN ====================
function initAcordeon() {
    const headers = document.querySelectorAll(".accordion-header");

    headers.forEach(header => {
        const body = document.getElementById(header.dataset.target);
        const icono = header.querySelector(".icono");

        header.addEventListener("click", () => {
            const abierto = body.style.maxHeight && body.style.maxHeight !== "0px";

            if (abierto) {
                body.style.maxHeight = "0px";
                icono.style.transform = "rotate(0deg)";
            } else {
                body.style.maxHeight = body.scrollHeight + "px";
                icono.style.transform = "rotate(90deg)";
            }
        });
    });
}

// ================== MODAL ==================
let modal, modalEliminar, form, tablaBody;

function initModal() {
    modal = document.getElementById("modalRegistro");
    modalEliminar = document.getElementById("modalEliminar");
    form = document.getElementById("formNuevoAlumno");
    tablaBody = document.querySelector("#tablaAlumnos tbody");

    const btnNuevo = document.getElementById("btnAbrirModal");
    const btnCerrarX = document.getElementById("btnCerrarX");
    const btnCancelar = document.getElementById("btnCancelarModal");
    const btnCancelarDel = document.getElementById("btnCancelarEliminar");

    if (btnNuevo) {
        btnNuevo.addEventListener("click", abrirModalNuevo);
    }

    btnCerrarX?.addEventListener("click", cerrarModal);
    btnCancelar?.addEventListener("click", cerrarModal);
    btnCancelarDel?.addEventListener("click", () => modalEliminar.style.display = "none");

    form.addEventListener("submit", guardarAlumno);
}

function abrirModalNuevo() {
    document.getElementById("modalTitulo").innerText = "Registrar Nuevo Alumno";
    document.getElementById("editRowIndex").value = "-1";
    form.reset();
    modal.style.display = "block";
}

function cerrarModal() {
    modal.style.display = "none";
}

// ================== TABLA ==================
function initTabla() {
    window.prepararEdicion = prepararEdicion;
    window.confirmarEliminar = confirmarEliminar;
}

function renderRow(d) {
    return `
        <td>${d.matricula}</td>
        <td>${d.nombre}</td>
        <td>${d.correoInst}</td>
        <td>${d.correoAlt}</td>
        <td>${d.tel}</td>
        <td>${d.ingreso}</td>
        <td>${d.fechaIngUam}</td>
        <td>${d.pierdeCalidad}</td>
        <td><span class="status-badge">${d.estatus}</span></td>
        <td>${d.estatusBeca}</td>
        <td>${d.acta}</td>
        <td>${d.fechaTit}</td>
        <td>${d.tituloTesis}</td>
        <td>${d.area}</td>
        <td>${d.director}</td>
        <td>${d.codirector}</td>
        <td>
            <div style="display:flex; gap:8px;">
                <button class="btn-action-icon edit" onclick="prepararEdicion(this)">✏️</button>
                <button class="btn-action-icon delete" onclick="confirmarEliminar(this)">🗑️</button>
            </div>
        </td>
    `;
}

function prepararEdicion(btn) {
    const fila = btn.closest("tr");
    document.getElementById("modalTitulo").innerText = "Editar Información del Alumno";
    document.getElementById("editRowIndex").value = fila.sectionRowIndex;

    const get = i => fila.cells[i].innerText.trim();

    setFormValues({
        matricula: get(0),
        nombre: get(1),
        correoInst: get(2),
        correoAlt: get(3),
        tel: get(4),
        ingreso: get(5),
        fechaIngUam: get(6),
        pierdeCalidad: get(7),
        estatus: get(8),
        estatusBeca: get(9),
        acta: get(10),
        fechaTit: get(11),
        tituloTesis: get(12),
        area: get(13),
        director: get(14),
        codirector: get(15)
    });

    modal.style.display = "block";
}

let filaAEliminar = null;
function confirmarEliminar(btn) {
    filaAEliminar = btn.closest("tr");
    modalEliminar.style.display = "block";
    document.getElementById("btnConfirmarBorrado").onclick = () => {
        filaAEliminar.remove();
        modalEliminar.style.display = "none";
        aplicarFiltros();
    };
}

// ================== FORMULARIO ==================
function getAlumnoFromForm() {
    const val = id => document.getElementById(id)?.value || "-";

    return {
        matricula: val("regMatricula"),
        nombre: val("regNombre"),
        correoInst: val("regCorreoInst"),
        correoAlt: val("regCorreoAlt"),
        tel: val("regTel"),
        ingreso: val("regIngreso"),
        fechaIngUam: val("regFechaIngUam"),
        pierdeCalidad: val("regPierdeCalidad"),
        estatus: val("regEstatus"),
        estatusBeca: val("regEstatusBeca"),
        acta: val("regActa"),
        fechaTit: val("regFechaTit"),
        tituloTesis: val("alumTituloTesis"),
        area: val("alumArea"),
        director: val("alumDirector"),
        codirector: val("alumCodirector")
    };
}

function setFormValues(d) {
    Object.entries(d).forEach(([k, v]) => {
        const map = {
            matricula: "regMatricula",
            nombre: "regNombre",
            correoInst: "regCorreoInst",
            correoAlt: "regCorreoAlt",
            tel: "regTel",
            ingreso: "regIngreso",
            fechaIngUam: "regFechaIngUam",
            pierdeCalidad: "regPierdeCalidad",
            estatus: "regEstatus",
            estatusBeca: "regEstatusBeca",
            acta: "regActa",
            fechaTit: "regFechaTit",
            tituloTesis: "alumTituloTesis",
            area: "alumArea",
            director: "alumDirector",
            codirector: "alumCodirector"
        };
        const el = document.getElementById(map[k]);
        if (el) el.value = v;
    });
}

function guardarAlumno(e) {
    e.preventDefault();

    const alumno = getAlumnoFromForm();
    const index = document.getElementById("editRowIndex").value;

    if (index === "-1") {
        const row = tablaBody.insertRow();
        row.innerHTML = renderRow(alumno);
    } else {
        tablaBody.rows[index].innerHTML = renderRow(alumno);
    }

    cerrarModal();
    aplicarFiltros();
}

// ================== FILTROS ==================
function initFiltros() {
    document.getElementById("filtroMatricula")?.addEventListener("input", aplicarFiltros);
    document.getElementById("filtroTrimestre")?.addEventListener("change", aplicarFiltros);
    document.getElementById("filtroPierdeCalidad")?.addEventListener("change", aplicarFiltros);
    document.getElementById("filtroAnioTit")?.addEventListener("change", aplicarFiltros);
    document.getElementById("filtroEstatus")?.addEventListener("change", aplicarFiltros);
}

function aplicarFiltros() {
    const valMat = document.getElementById("filtroMatricula")?.value.toLowerCase() || "";
    const valTri = document.getElementById("filtroTrimestre")?.value || "";
    const valPierde = document.getElementById("filtroPierdeCalidad")?.value || "";
    const valAnio = document.getElementById("filtroAnioTit")?.value || "";
    const valEst = document.getElementById("filtroEstatus")?.value || "";

    Array.from(tablaBody.rows).forEach(row => {
        const txtMat = row.cells[0].innerText.toLowerCase();
        const txtTri = row.cells[5].innerText;
        const txtPierde = row.cells[7].innerText;
        const txtEst = row.cells[8].innerText;
        const txtFechaTit = row.cells[11].innerText;

        const ok =
            (valMat === "" || txtMat.includes(valMat)) &&
            (valTri === "" || txtTri === valTri) &&
            (valPierde === "" || txtPierde === valPierde) &&
            (valEst === "" || txtEst.includes(valEst)) &&
            (valAnio === "" || txtFechaTit.includes(valAnio));

        row.style.display = ok ? "" : "none";
    });
}
