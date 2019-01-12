import java.util.Scanner;

/**
 * @author Tino Muzambi
 * @version 2019/01/11 10:28 AM
 */

public class ProblemI {

    public static void main(String[] args) {
        String chargeStep;
        String phoneNumber;
        Time startTime;
        Time endTime;
        Scanner in = new Scanner(System.in);
        int startHour;
        int startMinute;
        int endHour;
        int endMinute;
        String text=in.next();
        while (!(text.equals("#"))) {
            chargeStep = text;
            text = in.next();
            phoneNumber = text;
            text = in.next();
            startHour = Integer.valueOf(text);
            text = in.next();
            startMinute = Integer.valueOf(text);
            text = in.next();
            endHour = Integer.valueOf(text);
            text = in.next();
            endMinute = Integer.valueOf(text);
            startTime=new Time(startHour,startMinute);
            endTime=new Time(endHour,endMinute);
            text = in.next();
            PhoneRecord phoneRecords=new PhoneRecord(chargeStep,phoneNumber,startTime,endTime);
            phoneRecords.calcCost(chargeStep);
            phoneRecords.printOutput();
        }
    }
}

//Sample input
/*
B 183-5724 17 58 18 04
A 183-5724 17 58 20 12
#

 */
