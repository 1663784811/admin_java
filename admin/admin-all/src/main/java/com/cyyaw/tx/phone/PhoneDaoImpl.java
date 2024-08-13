package com.cyyaw.tx.phone;


import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PhoneDaoImpl implements PhoneDao {


    public List<PhoneEntity> phoneList() {
        List<PhoneEntity> rest = new ArrayList<>();
        String shell = "adb devices";
        List<String> exec = ShellExec.exec(shell);
        boolean ok = false;
        for (int i = 0; i < exec.size(); i++) {
            String s = exec.get(i);
            if (s.indexOf("List of devices attached") != -1) {
                ok = true;
            } else if (ok) {
                String[] split = s.split("\t");
                if (split.length >= 2) {
                    if (!"offline".equals(split[1])) {
                        PhoneEntity entity = new PhoneEntity();
                        entity.setName(split[0]);
                        rest.add(entity);
                    }
                }
            }
        }
        return rest;
    }


    public String phoneImage(String phone) {
        String fileName = "screen.png";
        // 截图
        String shell = "adb -s " + phone + " shell screencap -p /sdcard/" + fileName;
        List<String> exec = ShellExec.exec(shell);
        // 传到本地
        String path = "/" + phone;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String pull = "adb -s " + phone + " pull /sdcard/" + fileName + " " + path;
        List<String> execPull = ShellExec.exec(pull);
        return path + "/" + fileName;
    }

    public String phoneStructure(String phone) {
        String fileName = "window_dump.xml";
        String shell = "adb -s " + phone + " shell uiautomator dump > /sdcard/" + fileName;
        List<String> exec = ShellExec.exec(shell);
        // 传到本地
        String path = "/" + phone;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String pull = "adb -s " + phone + " pull /sdcard/" + fileName + " " + path;
        List<String> execPull = ShellExec.exec(pull);
        StringBuffer sb = new StringBuffer();
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader(new File(path + "/" + fileName)));
            while (bf.ready()) {
                String line = bf.readLine() + "\n";
                sb.append(line);
            }
        } catch (Exception e) {

        } finally {
            if (null != bf) {
                try {
                    bf.close();
                } catch (IOException e) {
                }
            }
        }
        return sb.toString();
    }

    /**
     * 获取当前页面信息
     */
    public List<String> pageInfo(String phone) {
        String shell = "adb -s " + phone + " shell dumpsys window windows";
        return ShellExec.exec(shell);
    }


    /**
     * 获取当前页面包名
     */
    public String pagePackage(String phone) {
        List<String> sList = pageInfo(phone);
        for (int i = 0; i < sList.size(); i++) {
            String s = sList.get(i);
            int mCurrentFocus = s.indexOf("mCurrentFocus");
            if (mCurrentFocus != -1) {
                System.out.println(s);
                return s;
            }
            int mFocusedApp = s.indexOf("mFocusedApp");
            if (mFocusedApp != -1) {
                System.out.println(s);
            }
        }

        return "";
    }

    @Override
    public void clickHome(String phone) {
        String shell = "adb -s " + phone + " shell input keyevent 3";
        ShellExec.exec(shell);
    }

}
