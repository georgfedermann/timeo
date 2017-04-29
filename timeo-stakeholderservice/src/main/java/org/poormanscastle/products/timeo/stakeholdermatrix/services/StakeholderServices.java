package org.poormanscastle.products.timeo.stakeholdermatrix.services;

/**
 * Created by georg on 30.10.16.
 */
public class StakeholderServices {
    public static StakeholderService getStakeholderService() {
        return new StakeholderServiceImpl();
    }

}
