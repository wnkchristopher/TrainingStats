
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CalculateStats {

    public int calculateDaysBetweenDates(Date startDate, Date endDate){
        long diff = startDate.getTime() - endDate.getTime();
        return (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}
