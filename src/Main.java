import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        var encoder = new Encoder();
        assert Objects.equals(encoder.encode("BHELLO WORLD!"), "AGDKKN VNQKC");
        assert Objects.equals(encoder.encode("FHELLO WORLD!"), "AGDKKN VNQKC");
    }
}
