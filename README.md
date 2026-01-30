# Custom SMT to convert a string field from lowercase to uppercase.

### Step-by-step process to install your custom SMT:

1. Download SMT jar:
```
wget https://github.com/anilabhabaral/custom-smt-uppercase/releases/download/1.0/custom-smt-uppercase-1.0.0.jar
```
2. Identify the plugin.path directory in your connect-distributed.properties file.

3. Create a new folder inside that plugin path (e.g., /plugins/custom-uppercase-smt/).

4. Copy your JAR file directly into that specific new folder.

5. Restart all of your Kafka Connect worker processes.

6. Verify the install by checking the localhost:8083/connector-plugins REST endpoint.

7. Update your connector JSON config to include the new transforms properties.Compile your Java project into a JAR file using Maven or Gradle. Connector Configuration:
```
  "transforms": "CapName",
  "transforms.CapName.type": "com.anilabha.kafka.connect.transforms.CapitalizeField",
  "transforms.CapName.field.name": "name"
```
---

### Example:
- Before:
```
{
  "id": 1,
  "name": "kafka",
  "email": "kafka@apache.org",
  "last_modified": 1769761822000
}
```
- After
```
{
  "id": 1,
  "name": "KAFKA",
  "email": "kafka@apache.org",
  "last_modified": 1769761822000
}
```
