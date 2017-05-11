package org.poormanscastle.products.timeo.task.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * Created by georg on 11/05/2017.
 */
public class ActivityServiceBean implements ActivityService {

    final static Logger logger = Logger.getLogger(ActivityServiceBean.class);

    @Override
    public String processAndStoreActivity(String activityId, int timeInvestedInSeconds, String startDateTime,
                                          String endDateTime, String newTaskStatus, String comment) {
        logger.info(StringUtils.join("Received service request to save activity with following data: ",
                "activityId: ", activityId, "; timeInvested: ", timeInvestedInSeconds,
                "; startDateTime: ", startDateTime, "; endDateTime: ", endDateTime,
                "; status: ", newTaskStatus, "; comment: ", comment, " - Done!"
        ));

        return null;
    }


}
