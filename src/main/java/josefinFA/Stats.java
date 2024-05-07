package josefinFA;

public class Stats {

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
