package com.tecsup.mibodega.ui.cliente.screens.terminos

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.theme.BodegaTheme

/**
 * Pantalla de Términos y Condiciones.
 * Es informativa: solo recibe la acción de volver (state hoisting).
 */
@Composable
fun TerminosCondicionesScreen(onVolver: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        EncabezadoTerminos(onVolver = onVolver)

        Spacer(Modifier.height(24.dp))

        Seccion(
            titulo = "1. Aceptación de los términos",
            contenido = "Al descargar, instalar o usar la aplicación Mi Bodega aceptas estos términos y condiciones. Si no estás de acuerdo con alguna parte, te recomendamos no utilizar el servicio."
        )

        Seccion(
            titulo = "2. Descripción del servicio",
            contenido = "Mi Bodega es una aplicación que permite a los usuarios registrados explorar un catálogo de productos de bodega, armar un carrito y solicitar entregas a domicilio."
        )

        Seccion(
            titulo = "3. Registro y cuenta",
            contenido = "Para realizar pedidos debes crear una cuenta o iniciar sesión proporcionando información veraz y actualizada. Eres responsable de mantener la confidencialidad de tus credenciales de acceso."
        )

        Seccion(
            titulo = "4. Pedidos y entregas",
            contenido = "Los pedidos están sujetos a disponibilidad de productos y a la cobertura de entrega. El tiempo estimado de entrega puede variar según la zona y las condiciones del servicio."
        )

        Seccion(
            titulo = "5. Precios y pagos",
            contenido = "Los precios mostrados están expresados en soles (S/) e incluyen los impuestos aplicables. El costo de envío (delivery) se informa antes de confirmar el pedido."
        )

        Seccion(
            titulo = "6. Cancelaciones y devoluciones",
            contenido = "Las solicitudes de cancelación o devolución deben realizarse a través de los canales de atención indicados en la aplicación, según las políticas vigentes al momento de la compra."
        )

        Seccion(
            titulo = "7. Limitación de responsabilidad",
            contenido = "Mi Bodega no se hace responsable por retrasos ocasionados por causas ajenas a su control, ni por el uso indebido de la información proporcionada por el usuario."
        )

        Seccion(
            titulo = "8. Contacto",
            contenido = "Para consultas sobre estos términos, puedes escribirnos al correo de soporte indicado en la sección de ayuda de la aplicación."
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun EncabezadoTerminos(onVolver: () -> Unit) {
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
            text = "Términos y Condiciones",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f, fill = false)
        )
        Spacer(Modifier.size(48.dp)) // balancea el ancho del ícono de la izquierda
    }
}

@Composable
private fun Seccion(titulo: String, contenido: String) {
    Text(
        text = titulo,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(Modifier.height(8.dp))
    Text(
        text = contenido,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(Modifier.height(20.dp))
}


