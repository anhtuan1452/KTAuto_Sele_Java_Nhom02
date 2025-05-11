package Railway.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            extent = createInstance();
        }
        return extent;
    }

    private static ExtentReports createInstance() {
        // Đường dẫn lưu báo cáo
        String reportPath = "target/ExtentReports/ExtentReport_" + System.currentTimeMillis() + ".html";
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        // Cấu hình báo cáo
        spark.config().setDocumentTitle("Báo Cáo Kiểm Thử Selenium");
        spark.config().setReportName("Báo Cáo Kiểm Thử Tự Động");
        spark.config().setTheme(Theme.DARK); // Giao diện tối
        spark.config().setEncoding("UTF-8");

        extent = new ExtentReports();
        extent.attachReporter(spark);

        // Thêm thông tin hệ thống
        extent.setSystemInfo("Hệ điều hành", System.getProperty("os.name"));
        extent.setSystemInfo("Trình duyệt", "Chrome");
        extent.setSystemInfo("Người kiểm thử", "Nhóm 4");

        return extent;
    }
    public static void reset() {
        extent = null;
    }
}