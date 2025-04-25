import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

public class MainTest {
    @Test
    @Disabled
    @Timeout(22)
    public void timeTest(){
        try{
            Main.main(new String[]{});
        }catch (Exception e){

        }
    }
}
