package gov.nist.javax.sip.header.ims;

import gov.nist.core.Separators;
import gov.nist.javax.sip.header.ParametersHeader;
import java.text.ParseException;
import javax.sip.header.ExtensionHeader;

public class PUserDatabase extends ParametersHeader implements PUserDatabaseHeader, SIPHeaderNamesIms, ExtensionHeader {
    private String databaseName;

    public PUserDatabase(String databaseName2) {
        super("P-User-Database");
        this.databaseName = databaseName2;
    }

    public PUserDatabase() {
        super("P-User-Database");
    }

    @Override // gov.nist.javax.sip.header.ims.PUserDatabaseHeader
    public String getDatabaseName() {
        return this.databaseName;
    }

    @Override // gov.nist.javax.sip.header.ims.PUserDatabaseHeader
    public void setDatabaseName(String databaseName2) {
        if (databaseName2 == null || databaseName2.equals(Separators.SP)) {
            throw new NullPointerException("Database name is null");
        } else if (!databaseName2.contains("aaa://")) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("aaa://");
            stringBuffer.append(databaseName2);
            this.databaseName = stringBuffer.toString();
        } else {
            this.databaseName = databaseName2;
        }
    }

    /* access modifiers changed from: protected */
    @Override // gov.nist.javax.sip.header.ParametersHeader, gov.nist.javax.sip.header.SIPHeader
    public String encodeBody() {
        StringBuffer retval = new StringBuffer();
        retval.append(Separators.LESS_THAN);
        if (getDatabaseName() != null) {
            retval.append(getDatabaseName());
        }
        if (!this.parameters.isEmpty()) {
            retval.append(Separators.SEMICOLON + this.parameters.encode());
        }
        retval.append(Separators.GREATER_THAN);
        return retval.toString();
    }

    @Override // gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject, java.lang.Object
    public boolean equals(Object other) {
        return (other instanceof PUserDatabaseHeader) && super.equals(other);
    }

    @Override // gov.nist.javax.sip.header.ParametersHeader, gov.nist.core.GenericObject, java.lang.Object
    public Object clone() {
        return (PUserDatabase) super.clone();
    }

    @Override // javax.sip.header.ExtensionHeader
    public void setValue(String value) throws ParseException {
        throw new ParseException(value, 0);
    }
}
