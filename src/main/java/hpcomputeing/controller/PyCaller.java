package hpcomputeing.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/25
 * Time: 20:28
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *@ClassName: PyCaller
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/5/25 20:28
 **/
public class PyCaller {
    public static void main(String[] args) {
        pythonTest1();
        System.out.println("test1 finish!");
        pythonTest2();
        System.out.println("test2 finish!");
    }
    public static void pythonTest1(){
        try {
            Process pr = Runtime.getRuntime().exec("python ./data/test.py");
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                //line = decodeUnicode(line);
                System.out.println(line);
            }
            in.close();
            pr.waitFor();
            System.out.println("end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void pythonTest2(){
        try {
            //需传入的参数
            String a = "a", b = "D3455054", c = "LJ12GKS28D4418248", d = "qingdao";
            System.out.println("start;;;" + a);
            //设置命令行传入参数
            String[] args = new String[] { "python", "./data/test2.py", a, b, c, d };
            Process pr = Runtime.getRuntime().exec(args);

            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                //line = decodeUnicode(line);
                System.out.println(line);
            }
            in.close();
            pr.waitFor();
            System.out.println("end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
