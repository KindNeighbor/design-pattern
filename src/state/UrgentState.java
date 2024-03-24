package state;

public class UrgentState implements State {
    private static UrgentState singleton = new UrgentState();

    private UrgentState() {
    }

    public static State getInstance() {
        return singleton;
    }

    @Override
    public void doClock(Context context, int hour) {
        // 동작 X
    }

    @Override
    public void doUse(Context context) {
        context.callSecurityCenter("비상: 비상시 금고 사용!");
    }

    @Override
    public void doAlarm(Context context) {
        context.callSecurityCenter("비상벨(비상시)");
    }

    @Override
    public void doPhone(Context context) {
        context.callSecurityCenter("일반 통화(비상시)");
    }

    @Override
    public String toString() {
        return "[비상시]";
    }
}
