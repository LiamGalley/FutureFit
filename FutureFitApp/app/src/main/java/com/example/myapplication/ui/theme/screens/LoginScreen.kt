package com.example.myapplication.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.Database.DatabaseViewModel
import com.example.myapplication.data.Entities.Account

@Composable
fun LoginScreen(dbViewModel: DatabaseViewModel, onRegistrationSuccess:(value: Account)->Unit){

    var login by remember { mutableStateOf(true) }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Login",
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(100.dp))
        )

        if(login)
        {
            Login({login = !login}, onRegistrationSuccess,dbViewModel)
        }
        else
        {
            Register({login = !login}, onRegistrationSuccess,dbViewModel)
        }

    }

}
@Composable
fun Login(callBack:()->Unit,onRegistrationSuccess:(value: Account)->Unit,dbViewModel: DatabaseViewModel)
{

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Text(text = "Welcome Back", fontSize = 28.sp, fontWeight = FontWeight.Bold)

    Spacer(modifier = Modifier.height(4.dp))

    Text(text = "Login to your account")

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(value = email, onValueChange = {email = it}, label = {Text(text = "Email adress")})

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(value = password, onValueChange = {password = it}, label = {Text(text = "Password")})

    Spacer(modifier = Modifier.height(16.dp))

    var current = LocalContext.current

    val accountList by dbViewModel.getAccountByEmail(email).observeAsState(emptyList())

    Button(onClick = {

        val account = accountList.firstOrNull()

        if (account != null && account.password == password && account.emailAddress == email) {
            onRegistrationSuccess(account)
        } else {
            Toast.makeText(current, "Invalid email or password", Toast.LENGTH_SHORT).show()
        }
    }){
        Text(text = "Login")
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row()
    {
        Text(text = "Don't have an account yet? ")
        Text(text = "Register", modifier = Modifier.clickable {callBack()}, color= Color.Cyan)
    }

}

@Composable
fun Register(callBack:()->Unit,onRegistrationSuccess:(Account)->Unit,dbViewModel: DatabaseViewModel)
{
    var email by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("")  }
    var weight by remember { mutableStateOf("")  }
    var age by remember { mutableStateOf("")  }
    var bodyFat by remember { mutableStateOf("")  }
    var activityLevel by remember { mutableStateOf("") }

    Text(text = "Hi!", fontSize = 28.sp, fontWeight = FontWeight.Bold)

    Spacer(modifier = Modifier.height(4.dp))

    Text(text = "Create an account")

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(value = firstName, onValueChange = {firstName = it}, label = {Text(text = "First Name")})

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(value = lastName, onValueChange = {lastName = it}, label = {Text(text = "LastName")})

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(value = email, onValueChange = {email = it}, label = {Text(text = "Email adress")})

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(value = height, onValueChange = {height = it}, label = {Text(text = "Height")})

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(value = weight, onValueChange = {weight = it}, label = {Text(text = "Weight")})

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(value = age, onValueChange = {age = it}, label = {Text(text = "Age")})

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(value = activityLevel, onValueChange = {activityLevel = it}, label = {Text(text = "Activity Level")})

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(value = bodyFat, onValueChange = {bodyFat = it}, label = {Text(text = "BodyFat")})

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(value = password, onValueChange = {password = it}, label = {Text(text = "Password")})

    Spacer(modifier = Modifier.height(16.dp))

    var current = LocalContext.current
    val accountList = dbViewModel.getAccountByEmail(email).observeAsState(emptyList())

    Button(onClick = {
        var check = false

        if (email != "" && firstName != "" && lastName != "" && password != "" && accountList.value.size == 0 && height.toDoubleOrNull() != null && weight.toDoubleOrNull() != null && activityLevel.toIntOrNull() != null && bodyFat.toIntOrNull() != null) {
            dbViewModel.upsertAccountFromUI(Account(firstName, lastName, email, password, height.toDouble(),weight.toDouble(),age.toInt(),activityLevel.toInt(),bodyFat.toInt())) { newAccount ->
                onRegistrationSuccess(newAccount)
            }

        } else {
            Toast.makeText(current, "Please create a valid user", Toast.LENGTH_SHORT).show()
        }
    }){
        Text(text = "Register")
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row()
    {
        Text(text = "Already have an account? ")
        Text(text = "Login", modifier = Modifier.clickable {callBack()}, color= Color.Cyan)
    }

    Spacer(modifier = Modifier.height(20.dp))
}