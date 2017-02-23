import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Application implements IApplication {
    private final String userDirectory = System.getProperty("user.dir");
    private final String fileSeparator = System.getProperty("file.separator");
    private final String dataDirectory = userDirectory + fileSeparator + "data" + fileSeparator;
    private final String databaseFile = dataDirectory + "exercise.db";

    private Connection connection;
    private String driverName = "jdbc:hsqldb:";
    private String username = "sa";
    private String password = "";

    public void setupConnection() {
        System.out.println("--- setupConnection");

        try {
            Class.forName("org.hsqldb.jdbcDriver");
            String databaseURL = driverName + databaseFile;
            connection = DriverManager.getConnection(databaseURL,username,password);
            System.out.println("connection : " + connection);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void getStatus() {
        try {
            System.out.println("--- status");
            System.out.println("userDirectory : " + userDirectory);
            System.out.println("dataDirectory : " + dataDirectory);
            System.out.println("databaseFile  : " + databaseFile);
            System.out.println("driverName    : " + driverName);
            System.out.println("username      : " + username);
            System.out.println("password      : " + password);
            System.out.println("connection    : " + connection.hashCode());
            System.out.println("schema        : " + connection.getSchema());
            System.out.println("status        : " + connection.isClosed());
            System.out.println();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    public synchronized void update(String sqlStatement) {
        try {
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate(sqlStatement);
            if (result == -1)
                System.out.println("error executing " + sqlStatement);
            statement.close();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    public void dropTable() {
        System.out.println("--- dropTable");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE data");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        update(sqlStringBuilder.toString());

        System.out.println();
    }

    public void createTable() {
        System.out.println("--- createTable");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("CREATE TABLE data ").append(" ( ");
        sqlStringBuilder.append("id INTEGER NOT NULL").append(",");
        sqlStringBuilder.append("petrolType VARCHAR(1) NOT NULL").append(",");
        sqlStringBuilder.append("amount DOUBLE NOT NULL").append(",");
        sqlStringBuilder.append("carType VARCHAR(1) NOT NULL").append(",");
        sqlStringBuilder.append("stationID INTEGER NOT NULL").append(",");
        sqlStringBuilder.append("partOfDay INTEGER NOT NULL").append(",");
        sqlStringBuilder.append("dayOfWeek INTEGER NOT NULL").append(",");
        sqlStringBuilder.append("PRIMARY KEY (id)");
        sqlStringBuilder.append(" )");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        update(sqlStringBuilder.toString());

        System.out.println();
    }

    public void dump(ResultSet resultSet) {
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int maximumNumberColumns = resultSetMetaData.getColumnCount();
            int i;
            Object object;

            for (;resultSet.next();) {
                for (i = 0;i < maximumNumberColumns;++i) {
                    object = resultSet.getObject(i + 1);
                    System.out.print(object.toString() + " ");
                }
                System.out.println(" ");
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    public synchronized void queryDump(String sqlStatement) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            dump(resultSet);
            statement.close();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    private String buildSQLStatement(int id,String petrolType,double amount,String carType,
                                     int stationID,int partOfDay,int dayOfWeek) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO data (id,petrolType,amount,carType,stationID,partOfDay,dayOfWeek) VALUES (");
        stringBuilder.append(id).append(",");
        stringBuilder.append("'").append(petrolType).append("'").append(",");
        stringBuilder.append(amount).append(",");
        stringBuilder.append("'").append(carType).append("'").append(",");
        stringBuilder.append(stationID).append(",");
        stringBuilder.append(partOfDay).append(",");
        stringBuilder.append(dayOfWeek);
        stringBuilder.append(")");
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    private void insertData() {
        update(buildSQLStatement(1,"A",55,"A",1,1,1));
        update(buildSQLStatement(2,"A",35,"A",1,2,1));
        update(buildSQLStatement(3,"B",65,"C",3,2,1));
        update(buildSQLStatement(4,"A",25,"B",2,2,1));
        update(buildSQLStatement(5,"C",65,"B",5,3,1));
        update(buildSQLStatement(6,"B",45,"A",5,1,1));
        update(buildSQLStatement(7,"A",30,"A",4,1,1));
        update(buildSQLStatement(8,"A",25,"A",3,1,2));
        update(buildSQLStatement(9,"B",35,"C",2,2,4));
        update(buildSQLStatement(10,"C",85,"B",3,1,5));
        System.out.println();
    }

    public void init() {
        insertData();
    }

    // aggregation - average
    public void executeSQL01() {
        System.out.println("--- executeSQL01");

        String sqlStatement = "SELECT AVG(amount) FROM data " +
                              "WHERE (petrolType = 'A' AND carType = 'A')";
        queryDump(sqlStatement);
        System.out.println();
    }

    // aggregation - max
    public void executeSQL02() {
        System.out.println("--- executeSQL02");
        String sqlStatement = "SELECT MAX(amount) FROM data " +
                              "WHERE (petrolType = 'A' and dayOfWeek = 1)";
        queryDump(sqlStatement);
        System.out.println();
    }

    // sort
    public void executeSQL03() {
        System.out.println("--- executeSQL03");
        String sqlStatement = "SELECT petrolType,carType,amount FROM data " +
                              "ORDER BY amount";
        queryDump(sqlStatement);
        System.out.println();
    }

    // sort
    public void executeSQL04() {
        System.out.println("--- executeSQL04");
        String sqlStatement = "SELECT * FROM data " +
                              "ORDER BY stationID,amount DESC";
        queryDump(sqlStatement);
        System.out.println();
    }

    // filter
    public void executeSQL05() {
        System.out.println("--- executeSQL05");
        String sqlStatement = "SELECT * FROM data " +
                              "WHERE (carType IN ('A','C') AND stationID <= 3)";
        queryDump(sqlStatement);
        System.out.println();
    }

    // filter and sort
    public void executeSQL06() {
        System.out.println("--- executeSQL06");
        String sqlStatement = "SELECT * FROM data " +
                              "WHERE (carType IN ('A','B') AND stationID > 2) " +
                              "ORDER BY amount DESC";
        queryDump(sqlStatement);
        System.out.println();
    }

    // filter, sort and limit
    public void executeSQL07() {
        System.out.println("--- executeSQL07");
        String sqlStatement = "SELECT * FROM data " +
                              "WHERE (carType IN ('A','C') AND stationID < 5) " +
                              "ORDER BY amount LIMIT 3";
        queryDump(sqlStatement);
        System.out.println();
    }

    // aggregation - count
    public void executeSQL08() {
        System.out.println("--- executeSQL08");
        String sqlStatement = "SELECT COUNT(*) FROM data " +
                              "WHERE (carType = 'C' AND amount >= 35)";
        queryDump(sqlStatement);
        System.out.println();
    }

    // aggregation - group
    public void executeSQL09() {
        System.out.println("--- executeSQL09");
        String sqlStatement = "SELECT COUNT(*),carType FROM data " +
                              "GROUP BY carType";
        queryDump(sqlStatement);
        System.out.println();
    }

    // aggregation - group and filter
    public void executeSQL10() {
        System.out.println("--- executeSQL10");
        String sqlStatement = "SELECT COUNT(*),petrolType FROM data " +
                              "WHERE (amount < 65 AND carType IN ('A','C')) " +
                              "GROUP BY petrolType";
        queryDump(sqlStatement);
        System.out.println();
    }

    public void shutdown() {
        System.out.println("--- shutdown");

        try {
            Statement statement = connection.createStatement();
            statement.execute("SHUTDOWN");
            connection.close();
            System.out.println("isClosed : " + connection.isClosed());
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    public static void main(String... args) {
        Application application = new Application();
        application.setupConnection();
        application.getStatus();
        application.dropTable();
        application.createTable();
        application.init();
        application.executeSQL01();
        application.executeSQL02();
        application.executeSQL03();
        application.executeSQL04();
        application.executeSQL05();
        application.executeSQL06();
        application.executeSQL07();
        application.executeSQL08();
        application.executeSQL09();
        application.executeSQL10();
        application.shutdown();
    }
}