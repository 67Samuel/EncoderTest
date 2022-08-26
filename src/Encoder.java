import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Encoder {

    private char key;

    private final Map<Character, Integer> forwardReference = new HashMap<>();
    private final Map<Integer, Character> backwardReference = new HashMap<>();

    public Encoder() {
        IntStream.range(65, 90+1)
                .peek(i -> backwardReference.put(i-65, (char)i))
                .forEach(i -> forwardReference.put((char)i, i-65));

        var digitIndexOffset = 90-65+1;
        IntStream.range(48, 57+1)
                .peek(i -> backwardReference.put(i-48+digitIndexOffset, (char)i))
                .forEach(i -> forwardReference.put((char)i, i-48+digitIndexOffset));

        var specialCharIndexOffset = digitIndexOffset + 57-48+1;
        IntStream.range(40, 47+1)
                .peek(i -> backwardReference.put(i-40+specialCharIndexOffset, (char)i))
                .forEach(i -> forwardReference.put((char)i, i-40+specialCharIndexOffset));
    }

    public String encode(String plaintext) {
        key = getKey(plaintext);

        var stringBuilder = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            if (forwardReference.containsKey(c))
                stringBuilder.append(encodeChar(c));
            else
                stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }

    private char encodeChar(char c) {
        var index = (forwardReference.get(c) - forwardReference.get(key))%44;
        if (index < 0)
            index = 44 + index;

        return backwardReference.get(index);
    }

    private char getKey(String plaintext) {
        var key = plaintext.charAt(0);
        if (forwardReference.containsKey(key))
            return key;
        else
            // default in case first char is invalid
            return 'A';
    }
}
