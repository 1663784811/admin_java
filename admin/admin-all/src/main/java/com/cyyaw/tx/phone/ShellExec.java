package com.cyyaw.tx.phone;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ShellExec {


    public static List<String> exec(String cmd) {
        List<String> rest = new ArrayList<>();
        try {
            log.info("==============   执行命令: " + cmd);
            Process psServe = Runtime.getRuntime().exec(cmd);
//            psServe.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(psServe.getInputStream(), "utf-8"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                rest.add(line);
            }
        } catch (Exception e) {
            return null;
        }
        return rest;
    }


}
