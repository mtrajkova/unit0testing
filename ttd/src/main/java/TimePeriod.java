import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimePeriod {

    private Date start;
    private Date end;

    public boolean overlapsWith(TimePeriod period) {
        // testAcontainsB()
        if ((this.start.before(period.start))
                && (this.end.after(period.end))) {
            return true;
        }

        // testBcontainsA()
        if ((this.start.after(period.start))
                && (this.end.before(period.end))) {
            return true;
        }

        // testBinteractA()
        if ((this.start.after(period.start))
                && (this.end.after(period.end))
                && (this.start.before(period.end))) {
            return true;
        }

        // testAinteractB()
        if ((this.start.before(period.start))
                && (this.end.before(period.end))
                && (this.end.after(period.start))) {
            return true;
        }

        // testAequalsB()
        if ((this.start.equals(period.start))
                && (this.end.equals(period.end))) {
            return true;
        }

        // testAendEqualToBstart()
        if (this.end.equals(period.start)) {
            return true;
        }

        if (this.start.equals(period.end)) {
            return true;
        }

        return false;
    }

}
