package DbInterface;

public class TestDriver {
    public static void main(String[] args) {
        try{
            Class cls = Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println(cls + " loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("Error! Driver not found!" + e.getMessage());
        }
    }
}
