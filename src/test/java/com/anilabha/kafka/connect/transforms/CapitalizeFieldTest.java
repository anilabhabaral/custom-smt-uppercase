package com.anilabha.kafka.connect.transforms;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CapitalizeFieldTest {

    private CapitalizeField<SourceRecord> xform = new CapitalizeField<>();

    @Before
    public void setUp() {
        // Configure the SMT to look for the field "name"
        Map<String, String> props = new HashMap<>();
        props.put("field.name", "name");
        xform.configure(props);
    }

    @After
    public void tearDown() {
        xform.close();
    }

    @Test
    public void testSchemalessRecord() {
        // Simulating JSON without schema
        Map<String, Object> value = new HashMap<>();
        value.put("name", "john doe");
        value.put("city", "london");

        SourceRecord record = new SourceRecord(null, null, "test-topic", 0, null, value);
        SourceRecord transformedRecord = xform.apply(record);

        @SuppressWarnings("unchecked")
        Map<String, Object> updatedValue = (Map<String, Object>) transformedRecord.value();

        // Note: JUnit 4 signature is Assert.assertEquals(message, expected, actual)
        Assert.assertEquals("The 'name' field should be capitalized", "JOHN DOE", updatedValue.get("name"));
        Assert.assertEquals("Other fields should remain untouched", "london", updatedValue.get("city"));
    }

    @Test
    public void testRecordWithSchema() {
        // Simulating Avro/JSON with schema
        Schema schema = SchemaBuilder.struct()
                .field("name", Schema.STRING_SCHEMA)
                .field("city", Schema.STRING_SCHEMA)
                .build();

        Struct value = new Struct(schema);
        value.put("name", "jane doe");
        value.put("city", "paris");

        SourceRecord record = new SourceRecord(null, null, "test-topic", 0, schema, value);
        SourceRecord transformedRecord = xform.apply(record);

        Struct updatedValue = (Struct) transformedRecord.value();

        Assert.assertEquals("The 'name' field should be capitalized", "JANE DOE", updatedValue.getString("name"));
        Assert.assertEquals("Other fields should remain untouched", "paris", updatedValue.getString("city"));
    }

    @Test
    public void testMissingField() {
        // When the configured field is missing from the payload
        Map<String, Object> value = new HashMap<>();
        value.put("city", "tokyo");

        SourceRecord record = new SourceRecord(null, null, "test-topic", 0, null, value);
        SourceRecord transformedRecord = xform.apply(record);

        @SuppressWarnings("unchecked")
        Map<String, Object> updatedValue = (Map<String, Object>) transformedRecord.value();

        Assert.assertEquals("tokyo", updatedValue.get("city"));
        Assert.assertNull("Missing fields should not throw an error and remain absent", updatedValue.get("name"));
    }

    @Test
    public void testNonStringField() {
        // When the configured field is missing from the payload, and a non-string field is present
        Map<String, Object> value = new HashMap<>();
        value.put("id", 1);
        value.put("price", 100.123);

        SourceRecord record = new SourceRecord(null, null, "test-topic", 0, null, value);
        SourceRecord transformedRecord = xform.apply(record);

        @SuppressWarnings("unchecked")
        Map<String, Object> updatedValue = (Map<String, Object>) transformedRecord.value();

        Assert.assertEquals("Non String field should remain untouched and same",1, updatedValue.get("id"));
        Assert.assertEquals("Non String field should remain untouched and same",100.123, updatedValue.get("price"));

    }
    
    @Test
    public void testNullValueRecord() {
        // Tombstone messages / null payloads (e.g., topic compaction deletes)
        SourceRecord record = new SourceRecord(null, null, "test-topic", 0, null, null);
        SourceRecord transformedRecord = xform.apply(record);
        
        Assert.assertNull("Null records should be handled gracefully", transformedRecord.value());
    }
}