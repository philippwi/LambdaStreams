public class Record {
    private int id;
    private String petrolType;
    private int amount;
    private String carType;
    private int stationID;
    private int partOfTheDay;
    private int dayOfTheWeek;

    public Record(int id, String petrolType,int amount,String carType,int stationID,int partOfTheDay,int dayOfTheWeek) {
        this.id = id;
        this.petrolType = petrolType;
        this.amount = amount;
        this.carType = carType;
        this.stationID = stationID;
        this.partOfTheDay = partOfTheDay;
        this.dayOfTheWeek = dayOfTheWeek;
    }


    public int getId(){return this.id;}
    public String getPetrolType(){return this.petrolType;}
    public int getAmount(){return this.amount;}
    public String getCarType(){return this.carType;}
    public int getStationID(){return this.stationID;}
    public int getPartOfTheDay(){return this.partOfTheDay;}
    public int getDayOfTheWeek(){return this.dayOfTheWeek;}


    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id).append(",").append(petrolType).append(",");
        stringBuilder.append(amount).append(",").append(carType).append(",");
        stringBuilder.append(stationID).append(",").append(partOfTheDay).append(",");
        stringBuilder.append(dayOfTheWeek);
        return stringBuilder.toString();
    }
}