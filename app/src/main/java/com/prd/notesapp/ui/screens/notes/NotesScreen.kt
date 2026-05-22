package com.prd.notesapp.ui.screens.notes

import android.R.attr.scaleX
import android.R.attr.scaleY
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.prd.notesapp.data.model.Note

@Composable
fun NotesScreen(
    notes: List<Note>,
    onNoteClick: (Note) -> Unit
) {

    if (notes.isEmpty()) {
        AnimatedEmptyState()
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                start = 8.dp,
                end = 8.dp,
                top = 8.dp,
                bottom = 8.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Top),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(notes) { note ->
                NoteCard(note = note, onClick = { onNoteClick(note) })
            }
        }
    }

}

@Composable
fun NoteCard(
    note: Note,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .padding(2.dp)
            .heightIn(200.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (!note.isLocked) {
                    Text(
                        note.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1
                    )
                } else {
                    Text(
                        if (note.title.isNotEmpty()) note.title.take(1) + ".hidden" else "Untitled.hidden",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1
                    )
                }
                if (note.isLocked) {
                    Icon(Icons.Default.Lock, contentDescription = "Locked")
                }
            }

            Spacer(Modifier.height(4.dp))

            if (!note.isLocked) {
                Text(
                    text = note.content.take(50) + if (note.content.length > 50) "..." else "",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 5
                )
            } else {
                Text(
                    text = "Access is Restricted",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1
                )
            }

        }
    }
}


@Composable
fun AnimatedEmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // animation : scale
        val scale by rememberInfiniteTransition().animateFloat(
            initialValue = 1f,
            targetValue = 1.1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse
            )
        )
        Icon(
            imageVector = Icons.Default.Description,
            contentDescription = "AddNote",
            modifier = Modifier
                .size(64.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                },
            tint = Color(0xFFF9D74C) // Windows File Explorer yellow
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "No notes yet. Tap + to add one",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesTopBar(
    onSearchClick: () -> Unit = { },
    onMenuClick: () -> Unit = { },
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text("Notes List") },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Settings") },
                    onClick = {
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("About") },
                    onClick = onMenuClick
                )
            }
        },
        windowInsets = WindowInsets.statusBars // Evitar que el TopAppBar se superponga a la barra de estado
    )
}