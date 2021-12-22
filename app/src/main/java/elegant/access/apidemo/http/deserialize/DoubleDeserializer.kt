package elegant.access.apidemo.http.deserialize

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.IllegalStateException
import java.lang.NumberFormatException
import java.lang.UnsupportedOperationException
import java.lang.reflect.Type

class DoubleDeserializer : JsonDeserializer<Double> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Double {
        return try {
            json.asDouble
        } catch (e: NumberFormatException) {
            -1.0
        } catch (e: UnsupportedOperationException) {
            -1.0
        } catch (e: IllegalStateException) {
            -1.0
        }
    }
}