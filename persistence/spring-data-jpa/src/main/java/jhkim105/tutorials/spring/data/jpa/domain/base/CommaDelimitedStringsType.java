package jhkim105.tutorials.spring.data.jpa.domain.base;

import java.util.List;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

public class CommaDelimitedStringsType extends AbstractSingleColumnStandardBasicType<List> {

    public CommaDelimitedStringsType() {
        super(
            VarcharTypeDescriptor.INSTANCE,
            CommaDelimitedStringsJavaTypeDescriptor.INSTANCE
        );
    }

    @Override
    public String getName() {
        return "comma_delimited_strings";
    }
}