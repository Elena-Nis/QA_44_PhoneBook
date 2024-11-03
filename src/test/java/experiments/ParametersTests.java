package experiments;

import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParametersTests {


    @Test
    @Parameters({"value1", "value2"})
    public void test1(@Optional("10") int v1, @Optional("20") int v2) {
        System.out.println("v1="+v1+" v2="+v2);
        int result = v1 + v2;
        Assert.assertTrue(result < 0, "result <= 0");
    }
}
