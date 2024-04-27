package users;

import java.util.Date;

public class CasualUser extends User{
    public CasualUser(int id) {
        super(id);
    }

    public CasualUser(int id, String name, String username, Date birthdate, String address, String email, boolean sex, double height, double weight, int heartFreq) {
        super(id, name, username, birthdate, address, email, sex, height, weight, heartFreq);
    }

    public CasualUser(User u) {
        super(u);
    }
}
