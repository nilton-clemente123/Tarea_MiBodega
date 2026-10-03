package com.tecsup.mibodega.ui.cliente

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import kotlin.random.Random
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.mibodega.ui.cliente.modelo.COSTO_DELIVERY
import com.tecsup.mibodega.ui.cliente.modelo.ItemCarrito
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.Usuario
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.cliente.screens.bienvenida.BienvenidaScreen
import com.tecsup.mibodega.ui.cliente.screens.carrito.CarritoScreen
import com.tecsup.mibodega.ui.cliente.screens.confirmacion.ConfirmacionScreen
import com.tecsup.mibodega.ui.cliente.screens.detalle.DetalleProductoScreen
import com.tecsup.mibodega.ui.cliente.screens.entrega.DatosEntregaScreen
import com.tecsup.mibodega.ui.cliente.screens.estado.EstadoPedidoScreen
import com.tecsup.mibodega.ui.cliente.screens.inicio.InicioScreen
import com.tecsup.mibodega.ui.cliente.screens.login.LoginScreen
import com.tecsup.mibodega.ui.cliente.screens.registro.RegistroScreen
import com.tecsup.mibodega.ui.cliente.screens.terminos.TerminosCondicionesScreen

/**
 * "Director de orquesta" de la app cliente:
 * - Tiene el NavHost con las rutas de cada pantalla.
 * - Tiene el estado del carrito (List<ItemCarrito>), que se reparte
 *   hacia abajo a Inicio, Detalle, Carrito y Entrega.
 * Ninguna Screen navega sola ni modifica el carrito directamente:
 * todas reciben funciones (lambdas) desde aquí (state hoisting).
 */
private object Rutas {
    const val BIENVENIDA = "bienvenida"
    const val REGISTRO = "registro"
    const val LOGIN = "login"
    const val TERMINOS = "terminos"
    const val INICIO = "inicio"
    const val DETALLE = "detalle/{productoId}"
    const val CARRITO = "carrito"

    const val DATOSENTREGA = "datosEntrega"
    const val CONFIRMACION = "confirmacion"
    const val ESTADO_PEDIDO = "estadoPedido"

    fun detalle(productoId: Int) = "detalle/$productoId"
}

@Composable
fun ClienteApp() {
    val navController = rememberNavController()

    // El carrito vive aquí arriba, no en ninguna Screen.
    var carrito by remember { mutableStateOf<List<ItemCarrito>>(emptyList()) }

    // Datos del pedido ya confirmado, para mostrarlos en Confirmación.
    var pedidoConfirmado by remember { mutableStateOf<PedidoConfirmado?>(null) }

    // Usuarios registrados en memoria (mock: se pierden al cerrar la app).
    var usuarios by remember { mutableStateOf<List<Usuario>>(emptyList()) }

    // Mensaje de error del login (null = sin error).
    var errorLogin by remember { mutableStateOf<String?>(null) }

    // IDs de productos marcados como favoritos.
    var favoritos by remember { mutableStateOf<Set<Int>>(emptySet()) }

    NavHost(
        navController = navController,
        startDestination = Rutas.BIENVENIDA
    ) {
        composable(Rutas.BIENVENIDA) {
            BienvenidaScreen(
                onRegistrarse = { navController.navigate(Rutas.REGISTRO) },
                onIniciarSesion = {
                    errorLogin = null
                    navController.navigate(Rutas.LOGIN)
                },
                onTerminos = { navController.navigate(Rutas.TERMINOS) }
            )
        }

        composable(Rutas.TERMINOS) {
            TerminosCondicionesScreen(
                onVolver = { navController.popBackStack() }
            )
        }

        composable(Rutas.REGISTRO) {
            RegistroScreen(
                onVolver = { navController.popBackStack() },
                onCrearCuenta = { nombre, telefono, contrasena, direccion, referencia ->
                    usuarios = usuarios + Usuario(
                        nombre = nombre,
                        telefono = telefono,
                        contrasena = contrasena,
                        direccion = direccion,
                        referencia = referencia
                    )
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.BIENVENIDA) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.LOGIN) {
            LoginScreen(
                onVolver = { navController.popBackStack() },
                onIniciarSesion = { telefono, contrasena ->
                    val usuario = usuarios.find {
                        it.telefono == telefono && it.contrasena == contrasena
                    }
                    if (usuario != null) {
                        errorLogin = null
                        navController.navigate(Rutas.INICIO) {
                            popUpTo(Rutas.BIENVENIDA) { inclusive = true }
                        }
                    } else {
                        errorLogin = "Teléfono o contraseña incorrectos"
                    }
                },
                error = errorLogin
            )
        }

        composable(Rutas.INICIO) {
            InicioScreen(
                cantidadCarrito = carrito.sumOf { it.cantidad },
                onVerCarrito = { navController.navigate(Rutas.CARRITO) },
                onProductoClick = { producto ->
                    navController.navigate(Rutas.detalle(producto.id))
                },
                onAgregarProducto = { producto ->
                    carrito = agregarOSumarProducto(carrito, producto, 1)
                }
            )
        }

        composable(
            route = Rutas.DETALLE,
            arguments = listOf(navArgument("productoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getInt("productoId") ?: 0
            val producto = listaProductosFake.first { it.id == productoId }

            DetalleProductoScreen(
                producto = producto,
                esFavorito = producto.id in favoritos,
                onVolver = { navController.popBackStack() },
                onAgregarAlCarrito = { productoSeleccionado, cantidad ->
                    carrito = agregarOSumarProducto(carrito, productoSeleccionado, cantidad)
                    navController.popBackStack()
                },
                onToggleFavorito = {
                    favoritos = if (producto.id in favoritos) {
                        favoritos - producto.id
                    } else {
                        favoritos + producto.id
                    }
                }
            )
        }

        composable(Rutas.CARRITO) {
            CarritoScreen(
                carrito = carrito,
                onVolver = { navController.popBackStack() },
                onIncrementar = { producto ->
                    carrito = carrito.map {
                        if (it.producto.id == producto.id) it.copy(cantidad = it.cantidad + 1) else it
                    }
                },
                onDecrementar = { producto ->
                    carrito = carrito.mapNotNull {
                        when {
                            it.producto.id != producto.id -> it
                            it.cantidad > 1 -> it.copy(cantidad = it.cantidad - 1)
                            else -> null // si llega a 0, se elimina de la lista
                        }
                    }
                },
                onEliminar = { producto ->
                    carrito = carrito.filterNot { it.producto.id == producto.id }
                },
                onContinuarPedido = {
                    navController.navigate(Rutas.DATOSENTREGA)
                }
            )
        }


        composable(Rutas.DATOSENTREGA) {
            DatosEntregaScreen(
                onVolver = {
                    navController.popBackStack()
                },
                onContinuar = { direccion ->
                    pedidoConfirmado = PedidoConfirmado(
                        numero = generarNumeroPedido(),
                        total = carrito.sumOf { it.producto.precio * it.cantidad } + COSTO_DELIVERY,
                        direccion = direccion
                    )
                    navController.navigate(Rutas.CONFIRMACION)
                }
            )
        }

        composable(Rutas.CONFIRMACION) {
            val pedido = pedidoConfirmado
            if (pedido != null) {
                ConfirmacionScreen(
                    numeroPedido = pedido.numero,
                    total = pedido.total,
                    direccion = pedido.direccion,
                    onVerEstadoPedido = { navController.navigate(Rutas.ESTADO_PEDIDO) },
                    onVolverInicio = {
                        carrito = emptyList()
                        navController.popBackStack(Rutas.INICIO, inclusive = false)
                    }
                )
            }
        }

        composable(Rutas.ESTADO_PEDIDO) {
            val pedido = pedidoConfirmado
            if (pedido != null) {
                EstadoPedidoScreen(
                    numeroPedido = pedido.numero,
                    direccion = pedido.direccion,
                    onVolver = { navController.popBackStack() },
                    onVolverInicio = {
                        carrito = emptyList()
                        navController.popBackStack(Rutas.INICIO, inclusive = false)
                    }
                )
            }
        }
    }
}

/**
 * Si el producto ya está en el carrito, le suma la cantidad;
 * si no, lo agrega como un ItemCarrito nuevo.
 */
private fun agregarOSumarProducto(
    carrito: List<ItemCarrito>,
    producto: Producto,
    cantidad: Int
): List<ItemCarrito> {
    val itemExistente = carrito.find { it.producto.id == producto.id }
    return if (itemExistente != null) {
        carrito.map {
            if (it.producto.id == producto.id) it.copy(cantidad = it.cantidad + cantidad) else it
        }
    } else {
        carrito + ItemCarrito(producto = producto, cantidad = cantidad)
    }
}

private data class PedidoConfirmado(
    val numero: String,
    val total: Double,
    val direccion: String
)

private fun generarNumeroPedido(): String =
    "#%05d".format(Random.nextInt(100000))