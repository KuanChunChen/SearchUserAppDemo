package elegant.access.apidemo.http.deserialize

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.IllegalStateException
import java.lang.NumberFormatException
import java.lang.UnsupportedOperationException
import java.lang.reflect.Type

class LongDeserializer : JsonDeserializer<Long> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Long {
        return try {
            json.asLong
        } catch (e: NumberFormatException) {
            -1L
        } catch (e: UnsupportedOperationException) {
            -1L
        } catch (e: IllegalStateException) {
            -1L
        }
    }
}