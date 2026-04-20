package com.janes.saenz.puerta.usersmanagementapp.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.janes.saenz.puerta.usersmanagementapp.domain.dtos.UsuarioDtos

/**
 * UsuarioItem - Representación de tarjeta para el listado de usuarios.
 *
 * Diseñado bajo los principios de Material Design 3, este componente optimiza
 * la visualización de los datos del usuario, destacando la información principal
 * y usando elementos visuales de soporte (Chips) para metadatos.
 *
 * @param usuario Entidad de datos [Usuario] con la información a mostrar.
 * @param onClick Callback que expone el objeto completo al ser seleccionado.
 */
@Composable
fun UsuarioItem(
    usuario: UsuarioDtos,
    onClick: (Int) -> Unit,
    paddingValues: PaddingValues
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            // El padding exterior define la separación entre tarjetas
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = { onClick(usuario.id) } // Implementación nativa de click en Card (M3)
    ) {
        Column(
            modifier = Modifier
                // Padding interior de la tarjeta
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Nombre: Prioridad visual alta
            Text(
                text = usuario.nombre.ifBlank { "Sin nombre" },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Correo electrónico
            Text(
                text = usuario.email.ifBlank { "Sin correo" },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Footer de la tarjeta: Metadatos técnicos o temporales
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Podríamos usar el chip para mostrar la fecha de creación
                SuggestionChip(
                    onClick = { /* Acción opcional, ej: copiar fecha o ver detalle */ },
                    label = {
                        // Formateamos la fecha si es muy larga, o simplemente la mostramos
                        Text("Ingreso: ${usuario.fechaCreacion.take(10)}")
                    }
                )

                Text(
                    text = "ID: ${usuario.id}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}