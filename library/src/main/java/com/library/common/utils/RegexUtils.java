package com.library.common.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexUtils
{
    private RegexUtils()
    {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 验证手机号（简单）
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileSimple(final CharSequence input)
    {
        return isMatch(RegexConstants.REGEX_MOBILE_SIMPLE, input);
    }

    /**
     * 验证手机号（精确）
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileExact(final CharSequence input)
    {
        return isMatch(RegexConstants.REGEX_MOBILE_EXACT, input);
    }

    /**
     * 验证电话号码
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isTel(final CharSequence input)
    {
        return isMatch(RegexConstants.REGEX_TEL, input);
    }

    /**
     * 验证身份证号码15位
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard15(final CharSequence input)
    {
        return isMatch(RegexConstants.REGEX_ID_CARD15, input);
    }

    /**
     * 验证身份证号码18位
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard18(final CharSequence input)
    {
        return isMatch(RegexConstants.REGEX_ID_CARD18, input);
    }

    /**
     * 验证邮箱
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isEmail(final CharSequence input)
    {
        return isMatch(RegexConstants.REGEX_EMAIL, input);
    }

    /**
     * 验证URL
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isURL(final CharSequence input)
    {
        return isMatch(RegexConstants.REGEX_URL, input);
    }

    /**
     * 验证汉字
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isZh(final CharSequence input)
    {
        return isMatch(RegexConstants.REGEX_ZH, input);
    }

    /**
     * 验证用户名
     * <p>取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位</p>
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isUsername(final CharSequence input)
    {
        return isMatch(RegexConstants.REGEX_USERNAME, input);
    }

    /**
     * 验证yyyy-MM-dd格式的日期校验，已考虑平闰年
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isDate(final CharSequence input)
    {
        return isMatch(RegexConstants.REGEX_DATE, input);
    }

    /**
     * 验证IP地址
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIP(final CharSequence input)
    {
        return isMatch(RegexConstants.REGEX_IP, input);
    }

    /**
     * 判断是否匹配正则
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(final String regex, final CharSequence input)
    {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    /**
     * 获取正则匹配的部分
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return 正则匹配的部分
     */
    public static List<String> getMatches(final String regex, final CharSequence input)
    {
        if (input == null)
        {
            return null;
        }
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find())
        {
            matches.add(matcher.group());
        }
        return matches;
    }

    /**
     * 获取正则匹配分组
     *
     * @param input 要分组的字符串
     * @param regex 正则表达式
     * @return 正则匹配分组
     */
    public static String[] getSplits(final String input, final String regex)
    {
        if (input == null)
        {
            return null;
        }
        return input.split(regex);
    }

    /**
     * 替换正则匹配的第一部分
     *
     * @param input       要替换的字符串
     * @param regex       正则表达式
     * @param replacement 代替者
     * @return 替换正则匹配的第一部分
     */
    public static String getReplaceFirst(final String input, final String regex,
                                         final String replacement)
    {
        if (input == null)
        {
            return null;
        }
        return Pattern.compile(regex).matcher(input).replaceFirst(replacement);
    }

    /**
     * 替换所有正则匹配的部分
     *
     * @param input       要替换的字符串
     * @param regex       正则表达式
     * @param replacement 代替者
     * @return 替换所有正则匹配的部分
     */
    public static String getReplaceAll(final String input, final String regex,
                                       final String replacement)
    {
        if (input == null)
        {
            return null;
        }
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }

    static class RegexConstants
    {
        /**
         * 正则：手机号（简单）
         */
        public static final String REGEX_MOBILE_SIMPLE = "^[1]\\d{10}$";
        /**
         * 正则：手机号（精确）
         * <p>移动：134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188</p>
         * <p>联通：130、131、132、145、155、156、171、175、176、185、186</p>
         * <p>电信：133、153、173、177、180、181、189</p>
         * <p>全球星：1349</p>
         * <p>虚拟运营商：170</p>
         */
        public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";
        /**
         * 正则：电话号码
         */
        public static final String REGEX_TEL = "^0\\d{2,3}[- ]?\\d{7,8}";
        /**
         * 正则：身份证号码15位
         */
        public static final String REGEX_ID_CARD15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
        /**
         * 正则：身份证号码18位
         */
        public static final String REGEX_ID_CARD18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";
        /**
         * 正则：邮箱
         */
        public static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        /**
         * 正则：URL
         */
        public static final String REGEX_URL = "[a-zA-z]+://[^\\s]*";
        /**
         * 正则：汉字
         */
        public static final String REGEX_ZH = "^[\\u4e00-\\u9fa5]+$";
        /**
         * 正则：用户名，取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位
         */
        public static final String REGEX_USERNAME = "^[\\w\\u4e00-\\u9fa5]{6,20}(?<!_)$";
        /**
         * 正则：yyyy-MM-dd格式的日期校验，已考虑平闰年
         */
        public static final String REGEX_DATE = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
        /**
         * 正则：IP地址
         */
        public static final String REGEX_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";

        ///////////////////////////////////////////////////////////////////////////
        // 以下摘自http://tool.oschina.net/regex
        ///////////////////////////////////////////////////////////////////////////

        /**
         * 正则：双字节字符(包括汉字在内)
         */
        public static final String REGEX_DOUBLE_BYTE_CHAR = "[^\\x00-\\xff]";
        /**
         * 正则：空白行
         */
        public static final String REGEX_BLANK_LINE = "\\n\\s*\\r";
        /**
         * 正则：QQ号
         */
        public static final String REGEX_TENCENT_NUM = "[1-9][0-9]{4,}";
        /**
         * 正则：中国邮政编码
         */
        public static final String REGEX_ZIP_CODE = "[1-9]\\d{5}(?!\\d)";
        /**
         * 正则：正整数
         */
        public static final String REGEX_POSITIVE_INTEGER = "^[1-9]\\d*$";
        /**
         * 正则：负整数
         */
        public static final String REGEX_NEGATIVE_INTEGER = "^-[1-9]\\d*$";
        /**
         * 正则：整数
         */
        public static final String REGEX_INTEGER = "^-?[1-9]\\d*$";
        /**
         * 正则：非负整数(正整数 + 0)
         */
        public static final String REGEX_NOT_NEGATIVE_INTEGER = "^[1-9]\\d*|0$";
        /**
         * 正则：非正整数（负整数 + 0）
         */
        public static final String REGEX_NOT_POSITIVE_INTEGER = "^-[1-9]\\d*|0$";
        /**
         * 正则：正浮点数
         */
        public static final String REGEX_POSITIVE_FLOAT = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";
        /**
         * 正则：负浮点数
         */
        public static final String REGEX_NEGATIVE_FLOAT = "^-[1-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*$";
    }
}


