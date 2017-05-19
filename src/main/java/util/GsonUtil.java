package util;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by BASA on 2017/5/14.
 */
public class GsonUtil {

    private static Gson gson = new Gson();

    private static Gson gson_queue = null;


    static {
        // 让Gson能够解析timestamp格式的json字符串
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
            }
        });

        gson_queue = builder.create();
    }

    private GsonUtil() {

    }



    public static Gson useGson() {
        return gson;
    }


    public static Gson useGsonQueue() {
        return gson_queue;
    }

}
