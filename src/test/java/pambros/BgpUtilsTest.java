package pambros;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class BgpUtilsTest
{

	private final BgpUtils toTest = new BgpUtils();

	@Test
	public void testEmpty() {
		Collection<String> paths = toTest.parse("");
		assertEquals(0, paths.size());

		paths = toTest.parse("    ");
		assertEquals(0, paths.size());

		paths = toTest.parse(null);
		assertEquals(0, paths.size());
	}

	@Test
	public void testSimplePath() {
		Collection<String> paths = toTest.parse("111");
		assertEquals(1, paths.size());
		Iterator<String> it = paths.iterator();
		assertEquals("111", it.next());

		paths = toTest.parse("222 333");
		assertEquals(1, paths.size());
		it = paths.iterator();
		assertEquals("222 333", it.next());

		paths = toTest.parse("444 555 666");
		assertEquals(1, paths.size());
		it = paths.iterator();
		assertEquals("444 555 666", it.next());
	}


	@Test
	public void testSimplePathRepeatingAsns() {
		Collection<String> paths = toTest.parse("111 111");
		assertEquals(1, paths.size());
		Iterator<String> it = paths.iterator();
		assertEquals("111", it.next());

		paths = toTest.parse("222 222 333 333");
		assertEquals(1, paths.size());
		it = paths.iterator();
		assertEquals("222 333", it.next());

		paths = toTest.parse("444 444 555 666 666");
		assertEquals(1, paths.size());
		it = paths.iterator();
		assertEquals("444 555 666", it.next());
	}

	@Test
    public void testSingleAsnGroup()
    {
    	final Collection<String> paths = toTest.parse("111 {222, 333}");
		assertEquals(2, paths.size());
		final Iterator<String> it = paths.iterator();
		assertEquals("111 222", it.next());
		assertEquals("111 333", it.next());
    }

	@Test
    public void testMultipleAsnGroup()
    {
    	final Collection<String> paths = toTest.parse("111 {222,333} 444 {555,666,777}");
		assertEquals(6, paths.size());
		final Iterator<String> it = paths.iterator();
		assertEquals("111 222 444 555", it.next());
		assertEquals("111 222 444 666", it.next());
		assertEquals("111 222 444 777", it.next());
		assertEquals("111 333 444 555", it.next());
		assertEquals("111 333 444 666", it.next());
		assertEquals("111 333 444 777", it.next());
    }

	@Test
    public void testMultipleAsnGroupExtraWhitespace()
    {
    	final Collection<String> paths = toTest.parse("111  {222,  333}  444 { 555, 666, 777 }");
		assertEquals(6, paths.size());
		final Iterator<String> it = paths.iterator();
		assertEquals("111 222 444 555", it.next());
		assertEquals("111 222 444 666", it.next());
		assertEquals("111 222 444 777", it.next());
		assertEquals("111 333 444 555", it.next());
		assertEquals("111 333 444 666", it.next());
		assertEquals("111 333 444 777", it.next());
    }

	@Test
    public void testMultipleAsnGroupRepeatingAsns()
    {
    	final Collection<String> paths = toTest.parse("111 {111,333} 444 {555,444,777} 888");
		assertEquals(6, paths.size());
		final Iterator<String> it = paths.iterator();
		assertEquals("111 444 555 888", it.next());
		assertEquals("111 444 888", it.next());
		assertEquals("111 444 777 888", it.next());
		assertEquals("111 333 444 555 888", it.next());
		assertEquals("111 333 444 888", it.next());
		assertEquals("111 333 444 777 888", it.next());
    }

	@Test
    public void testJustMultipleAsnGroup()
    {
    	final Collection<String> paths = toTest.parse("{111,222}{222,333,444}");
		assertEquals(6, paths.size());
		final Iterator<String> it = paths.iterator();
		assertEquals("111 222", it.next());
		assertEquals("111 333", it.next());
		assertEquals("111 444", it.next());
		assertEquals("222", it.next());
		assertEquals("222 333", it.next());
		assertEquals("222 444", it.next());
    }

}
