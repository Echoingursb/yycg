package yycg.base.action.converters;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.core.convert.converter.Converter;

public class DatetimeConverter implements Converter<String, Timestamp> {

    public Timestamp convert(String source) {

        if (source != null) {
            source = source.trim();
            if (source.equals("")) {
                source = null;
            }
            if (source != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    return new Timestamp(simpleDateFormat.parse(source).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;

    }
}
