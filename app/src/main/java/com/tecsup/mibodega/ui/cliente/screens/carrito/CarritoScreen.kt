package com.tecsup.mibodega.ui.cliente.screens.carrito

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.cliente.modelo.COSTO_DELIVERY
import com.tecsup.mibodega.ui.cliente.modelo.ItemCarrito
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.SelectorCantidad
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.GrisBorde
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Pantalla 5: Mi carrito (mockup "Cliente").
 * No guarda estado propio: el carrito viene de ClienteApp y cualquier
 * cambio (sumar, restar, eliminar) se avisa hacia arriba con callbacks.
 */
@Composable
fun CarritoScreen(
    carrito: List<ItemCarrito>,
    esDelivery: Boolean,
    onCambiarEsDelivery: (Boolean) -> Unit,
    onVolver: () -> Unit,
    onIncrementar: (Producto) -> Unit,
    onDecrementar: (Producto) -> Unit,
    onEliminar: (Producto) -> Unit,
    onContinuarPedido: () -> Unit
) {
    val subtotal = carrito.sumOf { it.producto.precio * it.cantidad }
    val costoDelivery = if (esDelivery) COSTO_DELIVERY else 0.0
    val total = subtotal + costoDelivery

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
    ) {
        EncabezadoCarrito(onVolver = onVolver)

        if (carrito.isEmpty()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Tu carrito está vacío",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Agrega productos para continuar",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(carrito, key = { it.producto.id }) { item ->
                    FilaCarrito(
                        item = item,
                        onIncrementar = { onIncrementar(item.producto) },
                        onDecrementar = { onDecrementar(item.producto) },
                        onEliminar = { onEliminar(item.producto) }
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }

        SelectorTipoEntrega(
            esDelivery = esDelivery,
            onCambiarEsDelivery = onCambiarEsDelivery
        )

        ResumenYBoton(
            subtotal = subtotal,
            delivery = costoDelivery,
            total = total,
            habilitado = carrito.isNotEmpty(),
            onContinuarPedido = onContinuarPedido
        )
    }
}

// Sub-composables PRIVADOS: solo los usa esta pantalla.

@Composable
private fun EncabezadoCarrito(onVolver: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onVolver) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = MaterialTheme.colorScheme.onBackground)
        }
        Text(
            text = "Mi carrito",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun FilaCarrito(
    item: ItemCarrito,
    onIncrementar: () -> Unit,
    onDecrementar: () -> Unit,
    onEliminar: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(item.producto.imagen),
            contentDescription = item.producto.nombre,
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.producto.nombre,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "S/ %.2f".format(item.producto.precio),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        SelectorCantidad(
            cantidad = item.cantidad,
            onIncrementar = onIncrementar,
            onDecrementar = onDecrementar
        )

        IconButton(onClick = onEliminar) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar ${item.producto.nombre}",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ResumenYBoton(
    subtotal: Double,
    delivery: Double,
    total: Double,
    habilitado: Boolean,
    onContinuarPedido: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
        FilaResumen(etiqueta = "Subtotal", valor = subtotal)
        FilaResumen(etiqueta = "Costo de delivery", valor = delivery)

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "S/ %.2f".format(total),
                style = MaterialTheme.typography.titleMedium,
                color = VerdeBodega
            )
        }

        Spacer(Modifier.height(16.dp))

        BotonPrimario(
            texto = "Continuar pedido",
            habilitado = habilitado,
            onClick = onContinuarPedido
        )
    }
}

@Composable
private fun FilaResumen(etiqueta: String, valor: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = etiqueta, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = "S/ %.2f".format(valor), color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun SelectorTipoEntrega(
    esDelivery: Boolean,
    onCambiarEsDelivery: (Boolean) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(
            text = "Tipo de entrega",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCambiarEsDelivery(false) }
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = !esDelivery,
                onClick = { onCambiarEsDelivery(false) },
                colors = RadioButtonDefaults.colors(
                    selectedColor = VerdeBodega,
                    unselectedColor = GrisBorde
                )
            )
            Spacer(Modifier.width(8.dp))
            Text("Recojo en tienda", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCambiarEsDelivery(true) }
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = esDelivery,
                onClick = { onCambiarEsDelivery(true) },
                colors = RadioButtonDefaults.colors(
                    selectedColor = VerdeBodega,
                    unselectedColor = GrisBorde
                )
            )
            Spacer(Modifier.width(8.dp))
            Text("Delivery", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CarritoPreview() {
    val carritoEjemplo = listOf(
        ItemCarrito(listaProductosFake[4], 1), // Coca-Cola
        ItemCarrito(listaProductosFake[0], 2), // Arroz Costeño
        ItemCarrito(listaProductosFake[2], 1)  // Leche Gloria
    )
    BodegaTheme {
        CarritoScreen(
            carrito = carritoEjemplo,
            esDelivery = true,
            onCambiarEsDelivery = {},
            onVolver = {},
            onIncrementar = {},
            onDecrementar = {},
            onEliminar = {},
            onContinuarPedido = {}
        )
    }
}

