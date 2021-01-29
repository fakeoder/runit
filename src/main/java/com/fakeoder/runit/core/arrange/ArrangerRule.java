package com.fakeoder.runit.core.arrange;

public class ArrangerRule {

    private String from;

    private String to;

    private boolean canceled;

    private String passCondition;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public String getPassCondition() {
        return passCondition;
    }

    public void setPassCondition(String passCondition) {
        this.passCondition = passCondition;
    }
}
