package com.ajmir.mybank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ajmir.mybank.ui.theme.MyBankTheme
import com.ajmir.ui.commons.resources.Colors
import com.ajmir.ui.home.HomeScreen
import com.ajmir.ui.transaction.TransactionDetailScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyBankTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = HOME_ROUTE) {
                        composable(HOME_ROUTE) {
                            HomeScreen(
                                onTransactionClicked = { id ->
                                    navController.navigate("$TRANSACTION_PREFIX_ROUTE/$id")
                                }
                            )
                        }
                        composable(
                            route = TRANSACTION_ROUTE,
                            arguments = listOf(
                                navArgument(TRANSACTION_ID_ARG) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                }
                            )
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getString(TRANSACTION_ID_ARG)!!
                            TransactionDetailScreen(
                                id = id,
                                onClose = { navController.popBackStack() }
                            )
                        }

                }
            }
        }
    }
    }

    companion object {
        const val HOME_ROUTE = "home"
        const val TRANSACTION_ID_ARG = "id"
        const val TRANSACTION_PREFIX_ROUTE = "transaction"
        const val TRANSACTION_ROUTE = "$TRANSACTION_PREFIX_ROUTE/{id}"
    }
}
