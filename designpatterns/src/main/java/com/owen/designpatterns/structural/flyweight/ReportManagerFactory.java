package com.owen.designpatterns.structural.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author owen[暖风]
 * @Date 17/12/26 下午10:29
 * @Version 1.0
 */
public class ReportManagerFactory {

    Map<String, IReportManager> financialReportManager = new HashMap<>();

    Map<String, IReportManager> employeeReportManager = new HashMap<>();

    IReportManager getFinancialReportManager(String tenantId) {
        IReportManager r = financialReportManager.get(tenantId);
        if (r == null) {
            r = new FinancialReportManager(tenantId);
            financialReportManager.put(tenantId, r);
        }
        return r;
    }

    IReportManager getEmployeeReportManager(String tenantId) {
        IReportManager r = employeeReportManager.get(tenantId);
        if (r == null) {
            r = new EmployeeReportManager(tenantId);
            employeeReportManager.put(tenantId, r);
        }
        return r;
    }

    public static void main(String[] args) {
        ReportManagerFactory rmf = new ReportManagerFactory();
        IReportManager rm = rmf.getEmployeeReportManager("A");
        System.out.println(rm.createReport());
    }


}
