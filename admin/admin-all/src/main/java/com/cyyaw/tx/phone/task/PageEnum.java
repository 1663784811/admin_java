package com.cyyaw.tx.phone.task;

public enum PageEnum {

    Home("模拟器主页", "com.android.launcher3", "com.android.launcher3.Launcher"),
    RedBookHome("小红书首页", "com.xingin.xhs", "com.xingin.welcome.refactor.NewWelcomeActivity");
    // 标题
    private String name;
    // 包名
    private String pagePackage;
    // 页面名
    private String activityName;

    PageEnum(String name, String pagePackage, String activityName) {
        this.name = name;
        this.pagePackage = pagePackage;
        this.activityName = activityName;
    }


    public String getName() {
        return name;
    }

    public String getPagePackage() {
        return pagePackage;
    }

    public String getActivityName() {
        return activityName;
    }

    public static PageEnum getPage(String info) {
        PageEnum[] values = PageEnum.values();
        for (int i = 0; i < values.length; i++) {
            PageEnum pageEnum = values[i];
            boolean isThis = info.contains(pageEnum.getActivityName());
            if (isThis) {
                return pageEnum;
            }
        }
        return null;
    }

}
