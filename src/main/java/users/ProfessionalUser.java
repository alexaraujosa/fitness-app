package users;

import java.util.Date;

public class ProfessionalUser extends User{
    public ProfessionalUser(int id) {
        super(id);
    }

    public ProfessionalUser(int id, String name, String username, Date birthdate, String address, String email, boolean sex, double height, double weight, int heartFreq) {
        super(id, name, username, birthdate, address, email, sex, height, weight, heartFreq);
    }

    public ProfessionalUser(User u) {
        super(u);
    }
}
