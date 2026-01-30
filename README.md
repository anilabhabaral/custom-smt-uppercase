# Custom SMT to convert a string field from lowercase to uppercase.

Configuration:
```
  "transforms": "CapName",
  "transforms.CapName.type": "com.anilabha.kafka.connect.transforms.CapitalizeField",
  "transforms.CapName.field.name": "name"
```
Example:
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
