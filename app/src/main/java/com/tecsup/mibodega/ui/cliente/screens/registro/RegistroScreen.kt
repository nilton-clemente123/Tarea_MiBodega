package com.tecsup.mibodega.ui.cliente.screens.registro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.CampoTexto
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Pantalla 2: Registro de datos (mockup "Cliente").
 * Guarda su propio estado de formulario (remember) porque solo esta
 * pantalla lo necesita. Al enviar, entrega los datos ya listos.
 */
@Composable
fun RegistroScreen(
    onVolver: () -> Unit,
    onCrearCuenta: (nombre: String, telefono: String, contrasena: String, direccion: String, referencia: String) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var referencia by remember { mutableStateOf("") }

    var errorNombre by remember { mutableStateOf<String?>(null) }
    var errorTelefono by remember { mutableStateOf<String?>(null) }
    var errorContrasena by remember { mutableStateOf<String?>(null) }
    var errorDireccion by remember { mutableStateOf<String?>(null) }
    var errorReferencia by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        EncabezadoRegistro(onVolver = onVolver)

        Spacer(Modifier.height(24.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Foto de perfil",
                tint = VerdeBodega,
                modifier = Modifier
                    .size(84.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                    .padding(4.dp)
            )
        }

        Spacer(Modifier.height(28.dp))

        CampoTexto(
            etiqueta = "Nombre completo",
            valor = nombre,
            onValorCambia = {
                nombre = it
                errorNombre = null
            },
            placeholder = "Juan Pérez",
            error = errorNombre
        )
        Spacer(Modifier.height(16.dp))

        CampoTexto(
            etiqueta = "Teléfono",
            valor = telefono,
            onValorCambia = {
                telefono = it
                errorTelefono = null
            },
            placeholder = "987 654 321",
            teclado = KeyboardType.Phone,
            error = errorTelefono
        )
        Spacer(Modifier.height(16.dp))

        CampoTexto(
            etiqueta = "Contraseña",
            valor = contrasena,
            onValorCambia = {
                contrasena = it
                errorContrasena = null
            },
            placeholder = "••••••••",
            teclado = KeyboardType.Password,
            ocultarTexto = true,
            error = errorContrasena
        )
        Spacer(Modifier.height(16.dp))

        CampoTexto(
            etiqueta = "Dirección de entrega",
            valor = direccion,
            onValorCambia = {
                direccion = it
                errorDireccion = null
            },
            placeholder = "Av. Los Olivos 123",
            error = errorDireccion
        )
        Spacer(Modifier.height(16.dp))

        CampoTexto(
            etiqueta = "Referencia",
            valor = referencia,
            onValorCambia = {
                referencia = it
                errorReferencia = null
            },
            placeholder = "Frente al parque",
            error = errorReferencia
        )

        Spacer(Modifier.height(28.dp))

        BotonPrimario(
            texto = "Crear cuenta",
            onClick = {
                val nombreValido = nombre.isNotBlank()
                val telefonoValido = telefono.isNotBlank()
                val contrasenaValida = contrasena.isNotBlank()
                val direccionValida = direccion.isNotBlank()
                val referenciaValida = referencia.isNotBlank()

                errorNombre = if (nombreValido) null else "Ingresa tu nombre completo"
                errorTelefono = if (telefonoValido) null else "Ingresa tu teléfono"
                errorContrasena = if (contrasenaValida) null else "Ingresa una contraseña"
                errorDireccion = if (direccionValida) null else "Ingresa tu dirección"
                errorReferencia = if (referenciaValida) null else "Ingresa una referencia"

                if (nombreValido && telefonoValido && contrasenaValida && direccionValida && referenciaValida) {
                    onCrearCuenta(
                        nombre.trim(),
                        telefono.trim(),
                        contrasena,
                        direccion.trim(),
                        referencia.trim()
                    )
                }
            }
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun EncabezadoRegistro(onVolver: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = onVolver,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = MaterialTheme.colorScheme.onBackground)
        }
        Text(
            text = "Crear cuenta",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f, fill = false)
        )
        Spacer(Modifier.size(48.dp)) // balancea el ancho del ícono de la izquierda
    }
    Text(
        text = "Completa tus datos para continuar",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.fillMaxWidth(),
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegistroPreview() {
    BodegaTheme {
        RegistroScreen(onVolver = {}, onCrearCuenta = { _, _, _, _, _ -> })
    }
}

