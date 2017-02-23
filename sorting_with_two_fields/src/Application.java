import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Application {
    private List<Record> records;
    RecordComparator recordComparator;

    public Application(){}

    public void init() {

        Record record01 = new Record(1,"A",55,"A",1,1,1);
        Record record02 = new Record(2,"A",35,"A",1,2,1);
        Record record03 = new Record(3,"B",65,"C",3,2,1);
        Record record04 = new Record(4,"A",25,"B",2,2,1);
        Record record05 = new Record(5,"C",65,"B",5,3,1);
        Record record06 = new Record(6,"B",45,"A",5,1,1);
        Record record07 = new Record(7,"A",30,"A",4,1,1);
        Record record08 = new Record(8,"A",25,"A",3,1,2);
        Record record09 = new Record(9,"B",35,"C",2,2,4);
        Record record10 = new Record(10,"C",85,"B",3,1,5);
        records = Arrays.asList(record01,record02,record03,record04,record05,record06,record07,record08,record09,record10);
    }

    public void execute01(){
        // aggregation - average
        //SELECT AVG(amount) FROM data WHERE (petrolType = 'A' AND carType = 'A')

        int counter = (int)records.stream()
                .filter(r -> r.getPetrolType()=="A")
                .filter(r -> r.getCarType()=="A")
                .count();

        double total = records.stream()
                .filter(r -> r.getPetrolType()=="A")
                .filter(r -> r.getCarType()=="A")
                .mapToDouble(Record::getAmount)
                .sum();

        System.out.println("SELECT AVG(amount) FROM data WHERE (petrolType = 'A' AND carType = 'A')\n" + (total/counter) + "\n");
    }
    public void execute02(){
        // aggregation - max
        //SELECT MAX(amount) FROM data WHERE (petrolType = 'A' and dayOfWeek = 1)

        int result = records.stream()
                .filter(r -> r.getPetrolType()=="A")
                .filter(r -> r.getDayOfTheWeek()==1)
                .mapToInt(Record::getAmount)
                .max().getAsInt();

        System.out.println("SELECT MAX(amount) FROM data WHERE (petrolType = 'A' and dayOfWeek = 1)\n" + result + "\n");

    }    public void execute03(){
        // sort
        //SELECT petrolType,carType,amount FROM data ORDER BY amount

        records.stream().sorted(recordComparator)...


        System.out.println("SELECT petrolType,carType,amount FROM data ORDER BY amount\n" + result + "\n");
    }

    public void execute04(){
        // sort
        //SELECT * FROM data ORDER BY stationID,amount DESC



        System.out.println("SELECT * FROM data ORDER BY stationID,amount DESC\n"  + "\n");
    }

    public void execute05(){
        // filter
        //SELECT * FROM data WHERE (carType IN ('A','C') AND stationID <= 3)
        System.out.println("SELECT * FROM data WHERE (carType IN ('A','C') AND stationID <= 3)\n"  + "\n");
    }

    public void execute06(){

        // filter and sort
        //SELECT * FROM data WHERE (carType IN ('A','B') AND stationID > 2) ORDER BY amount DESC

        System.out.println("SELECT * FROM data WHERE (carType IN ('A','B') AND stationID > 2) ORDER BY amount DESC\n"  + "\n");
    }

    public void execute07(){
        // filter, sort and limit
        //SELECT * FROM data WHERE (carType IN ('A','C') AND stationID < 5) ORDER BY amount LIMIT 3

        System.out.println("SELECT * FROM data WHERE (carType IN ('A','C') AND stationID < 5) ORDER BY amount LIMIT 3\n"  + "\n");
    }

    public void execute08(){
        // aggregation - count
        //SELECT COUNT(*) FROM data WHERE (carType = 'C' AND amount >= 35)

        System.out.println("SELECT COUNT(*) FROM data WHERE (carType = 'C' AND amount >= 35)\n"  + "\n");
    }

    public void execute09(){
        // aggregation - group
        //SELECT COUNT(*),carType FROM data GROUP BY carType

        System.out.println("SELECT COUNT(*),carType FROM data GROUP BY carType\n"  + "\n");
    }
    public void execute10(){
        // aggregation - group and filter
        //SELECT COUNT(*),petrolType FROM data WHERE (amount < 65 AND carType IN ('A','C')) GROUP BY petrolType

        System.out.println("SELECT COUNT(*),petrolType FROM data WHERE (amount < 65 AND carType IN ('A','C')) GROUP BY petrolType\n"  + "\n");
    }


    public void execute() {
        recordComparator = new RecordComparator();
        init();
        Collections.sort(records, recordComparator);
        System.out.println("All records:\n" + records + "\n");
         execute01();
         execute02();
         execute03();
         execute04();
         execute05();
         execute06();
         execute07();
         execute08();
         execute09();
         execute10();
    }


    public static void main(String... args) {
        Application application = new Application();
        application.execute();
    }
}