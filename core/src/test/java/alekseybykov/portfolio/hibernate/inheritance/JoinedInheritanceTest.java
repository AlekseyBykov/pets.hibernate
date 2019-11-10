//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.inheritance;

import alekseybykov.portfolio.hibernate.TestContextHook;
import alekseybykov.portfolio.hibernate.inheritance.strategies.joined.BusinessPlan;
import alekseybykov.portfolio.hibernate.inheritance.strategies.joined.Document;
import alekseybykov.portfolio.hibernate.inheritance.strategies.joined.EmploymentAgreement;
import common.utils.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-11-10
 */
@ExtendWith({TestContextHook.class})
@DisplayName("Tests for joined inheritance strategy")
class JoinedInheritanceTest {

    @Test
    @DisplayName("Save hierarchy into three linked tables")
    void testSaveHierarchyIntoThreeLinkedTables() {
        Long documentId, businessPlanId, employmentAgreementId;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Document document = new Document("approved");
            Document businessPlant = new BusinessPlan("rejected", "some description");
            Document employmentAgreement = new EmploymentAgreement("approved", 1_000_000d);

            session.persist(document);
            session.persist(businessPlant);
            session.persist(employmentAgreement);

            documentId = document.getId();
            businessPlanId = businessPlant.getId();
            employmentAgreementId = employmentAgreement.getId();

            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            Document document = session.load(Document.class, documentId);
            assertEquals("approved", document.getState());

            BusinessPlan businessPlan = session.load(BusinessPlan.class, businessPlanId);
            assertEquals("some description", businessPlan.getProductDescription());
            assertEquals("rejected", businessPlan.getState());

            EmploymentAgreement employmentAgreement = session.load(EmploymentAgreement.class, employmentAgreementId);
            assertEquals("approved", employmentAgreement.getState());
            assertEquals(Double.valueOf(1_000_000), employmentAgreement.getSalary());
        }
    }
}
