package com.brz.app.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.brz.app.Route
import com.brz.app.model.GitHubUser
import com.brz.app.ui.theme.Pastel
import com.brz.app.vm.GitHubUsersViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(navController: NavHostController, vm: GitHubUsersViewModel = viewModel()) {

    var inputText by remember { mutableStateOf("") }
    val users by vm.gitHubUsers.observeAsState()
    val isLoading by vm.isLoading.observeAsState()
    

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(8.dp))

            TextField(
                label = { Text("Search") },
                value = inputText,
                onValueChange = {
                    inputText = it
                    vm.getGitHubUsers(inputText)
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(8.dp))

            AnimatedVisibility(visible = vm.errorMessage != ""){
                vm.isLoading.value = false
                Text(text = "Error: ${vm.errorMessage}")
            }

            AnimatedVisibility(isLoading!!) {
                Row(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator()
                }
            }

            AnimatedVisibility(visible = users != null) {
                LazyColumn {
                    items(users!!) { item ->
                        Row(Modifier.animateItemPlacement()) {
                            ItemView(
                                item,
                                onClick = {
                                    navController.navigate(Route.DetailScreen.ofUser(item.login))
                                }
                            )
                        }
                    }
                }
            }
        }
    }


}

@Composable
fun ItemView(user: GitHubUser, onClick: () -> Unit = {}) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick() }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    Modifier
                        .height(150.dp)
                        .width(150.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Pastel,
                    ),


                    ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        SubcomposeAsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            model = user.avatar_url,
                            loading = {
                                CircularProgressIndicator()
                            },
                            contentDescription = "",
                            alignment = Alignment.Center,
                            contentScale = ContentScale.FillHeight
                        )
                    }

                }


                Spacer(modifier = Modifier.padding(8.dp))
                Column() {
                    Text(text = user.login)

                    Text(text = "Repositories.")
                }


            }
        }
    }
}

