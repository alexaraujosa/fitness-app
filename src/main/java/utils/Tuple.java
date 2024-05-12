package utils;

public class Tuple<A, B> {
    public A left;
    public B right;

    Tuple(A left, B right) {
        this.left = left;
        this.right = right;
    }

    static public class Tuple3<A, B, C> {
        public A first;
        public B second;
        public C third;

        Tuple3(A first, B second, C third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }
    }

    static public class Tuple4<A, B, C, D> {
        public A first;
        public B second;
        public C third;
        public D fourth;

        Tuple4(A first, B second, C third, D fourth) {
            this.first = first;
            this.second = second;
            this.third = third;
            this.fourth = fourth;
        }
    }
}
