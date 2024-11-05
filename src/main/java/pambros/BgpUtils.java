package pambros;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class BgpUtils {
	
	public Collection<String> parse(final String asnPath) {

		List<String[]> breakdown = breakdown(asnPath);
		
		Collection<String> paths = new LinkedList<>();
		return paths;
		
	}

	private List<String[]> breakdown(String asnPath) {
		List<String[]> breakDown = new LinkedList<>();
		boolean parsingGroup = false;
		StringBuilder token = new StringBuilder();
		for (char c : asnPath.toCharArray()) {
			if (c == ' ') {
				if (!parsingGroup && !token.isEmpty()) {
					breakDown.add(new String[] {token.toString()});
					token.setLength(0);
				}
				continue;
			}
			if (c == '{') {
				parsingGroup = true;
			}
			else if (c == '}') {
				parsingGroup = false;
				breakDown.add(token.toString().split(","));
				token.setLength(0);
			}
			else {
				token.append(c);
			}
		}
		if (token.length() > 0) {
			breakDown.add(new String[] {token.toString()});
		}
		
		return breakDown;
	}

}
