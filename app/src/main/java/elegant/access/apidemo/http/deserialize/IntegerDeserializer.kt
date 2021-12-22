package elegant.access.apidemo.http.deserialize

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.IllegalStateException
import java.lang.NumberFormatException
import java.lang.UnsupportedOperationException
import java.lang.reflect.Type

class IntegerDeserializer : JsonDeserializer<Int> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Int {
        return try {
            json.asInt
        } catch (e: NumberFormatException) {
            -1
        } catch (e: UnsupportedOperationException) {
            -1
        } catch (e: IllegalStateException) {
            -1
        }
    }
}