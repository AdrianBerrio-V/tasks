package com.example.tasks.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasks.R

@Preview(showBackground = true)
@Composable
fun ProfileScreen () {
    Column(
        modifier = Modifier.fillMaxSize().padding( top = 30.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Perfil",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth()
                )

                Image(
                    painter = painterResource(R.drawable.test_image_profile),//ToDo que sea dinamico
                    contentDescription = "Perfil_image",
                    modifier = Modifier
                        .size(130.dp)
                        .clip(CircleShape)
                        .border(BorderStroke(4.dp, Color.Black), CircleShape)
                )

                Text(
                    text = "Juanito Alvares",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth()
                )
            }

            Column (
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
            ){
                Text(
                    text = "Cuenta",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )

                ProfileMenuItem(
                    iconRes = R.drawable.ic_mail,
                    title = "Email",
                    subtitle = "prueba@gmail.com",
                    onClick = {/*ToDo que haga algo a darle click*/}
                )

                ProfileMenuItem(
                    iconRes = R.drawable.ic_padlock,
                    title = "Contraseña",
                    subtitle = "***********",
                    onClick = {}
                )

                Text(
                    text = "Configuraciones",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                )

                ProfileMenuItem(
                    iconRes = R.drawable.ic_notifications,
                    title = "Notificaciones",
                    onClick = {/*ToDo abrir notificaciones*/}
                )

                ProfileMenuItem(
                    iconRes = R.drawable.ic_language,
                    title = "Lenguaje",
                    onClick = {/*ToDo abrir gestor lengua*/}
                )
            }
        }

        Button(
            onClick = {/*ToDo cerrar Sesion */},
            modifier = Modifier
                .padding(start = 50.dp, end = 50.dp, bottom = 25.dp )
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.primary_blue_dark),
                contentColor = colorResource(R.color.text_primary)
            ),
            shape = RoundedCornerShape(8.dp)
            ) {
            Text(
                text = "Cerrar Sesión",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ProfileMenuItem(
    iconRes: Int,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = Color.Black
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            subtitle?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}