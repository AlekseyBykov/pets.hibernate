package alekseybykov.portfolio.hibernate.inheritance;

import alekseybykov.portfolio.hibernate.TestContextHook;
import alekseybykov.portfolio.hibernate.inheritance.strategies.singletable.CsvReport;
import alekseybykov.portfolio.hibernate.inheritance.strategies.singletable.PdfReport;
import alekseybykov.portfolio.hibernate.inheritance.strategies.singletable.Report;
import common.utils.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-11-09
 */
@ExtendWith({TestContextHook.class})
@DisplayName("Tests for single table inheritance strategy")
class SingleTableInheritanceTest {

    @Test
    @DisplayName("Save hierarchy into the one single table")
    void testSaveHierarchyIntoTheOneSingleTable() {
        Long reportId, csvReportId, pdfReportId;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Report report = new Report("format");
            Report csvReport = new CsvReport("csv", false);
            Report pdfReport = new PdfReport("pdf", "Helvetica");

            session.persist(report);
            session.persist(csvReport);
            session.persist(pdfReport);

            reportId = report.getId();
            csvReportId = csvReport.getId();
            pdfReportId = pdfReport.getId();

            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            Report report = session.load(Report.class, reportId);
            assertEquals("format", report.getFormat());

            CsvReport csvReport = session.load(CsvReport.class, csvReportId);
            assertEquals("csv", csvReport.getFormat());
            assertFalse(csvReport.isQuoted());

            PdfReport pdfReport = session.load(PdfReport.class, pdfReportId);
            assertEquals("pdf", pdfReport.getFormat());
            assertEquals("Helvetica", pdfReport.getTypeface());
        }
    }
}
