## Apilogix

####JAVA lightweight libraries intended to be used in lambda functions

#XML-Parser

## Gradle
```groovy
compile group: 'io.github.apilogic-io', name: 'xml-parser', version: '0.1'
```

### Usage
```java
// get the xml url and the config url
 var url = getClass().getClassLoader().getResource("./samples/countries.xml");
 var config = getClass().getClassLoader().getResource("./configs/countries.yaml");
 var response = XmlParser.parse(url, config);
// the response will be an XML flattened map 
```

### Example

#### Given xml
```xml
<HEADER xmlns="http://www.xxxxx" TimeStamp="xxxx">
    <ReadResponse>
        <Countries>
            <Country CountryCode="30">
                <CountryName>ANDORRA</CountryName>
                <CountryISO>AD</CountryISO>
            </Country>
            <Country CountryCode="44">
                <CountryName>UNITED ARAB EMIRATES</CountryName>
                <CountryISO>AE</CountryISO>
            </Country>
        </Countries>
    </ReadResponse>
</HEADER>
```

#### When configuration applied:

```yaml
contextPath: "ReadResponse"
arrayPaths:
  - "HEADER/ReadResponse/Countries"
```

#### Then the parsed result will be:
```json
{
  "Countries": [
    {
      "CountryCode": "30",
      "CountryName": "ANDORRA",
      "CountryISO": "AD"
    },
    {
      "CountryCode": "44",
      "CountryName": "UNITED ARAB EMIRATES",
      "CountryISO": "AE"
    }
  ]
}
```