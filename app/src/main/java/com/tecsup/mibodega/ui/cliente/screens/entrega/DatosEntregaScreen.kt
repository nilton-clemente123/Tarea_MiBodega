package com.tecsup.mibodega.ui.cliente.screens.entrega

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.cliente.modelo.COSTO_DELIVERY
import com.tecsup.mibodega.ui.cliente.modelo.MetodosPago
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.CampoTexto
import com.tecsup.mibodega.ui.componentes.SelectorOpcionUnica
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.VerdeBodega


@Composable
fun DatosEntregaScreen(
    subtotal: Double,
    esDelivery: Boolean,
    onVolver: () -> Unit,
    onContinuar: (direccion: String) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var referencia by remember { mutableStateOf("") }

    var metodoPago by remember { mutableStateOf("") }

    var errorNombre by remember { mutableStateOf<String?>(null) }
    var errorTelefono by remember { mutableStateOf<String?>(null) }
    var errorDireccion by remember { mutableStateOf<String?>(null) }
    var errorReferencia by remember { mutableStateOf<String?>(null) }
    var errorMetodoPago by remember { mutableStateOf<String?>(null) }

    val costoDelivery = if (esDelivery) COSTO_DELIVERY else 0.0
    val total = subtotal + costoDelivery

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

        if (esDelivery) {
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
        }

        Spacer(Modifier.height(28.dp))

        SelectorOpcionUnica(
            opciones = MetodosPago,
            seleccionada = metodoPago,
            onSeleccionar = {
                metodoPago = it
                errorMetodoPago = null
            }
        )

        if (errorMetodoPago != null) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = errorMetodoPago ?: "",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(Modifier.height(28.dp))

        FilaResumen(etiqueta = "Subtotal", valor = "S/ %.2f".format(subtotal))
        Spacer(Modifier.height(6.dp))
        FilaResumen(etiqueta = "Costo de delivery", valor = "S/ %.2f".format(costoDelivery))
        Spacer(Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Text(
                text = "S/ %.2f".format(total),
                fontWeight = FontWeight.Bold,
                color = VerdeBodega
            )
        }

        Spacer(Modifier.height(24.dp))

        BotonPrimario(
            texto = "Continuar pedido",
            onClick = {
                val nombreValido = !esDelivery || nombre.isNotBlank()
                val telefonoValido = !esDelivery || telefono.isNotBlank()
                val direccionValida = !esDelivery || direccion.isNotBlank()
                val referenciaValida = !esDelivery || referencia.isNotBlank()
                val metodoPagoValido = metodoPago.isNotBlank()

                errorNombre = if (nombreValido) null else "Ingresa tu nombre completo"
                errorTelefono = if (telefonoValido) null else "Ingresa tu teléfono"
                errorDireccion = if (direccionValida) null else "Ingresa tu dirección"
                errorReferencia = if (referenciaValida) null else "Ingresa una referencia"
                errorMetodoPago = if (metodoPagoValido) null else "Selecciona un método de pago"

                if (nombreValido && telefonoValido && direccionValida && referenciaValida && metodoPagoValido) {
                    val direccionFinal = if (esDelivery) direccion.trim() else "Recojo en tienda"
                    onContinuar(direccionFinal)
                }
            }
        )
    }
}

@Composable
private fun FilaResumen(etiqueta: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = etiqueta, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = valor, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun EncabezadoRegistro(onVolver: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onVolver) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = MaterialTheme.colorScheme.onBackground)
        }

        Text(
            text = "Datos de entrega",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f, fill = false)
        )
        Spacer(Modifier.size(48.dp)) // balancea el ancho del ícono de la izquierda
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DatosEntregaPreview() {
    BodegaTheme {
        DatosEntregaScreen(subtotal = 25.0, esDelivery = true, onVolver = {}, onContinuar = {})
    }
}
