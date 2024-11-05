package pambros;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class BgpUtils {

	public Collection<String> parse(final String asnPath) {
		if (asnPath == null) {
			return Collections.emptyList();
		}
		final List<String[]> pathElements = breakdown(asnPath);
		final Collection<String> newPaths = new LinkedList<>();
		buildPaths(pathElements, 0, new Stack<String>(), newPaths);
		return newPaths;
	}

	private void buildPaths(final List<String[]> pathElements,
			                final int pathElementIndex,
			                final Stack<String> asnPath,
			                final Collection<String> allNewPaths) {
		if (pathElementIndex >= pathElements.size()) {
			if (!asnPath.isEmpty()) {
				allNewPaths.add(toSpaceSeparatedString(asnPath));
			}
			return;
		}

		boolean doPop = false;
		final String[] asnsAtIndex = pathElements.get(pathElementIndex);
		for (final String asn : asnsAtIndex) {
			// subsequent asn in this group, remove previous
			if (doPop) {
				asnPath.pop();
				doPop = false;
			}
			// add this asn to path if not the same as the latest in path
			if (asnPath.size() == 0 || !asnPath.peek().equals(asn)) {
				asnPath.push(asn);
				doPop = true;
			}

			// next asn item/group
			buildPaths(pathElements, pathElementIndex + 1, asnPath, allNewPaths);
		}

		// when leaving, pop the item I have probably just added
		if (doPop) {
			asnPath.pop();
		}

	}


	private List<String[]> breakdown(final String asnPath) {
		final List<String[]> breakDown = new ArrayList<>(20);
		boolean parsingGroup = false;
		final StringBuilder token = new StringBuilder();
		for (final char c : asnPath.toCharArray()) {
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

	private String toSpaceSeparatedString(final Collection<String> strings) {
		final StringBuilder builder = new StringBuilder();
		for (final String str : strings) {
			if (builder.length() > 0) {
				builder.append(" ");
			}
			builder.append(str);
		}
		return builder.toString();
	}

}
