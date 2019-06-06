package com.alekseybykov.examples.hibernate.entities;

import com.alekseybykov.examples.hibernate.utils.SessionUtil;
import org.hibernate.*;
import org.hibernate.id.IdentifierGenerationException;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class ManualTypeTest {

    @Test(expectedExceptions = IdentifierGenerationException.class)
    public void test__ShouldPass__saveManualTypeWithoutId() {
        try(Session session = SessionUtil.getSession()) {
            Long id;

            Transaction tx = session.beginTransaction();

            ManualType manualType = new ManualType();
            manualType.setName("some name");

            session.save(manualType);
            id = manualType.getId();

            assertNull(id);

            tx.commit();
        }
    }

    @Test
    public void test__ShouldPass__saveManualTypeWithId() {
        try(Session session = SessionUtil.getSession()) {
            Long id;

            Transaction tx = session.beginTransaction();

            ManualType manualType = new ManualType();
            manualType.setId(1L);
            manualType.setName("some another name");

            session.save(manualType);
            id = manualType.getId();

            assertNotNull(id);

            tx.commit();
        }
    }
}