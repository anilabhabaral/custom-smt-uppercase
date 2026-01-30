package com.anilabha.kafka.connect.transforms;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.transforms.Transformation;
import org.apache.kafka.connect.transforms.util.SimpleConfig;

import java.util.HashMap;
import java.util.Map;

public class CapitalizeField<R extends ConnectRecord<R>> implements Transformation<R> {

    public static final String FIELD_CONFIG = "field.name";

    public static final ConfigDef CONFIG_DEF = new ConfigDef()
            .define(
                    FIELD_CONFIG,
                    ConfigDef.Type.STRING,
                    ConfigDef.NO_DEFAULT_VALUE,
                    ConfigDef.Importance.LOW,
                    "Name of the field in the value to capitalize"
            );

    private String fieldName;



/*
apply() is called for every record that passes through this SMT.

The logic is:

Get the value of the record: record.value().
If there is no value → return record unchanged.
If there is no schema (schemaless JSON), treat it as a Map:
Look up fieldName in the map (e.g. "name").
If that value is a String, convert it to UPPERCASE.
Create a copy of the map with that field changed.
Build a new record with the updated map as value.
If there is a schema (typical for JDBC source/sink, Avro, JSON-with-schema):
Check that the value is a Struct (Connect’s “row” object).
Get fieldName from the Struct (e.g. struct.get("name")).
If that is a String, convert it to UPPERCASE.
Create a new Struct with the same schema.
Copy all fields from the original struct to the new one.
For the target field (e.g. name), put the UPPERCASE version.
Build a new record with the new Struct as value.
Every time it creates a “new record”, it keeps:

Same topic
Same partition
Same key and key schema
Same value schema
Same timestamp
Only the value content (that one field) is changed.
*/

    @Override
    public R apply(R record) {
        Object value = record.value();
        if (value == null) {
            return record;
        }

        // Schemaless Map
        if (record.valueSchema() == null) {
            if (!(value instanceof Map)) {
                return record;
            }

            Map<String, Object> original = (Map<String, Object>) value;
            Object fieldVal = original.get(fieldName);
            if (!(fieldVal instanceof String)) {
                return record;
            }

            Map<String, Object> updated = new HashMap<>(original);
            updated.put(fieldName, toUpper((String) fieldVal));

            return record.newRecord(
                    record.topic(),
                    record.kafkaPartition(),
                    record.keySchema(),
                    record.key(),
                    null,
                    updated,
                    record.timestamp()
            );
        }

        // With schema: Struct
        Schema schema = record.valueSchema();
        if (schema.type() != Schema.Type.STRUCT || !(value instanceof Struct)) {
            return record;
        }

        Struct struct = (Struct) value;
        Object fieldVal = struct.get(fieldName);
        if (!(fieldVal instanceof String)) {
            return record;
        }

        Struct newStruct = new Struct(schema);
        for (Field f : schema.fields()) {
            if (f.name().equals(fieldName)) {
                newStruct.put(f, toUpper((String) fieldVal));
            } else {
                newStruct.put(f, struct.get(f));
            }
        }

        return record.newRecord(
                record.topic(),
                record.kafkaPartition(),
                record.keySchema(),
                record.key(),
                schema,
                newStruct,
                record.timestamp()
        );
    }

    private String toUpper(String s) {
        return (s == null) ? null : s.toUpperCase();
    }

    @Override
    public ConfigDef config() {
        return CONFIG_DEF;
    }

    @Override
    public void configure(Map<String, ?> configs) {
        SimpleConfig simpleConfig = new SimpleConfig(CONFIG_DEF, configs);
        this.fieldName = simpleConfig.getString(FIELD_CONFIG);
    }

    @Override
    public void close() {
        // nothing to close
    }
}