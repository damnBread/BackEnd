package com.example.damnbreadback.config;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GsonDateConverter implements JsonSerializer<Date>, JsonDeserializer<Date> {
    private static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT); return src == null ? null : new JsonPrimitive(simpleDateFormat.format(src));
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        try { return json == null ? null : simpleDateFormat.parse(json.getAsString()); }
        catch (ParseException e) { throw new JsonParseException(e); }
    }
}