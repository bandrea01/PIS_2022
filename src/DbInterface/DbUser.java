package DbInterface;

public class DbUser {
    private static DbUser instance = new DbUser();
    private String schemaName;
    private String userName;
    private String pwd;

    private DbUser() {
        schemaName = "mydb";
        userName = "root"; //My username for Local instance 3306
        pwd = "140701"; //My password for Local instance 3306
    }

    public static DbUser getInstance(){
        return instance;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPwd() {
        return pwd;
    }
}
