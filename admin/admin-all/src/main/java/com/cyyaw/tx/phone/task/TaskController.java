package com.cyyaw.tx.phone.task;

import com.cyyaw.tx.phone.PhoneDao;
import com.cyyaw.tx.phone.task.page.HandlePage;
import com.cyyaw.tx.phone.task.user.AbstractUserInfo;
import com.cyyaw.tx.phone.task.user.GoToStartPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * adb shell dumpsys window windows | grep -E 'mCurrentFocus|mFocusedApp'
 * <p>
 * adb -s emulator-5554 shell dumpsys window windows
 * <p>
 * 这个命令会通过 adb shell 进入设备的命令行界面，
 * 并使用 dumpsys window windows 命令获取窗口信息。
 * 然后使用 grep 命令过滤出包含 "mCurrentFocus" 或 "mFocusedApp" 的行，
 * 即当前焦点窗口的信息。
 */
@Slf4j
public class TaskController {

    @Autowired
    private PhoneDao phoneDao;

    private String phoneName;

    // 当前app
    private String pagePackage;

    private String pageActive;

    private String listId;

    private String listNum;


    private List<HandlePage> handlePage;


    public void aaa() {

        // 登录

        // 1. 收集用户信息
        AbstractUserInfo info = getAbstractUserInfo();
        info.go();


        // 2. 读取个人信息


    }

    public AbstractUserInfo getAbstractUserInfo() {
        GoToStartPage one = new GoToStartPage();

        return null;
    }


    /**
     * 初始化任务
     */
    public void initTask(String phoneName) {
        log.info("=============== 任务: 获取小红书用户信息");
        this.phoneName = phoneName;


    }


    /**
     * 执行任务
     */
    public void execTask() {
        // 1 判断当前页面是否是小红书首页
        boolean run = true;
        while (run) {
            // 到任务页面
            toTaskPage();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 点击列表的第n个, 去视频页面
            


            // 点击头像,   进入主面


            // 收集数据,  返回列表


        }






    }

    // 点到任务页面
    public boolean toTaskPage() {
        String pageInfo = phoneDao.pagePackage(phoneName);
        PageEnum page = PageEnum.getPage(pageInfo);
        assert page != null;
        String activityName = page.getActivityName();

        for (int i = 0; i < handlePage.size(); i++) {
            HandlePage hp = handlePage.get(i);
            if (hp.handle(pageInfo)) {
                break;
            }
        }

        return true;
    }


    /**
     * 结束任务
     */
    public void delTask() {

    }


    //==================
    public void setPhoneDao(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    public void setHandlePage(List<HandlePage> handlePage) {
        this.handlePage = handlePage;
    }
}
