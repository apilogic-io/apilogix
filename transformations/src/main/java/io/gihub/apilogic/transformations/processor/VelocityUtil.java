package io.gihub.apilogic.transformations.processor;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class VelocityUtil {

    public String autoId() {
        return UUID.randomUUID().toString();
    }

    public String getCreated() {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return formatter.format(new Date());
    }

    public Long expDate(Integer days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime().getTime();
    }

    public String joinArray(List<String> array) {
        return String.join(",", array);
    }

    public String joinLongArray(List<Long> array) {
        var temp = array.stream().map(Object::toString).collect(Collectors.toSet());
        return String.join(",", temp);
    }

}
