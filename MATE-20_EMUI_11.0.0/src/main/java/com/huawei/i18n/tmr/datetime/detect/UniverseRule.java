package com.huawei.i18n.tmr.datetime.detect;

import java.util.HashMap;

public class UniverseRule {
    private HashMap<Integer, String> rules = new HashMap<Integer, String>() {
        /* class com.huawei.i18n.tmr.datetime.detect.UniverseRule.AnonymousClass2 */

        {
            put(40001, "[regex_d]\\.?\\s*([-–~]|تا|देखि|থেকে|ngantos|tekan|па)\\s*[regex_d](\\.|([ ]+de[ ]+)|/){0,1}[ ]*([param_MMMM]|[param_MMM])(([ ]+de|\\.|,|/|،){0,1}[ ]*[regex_y](\\s*ж\\.|\\.|\\s*г\\.){0,1}){0,1}");
            put(40002, "([regex_y]\\.{0,1}[ ]*)?([param_MMMM]|[param_MMM])[ ]+[regex_d](\\.){0,1}\\s*([-–]|देखि|থেকে|ngantos|tekan|па)\\s*[regex_d]");
            put(20001, "(([param_EEEE]|[param_E]),{0,1}[ ]*){0,1}[regex_d](\\.|([ ]+de[ ]+)|/){0,1}[ ]*([param_MMMM]|[param_MMM])(([ ]+de|\\.|,|/){0,1}[ ]*[regex_y](\\s*(года|г))?\\.{0,1}){0,1}");
            put(20002, "(([param_EEEE]|[param_E])[\\s\\p{Punct}]*)?([param_MMMM]|[param_MMM])([\\s\\p{Punct}]|de)+\\d{1,2}(?!\\d)\\p{Punct}?(\\s*-?\\s*\\d{1,2}(?!\\d))?([\\s\\p{Punct}]*\\d{2,4}(?!\\d))?");
            put(20003, "(?<![-/.a-zA-Z\\d])\\d{1,4}(\\.?\\s*[-~]+\\s*\\d{1,2})?(/|-|(\\.[ ]*))\\d{1,2}(?!\\d)((/|-|(\\.[ ]*))\\d{1,4}(?!\\d)\\.?)?(\\s*[-~]+\\s*\\d{1,2}(\\.|\\s*г\\.)?(?!\\d))?(?![-/.a-zA-Z\\d])");
            put(20004, "(([param_EEEE]|[param_E])[\\s\\p{Punct}]*)?(?<!\\d)\\d{1,4}\\s*(/|-|\\.)\\s*\\d{1,2}(\\s*\\3\\s*\\d{1,4}(\\s*г\\.)?)?\\.?(?!\\d)(?!\\3)");
            put(20005, "[regex_d][ ]+([param_MMMM]|[param_MMM])([ ]+(de[ ]+)?[regex_y]\\.{0,1})?(\\s*,)?([ ]*([param_EEEE]|[param_E]))");
            put(20006, "(([param_EEEE]|[param_E2])(,[ ]*|[ ]+))[regex_d]\\.?(?![.:\\d])");
            put(20013, "[regex_d](号|日|일)[ ]*[(（]{0,1}([param_EEEE]|[param_E])[)）]{0,1}");
            put(20007, "(?<!月)([regex_d][param_digitDay])[号日일]");
            put(20008, "([regex_y]\\.{0,1}[ ]*){0,1}([param_MMMM]|[param_MMM])[ ]+[regex_d](\\.,|\\.|,){0,1}([ ]*([param_EEEE]|[param_E])){0,1}");
            put(20009, "([param_EEEE]|[param_E2])");
            put(20010, "[param_days]");
            put(20011, "([param_nextweek]|[param_thisweek])");
            put(30001, "([regex_hms])(\\s*\\(?[regex_zzzz]\\)?(?![.:]?\\d)){0,1}");
        }
    };
    private HashMap<Integer, HashMap<Integer, String>> subRulesMaps = new HashMap<Integer, HashMap<Integer, String>>() {
        /* class com.huawei.i18n.tmr.datetime.detect.UniverseRule.AnonymousClass1 */

        {
            put(20002, new HashMap<Integer, String>() {
                /* class com.huawei.i18n.tmr.datetime.detect.UniverseRule.AnonymousClass1.AnonymousClass1 */

                {
                    put(20012, "(([param_EEEE]|[param_E]),{0,1}[ ]*){0,1}([param_MMMM]|[param_MMM])[ ]+[regex_d][,.]{0,1}([ ]*[regex_y]\\.{0,1}){0,1}");
                    put(40005, "([param_MMMM]|[param_MMM])[ ]+[regex_d]\\s*(-|देखि|থেকে|ngantos|tekan|па)\\s*[regex_d],{0,1}(\\s*[regex_y])(\\s*ж\\.)?");
                }
            });
            put(20003, new HashMap<Integer, String>() {
                /* class com.huawei.i18n.tmr.datetime.detect.UniverseRule.AnonymousClass1.AnonymousClass2 */

                {
                    put(40003, "(?<![-/.])([regex_d])\\.?\\s*(-|–|~|देखि|থেকে)\\s*([regex_d])(?!\\3)(/|-|(\\.[ ]*))([regex_m])(\\6([regex_y])((\\.|\\s*г\\.|\\s*ж\\.)?))?(?![-/.])");
                    put(40004, "(?<![-/.])(([regex_y])\\s*([-/]|\\.)\\s*)([regex_m])\\s*\\5\\s*([regex_d])\\.?(?!\\5])(\\s*(-|–|~|देखि|থেকে)\\s*)([regex_d])\\.?(?![.\\d–~-])");
                    put(40006, "(?<![-/.])([regex_m])([-/]|\\.[ ]{0,3})([regex_d])\\.?\\s*(?!\\3)(-|–|~|देखि|থেকে)\\s*([regex_d])\\.?(?![.\\d–~-])");
                }
            });
            put(20004, new HashMap<Integer, String>() {
                /* class com.huawei.i18n.tmr.datetime.detect.UniverseRule.AnonymousClass1.AnonymousClass3 */

                {
                    put(20014, "(([param_EEEE]|[param_E]),{0,1}[ ]*)?[regex_d]\\s*(/|-|\\.)\\s*([regex_m])(\\s*\\4\\s*[regex_y]((\\.|\\s*г\\.)?)){0,1}");
                    put(20015, "(([param_EEEE]|[param_E]),{0,1}[ ]*)?([regex_m])\\s*(/|-|\\.)\\s*[regex_d](\\s*\\5\\s*[regex_y](\\.{0,1})){0,1}");
                    put(20016, "(([param_EEEE]|[param_E]),{0,1}[ ]*)?[regex_y]\\s*(/|-|\\.)\\s*([regex_m])\\s*\\5\\s*[regex_d]");
                }
            });
        }
    };

    public HashMap<Integer, String> getRules() {
        return this.rules;
    }

    public HashMap<Integer, HashMap<Integer, String>> getSubRulesMaps() {
        return this.subRulesMaps;
    }
}
