package backend;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ViolationsJson
{
    private Map<String, String> violations = new HashMap<>();

    @JsonAnyGetter
    public Map<String, String> violationsMap() {
        return violations;
    }
}



/*Map<String, String> map = new HashMap<>();
        map.put("key", "value");

        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = mapper.writerWithDefaultPrettyPrinter()
        .writeValueAsString(map);

public class MyPairSerializer extends JsonSerializer<MyPair> {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void serialize(MyPair value,
                          JsonGenerator gen,
                          SerializerProvider serializers)
            throws IOException, JsonProcessingException {

        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, value);
        gen.writeFieldName(writer.toString());
    }

    map = new HashMap<>();
    MyPair key = new MyPair("Abbott", "Costello");
map.put(key, "Comedy");

    String jsonResult = mapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(map);

    @JsonSerialize(keyUsing = MapSerializer.class)
    Map<MyPair, MyPair> map;

    @JsonSerialize(keyUsing = MyPairSerializer.class)
    MyPair mapKey;

    @JsonSerialize(keyUsing = MyPairSerializer.class)
    MyPair mapValue;

    public MyPair(String both) {
        String[] pairs = both.split("and");
        this.first = pairs[0].trim();
        this.second = pairs[1].trim();
    }*/