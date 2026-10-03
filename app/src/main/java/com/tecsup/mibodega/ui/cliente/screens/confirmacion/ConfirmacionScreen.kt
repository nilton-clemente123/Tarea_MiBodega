package com.tecsup.mibodega.ui.cliente.screens.confirmacion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.BotonSecundario
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.GrisClaro
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Pantalla de confirmación del pedido (mockup "Cliente").
 * Muestra el check de éxito, el número de pedido, el total y la dirección.
 * No navega sola: recibe las acciones por parámetro.
 */
@Composable
fun ConfirmacionScreen(
    numeroPedido: String,
    total: Double,
    direccion: String,
    onVerEstadoPedido: () -> Unit,
    onVolverInicio: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))

        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Pedido confirmado",
            tint = VerdeBodega,
            modifier = Modifier.size(96.dp)
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "pedido realizado",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "tu pedido esta siendo preparado y sera entregado pronto",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(32.dp))

        TarjetaResumen(
            numeroPedido = numeroPedido,
            total = total,
            direccion = direccion
        )

        Spacer(Modifier.weight(1f))

        BotonPrimario(
            texto = "Ver estado del pedido",
            onClick = onVerEstadoPedido
        )

        Spacer(Modifier.height(12.dp))

        BotonSecundario(
            texto = "Volver al inicio",
            onClick = onVolverInicio
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun TarjetaResumen(
    numeroPedido: String,
    total: Double,
    direccion: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(GrisClaro, RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        DatoResumen(etiqueta = "Número de pedido", valor = numeroPedido)
        Spacer(Modifier.height(16.dp))
        DatoResumen(etiqueta = "Total a pagar", valor = "S/ %.2f".format(total))
        Spacer(Modifier.height(16.dp))
        DatoResumen(etiqueta = "Dirección de entrega", valor = direccion)
    }
}

@Composable
private fun DatoResumen(etiqueta: String, valor: String) {
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ConfirmacionPreview() {
    BodegaTheme {
        ConfirmacionScreen(
            numeroPedido = "#04231",
            total = 23.40,
            direccion = "Av. Los Olivos 123",
            onVerEstadoPedido = {},
            onVolverInicio = {}
        )
    }
}

