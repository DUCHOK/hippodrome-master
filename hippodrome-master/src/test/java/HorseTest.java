import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class HorseTest {
    @Test
    public void test1() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 1),
                "Должно быть выброшено иллегал аргумент эксэпшн");
    }

    @Test
    public void test2() {
        try {
            new Horse(null, 1);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Name cannot be null.");
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\n", " \n", "\n ", " \n ", "\n \n"})
    public void test3(String input) {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse(input, 1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\n", " \n", "\n ", " \n ", "\n \n"})
    public void test4(String input) {
        try {
            new Horse(input, 1);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Name cannot be blank.");
        }
    }

    @Test
    public void test5() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse("Booba", -1),
                "Должно быть выброшено иллегал аргумент эксэпшн");
    }

    @Test
    public void test6() {
        try {
            new Horse("Booba", -1);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Speed cannot be negative.");
        }
    }

    @Test
    public void test7() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse("Booba", 1, -1),
                "Должно быть выброшено иллегал аргумент эксэпшн");
    }

    @Test
    public void test8() {
        try {
            new Horse("Booba", 1, -1);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Distance cannot be negative.");
        }
    }

    @Test
    public void getNameTest() {
        assertEquals(new Horse("Booba", 1, 2).getName(), "Booba");
    }

    @Test
    public void getSpeedTest() {
        assertEquals(new Horse("Booba", 1, 2).getSpeed(), 1);
    }

    @Test
    public void getDistanceTest() {
        assertEquals(new Horse("Booba", 1, 2).getDistance(), 2);
    }

    @Test
    void moveTest1() {
        try (MockedStatic<Horse> booba = Mockito.mockStatic(Horse.class)) {
            // 1. Задаём поведение статического метода
            booba.when(() -> Horse.getRandomDouble(0.2, 0.9))
                    .thenReturn(0.5);  // Фиктивное значение

            // 2. Вызываем метод move()
            new Horse("Booba", 1).move();

            // 3. Проверяем, что getRandomDouble вызвался с (0.2, 0.9)
            booba.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @Test
    void moveTest2() {
        try (MockedStatic<Horse> booba1 = Mockito.mockStatic(Horse.class)) {
            // 1. Задаём поведение статического метода
            booba1.when(() -> Horse.getRandomDouble(0.2, 0.9))
                    .thenReturn(0.5);  // Фиктивное значение
            // 2. Вызываем метод move()
            Horse booba2 = new Horse("Booba", 1);
            booba2.move();
            // 3. Проверяем, что дистанс установлен
            assertEquals(booba2.getDistance(), 0.5);
        }
    }
}
