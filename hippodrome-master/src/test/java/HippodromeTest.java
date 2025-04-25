import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.verify;

public class HippodromeTest {
    @Test
    public void test1() {
        assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null),
                "Должно быть выброшено иллегал аргумент эксэпшн");
    }

    @Test
    public void test2() {
        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Horses cannot be null.");
        }
    }

    @Test
    public void test3() {
        assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<Horse>()),
                "Должно быть выброшено иллегал аргумент эксэпшн");
    }

    @Test
    public void test4() {
        try {
            new Hippodrome(new ArrayList<Horse>());
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Horses cannot be empty.");
        }
    }

    @Test
    public void getHorsesTest() {
        List<Horse> horses = IntStream.range(0, 50)
                .parallel()
                .mapToObj(i -> new Horse("Horse " + i, i))
                .collect(Collectors.toList());
        assertTrue(horses.equals(new Hippodrome(horses).getHorses()));
    }

    @Test
    void moveTest() {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            // 1. Мокаем статический метод
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9))
                    .thenReturn(0.5);
            // 2. Создаем реальные объекты Horse
            List<Horse> horses = IntStream.range(0, 50)
                    .parallel()
                    .mapToObj(i -> new Horse("Horse " + i, i))
                    .collect(Collectors.toList());
            // 3. Создаем Hippodrome
            Hippodrome hippodrome = new Hippodrome(horses);
            // 4. Вызываем метод move()
            hippodrome.move();
            // 5. Проверяем, что у всех лошадей изменилась дистанция
            horses.forEach(horse ->
                    assertEquals(0.5, horse.getDistance(), 0.001)
            );
        }
    }

    @Test
    void getWinnerTest() {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            // 1. Мокаем статический метод
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9))
                    .thenReturn(0.5);
            // 2. Создаем реальные объекты Horse
            List<Horse> horses = IntStream.range(0, 50)
                    .parallel()
                    .mapToObj(i -> new Horse("Horse " + i, i))
                    .collect(Collectors.toList());
            // 3. Создаем Hippodrome
            Hippodrome hippodrome = new Hippodrome(horses);
            // 4. Вызываем метод move()
            hippodrome.move();
            // 5. Проверяем, что у всех лошадей изменилась дистанция
            Horse winner = horses.stream()
                    .max(Comparator.comparing(Horse::getDistance))
                    .get();
            assertTrue(hippodrome.getWinner() == winner);
        }
    }
}
