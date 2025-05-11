package Testcases.Railway.utils;

import Railway.utils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class TestListener implements ISuiteListener {
    private ExtentReports extent;

    @Override
    public void onStart(ISuite suite) {
        // Khởi tạo ExtentReports khi suite bắt đầu
        extent = ExtentManager.getInstance();
    }

    @Override
    public void onFinish(ISuite suite) {
        // Ghi báo cáo khi suite kết thúc
        if (extent != null) {
            extent.flush();
        }
        // Reset instance để đảm bảo không tái sử dụng cho các lần chạy sau (tùy chọn)
        ExtentManager.reset();
    }
}