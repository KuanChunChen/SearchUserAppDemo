package elegant.access.apidemo.legacy.jsoner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;

public class Jsoner {

    private static Jsoner sInstance;
    static GsonBuilder gsonBuilder;
    public static Jsoner getInstance() {
        if (sInstance == null) {
            sInstance = new Jsoner();
        }
        return sInstance;
    }

    static {
//        gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(Map.class, new MapDeserializer()).registerTypeAdapter(List.class, new ListDeserializer()).setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
//
//    }
        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Boolean.class, (JsonDeserializer<Boolean>) (json, typeOfT, context) -> {
//            System.out.println("===="+json.getAsBoolean());
            try {
                return json.getAsBoolean();
            } catch (IllegalStateException | UnsupportedOperationException e) {
                return false;
            }
        }).registerTypeAdapter(String.class, (JsonDeserializer<String>) (json, typeOfT, context) -> {
//            System.out.println("===="+json.getAsString());
            try {
                return json.getAsString();
            } catch (IllegalStateException | UnsupportedOperationException e) {
                return "empty";
            }
        }).registerTypeAdapter(Integer.class, (JsonDeserializer<Integer>) (json, typeOfT, context) -> {
            try {
                return json.getAsInt();
            } catch (  NumberFormatException | UnsupportedOperationException | IllegalStateException e ) {

                return -1;
            }
        }).registerTypeAdapter(Float.class, (JsonDeserializer<Float>) (json, typeOfT, context) -> {
            try {
                return json.getAsFloat();
            } catch (  NumberFormatException | UnsupportedOperationException | IllegalStateException e ) {
                return -1f;
            }
        }).registerTypeAdapter(Double.class, (JsonDeserializer<Double>) (json, typeOfT, context) -> {
            try {
                return json.getAsDouble();
            } catch ( NumberFormatException | UnsupportedOperationException | IllegalStateException e ) {
                return -1.0;
            }
        }).registerTypeAdapter(Long.class, (JsonDeserializer<Long>) (json, typeOfT, context) -> {
            try {
                return json.getAsLong();
            } catch ( NumberFormatException | UnsupportedOperationException | IllegalStateException e ) {
                return -1L;
            }
        }).registerTypeAdapter(Integer.TYPE, (JsonDeserializer<Integer>) (json, typeOfT, context) -> {
            try {
                return json.getAsInt();
            } catch ( NumberFormatException | UnsupportedOperationException | IllegalStateException e ) {
                return -1;
            }
        });

    }

    private Gson mGson;

    private Jsoner() {
        mGson = new Gson();
    }

    public String toJson(Object obj) {
        return mGson.toJson(obj);
    }

    public <T> T fromJson(String s, Class<T> cls) {
//        gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapterFactory(new MyTypeAdapterFactory());
        mGson = gsonBuilder.create();

        return mGson.fromJson(s, cls);
    }

    public <T> T fromJson(JsonReader json, Class<T> cls) {
        return mGson.fromJson(json, cls);
    }

    public class Deserializer<M> implements JsonDeserializer<M> {
        @Override
        public M deserialize (JsonElement jsonElement, Type typeOfM, JsonDeserializationContext context) throws JsonParseException {
            JsonObject mJsonObject = jsonElement.getAsJsonObject();
            System.out.println(typeOfM);

            for ( String key : mJsonObject.keySet() ) {
                try {
                    JsonElement value = mJsonObject.get(key);
                    System.out.println("Key : " + key + "  Value " + value);
                } catch ( Exception exception ) {
                    System.out.println("exception: " +exception);

                }
            }
            return new Gson().fromJson(jsonElement, typeOfM);
        }
    }
    public class MyTypeAdapterFactory<T> implements TypeAdapterFactory {

        @SuppressWarnings("unchecked")
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> rawType = (Class<T>) type.getRawType();
            if ( rawType == Float.class || rawType == float.class ) {
                return (TypeAdapter<T>) new FloatNullAdapter();
            } else if ( rawType == Boolean.class || rawType == boolean.class ) {
                return (TypeAdapter<T>) new BooleanNullAdapter();
            } else if ( rawType == Integer.TYPE||rawType == int.class || rawType == Integer.class ) {
                return (TypeAdapter<T>) new IntNullAdapter();
            } else if ( rawType == String.class ) {
                return (TypeAdapter<T>) new StringNullAdapter();
            }
            return null;
        }
    }


    public class IntNullAdapter extends TypeAdapter<Integer> {
        @Override
        public Integer read(JsonReader reader) throws IOException {


            if ( reader.peek() == JsonToken.STRING || reader.peek() == JsonToken.BOOLEAN ||reader.peek() == JsonToken.BEGIN_OBJECT ) {
                reader.skipValue();
                return -1;
            }
            BigDecimal bigDecimal = new BigDecimal(reader.nextInt());
            return bigDecimal.intValue();
        }

        @Override
        public void write(JsonWriter writer, Integer value) throws IOException {
            writer.value(value);
        }
    }


    public class BooleanNullAdapter extends TypeAdapter<Boolean> {
        @Override
        public Boolean read(JsonReader reader) throws IOException {
            if ( reader.peek() == JsonToken.BOOLEAN) {
                return reader.nextBoolean();
            }
            reader.skipValue();
            return false;
        }

        @Override
        public void write(JsonWriter writer, Boolean value) throws IOException {
            writer.value(value);
        }
    }


    public class FloatNullAdapter extends TypeAdapter<Float> {
        @Override
        public Float read(JsonReader reader) throws IOException {
            if ( reader.peek() == JsonToken.STRING || reader.peek() == JsonToken.BOOLEAN ) {
                reader.skipValue();
                return -1f;
            }
            BigDecimal bigDecimal = new BigDecimal(reader.nextString());
            return bigDecimal.floatValue();
        }

        @Override
        public void write(JsonWriter writer, Float value) throws IOException {
            writer.value(value);
        }
    }

    public static class StringNullAdapter extends TypeAdapter<String> {
        @Override
        public String read(JsonReader reader) throws IOException {
            if ( reader.peek() == JsonToken.STRING ) {
                return reader.nextString();
            }
            reader.skipValue();
            return "0";
        }

        @Override
        public void write(JsonWriter writer, String value) throws IOException {
            writer.value(value);
        }
    }



}





