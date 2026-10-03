package com.tecsup.mibodega.ui.cliente.screens.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import com.tecsup.mibodega.ui.theme.GrisClaro
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Pantalla de perfil (pestaña 3 del bottom bar).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    onVerCarrito: () -> Unit,
    onNavegarPestana: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi perfil", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onVerCarrito) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                    }
                }
            )
        },
        bottomBar = {
            BarraInferior(seleccionado = 3, onSeleccionar = onNavegarPestana)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(40.dp))
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                tint = VerdeBodega,
                modifier = Modifier
                    .size(96.dp)
                    .background(GrisClaro, CircleShape)
                    .padding(4.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Invitado",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Inicia sesión o regístrate para ver tu perfil",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PerfilPreview() {
    BodegaTheme {
        PerfilScreen(onVerCarrito = {}, onNavegarPestana = {})
    }
}
