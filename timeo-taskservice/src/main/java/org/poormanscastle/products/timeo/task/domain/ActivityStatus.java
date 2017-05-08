package org.poormanscastle.products.timeo.task.domain;

import org.apache.commons.lang3.StringUtils;

/**
 * Defines the status of an activity. An activity can be ongoing if it is currently being
 * processed, or done if it is finished. Any resource can only have one activity ongoing
 * at a given time.
 * Created by georg on 08/05/2017.
 */
public enum ActivityStatus {
    
    ONGOING(0, "task.activity.status.ongoing"),
    DONE(1, "task.activity.status.done");

    /**
     * some unique id
     */
    private final int id;

    /**
     * used to look up a label text from some i18n translation mechanism.
     */
    private final String i18nKey;

    private ActivityStatus(int id, String i18nKey) {
        this.id = id;
        this.i18nKey = i18nKey;
    }

    public int getId() {
        return id;
    }

    public String getI18nKey() {
        return i18nKey;
    }

    public static ActivityStatus resolveStatus(Integer id) {
        if (id == null) {
            return null;
        }
        for (ActivityStatus status : ActivityStatus.values()) {
            if (id.equals(status.id)) {
                return status;
            }
        }
        // if execution arrives here, no ActivityStatus was found for the stated id
        // and thus the stated ID is invalid
        throw new IllegalArgumentException(StringUtils.join(
                "No matching ActivityStatus was found for id ", id, "."));
    }
}
