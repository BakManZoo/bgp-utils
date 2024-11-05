package pambros;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class BgpUtilsTest 
{

	private BgpUtils toTest = new BgpUtils();
	
    /**
     * 
     */
	@Test
    public void testApp()
    {
    	Collection<String> paths = toTest.parse("111  {222, 333} 444 {555,666} 777");
        assertTrue( true );
    }
}
