package com.tecsup.mibodega.ui.cliente.modelo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Payment
import androidx.compose.ui.graphics.vector.ImageVector

private val IconoOpcionPorDefecto: ImageVector = Icons.Default.Payment
data class OpcionSeleccion(
    val id: String,
    val texto: String,
    val icono: ImageVector = IconoOpcionPorDefecto
)