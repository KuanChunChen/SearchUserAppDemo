package elegant.access.apidemo.http.deserialize

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.IllegalStateException
import java.lang.NumberFormatException
import java.lang.UnsupportedOperationException
import java.lang.reflect.Type

class FloatDeserializer : JsonDeserializer<Float> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Float {
        return try {
            json.asFloat
        } catch (e: NumberFormatException) {
            -1f
        } catch (e: UnsupportedOperationException) {
            -1f
        } catch (e: IllegalStateException) {
            -1f
        }
    }
}