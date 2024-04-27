package users;

import java.util.Date;

public class AmateurUser extends User{
    public AmateurUser(int id) {
        super(id);
    }

    public AmateurUser(int id, String name, String username, Date birthdate, String address, String email, boolean sex, double height, double weight, int heartFreq) {
        super(id, name, username, birthdate, address, email, sex, height, weight, heartFreq);
    }

    public AmateurUser(User u) {
        super(u);
    }
}
