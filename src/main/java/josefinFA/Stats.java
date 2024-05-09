package josefinFA;

import java.io.Serializable;

public class Stats implements Serializable {

    public Stats(){
    }

    public Stats(Stats st){
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }

    public Stats clone(){
        return new Stats(this);
    }
}
