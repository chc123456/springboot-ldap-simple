package cn.fintecher.simple.demo.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.ldap.support.LdapUtils;

import javax.naming.Name;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by ChenChang on 2018/8/12.
 */
public class LdapNameSerializer extends StdSerializer<Name> {
    public final static LdapNameSerializer instance = new LdapNameSerializer();

    public LdapNameSerializer() {
        super(Name.class);
    }


    @Override
    public void serialize(Name value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (null == value) {
            //write the word 'null' if there's no value available
            gen.writeNull();
        } else {

           gen.writeString(value.toString());

        }
    }
}
