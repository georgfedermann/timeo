package org.poormanscastle.products.timeo.stakeholdermatrix.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.poormanscastle.products.timeo.stakeholdermatrix.domain.exceptions.BusinessSectionNotFoundException;
import org.poormanscastle.products.timeo.stakeholdermatrix.domain.exceptions.OrganisationNotFoundException;
import org.poormanscastle.products.timeo.stakeholdermatrix.exceptions.InvalidStakeholderInputDataException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by georg on 27.10.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/spring/test-applicationContext.xml")
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class StakeholderTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() throws Exception {
        Organisation organisation = new Organisation("Piggy Bank");
        organisation.persist();
        BusinessSection coo = new BusinessSection("COO");
        coo.persist();
    }

    @Test
    public void parseThreeValidStakeholdersFromTsvStringTest() throws Exception {
        List<Stakeholder> myStakeholders = new ArrayList<>();

        List<String> lines = IOUtils.readLines(getClass().getResource("/ThreeValidStakeholders.tsv").openStream());
        assertNotNull(lines);
        for (String line : lines) {
            myStakeholders.add(new Stakeholder().parseStakeholderFromCsvString(line, "\\t"));
        }
        assertEquals(3, myStakeholders.size());
    }

    @Test
    public void parseOneRecordInvalidDirectContactTest() throws Exception {
        List<Stakeholder> stakeholders = new ArrayList<>();

        List<String> lines = IOUtils.readLines(getClass().getResource("/OneRecord_InvalidDirectContact.tsv").openStream());
        assertNotNull(lines);
        for (String line : lines) {
            expectedException.expect(InvalidStakeholderInputDataException.class);
            expectedException.expectMessage("directContact '3' is not 0 or 1.");
            stakeholders.add(new Stakeholder().parseStakeholderFromCsvString(line, "\\t"));
        }
        assertEquals(1, stakeholders.size());
    }

    @Test
    public void reportsToTest() throws Exception {
        // first test the valid values "7" and "-".
        // then test invalid values " " and "A".
        List<String> lines = IOUtils.readLines(getClass().getResource("/4Records_2ValidReportsTo2InvalidReportsTo.tsv").openStream());
        assertNotNull(lines);
        assertEquals(3, lines.size());
        Stakeholder stakeholder = new Stakeholder().parseStakeholderFromCsvString(lines.get(0), "\\t");
        assertEquals("7", stakeholder.getReportsTo());
        stakeholder = new Stakeholder().parseStakeholderFromCsvString(lines.get(1), "\\t");
        assertEquals("-", stakeholder.getReportsTo());
    }

    @Test
    public void invalidBusinessSectionTest() throws Exception {
        List<String> lines = IOUtils.readLines(getClass().getResource("/OneRecord_InvalidSection.tsv").openStream());
        assertNotNull(lines);
        assertEquals(1, lines.size());

        try {
            new Stakeholder().parseStakeholderFromCsvString(lines.get(0), "\\t");
        } catch (BusinessSectionNotFoundException exception) {
            assertTrue(exception.getMessage().contains("business section 'Red Devils' not found."));
            assertEquals("Red Devils", exception.getBusinessSectionName());
        }

    }

    @Test
    public void invalidOrganisationTest() throws Exception {
        List<String> lines = IOUtils.readLines(getClass().getResource("/OneRecord_InvalidOrganisation.tsv").openStream());
        assertNotNull(lines);
        assertEquals(2, lines.size());
        try {
            new Stakeholder().parseStakeholderFromCsvString(lines.get(0), "\\t");
        } catch (OrganisationNotFoundException exception) {
            assertTrue(exception.getMessage().contains("Organisation 'Red Devils' not found."));
            assertEquals("Red Devils", exception.getOrganisationName());
        }

        Stakeholder stakeholder = new Stakeholder().parseStakeholderFromCsvString(lines.get(1), "\\t");
        assertNull(stakeholder.getOrganisation());
    }

}