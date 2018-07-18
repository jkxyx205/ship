package com.yodean.dictionary;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.yodean.dictionary.entity.Dict;

import java.io.IOException;

/**
 * Created by rick on 7/17/18.
 */
public class DictDeserializer extends JsonDeserializer<Dict> {
    @Override
    public Dict deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        return DictionaryUtils.idConvert2Dict(Long.parseLong(node.asText()));
    }
}
