package com.tecsup.mibodega.ui.cliente.screens.pedidos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.componentes.BarraInferior
import com.tecsup.mibodega.ui.theme.BodegaTheme

/**
 * Pantalla de pedidos (pestaña 2 del bottom bar).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidosScreen(
    onVerCarrito: () -> Unit,
    onNavegarPestana: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis pedidos", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onVerCarrito) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                    }
                }
            )
        },
        bottomBar = {
            BarraInferior(seleccionado = 2, onSeleccionar = onNavegarPestana)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Receipt,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(64.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Aún no tienes pedidos",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Tus pedidos aparecerán aquí",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PedidosPreview() {
    BodegaTheme {
        PedidosScreen(onVerCarrito = {}, onNavegarPestana = {})
    }
}
