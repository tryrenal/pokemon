package network

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun testCardServiceCall() {
    val service = CardService(HttpClientFactory().create())

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val result = service.getCards("name:charizard")
            Log.d("CardService", "✅ Card Data: ${result.data.map { it.name }}")
        } catch (e: Exception) {
            println("❌ Error: ${e.message}")
        }
    }
}