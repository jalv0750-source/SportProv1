package com.example.sportpro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

data class FootballPost(
    val id: String,
    val authorName: String,
    val authorProfileUrl: String,
    val timeAgo: String,
    val textContent: String,
    val imageUrl: String,
    val initialLikes: Int,
    val commentsCount: Int,
    val sharesCount: Int,
)

@Composable
fun HomeScreenFeed(
    modifier: Modifier = Modifier,
    userName: String = "Juan",
) {
    val samplePosts = remember {
        listOf(
            FootballPost(
                id = "1",
                authorName = "Liga SportPro",
                authorProfileUrl = "https://images.unsplash.com/photo-1522778119026-d647f0596c20?auto=format&fit=crop&w=200&q=80",
                timeAgo = "Hace 2 horas",
                textContent = "¡Partidazo este fin de semana! La final de la Cantera Local se disputará en el estadio principal este domingo a las 4:00 PM. ¡No te lo pierdas!",
                imageUrl = "https://images.unsplash.com/photo-1522778119026-d647f0596c20?w=800",
                initialLikes = 142,
                commentsCount = 28,
                sharesCount = 15,
            ),
            FootballPost(
                id = "2",
                authorName = "Club Deportivo Los Rayos",
                authorProfileUrl = "https://images.unsplash.com/photo-1574629810360-7efbbe195018?w=800",
                timeAgo = "Hace 5 horas",
                textContent = "Entrenamiento intenso de nuestro equipo principal afinando detalles tácticos para el gran torneo de la categoría libre.",
                imageUrl = "https://images.unsplash.com/photo-1518091043644-c1d4457512c6?auto=format&fit=crop&w=800&q=80",
                initialLikes = 89,
                commentsCount = 12,
                sharesCount = 6,
            ),
            FootballPost(
                id = "3",
                authorName = "Academia de Fútbol Base",
                authorProfileUrl = "https://images.unsplash.com/photo-1517466787929-bc90951d0974?auto=format&fit=crop&w=200&q=80",
                timeAgo = "Hace 1 día",
                textContent = "Iniciamos las inscripciones para los entrenamientos de menores. Formamos a los futuros talentos del deporte rey. ¡Súmate!",
                imageUrl = "https://images.unsplash.com/photo-1574629810360-7efbbe195018?auto=format&fit=crop&w=800&q=80",
                initialLikes = 215,
                commentsCount = 45,
                sharesCount = 33,
            ),
        )
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Encabezado
        item {
            FeedHeader(userName = userName)
        }

        // Publicaciones de fútbol
        items(
            items = samplePosts,
            key = { it.id },
        ) { post ->
            FootballPostCard(
                post = post,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )
        }

        // Espaciado inferior para la barra de navegación
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun FeedHeader(userName: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Text(
            text = "SportPro",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = "Hola, $userName",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 2.dp),
        )
    }
}

@Composable
fun FootballPostCard(
    post: FootballPost,
    modifier: Modifier = Modifier,
) {
    var isLiked by remember { mutableStateOf(false) }
    var likesCount by remember { mutableIntStateOf(post.initialLikes) }

    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            // Header: Foto de perfil, Nombre de liga/club y tiempo
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(post.authorProfileUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Foto de perfil de ${post.authorName}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape),
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = post.authorName,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = post.timeAgo,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Texto de la noticia / publicación
            Text(
                text = post.textContent,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 20.sp,
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Imagen principal de fútbol
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(post.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Imagen de la noticia de fútbol",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(12.dp)),
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Barra inferior con íconos y contadores
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Me gusta
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = {
                            if (isLiked) {
                                isLiked = false
                                likesCount--
                            } else {
                                isLiked = true
                                likesCount++
                            }
                        },
                    ) {
                        Icon(
                            imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Me gusta",
                            tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    Text(
                        text = likesCount.toString(),
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                // Comentarios
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = { /* Acción comentarios */ }) {
                        Icon(
                            imageVector = Icons.Outlined.ChatBubbleOutline,
                            contentDescription = "Comentarios",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    Text(
                        text = "${post.commentsCount}",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                // Compartir
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = { /* Acción compartir */ }) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = "Compartir",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    Text(
                        text = "${post.sharesCount}",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}
