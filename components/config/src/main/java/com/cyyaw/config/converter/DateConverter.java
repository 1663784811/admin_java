package com.cyyaw.config.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期类型转换器
 */
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        Date date = null;
        if (null != source && !source.equals("")) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = simpleDateFormat.parse(source);
            } catch (ParseException e) {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    date = simpleDateFormat.parse(source);
                } catch (ParseException e1) {
                    try {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                        date = simpleDateFormat.parse(source);
                    } catch (ParseException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        return date;
    }
}
