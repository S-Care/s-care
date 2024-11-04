package com.scare.ui.mobile.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.work.Configuration
import coil.compose.AsyncImage
import com.scare.R

@Composable
fun TheHeader (
    imageUrl: String? = null,
    isMainPage: Boolean = false,
    navController: NavController,
    onBackClick: () -> Unit = {},
    onProfileClick: () -> Unit = { navController.navigate("mypage")},
    onLogoClick: () -> Unit = { navController.navigate("main")}
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isMainPage && imageUrl != null) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "User Image",
                modifier = Modifier.size(48.dp).fillMaxWidth()
                                    .clickable(onClick = onProfileClick)
            )
        } else {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Back")
            }
        }

        Row {
            Logo(modifier = Modifier.weight(1f).clickable(onClick = onLogoClick))
            // 오른쪽에 비어있는 공간
            Spacer(modifier = Modifier.size(16.dp)) // 아이콘 크기와 동일한 크기의 Spacer
        }

    }
}

@Composable
fun Logo (modifier: Modifier = Modifier) {
    val logoImage = if (isSystemInDarkTheme()) {
        painterResource(R.drawable.logo_white) // 다크 모드: 흰색 로고
    } else {
        painterResource(R.drawable.logo_black) // 라이트 모드: 검은색 로고
    }

    Image(
        painter = logoImage,
        contentDescription = "Logo",
        modifier = Modifier.width(80.dp) // 가로 크기 200dp로 조정
                            .height(60.dp) // 세로 크기 60dp로 고정 // 크기 조정
    )
}