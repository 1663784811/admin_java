package com.cyyaw.util.tools;


import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class WhyStringUtil extends StringUtils {

    private static String string = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";

    /**
     * 获取UUID
     *
     * @return
     */
    public static String getUUID() {
        String str = UUID.randomUUID().toString();
        return str.replace("-", "");
    }

    /**
     * 生成随机数
     */
    public static String getRandomString(int length, String str) {
        StringBuffer sb = new StringBuffer();
        int len = str.length();
        for (int i = 0; i < length; i++) {
            sb.append(str.charAt(getRandom(len - 1)));
        }
        return sb.toString();
    }

    /**
     * 生成随机数
     */
    public static String getRandomString(int length) {
        return getRandomString(length, string);
    }

    private static int getRandom(int count) {
        return (int) Math.round(Math.random() * (count));
    }

    /**
     * 生成订单号  2019 07 07 16 16 10
     *
     * @return
     */
    public static String createOrderNum() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(new Date());
        for (int i = 0; i < 6; i++) {
            format += getRandom(10);
        }
        return format;
    }

    /**
     * 生成字符串
     *
     * @return
     */
    public static String createStr(int l, String str, String str2) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < l; i++) {
            if (i != 0 && null != str2) {
                sb.append(str2);
            }
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * 生成字符串
     *
     * @param o
     * @param l
     * @param str
     * @return
     */
    public static String createStrLength(String o, int l, String str) {
        if (null == o) {
            o = "";
        }
        if (null == str) {
            str = "0";
        }
        for (int i = o.length(); i < l; i++) {
            o = str + o;
        }
        return o;
    }

    /**
     * 提取网址
     *
     * @param address
     * @return
     */
    public static String getWebAdd(String address) {
        String regex = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
        StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(address);
        if (matcher.find()) {
            sb.append(matcher.group());
        }
        return sb.toString();
    }

    public static String getWebNotParam(String address) {
        String ad = getWebAdd(address);
        int i = ad.indexOf("?");
        if (i != -1) {
            return ad.substring(0, i);
        } else {
            return ad;
        }
    }


    public static String getWebIndexUrl(String baseurl, String patch) {
        if (!isEmpty(getWebFistStr(patch))) {
            return patch;
        } else {
            String url = getWebFistStr(baseurl);
            if (!isEmpty(url)) {
                if (!isEmpty(patch)) {
                    if (patch.indexOf("../") == 0) {
                        String wd = getWebNotParam(baseurl);
                        int ins = wd.lastIndexOf("/");
                        if (ins > 8) {
                            return wd.substring(0, ins) + patch.substring(2);
                        } else {
                            return url + patch;
                        }
                    } else if (patch.indexOf("./") == 0) {
                        return url + patch.substring(1);
                    } else if (patch.indexOf("/") == 0) {
                        return url + patch;
                    } else {
                        return url + "/" + patch;
                    }
                } else {
                    return url;
                }
            } else {
                return null;
            }
        }
    }

    public static String getWebFistStr(String baseurl) {
        if (!isEmpty(baseurl)) {
            String regex = "(https?|ftp|file)://([\\w]|[.])+(.com|.cn|)";
            StringBuffer sb = new StringBuffer();
            Pattern p = Pattern.compile(regex);
            Matcher matcher = p.matcher(baseurl);
            if (matcher.find()) {
                sb.append(matcher.group());
            }
            return sb.toString();
        } else {
            return baseurl;
        }
    }

    public static String join(String[] arr, String str) {
        StringBuffer sb = new StringBuffer();
        if (isEmpty(str)) {
            for (int i = 0; i < arr.length; i++) {
                sb.append(arr[i]);
            }
        } else {
            for (int i = 0; i < arr.length; i++) {
                if (i > 0 && arr.length > i) {
                    sb.append(str + arr[i]);
                } else {
                    sb.append(arr[i]);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 获取域名
     *
     * @return
     */
    public static String getDomainName(String name) {
        String pattern = "((http://)|(https://))?([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}(/){0,}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(name);
        if (m.find()) {
            String group = m.group();
            int i = group.indexOf("?");
            if (i > -1) {
                group = group.substring(0, i);
            }
            i = group.indexOf("http://");
            if (i > -1) {
                group = group.substring(i + "http://".length());
            }
            i = group.indexOf("https://");
            if (i > -1) {
                group = group.substring(i + "https://".length());
            }
            group = group.replaceAll("/", "");
            return group;
        }

        return null;
    }

}
