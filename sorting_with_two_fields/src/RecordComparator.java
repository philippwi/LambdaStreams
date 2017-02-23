import java.util.Comparator;
import com.google.common.collect.ComparisonChain;

public class RecordComparator implements Comparator<Record> {
    public int compare(Record record01,Record record02) {
        return ComparisonChain.start()
                .compare(record01.getId(),record02.getId())
                .compare(record02.getPetrolType(),record01.getPetrolType())
                .compare(record02.getAmount(),record01.getAmount())
               .compare(record02.getCarType(),record01.getCarType())
                .compare(record02.getStationID(),record01.getStationID())
                .compare(record02.getPartOfTheDay(),record01.getPartOfTheDay())
                .compare(record02.getDayOfTheWeek(),record01.getDayOfTheWeek())
                .result();
    }
}