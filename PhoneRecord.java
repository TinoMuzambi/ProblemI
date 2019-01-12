/**
 * @author Tino Muzambi
 * @version 2019/01/12 10:57 AM
 * An object of this class represents
 * the phone record of a single call
 */

class PhoneRecord {

    private String chargeStep;
    private String phoneNumber;
    private int dayDuration;
    private int eveningDuration;
    private int nightDuration;
    private Time startTime;
    private Time endTime;
    private double cost;
    private final double ADAYRATE=0.1;
    private final double AEVENINGRATE=0.06;
    private final double ANIGHTRATE=0.02;
    private final double BDAYRATE=0.25;
    private final double BEVENINGRATE=0.15;
    private final double BNIGHTRATE=0.05;
    private final double CDAYRATE=0.53;
    private final double CEVENINGRATE=0.33;
    private final double CNIGHTRATE=0.13;
    private final double DDAYRATE=0.87;
    private final double DEVENINGRATE=0.47;
    private final double DNIGHTRATE=0.17;
    private final double EDAYRATE=1.44;
    private final double EEVENINGRATE=0.8;
    private final double ENIGHTRATE=0.3;

    PhoneRecord(String chargeStep, String phoneNumber, Time startTime, Time endTime) {
        this.chargeStep = chargeStep;
        this.phoneNumber = phoneNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Determines the relevant charge step
     * based on the charge step and time of
     * day.
     * @return The relevant charge step for
     * that time of day.
     */
    private double getChargeStep(String chargeStep, String timeOfDay) {
        if (chargeStep.equals("A") && timeOfDay.equals("Day")) {
            return ADAYRATE;
        }
        else if (chargeStep.equals("A") && timeOfDay.equals("Evening")) {
            return AEVENINGRATE;
        }
        else if (chargeStep.equals("A") && timeOfDay.equals("Night")) {
            return ANIGHTRATE;
        }
        else if (chargeStep.equals("B") && timeOfDay.equals("Day")) {
            return BDAYRATE;
        }
        else if (chargeStep.equals("B") && timeOfDay.equals("Evening")) {
            return BEVENINGRATE;
        }
        else if (chargeStep.equals("B") && timeOfDay.equals("Night")) {
            return BNIGHTRATE;
        }
        else if (chargeStep.equals("C") && timeOfDay.equals("Day")) {
            return CDAYRATE;
        }
        else if (chargeStep.equals("C") && timeOfDay.equals("Evening")) {
            return CEVENINGRATE;
        }
        else if (chargeStep.equals("C") && timeOfDay.equals("Night")) {
            return CNIGHTRATE;
        }
        else if (chargeStep.equals("D") && timeOfDay.equals("Day")) {
            return DDAYRATE;
        }
        else if (chargeStep.equals("D") && timeOfDay.equals("Evening")) {
            return DEVENINGRATE;
        }
        else if (chargeStep.equals("D") && timeOfDay.equals("Night")) {
            return DNIGHTRATE;
        }
        else if (chargeStep.equals("E") && timeOfDay.equals("Day")) {
            return EDAYRATE;
        }
        else if (chargeStep.equals("E") && timeOfDay.equals("Evening")) {
            return EEVENINGRATE;
        }
        else {
            return ENIGHTRATE;
        }
    }


    /**
     * Calculate the cost of a call
     * @param chargeStep Rate at which call is being billed
     */
     void calcCost(String chargeStep) {
        int duration;
        double cost=0;
        if (startTime.hour==endTime.hour) {
            if (startTime.hour >= 8 && endTime.hour <= 18) {
                duration = startTime.getDuration(startTime, endTime);
                dayDuration+=duration;
                cost=getChargeStep(chargeStep,"Day")*duration;
            } else if (startTime.hour > 18 && endTime.hour <= 22) {
                duration = startTime.getDuration(startTime, endTime);
                eveningDuration+=duration;
                cost=getChargeStep(chargeStep,"Evening")*duration;
            } else {
                duration = startTime.getDuration(startTime, endTime);
                nightDuration+=duration;
                cost=getChargeStep(chargeStep,"Night")*duration;
            }
        }
        else {
            if (startTime.hour >= 8 && startTime.hour <= 18) {
                if (Time.endDayRate.isAfter(endTime)){
                    duration=startTime.getDuration(startTime,endTime);
                    dayDuration+=duration;
                    cost+=getChargeStep(chargeStep,"Day")*duration;
                }
                else if (endTime.isAfter(Time.endDayRate)) {
                    cost = costDuration5(chargeStep, cost, endTime);
                }
                else if (endTime.isAfter(Time.endEveningRate)) {
                    cost = costDuration3(chargeStep, cost, endTime);
                }
                else {
                    cost = costDuration3(chargeStep, cost, Time.endNightRate);
                    duration=startTime.getDuration(Time.beginDayRate,endTime);
                    dayDuration+=duration;
                    cost+=getChargeStep(chargeStep,"Day")*duration;
                }
            }
            else if (startTime.hour > 18 && startTime.hour <= 22) {
                if (Time.endEveningRate.isAfter(endTime)) {
                    duration=startTime.getDuration(startTime,endTime);
                    cost+=getChargeStep(chargeStep,"Evening")*duration;
                }
                else if (endTime.isAfter(Time.endEveningRate)) {
                    duration=startTime.getDuration(startTime,Time.endNightRate);
                    cost+=getChargeStep(chargeStep,"Evening")*duration;
                    duration=startTime.getDuration(Time.beginNightRate,endTime);
                    cost+=getChargeStep(chargeStep,"Night")*duration;
                }
                else if (endTime.isAfter(Time.endNightRate)) {
                    cost = costDuration(chargeStep, cost);
                    duration=startTime.getDuration(Time.beginDayRate,endTime);
                    cost+=getChargeStep(chargeStep,"Day")*duration;
                }
                else {
                    cost = costDuration(chargeStep, cost);
                    cost = costDuration2(chargeStep, cost);
                }
            } else {
                if (Time.endDayRate.isAfter(endTime)) {
                    duration= startTime.getDuration(startTime,endTime);
                    cost+=getChargeStep(chargeStep,"Night")*duration;
                }
                else if (endTime.isAfter(Time.endNightRate)) {
                    duration= startTime.getDuration(startTime,Time.endNightRate);
                    cost+=getChargeStep(chargeStep,"Night")*duration;
                    duration= startTime.getDuration(Time.beginDayRate,endTime);
                    cost+=getChargeStep(chargeStep,"Day")*duration;
                }
                else if (endTime.isAfter(Time.endDayRate)) {
                    duration=startTime.getDuration(startTime,Time.endNightRate);
                    cost+=getChargeStep(chargeStep,"Night")*duration;
                    cost = costDuration2(chargeStep, cost);
                }
                else {
                    duration= startTime.getDuration(startTime,Time.endNightRate);
                    cost+=getChargeStep(chargeStep,"Night")*duration;
                    duration=startTime.getDuration(Time.beginDayRate,Time.endDayRate);
                    cost+=getChargeStep(chargeStep,"Day")*duration;
                    duration=startTime.getDuration(Time.beginEveningRate,Time.endEveningRate);
                    cost+=getChargeStep(chargeStep,"Evening")*duration;
                    duration=startTime.getDuration(Time.beginNightRate,endTime);
                    cost+=getChargeStep(chargeStep,"Night")*duration;
                }
            }
        }
        this.cost=cost;
    }

    private double costDuration5(String chargeStep, double cost, Time endTime) {
        int duration;
        duration=startTime.getDuration(startTime,Time.endDayRate);
        dayDuration+=duration;
        cost+=getChargeStep(chargeStep,"Day")*duration;
        duration=startTime.getDuration(Time.beginEveningRate, endTime);
        eveningDuration+=duration;
        cost+=getChargeStep(chargeStep,"Evening")*duration;
        return cost;
    }

    private double costDuration3(String chargeStep, double cost, Time endTime) {
        int duration;
        cost = costDuration5(chargeStep, cost, Time.endEveningRate);
        duration=startTime.getDuration(Time.beginNightRate, endTime);
        nightDuration+=duration;
        cost+=getChargeStep(chargeStep,"Night")*duration;
        return cost;
    }

    private double costDuration2(String chargeStep, double cost) {
        int duration;
        duration=startTime.getDuration(Time.beginDayRate,Time.endDayRate);
        cost+=getChargeStep(chargeStep,"Day")*duration;
        duration=startTime.getDuration(Time.beginEveningRate,endTime);
        cost+=getChargeStep(chargeStep,"Evening")*duration;
        return cost;
    }

    private double costDuration(String chargeStep, double cost) {
        int duration;
        duration=startTime.getDuration(startTime,Time.endEveningRate);
        cost+=getChargeStep(chargeStep,"Evening")*duration;
        duration=startTime.getDuration(Time.beginNightRate,Time.endNightRate);
        cost+=getChargeStep(chargeStep,"Night")*duration;
        return cost;
    }

    /**
     * Prints output in required format.
     */
     void printOutput() {
        System.out.println(phoneNumber+" "+dayDuration+" "+eveningDuration+" "+nightDuration+" "+chargeStep+" "+cost);
    }
}