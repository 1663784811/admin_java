package com.cyyaw.tx.phone.task.user;


/**
 * 收集用户信息
 */
public abstract class AbstractUserInfo {

    // 下一个操作
    private AbstractUserInfo nextOperation;

    public void go() {
        boolean ok = exec();
        if (ok && null != nextOperation) {
            nextOperation.go();
        }
    }


    // 执行操作
    abstract boolean exec();

}
