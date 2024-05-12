package utils;

import java.io.Serializable;
import java.util.Objects;

public class Tuple<A, B> implements Serializable {
    private A left;
    private B right;

    public Tuple(A left, B right) {
        this.left = left;
        this.right = right;
    }

    public Tuple(Tuple<A, B> tuple) {
        this.left = tuple.left;
        this.right = tuple.right;
    }

    public A getLeft() {
        return left;
    }

    public B getRight() {
        return right;
    }

    public void setLeft(A left) {
        this.left = left;
    }

    public void setRight(B right) {
        this.right = right;
    }

    static public class Tuple3<A, B, C> implements Serializable {
        public A first;
        public B second;
        public C third;

        public Tuple3(A first, B second, C third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tuple3<?, ?, ?> tuple3 = (Tuple3<?, ?, ?>) o;
            return Objects.equals(first, tuple3.first) && Objects.equals(second, tuple3.second) && Objects.equals(third, tuple3.third);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second, third);
        }
    }

    static public class Tuple4<A, B, C, D> implements Serializable {
        public A first;
        public B second;
        public C third;
        public D fourth;

        public Tuple4(A first, B second, C third, D fourth) {
            this.first = first;
            this.second = second;
            this.third = third;
            this.fourth = fourth;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tuple4<?, ?, ?, ?> tuple4 = (Tuple4<?, ?, ?, ?>) o;
            return Objects.equals(first, tuple4.first) && Objects.equals(second, tuple4.second) && Objects.equals(third, tuple4.third) && Objects.equals(fourth, tuple4.fourth);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second, third, fourth);
        }
    }

    public Tuple<A, B> clone(){
        return new Tuple<A,B>(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(getLeft(), tuple.getLeft()) && Objects.equals(getRight(), tuple.getRight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLeft(), getRight());
    }
}
