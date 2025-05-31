package com.example.proje

import android.graphics.Rect
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import coil.compose.AsyncImage

@Composable
fun MainScreen(navController: NavController) {
    val items = remember {
        mutableStateListOf(
            Item(
                "Apple",
                "iPhone 16e 128GB Beyaz",
                "https://cdn.dsmcdn.com/ty1643/product/media/images/prod/PIM/20250221/05/0e03f543-4746-4000-ab00-cfbaf07a5d5b/1_org_zoom.jpg"
            ),
            Item(
                "Samsung",
                "Galaxy A06 128GB Siyah Cep Telefonu (Samsung Türkiye Garantili)",
                "https://cdn.dsmcdn.com/ty1535/product/media/images/prod/PIM/20240910/09/ffcc4116-048e-41d9-a714-a29706f97c84/1_org_zoom.jpg"
            ),
            Item(
                "Xiaomi",
                "Redmi Note 14 8GB RAM 256GB ROM, Siyah",
                "https://cdn.dsmcdn.com/ty1634/prod/QC/20250204/20/30ecf131-7308-30d4-9359-5758b52d14d1/1_org_zoom.jpg"
            )
        )
    }

    var selectedTab by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        when (selectedTab) {
            0 -> {
                AndroidView(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    factory = { context ->
                        RecyclerView(context).apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter = ItemAdapter(items)
                            addItemDecoration(object : RecyclerView.ItemDecoration() {
                                override fun getItemOffsets(
                                    outRect: Rect,
                                    view: View,
                                    parent: RecyclerView,
                                    state: RecyclerView.State
                                ) {
                                    outRect.bottom = 16
                                }
                            })
                            val itemTouchHelper =
                                ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                                    ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0
                                ) {
                                    override fun onMove(
                                        recyclerView: RecyclerView,
                                        viewHolder: RecyclerView.ViewHolder,
                                        target: RecyclerView.ViewHolder
                                    ): Boolean {
                                        val fromPos = viewHolder.adapterPosition
                                        val toPos = target.adapterPosition
                                        items.apply {
                                            add(toPos, removeAt(fromPos))
                                        }
                                        adapter?.notifyItemMoved(fromPos, toPos)
                                        return true
                                    }

                                    override fun onSwiped(
                                        viewHolder: RecyclerView.ViewHolder,
                                        direction: Int
                                    ) {
                                    }
                                })
                            itemTouchHelper.attachToRecyclerView(this)
                        }
                    }
                )
            }

            1 -> {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "", style = MaterialTheme.typography.headlineMedium)
                    AsyncImage(
                        model= "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcROuHwRNrAQNvXadAzeBreowxaeAb0vSU1KRg&s",
                        contentDescription= "Hoşgeldin"
                    )

                }
            }
        }

        NavigationBar {
            NavigationBarItem(
                icon = { Icon(Icons.Filled.List, contentDescription = "Liste") },
                label = { Text("Liste") },
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Person, contentDescription = "Profil") },
                label = { Text("Profil") },
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 }
            )
        }
    }
}