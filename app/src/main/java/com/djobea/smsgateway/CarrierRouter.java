
package com.djobea.smsgateway;

public class CarrierRouter {
    public static String getProvider(String phone) {
        String num = phone.replace("+237", "").replace(" ", "");
        if (num.startsWith("69") || num.startsWith("655") || num.startsWith("656")
            || num.startsWith("657") || num.startsWith("658") || num.startsWith("659")
            || num.startsWith("694") || num.startsWith("695")) return "ORANGE";
        if (num.startsWith("67") || num.startsWith("68") || num.startsWith("650")
            || num.startsWith("651") || num.startsWith("652") || num.startsWith("653")
            || num.startsWith("654") || num.startsWith("680") || num.startsWith("681")) return "MTN";
        return "UNKNOWN";
    }
}
