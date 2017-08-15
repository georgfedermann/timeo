package org.poormanscastle.products.timeo.task.web.util;

import java.util.UUID;

/**
 * Created by georg on 14/08/2017.
 */
public class WebUtils {
    
    public String createUuid(){
        return UUID.randomUUID().toString();
    }
    
}
