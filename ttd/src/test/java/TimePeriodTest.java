import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimePeriodTest {
    private TimePeriod timePeriodA;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    TimePeriod timePeriodB;

    @Before
    public void setUp() throws ParseException {
        String startA = "01/01/2019";
        String endA = "31/12/2019";
        timePeriodA = new TimePeriod(dateFormatter.parse(startA), dateFormatter.parse(endA));
    }

    @Test
    public void dateAContainsDateB() throws ParseException {
        String startB = "05/05/2019";
        String endB = "30/12/2019";
        timePeriodB = new TimePeriod(dateFormatter.parse(startB), dateFormatter.parse(endB));

        Assert.assertTrue(timePeriodA.overlapsWith(timePeriodB));
    }

    @Test
    public void dateBContainsDateA() throws ParseException {
        String startB = "01/05/2018";
        String endB = "30/12/2020";
        timePeriodB = new TimePeriod(dateFormatter.parse(startB), dateFormatter.parse(endB));

        Assert.assertTrue(timePeriodA.overlapsWith(timePeriodB));
    }

    @Test
    public void dateBEndIntersectsDateA() throws ParseException {
        String startB = "01/05/2018";
        String endB = "30/10/2019";
        timePeriodB = new TimePeriod(dateFormatter.parse(startB), dateFormatter.parse(endB));

        Assert.assertTrue(timePeriodA.overlapsWith(timePeriodB));
    }

    @Test
    public void dateBStartIntersectsDateA() throws ParseException {
        String startB = "05/05/2019";
        String endB = "01/01/2020";
        timePeriodB = new TimePeriod(dateFormatter.parse(startB), dateFormatter.parse(endB));

        Assert.assertTrue(timePeriodA.overlapsWith(timePeriodB));
    }

    @Test
    public void dateAEqualsDateB() throws ParseException {
        String startB = "01/01/2019";
        String endB = "31/12/2019";
        timePeriodB = new TimePeriod(dateFormatter.parse(startB), dateFormatter.parse(endB));

        Assert.assertTrue(timePeriodA.overlapsWith(timePeriodB));
    }

    @Test
    public void DateAEndEqualsDateBStart() throws ParseException {
        String startB = "31/12/2019";
        String endB = "31/12/2019";
        timePeriodB = new TimePeriod(dateFormatter.parse(startB), dateFormatter.parse(endB));

        Assert.assertTrue(timePeriodA.overlapsWith(timePeriodB));
    }

    @Test
    public void DateAStartEqualsDateBEnd() throws ParseException {
        String startB = "01/01/2018";
        String endB = "01/01/2019";
        timePeriodB = new TimePeriod(dateFormatter.parse(startB), dateFormatter.parse(endB));

        Assert.assertTrue(timePeriodA.overlapsWith(timePeriodB));
    }

    @Test
    public void dateANotOverlappingWithDateB() throws ParseException {
        String startB = "01/01/2018";
        String endB = "30/10/2018";
        timePeriodB = new TimePeriod(dateFormatter.parse(startB), dateFormatter.parse(endB));

        Assert.assertFalse(timePeriodA.overlapsWith(timePeriodB));
    }

}