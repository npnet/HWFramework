package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.ParametersHeader;
import java.text.ParseException;

public class AddressParametersParser extends ParametersParser {
    protected AddressParametersParser(Lexer lexer) {
        super(lexer);
    }

    protected AddressParametersParser(String buffer) {
        super(buffer);
    }

    /* access modifiers changed from: protected */
    public void parse(AddressParametersHeader addressParametersHeader) throws ParseException {
        dbg_enter("AddressParametersParser.parse");
        try {
            addressParametersHeader.setAddress(new AddressParser(getLexer()).address(false));
            this.lexer.SPorHT();
            char la = this.lexer.lookAhead(0);
            if (!this.lexer.hasMoreChars() || la == 0 || la == '\n' || !this.lexer.startsId()) {
                super.parse((ParametersHeader) addressParametersHeader);
            } else {
                super.parseNameValueList(addressParametersHeader);
            }
            dbg_leave("AddressParametersParser.parse");
        } catch (ParseException ex) {
            throw ex;
        } catch (Throwable th) {
            dbg_leave("AddressParametersParser.parse");
            throw th;
        }
    }
}
