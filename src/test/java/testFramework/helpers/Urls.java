package testFramework.helpers;

import testFramework.Context;

import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Java standard URL convertor, doesn't really help. If you miss the host, then it regards the first element of
 * the path as the host. This lets you omit the scheme, or the host. It uses the values set in the SUT configuration
 * file.
 */
public class Urls {

    /**
     * Apply the defaults specified in the SUT config file if those data are not specified in the i/p parameter
     *
     * @param in - URL, that can have scheme and / or host missing
     * @return - if scheme or host are missing, then these are supplied using the defaults. Null => can't make a good
     * url
     */
    public static String interpretURL(String in) throws MalformedURLException, NoSuchFieldException {
        String result = "";
        Matcher matcher;
        final String regex = "^(https|http)?(://)?((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.+[A-Za-z]{2,6})?(.*)";
        final Pattern REGEX = Pattern.compile(regex);
        matcher = REGEX.matcher(in);
        if (matcher.find()) {
            try {
                if (matcher.group(1) == null) {
                    result += Context.sutConfiguration.getProperty("defaultScheme");
                    if (!result.matches(".*://$")) // allow the default to be defined without the magic chars
                        result += "://";
                } else {
                    result += matcher.group(1) + "://";
                }

                result += matcher.group(3) == null ? Context.sutConfiguration.getProperty("defaultHost") : matcher.group(3);

                if ((matcher.group(4) == null) || (matcher.group(4).length() == 0))
                    result += "/";
                else
                    // make sure that there is a '/' at the start of the path part of the url
                    result += matcher.group(4).charAt(0) == '/' ? matcher.group(4) : '/' + matcher.group(4);
                return result;
            } catch (NoSuchFieldException e) {
                HtmlReport.writeToHtmlReport("Unable to supply a default scheme, or host");
                throw e;
            }
        }
        // if we have got here, there has been a problem (e.g. unable to match, or get defaults (if needed))
        HtmlReport.writeToHtmlReport("Unable to make a good URL out of >" + in + "<");
        HtmlReport.writeToHtmlReport("Found this scheme: " + matcher.group(1));
        HtmlReport.writeToHtmlReport("scheme's magic letters: " + matcher.group(2));
        HtmlReport.writeToHtmlReport("Found this host: " + matcher.group(3));
        HtmlReport.writeToHtmlReport("Found this reminder: " + matcher.group(4));
        throw new MalformedURLException();
    }
}
