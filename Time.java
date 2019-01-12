/**
 * @author Tino Muzambi
 * @version 2019/01/11 2:43 PM
 * An object of this class represents
 * a standard time in the format hh:mm
 */

class Time {
    int hour;
    private int minute;
    private int duration=0;
    static final Time beginDayRate=new Time(8,0);
    static final Time endDayRate=new Time(18,0);
    static final Time beginEveningRate=new Time(18,1);
    static final Time endEveningRate=new Time(22,0);
    static final Time beginNightRate=new Time(22,1);
    static final Time endNightRate=new Time(7,59);

    Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    int getDuration(Time startTime, Time endTime) {
        if (startTime.hour==endTime.hour) {
            duration+=endTime.minute-startTime.minute-1;
        }
        else if (endTime.hour-startTime.hour==1){
            duration+=(60-startTime.minute)+endTime.minute;
        }
        else {
            duration+=(endTime.hour-startTime.hour-1)*60+((60-startTime.minute)+endTime.minute);
        }
        return duration;
    }

    /**
     * Checks if this time is greater (after) other time.
     * @param other Other Time to be checked against this time.
     * @return True/False.
     */
    boolean isAfter(Time other) {
        if (this.hour>other.hour) {
            return true;
        }
        else if (this.hour<other.hour) {
            return false;
        }
        else {
            return (this.minute>other.minute);
        }

    }
}
