package gov.nist.javax.sip.header;

import gov.nist.core.Separators;
import gov.nist.javax.sip.Utils;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.header.ReasonHeader;

public class Reason extends ParametersHeader implements ReasonHeader {
    private static final long serialVersionUID = -8903376965568297388L;
    public final String CAUSE = ParameterNames.CAUSE;
    public final String TEXT = ParameterNames.TEXT;
    protected String protocol;

    @Override // javax.sip.header.ReasonHeader
    public int getCause() {
        return getParameterAsInt(ParameterNames.CAUSE);
    }

    @Override // javax.sip.header.ReasonHeader
    public void setCause(int cause) throws InvalidArgumentException {
        this.parameters.set(ParameterNames.CAUSE, Integer.valueOf(cause));
    }

    @Override // javax.sip.header.ReasonHeader
    public void setProtocol(String protocol2) throws ParseException {
        this.protocol = protocol2;
    }

    @Override // javax.sip.header.ReasonHeader
    public String getProtocol() {
        return this.protocol;
    }

    @Override // javax.sip.header.ReasonHeader
    public void setText(String text) throws ParseException {
        if (text.charAt(0) != '\"') {
            text = Utils.getQuotedString(text);
        }
        this.parameters.set(ParameterNames.TEXT, text);
    }

    @Override // javax.sip.header.ReasonHeader
    public String getText() {
        return this.parameters.getParameter(ParameterNames.TEXT);
    }

    public Reason() {
        super("Reason");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader, javax.sip.header.Header
    public String getName() {
        return "Reason";
    }

    /* access modifiers changed from: protected */
    @Override // gov.nist.javax.sip.header.ParametersHeader, gov.nist.javax.sip.header.SIPHeader
    public String encodeBody() {
        StringBuffer s = new StringBuffer();
        s.append(this.protocol);
        if (this.parameters != null && !this.parameters.isEmpty()) {
            s.append(Separators.SEMICOLON);
            s.append(this.parameters.encode());
        }
        return s.toString();
    }
}
