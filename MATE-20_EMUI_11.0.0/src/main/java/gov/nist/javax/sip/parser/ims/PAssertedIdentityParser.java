package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PAssertedIdentity;
import gov.nist.javax.sip.header.ims.PAssertedIdentityList;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.TokenTypes;
import java.text.ParseException;

public class PAssertedIdentityParser extends AddressParametersParser implements TokenTypes {
    public PAssertedIdentityParser(String assertedIdentity) {
        super(assertedIdentity);
    }

    protected PAssertedIdentityParser(Lexer lexer) {
        super(lexer);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public SIPHeader parse() throws ParseException {
        if (debug) {
            dbg_enter("AssertedIdentityParser.parse");
        }
        PAssertedIdentityList assertedIdList = new PAssertedIdentityList();
        try {
            headerName(TokenTypes.P_ASSERTED_IDENTITY);
            PAssertedIdentity pai = new PAssertedIdentity();
            pai.setHeaderName("P-Asserted-Identity");
            super.parse((AddressParametersHeader) pai);
            assertedIdList.add((PAssertedIdentityList) pai);
            this.lexer.SPorHT();
            while (this.lexer.lookAhead(0) == ',') {
                this.lexer.match(44);
                this.lexer.SPorHT();
                PAssertedIdentity pai2 = new PAssertedIdentity();
                super.parse((AddressParametersHeader) pai2);
                assertedIdList.add((PAssertedIdentityList) pai2);
                this.lexer.SPorHT();
            }
            this.lexer.SPorHT();
            this.lexer.match(10);
            return assertedIdList;
        } finally {
            if (debug) {
                dbg_leave("AssertedIdentityParser.parse");
            }
        }
    }
}
