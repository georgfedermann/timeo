package org.poormanscastle.products.timeo.stakeholdermatrix.services;

import java.io.InputStream;

import org.poormanscastle.products.timeo.stakeholdermatrix.domain.Stakeholder;

/**
 * defines the API of the Stakeholder API, right.
 * Created by georg on 27.10.16.
 */
public interface StakeholderService {

    /**
     * reads data from the input stream and merges it with the data available in the database.
     * If it finds a data set whose master key is not found in the database, this data record will
     * be imported into the database.
     * The csv data is understood as master version. If a data set contains a master key that
     * is already available in the database, then all fields of the related entity will be updated
     * to reflect the information found in the current data set.
     *
     * @param inputData      a stream containing serialized stakeholder data in csv format
     * @param separatorRegex a regex defining the separator character
     */
    void mergeData(InputStream inputData, String separatorRegex);

    /**
     * creates a visual representation of the stakeholder information contained in data storage.
     *
     * @return a InputStream whose content represent a visual representation that can be rendered using dot
     */
    String produceDataMatrix();

    /**
     * searches for the stakeholder with the given email.
     * @param email the email in question
     * @return One Stakeholder object if a stakeholder was found in storage for the given email, 
     * or null if no such stakeholder was found.
     */
    Stakeholder findStakeholderByEmail(String email);

    /**
     * searches for the stakeholder with the given masterKey.
     * @param masterKey the masterkey in question
     * @return One Stakeholder object if a stakeholder was found in storage rot the given masterKey,
     * or null if no such stakeholder was found.
     */
    Stakeholder findStakeholderByMasterKey(String masterKey);

}
