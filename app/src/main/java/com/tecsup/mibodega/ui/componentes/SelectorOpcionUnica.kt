package com.tecsup.mibodega.ui.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.cliente.modelo.OpcionSeleccion
import com.tecsup.mibodega.ui.theme.GrisBorde
import com.tecsup.mibodega.ui.theme.VerdeBodega




/**
 * Selector de una sola opción (tipo "combobox" de selección única).
 * Cada opción muestra: círculo de selección, ícono y texto.
 *
 * Pensado para elegir, por ejemplo, el método de pago.
 *
 * @param opciones lista de opciones disponibles
 * @param seleccionada id de la opción actualmente seleccionada (null si ninguna)
 * @param onSeleccionar callback con el id de la opción elegida
 */
@Composable
fun SelectorOpcionUnica(
    opciones: List<OpcionSeleccion>,
    seleccionada: String?,
    onSeleccionar: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text("Metodo de pago",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground)

        Spacer(modifier = Modifier.height(20.dp))


        opciones.forEachIndexed { indice, opcion ->
            FilaOpcion(
                opcion = opcion,
                seleccionada = opcion.id == seleccionada,
                onClick = { onSeleccionar(opcion.id) }
            )

            if (indice != opciones.lastIndex) {
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun FilaOpcion(
    opcion: OpcionSeleccion,
    seleccionada: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = seleccionada,
            onClick = null, // la fila completa gestiona el clic
            colors = RadioButtonDefaults.colors(
                selectedColor = VerdeBodega,
                unselectedColor = GrisBorde
            )
        )

        Spacer(Modifier.width(6.dp))

        Image(
            painter = painterResource(opcion.imagen),
            contentDescription = opcion.texto,
            modifier = Modifier.size(28.dp)
        )

        Spacer(Modifier.width(12.dp))

        Text(
            text = opcion.texto,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (seleccionada) FontWeight.SemiBold else FontWeight.Normal,
            color = if (seleccionada) {
                MaterialTheme.colorScheme.onBackground
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }
        )
    }
}
