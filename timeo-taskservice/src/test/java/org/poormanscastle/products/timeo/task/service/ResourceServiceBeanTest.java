package org.poormanscastle.products.timeo.task.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.runner.RunWith;
import org.poormanscastle.products.timeo.task.domain.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by georg on 20/02/2017.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/applicationContext.xml")
public class ResourceServiceBeanTest {

    @Autowired
    ResourceService resourceService;

    //    @Test
    public void loadResourceIntegrationTest() {
        Resource resource = resourceService.loadResourceByMasterKey("484");
        assertNotNull(resource);
        assertEquals("db909813-0795-41a1-a6c7-d5c164e0678a", resource.getId());
        assertEquals("484", resource.getMasterKey());
        assertEquals("Georg Federmann", resource.getName());
        assertEquals("georg.federmann@poormanscastle.com", resource.getEmail());
        assertEquals("+43664 412 7736", resource.getPhoneNumber());
        assertEquals("AT, Wien, Opernring 3, Floor 1", resource.getBusinessAddress());
    }

    //    @Test
    public void loadAllResourcesTest() {
        List<Resource> resources = resourceService.loadAllResources();
        assertNotNull(resources);
        assertFalse(resources.isEmpty());
        assertTrue(resources.size() > 360);
    }

}
