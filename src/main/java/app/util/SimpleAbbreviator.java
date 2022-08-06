package app.util;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public final class SimpleAbbreviator {

    private SimpleAbbreviator() {}

    public static String abbreviate(final String text, final int maxLength, final List<String> keywords) {
        if (text.length() <= maxLength) {
            return text;
        }

        final int maxBackwardLength = maxLength / 10;

        int minKeywordIndex = Integer.MAX_VALUE;
        int abbreviateOffset = minKeywordIndex;
        for (final String keyword : keywords) {
            final int keywordIndex = StringUtils.indexOfIgnoreCase(text, keyword);
            if (keywordIndex != -1) {
                minKeywordIndex = Math.min(minKeywordIndex, keywordIndex);
                for (int i = Math.max(minKeywordIndex - maxBackwardLength, 0); i < minKeywordIndex; i++) {
                    final char ch = text.charAt(i);
                    if (Character.isWhitespace(ch) || ".,!?;)/&]}\"'、。．，！？；）／＆］｝”’」】』〉〕､｡｣".indexOf(ch) != -1) {
                        abbreviateOffset = i + 1;
                        break;
                    }
                }
            }
        }
        if (minKeywordIndex == Integer.MAX_VALUE) {
            return StringUtils.abbreviate(text, maxLength);
        }
        return StringUtils.abbreviate(text, Math.min(abbreviateOffset, minKeywordIndex), maxLength);
    }

}
