package uet.oop.bomberman.entities.Enum;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum DIR {
    DEFAULT, UP, DOWN, LEFT, RIGHT;

    // Get random direction
    private static final List<DIR> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static DIR random() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
