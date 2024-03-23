package com.ua.money24.helper;

import com.ua.money24.constants.Emojis;
import com.ua.money24.constants.RateType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RateHelper {
    public String diffToText(double diff, String rateType) {
        if (diff == 0) {
            return "";
        }
        var signal = (RateType.BUY.equals(rateType) && diff > 0) || (RateType.SELL.equals(rateType) && diff < 0)
                ? Emojis.CHART_INCREASING_WITH_YEN
                : Emojis.SOS_BUTTON;
        var sign = diff > 0 ? Emojis.PLUS : Emojis.MINUS;
        return String.join(" ", signal, sign + Math.abs(diff));
    }
}
