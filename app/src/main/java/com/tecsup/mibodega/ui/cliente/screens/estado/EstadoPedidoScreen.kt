package com.tecsup.mibodega.ui.cliente.screens.estado

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.GrisBorde
import com.tecsup.mibodega.ui.theme.GrisClaro
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Pantalla de estado del pedido (mockup "Cliente").
 * Muestra el avance del pedido, el tiempo estimado y la dirección.
 * No navega sola: recibe las acciones por parámetro (state hoisting).
 */
@Composable
fun EstadoPedidoScreen(
    numeroPedido: String,
    direccion: String,
    onVolver: () -> Unit,
    onVolverInicio: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        EncabezadoEstado(onVolver = onVolver)

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Pedido $numeroPedido",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(24.dp))

        PasosEstado()

        Spacer(Modifier.height(32.dp))

        ResumenEstado(
            tiempoEstimado = "30 - 45 minutos",
            direccion = direccion
        )

        Spacer(Modifier.height(32.dp))

        BotonPrimario(
            texto = "Volver al inicio",
            onClick = onVolverInicio
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun EncabezadoEstado(onVolver: () -> Unit) {
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
            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
        }
        Text(
            text = "Estado del pedido",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f, fill = false)
        )
        Spacer(Modifier.size(48.dp)) // balancea el ancho del ícono de la izquierda
    }
}

@Composable
private fun PasosEstado() {
    val pasos = listOf(
        "Pedido confirmado" to "Hemos recibido tu pedido",
        "En preparación" to "Estamos armando tus productos",
        "En camino" to "Tu pedido va en camino",
        "Entregado" to "Pedido entregado"
    )

    Column {
        pasos.forEachIndexed { index, (titulo, descripcion) ->
            PasoEstado(
                titulo = titulo,
                descripcion = descripcion,
                completado = index <= 1, // mock: el pedido está en preparación
                esUltimo = index == pasos.lastIndex
            )
        }
    }
}

@Composable
private fun PasoEstado(
    titulo: String,
    descripcion: String,
    completado: Boolean,
    esUltimo: Boolean
) {
    val colorActivo = VerdeBodega
    val colorInactivo = GrisBorde

    Row {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(
                        color = if (completado) colorActivo else colorInactivo,
                        shape = CircleShape
                    )
            )
            if (!esUltimo) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(36.dp)
                        .background(if (completado) colorActivo else colorInactivo)
                )
            }
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(
                text = titulo,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                color = if (completado) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ResumenEstado(tiempoEstimado: String, direccion: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(GrisClaro, RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        DatoEstado(etiqueta = "Tiempo estimado", valor = tiempoEstimado)
        Spacer(Modifier.height(16.dp))
        DatoEstado(etiqueta = "Dirección de entrega", valor = direccion)
    }
}

@Composable
private fun DatoEstado(etiqueta: String, valor: String) {
    Column {
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}


