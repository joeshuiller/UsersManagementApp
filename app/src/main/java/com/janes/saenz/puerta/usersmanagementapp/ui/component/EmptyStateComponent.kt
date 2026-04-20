package com.janes.saenz.puerta.usersmanagementapp.ui.component
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.janes.saenz.puerta.usersmanagementapp.ui.utils.UIConstants

/**
 * EmptyStateComponent - Interfaz de retroalimentación para estados sin datos o errores.
 *
 * Este componente centraliza la experiencia de usuario cuando las consultas
 * no retornan resultados o fallan, proporcionando una estética coherente con Material Design 3.
 *
 * @param title Título principal del estado (Ej: "Sin usuarios", "Error de red").
 * @param message Texto descriptivo que explica por qué la vista está vacía.
 * @param icon Ícono representativo del estado actual.
 * @param actionText Texto del botón de recuperación (Ej: "Recargar", "Reintentar").
 * @param onAction Acción opcional para reintentar o limpiar estados. Si es null, el botón se oculta.
 */
@Composable
fun EmptyStateComponent(
    title: String = "No hay usuarios",
    message: String = "Actualmente no existen usuarios registrados en el sistema.",
    icon: ImageVector = Icons.Default.Group, // Cambiado a un ícono de grupo/usuarios por defecto
    actionText: String = "Actualizar",
    onAction: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center, // Centrado vertical para foco visual
        horizontalAlignment = Alignment.CenterHorizontally // Centrado horizontal
    ) {
        // Elemento visual primario: Dinámico según el contexto
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(80.dp),
            // Tonalidad suavizada para no sobrecargar visualmente al usuario
            tint = MaterialTheme.colorScheme.primary.copy(alpha = UIConstants.SHIMMER_ALPHA_HIGH)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Título de estado: Mensaje de alto nivel parametrizado
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Mensaje de soporte: Provee contexto adicional o instrucciones
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Estrategia de recuperación (Call to Action) genérica
        if (onAction != null) {
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onAction,
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Text(actionText)
            }
        }
    }
}