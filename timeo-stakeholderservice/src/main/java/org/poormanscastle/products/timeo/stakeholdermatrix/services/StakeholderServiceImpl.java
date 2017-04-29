package org.poormanscastle.products.timeo.stakeholdermatrix.services;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.stakeholdermatrix.domain.BusinessSection;
import org.poormanscastle.products.timeo.stakeholdermatrix.domain.Organisation;
import org.poormanscastle.products.timeo.stakeholdermatrix.domain.Stakeholder;
import org.poormanscastle.products.timeo.stakeholdermatrix.domain.exceptions.BusinessSectionNotFoundException;
import org.poormanscastle.products.timeo.stakeholdermatrix.domain.exceptions.OrganisationNotFoundException;
import org.poormanscastle.products.timeo.stakeholdermatrix.exceptions.InvalidStakeholderInputDataException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by georg on 27.10.16.
 */
@Service
class StakeholderServiceImpl implements StakeholderService {

    private final static Logger logger = Logger.getLogger(StakeholderServiceImpl.class);

    @Override
    public String produceDataMatrix() {
        List<Stakeholder> stakeholders = Stakeholder.findAllStakeholders();

        StringBuilder labelsBuilder = new StringBuilder();
        StringBuilder graphBuilder = new StringBuilder();

        // buffer = new StringBuffer("digraph ast {\n%labels%\nnode [shape = circle];\n");

        for (Stakeholder stakeholder : stakeholders) {
            // create content of stakeholder matrix node (label)
/*
            labelsBuilder.append(StringUtils.join(stakeholder.getMasterKey(), " [label=\"",
                    stakeholder.getName(), "\n", stakeholder.getFunctionWithinOrganisation(), "\n",
                    stakeholder.getEmail(), "\n", stakeholder.getPhone(), "\n", stakeholder.getBusinessAddress(),
                    "\",fontcolor=", stakeholder.getDirectContact() == 0 ? "lightblue" : "lightseagreen", "];\n"));
*/
            labelsBuilder.append(StringUtils.join(stakeholder.getMasterKey(), " [label=\"",
                    stakeholder.getName(), "\n", stakeholder.getFunctionWithinOrganisation().length() < 33 ?
                            stakeholder.getFunctionWithinOrganisation() :
                            StringUtils.abbreviate(stakeholder.getFunctionWithinOrganisation(), 33),
                    "\",fontcolor=", stakeholder.getDirectContact() == 0 ? "lightblue" : "lightseagreen", "];\n"));
            // create network via reportsTo field
            graphBuilder.append(StringUtils.join(stakeholder.getMasterKey(), " -> ", stakeholder.getReportsTo(), ";\n"));
        }
        StringBuilder dotScript = new StringBuilder("digraph G {\n")
                .append("node [shape = rectangle];\n")
                .append(labelsBuilder.toString()).append(graphBuilder.toString()).append("}\n");
        return dotScript.toString();
    }

    @Override
    public Stakeholder findStakeholderByEmail(String email) {
        logger.info(StringUtils.join("StakeholderService got a service request for the stakeholder with email '", email, "'."));
        checkArgument(!StringUtils.isBlank(email), "Email address must not be blank.");
        List<Stakeholder> stakeholders = Stakeholder.findStakeholdersByEmail(email).getResultList();
        // field email in database is unique. Thus stakeholders can have zero or one entries.
        if (stakeholders.size() == 1) {
            logger.info(StringUtils.join("StakeholderService found 1 stakeholder for email '", email, "'."));
            return stakeholders.get(0);
        } else {
            logger.info(StringUtils.join("StakeholderService found no stakeholder for email '", email, "'."));
            return null;
        }
    }

    @Override
    public Stakeholder findStakeholderByMasterKey(String masterKey) {
        logger.info(StringUtils.join("StakeholderService got a service request for the stakeholder with masterKey '", masterKey, "'."));
        checkArgument(!StringUtils.isBlank(masterKey), "MasterKey must not be blank.");
        if ("-".equals(masterKey)) {
            // here somebody probably called the service for the reportsTo stakeholder where only '-' and no
            // real masterKey was given. Typically, because reportsTo is yet unknown or irrelevant.
            return null;
        }
        List<Stakeholder> stakeholders = Stakeholder.findStakeholdersByMasterKey(masterKey).getResultList();
        // field masterKey is unique. Thus, stakeholders can have zero or one entries.
        if (stakeholders.size() == 1) {
            logger.info(StringUtils.join("StakeholderService found 1 stakeholder for masterKey '", masterKey, "'."));
            return stakeholders.get(0);
        } else {
            logger.info(StringUtils.join("StakeholderService found not stakeholder for masterKey '", masterKey, "'."));
            return null;
        }
    }

    @Override
    @Transactional
    public void mergeData(InputStream inputData, String separatorRegex) throws InvalidStakeholderInputDataException {
        try {
            List<String> lines = IOUtils.readLines(inputData);

            int counter = 0;
            while (counter < lines.size()) {
                String line = lines.get(counter);
                try {
                    // create a new stakeholder instance by parsing the data line
                    Stakeholder newStakeholder = new Stakeholder();
                    newStakeholder.parseStakeholderFromCsvString(line, separatorRegex);
                    // check whether a stakeholder with the given master key already exists in data storage
                    List<Stakeholder> existingStakeholders = Stakeholder.findStakeholdersByMasterKey(newStakeholder.getMasterKey()).getResultList();
                    if (existingStakeholders.size() == 0) {
                        newStakeholder.persist();
                    } else if (existingStakeholders.size() == 1) {
                        // merge data from newStakeholder into existingStakeholder
                        Stakeholder stakeholder = existingStakeholders.get(0);
                        // 2017-02-09 masterKey was changed from Long to String, thus the comparison must be adapted, too.
                        checkState(stakeholder.getMasterKey().equals(newStakeholder.getMasterKey()));
                        stakeholder.parseStakeholderFromCsvString(line, separatorRegex);
                    } else {
                        // if unique guard works in data storage this cannot happen
                        String errMsg = StringUtils.join("Multiple stakeholders for masterKey ", newStakeholder.getMasterKey(), " were found in data storage: ",
                                Arrays.toString(existingStakeholders.toArray()));
                        logger.error(errMsg);
                    }
                } catch (BusinessSectionNotFoundException exception) {
                    // one might argue about this. but if a given business section is not found the system
                    // will create one on the fly. that is how it is implemented right now.
                    if (StringUtils.isBlank(exception.getBusinessSectionName())) {
                        String errMsg = StringUtils.join("Cannot process empty business section name. ", exception.getMessage());
                        logger.error(errMsg, exception);
                    } else {
                        BusinessSection businessSection = new BusinessSection(exception.getBusinessSectionName());
                        businessSection.persist();
                        continue;
                    }
                } catch (OrganisationNotFoundException exception) {
                    if (StringUtils.isBlank(exception.getOrganisationName())) {
                        String errMsg = StringUtils.join("Cannot process empty organisation name. ", exception.getMessage());
                        logger.error(errMsg, exception);
                    } else {
                        Organisation organisation = new Organisation(exception.getOrganisationName());
                        organisation.persist();
                        continue;
                    }
                }
                counter++;
            }
        } catch (IOException exception) {
            String errorMessage = StringUtils.join("Could not parse stakeholder matrix input stream because ",
                    exception.getClass().getSimpleName(), " - ", exception.getMessage());
            logger.error(errorMessage, exception);
            throw new InvalidStakeholderInputDataException(errorMessage, exception);
        }
    }

}
