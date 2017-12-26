package com.owen.designpatterns.structural.flyweight;

/**
 * @Author owen[暖风]
 * @Date 17/12/26 下午10:03
 * @Version 1.0
 */
public class EmployeeReportManager implements IReportManager {

    protected String tenantId = null;

    public EmployeeReportManager(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 创建报表
     *
     * @return String
     */
    @Override
    public String createReport() {
        return "This is a employee report";
    }
}
