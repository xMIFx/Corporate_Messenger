package com.gitHub.xMIFx.view.servlets;

public final class DeterminantOfThePageTo {
    private static final String PAGE_MAIN = "/main.do";
    private static final String PAGE_DEPARTMENT = "/department.do";
    private static final String PAGE_WORKER = "/worker.do";

    public static String getPageTo(String pageFrom) {
        String pageTo = PAGE_MAIN;
        if (pageFrom == null) {
            pageTo = PAGE_MAIN;
        } else if (pageFrom.contains(PAGE_MAIN)) {
            pageTo = PAGE_MAIN;
        } else if (pageFrom.contains(PAGE_DEPARTMENT)) {
            pageTo = PAGE_DEPARTMENT;
        } else if (pageFrom.contains(PAGE_WORKER)) {
            pageTo = PAGE_WORKER;
        }
        return pageTo;
    }

}
