package com.brz.app.ui.screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import com.brz.app.R
import com.brz.app.model.Repository
import com.brz.app.ui.theme.Pastel
import com.brz.app.vm.GetRepoViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(user : String, vm : GetRepoViewModel = viewModel()){

    val repos by vm.repositories.observeAsState()
    val isLoaded by vm.isLoading.observeAsState()

    if(!isLoaded!!){
        vm.getRepositories(user)
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(16.dp))
            AnimatedVisibility(visible = repos == null) {
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
            Spacer(modifier = Modifier.padding(16.dp))

            AnimatedVisibility(visible = repos != null) {
                LazyColumn {
                    items(repos!!) { item ->
                        Row(Modifier.animateItemPlacement()) {
                            DetailView(
                                item
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun DetailView(repo : Repository){
    Column(
        Modifier
            .fillMaxWidth()
            .padding(4.dp)
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
                        .height(32.dp)
                        .width(32.dp),
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
                        Image(painter = painterResource(id = R.drawable.baseline_library_books_24), contentDescription = "")
                    }

                }

                Column(
                    Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth()
                ) {

                    Text(text = repo.name)
                    repo.description?.let { Text(text = it) }
                }

            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Divider(modifier = Modifier.fillMaxWidth())
            }

        }
    }
}