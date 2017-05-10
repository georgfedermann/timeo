package org.poormanscastle.products.timeo.task.web.ajax;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implements logic that can be used in the WebServices, UIs, JSPs, etc.
 * Created by georg on 10/05/2017.
 */
public class AjaxUtils {

    final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String formatDate(Date date) {
        return AjaxUtils.dateFormat.format(date);
    }

}
