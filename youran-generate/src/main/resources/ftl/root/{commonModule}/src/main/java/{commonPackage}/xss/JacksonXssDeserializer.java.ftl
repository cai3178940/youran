<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "com.fasterxml.jackson.core.JsonParser"/>
<@import "com.fasterxml.jackson.databind.DeserializationContext"/>
<@import "com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer"/>
<@import "com.fasterxml.jackson.databind.deser.std.StringDeserializer"/>
<@import "com.fasterxml.jackson.databind.jsontype.TypeDeserializer"/>
<@import "java.io.IOException"/>
<@classCom "jackson防XSS反序列化器"/>
public class JacksonXssDeserializer extends StdScalarDeserializer<String> {

    public JacksonXssDeserializer() {
        super(String.class);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = StringDeserializer.instance.deserialize(p, ctxt);
        return XSSUtil.clean(value);
    }
    @Override
    public String deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        String value = StringDeserializer.instance.deserializeWithType(jp, ctxt, typeDeserializer);
        return XSSUtil.clean(value);
    }

    @Override
    public boolean isCachable() {
        return StringDeserializer.instance.isCachable();
    }

}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.xss;

<@printImport/>

${code}
