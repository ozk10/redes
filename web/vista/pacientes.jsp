<%@ page import="modelo.EmpleadoDto" %>
<%@ page session="true" %>
<%
    HttpSession sesion = request.getSession(false);
    if (sesion == null || sesion.getAttribute("idusuario") == null || (int)sesion.getAttribute("idrol") != 1) {
        response.sendRedirect("login.jsp");
        return;
    }

    EmpleadoDto empleado = (EmpleadoDto) sesion.getAttribute("empleado");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Pacientes</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    <script>
        function mostrarPestana(id) {
            // Oculta todos los contenidos de pestañas
            const contenidos = document.querySelectorAll('.tab-content');
            contenidos.forEach(function(content) {
                content.style.display = 'none';
            });

            // Elimina la clase active de todas las pestañas
            const tabs = document.querySelectorAll('.tab');
            tabs.forEach(function(tab) {
                tab.classList.remove('active');
            });

            // Muestra el contenido de la pestaña seleccionada
            const contenidoSeleccionado = document.getElementById(id);
                
            if (contenidoSeleccionado) {
                contenidoSeleccionado.style.display = 'block';
            }

            // Marca la pestaña seleccionada como activa
            const tabActiva = Array.from(tabs).find(tab => tab.textContent.trim().toLowerCase().includes(id));
            if (tabActiva) {
                tabActiva.classList.add('active');
            }
        }
    </script>
    



</head>
<body>
    <header class="barra-superior">
        <h1>Gestión de Pacientes</h1>
        <div class="usuario-info">
            <span><strong><%= empleado.getNomemp() %> <%= empleado.getApeemp() %></strong></span> |
            <span><%= empleado.getNomcargo() %></span> |
            <span>ID: <%= empleado.getCodemp() %></span>
        </div>
    </header>

    <nav class="barra-menu">
        <a href="admision.jsp"><i class="fa fa-id-badge"></i> Mis Datos</a>
        <a href="pacientes.jsp"><i class="fa fa-user"></i> Pacientes</a>
        <a href="#"><i class="fa fa-calendar"></i> Citas</a>
        <a href="<%= request.getContextPath() %>/UsuarioServlet?accion=cerrar">Cerrar Sesión</a>
    </nav>

    <main class="contenido">
        <div class="tab-container">
            <div class="tabs">
                <div class="tab active" onclick="mostrarPestana('nuevo')">Nuevo Paciente</div>
                <div class="tab" onclick="mostrarPestana('actualizar')">Actualizar Datos</div>
                <div class="tab" onclick="mostrarPestana('listar')">Listar Asegurados</div>
            </div>

            <div id="nuevo" class="tab-content">

                <div class="titulotab">
                    <h2>Registrar Nuevo Paciente</h2>
                </div>

                <div class="columns">
                    <div class="col1">

                    <!-- Respuesta a la validación del correo electrónico con Email Validator-->    
                    <% String mensaje = (String) request.getAttribute("mensaje"); %>
                    <% if (mensaje != null) { %>
                        <div style="color:red;"><%= mensaje %></div>
                    <% } %>
                    <br>
                    
                        <form action="../PacienteServlet" method="post">
                            
                            <input type="hidden" name="accion" value="registrar" />
                            
                            <div class="form-group form-group-horizontal">
                                <div class="input-button-inline">
                                    <label for="codaseg">Código de Asegurado:</label>
                                    <input type="text" id="codaseg" name="codaseg" required />
                                    <button type="button" onclick="buscarAsegurado()" class="btn-buscar">Buscar</button>
                                </div>
                            </div>
                            
                            <br>

                            <div class="form-group">
                            <label>Fecha de Nacimiento:</label>
                            <input type="date" name="fechanac"  required />
                            </div>

                            <div class="form-group">
                            <label>Teléfono de Paciente:</label>
                            <input type="text" name="telefpac"
                            pattern="^\d{9}|\d{3}[- ]?\d{3}[- ]?\d{3}$"
                            title="Ingrese 9 dígitos continuos o separados por guiones o espacios (ej: 987654321, 987-654-321 o 987 654 321)"
                            maxlength="13" />
                            </div>
                            
                            <div class="form-group">
                            <label>Correo Electrónico:</label>
                            <input type="email" name="correopac" maxlength="100" required />
                            </div>

                            <div class="form-group">
                            <label>Fecha de Alta:</label>
                            <input type="date" name="fechaalta" required />
                            </div>
                            
                            <div class="form-group">
                            <button type="submit">Registrar Paciente</button>
                            </div>
                            
                        </form>
                    </div>

                    <div class="col2">
                        <div id="datos-asegurado">
                            <p><strong>Nombres:</strong> <span id="nomaseg"></span></p>
                            <p><strong>Apellidos:</strong> <span id="apeaseg"></span></p>
                            <p><strong>DNI:</strong> <span id="dniaseg"></span></p>
                            <p><strong>Certificados:</strong></p>
                            <ul id="lista-certificados"></ul>
                        </div>
                    </div>
                </div>
            </div>


            <div id="actualizar" class="tab-content" style="display: none;">
                <div class="titulotab">
                <h2>Actualizar Paciente</h2>
                </div>
                <!-- Buscar por código -->
                <div class="form-group form-group-horizontal">
                    <div class="input-button-inline">
                    <label for="codpac-buscar">Buscar por Código de Paciente:</label>
                    <input type="number" id="codpac-buscar" name="codpac-buscar" style="width: 30%">
                    <button type="button" id="btnBuscarPaciente" class="btn-buscar" style="width: 20%">Buscar</button>
                    </div>
                </div>

                <form action="../PacienteServlet" method="post" class="formulario">
                    <input type="hidden" name="accion" value="actualizar">

                    <div class="form-group">
                        <label for="codpac-actualizar">Código de Paciente:</label>
                        <input type="number" name="codpac" id="codpac-actualizar" required readonly style="background-color: #f2f2f2;">
                    </div>
                    
                    <div class="form-group">
                        <label for="codaseg-actualizar">Código de Asegurado:</label>
                        <input type="number" name="codaseg" id="codaseg-actualizar" required readonly style="background-color: #f2f2f2;">
                    </div>

                    <div class="form-group">
                        <label for="nomaseg-actualizar">Nombres de Asegurado:</label>
                        <input type="text" name="nomaseg" id="nomaseg-actualizar" required readonly style="background-color: #f2f2f2;">
                    </div>

                    <div class="form-group">
                        <label for="apeaseg-actualizar">Apellidos de Asegurado:</label>
                        <input type="text" name="apeaseg" id="apeaseg-actualizar" required readonly style="background-color: #f2f2f2;">
                    </div>
                    
                    <div class="form-group">
                        <label for="fechaalta-actualizar">Fecha de Alta:</label>
                        <input type="date" name="fechaalta" id="fechaalta-actualizar" required readonly style="background-color: #f2f2f2;">
                    </div>

                    <div class="form-group">
                        <label for="fechanac-actualizar">Fecha de Nacimiento:</label>
                        <input type="date" name="fechanac" id="fechanac-actualizar"  required>
                    </div>

                    <div class="form-group">
                        <label for="telefpac-actualizar">Teléfono:</label>
                        <input type="text" name="telefpac" id="telefpac-actualizar">
                    </div>

                    <div class="form-group">
                        <label for="correopac-actualizar">Correo Electrónico:</label>
                        <input type="email" name="correopac" id="correopac-actualizar" required>
                    </div>

                    <div class="form-group form-group-center">
                        <button type="submit" class="btn-submit">Actualizar</button>
                    </div>
                </form>
            </div>

            <div id="listar" class="tab-content" style="display: none;">
                
                <h2>Lista de Asegurados</h2>
                
                <label for="dniaseg-buscar">DNI:</label>
                <input type="text" id="dniaseg-buscar" placeholder="Ingrese DNI del asegurado" />
                <button id="btn-buscar-asegurados">Buscar</button>
    
                <table id="tabla-asegurados" class="tabla">
                    <thead>
                        <tr>
                            <th>Código</th>
                            <th>Nombre</th>
                            <th>Apellido</th>
                            <th>Certificado</th>
                            <th>Fecha Emisión</th>
                            <th>Inicio Vigencia</th>
                            <th>Fin Vigencia</th>
                            <th>Plan</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Datos serán llenados con JS -->
                    </tbody>
                </table>
                
                <br><!-- comment -->
                <button onclick="window.location.href='<%= request.getContextPath() %>/ExportarAseguradosServlet'" class="btn-exportar">Exportar a Excel</button>

            </div>
                        
                        
        </div>
    </main>
                        
<script>
    
document.getElementById("btnBuscarPaciente").addEventListener("click", function () {
    const codpac = document.getElementById("codpac-buscar").value;

    if (!codpac) {
        alert("Ingrese un código de paciente.");
        return;
    }

    fetch("../PacienteServlet?accion=buscar&codpac=" + codpac)
        .then(response => {
            if (!response.ok) {
                throw new Error("Respuesta no OK del servidor");
            }
            return response.json();
        })
        .then(data => {
            // Llenamos los campos del formulario "actualizar"
            document.getElementById("codpac-actualizar").value = data.codpac;
            document.getElementById("codaseg-actualizar").value = data.codaseg;
            document.getElementById("fechaalta-actualizar").value = data.fechaalta;
            document.getElementById("fechanac-actualizar").value = data.fechanac;
            document.getElementById("telefpac-actualizar").value = data.telefpac;
            document.getElementById("correopac-actualizar").value = data.correopac;
            
            document.getElementById("nomaseg-actualizar").value = data.nomaseg;
            document.getElementById("apeaseg-actualizar").value = data.apeaseg;

        })
        .catch(error => {
            console.error("Error en la búsqueda del paciente:", error);
            alert("Ocurrió un error al buscar el paciente.");
        });
});
</script>


</body>
</html>

<script>

function formatearFecha(fechaStr) {
    if (!fechaStr || typeof fechaStr !== 'string') return '';

    const partes = fechaStr.split('-'); // ["2025", "06", "01"]
    if (partes.length !== 3) return '';

    const [anio, mes, dia] = partes;
    return dia.padStart(2, '0') + "/" + mes.padStart(2, '0') + "/" + anio;
}



async function buscarAsegurado() {
    const cod = document.getElementById('codaseg').value.trim();
    if (!cod) return alert('Ingresa el código de asegurado.');

    try {
        const resp = await fetch("<%= request.getContextPath() %>/AseguradoServlet?codaseg=" + encodeURIComponent(cod));

        if (!resp.ok) throw new Error(resp.statusText);
        const data = await resp.json();

console.log("Datos recibidos:", JSON.stringify(data, null, 2));

        if (!data.nombre) return alert('Asegurado no encontrado.');

        document.getElementById('nomaseg').textContent = data.nombre;
        document.getElementById('apeaseg').textContent = data.apellido;
        document.getElementById('dniaseg').textContent = data.dni;

        const certUl = document.getElementById('lista-certificados');
        certUl.innerHTML = ''; // Limpia la lista anterior

        data.certificados.forEach(cert => {
            const li = document.createElement('li');

console.log("cert =", cert);

            const ini = formatearFecha(cert.fechaini);
            const fin = formatearFecha(cert.fechafin);

            li.textContent = cert.codigo + '(' + ini + ' a ' + fin + ')';
            
            certUl.appendChild(li);
        });

    } catch (err) {
        console.error("Error al consultar asegurado:", err);
        alert('Error al consultar asegurado.');
    }
}
</script>

<script>
document.addEventListener("DOMContentLoaded", function () {
    const btnBuscar = document.getElementById("btn-buscar-asegurados");

    btnBuscar.addEventListener("click", function () {        
        const dni = document.getElementById("dniaseg-buscar").value.trim();
        let url = "<%= request.getContextPath() %>/AseguradoServlet?accion=listar";

        if (dni) {
            url += "&dni=" + encodeURIComponent(dni);
        }

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error en la respuesta del servidor");
                }
                return response.json();
            })
            .then(data => {
                console.log("Asegurados recibidos:", data);
                const tbody = document.querySelector("#tabla-asegurados tbody");
                tbody.innerHTML = "";

                if (!Array.isArray(data) || data.length === 0) {
                    const fila = tbody.insertRow();
                    const celda = fila.insertCell(0);
                    celda.colSpan = 9;
                    celda.textContent = "No se encontraron asegurados.";
                    celda.style.textAlign = "center";
                    return;
                }

                data.forEach(aseg => {
                    const fila = tbody.insertRow();

                    const campos = [
                        aseg.codaseg,
                        aseg.nomaseg,
                        aseg.apeaseg,
                        aseg.codcertif,
                        aseg.fechaemi,
                        aseg.fechainicio,
                        aseg.fechafin,
                        aseg.nomplan,
                        aseg.status
                    ];

                    campos.forEach(valor => {
                        const celda = fila.insertCell();
                        celda.textContent = valor;
                    });
                });
            })
            .catch(error => {
                console.error("Error al cargar asegurados:", error);
            });
    });
});
</script>

