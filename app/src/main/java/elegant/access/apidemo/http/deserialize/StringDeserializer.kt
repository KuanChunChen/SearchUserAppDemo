package elegant.access.apidemo.http.deserialize

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.IllegalStateException
import java.lang.UnsupportedOperationException
import java.lang.reflect.Type


class StringDeserializer : JsonDeserializer<String> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): String {
        return try {
            json.asString
        } catch (e: IllegalStateException) {
            ""
        } catch (e: UnsupportedOperationException) {
            ""
        }
    }
}