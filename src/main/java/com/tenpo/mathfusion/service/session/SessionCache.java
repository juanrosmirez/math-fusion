package com.tenpo.mathfusion.service.session;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionCache {

    private Double lastPercentageValue;
    private long lastPercentageValueTimestamp;

    public Double getLastPercentageValue() {
        return lastPercentageValue;
    }

    public void setLastPercentageValue(Double lastPercentageValue) {
        this.lastPercentageValue = lastPercentageValue;
    }

    public long getLastPercentageValueTimestamp() {
        return lastPercentageValueTimestamp;
    }

    public void setLastPercentageValueTimestamp(long lastPercentageValueTimestamp) {
        this.lastPercentageValueTimestamp = lastPercentageValueTimestamp;
    }
}