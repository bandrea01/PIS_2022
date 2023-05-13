package Business;

import org.mindrot.jbcrypt.BCrypt;

public class HashingBusiness {
    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String plain, String hashed){
        return BCrypt.checkpw(plain, hashed);
    }
}
