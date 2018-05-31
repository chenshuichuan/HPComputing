package hpcomputeing.tools;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/31
 * Time: 18:12
 */

import hpcomputeing.domain.ReadDataFile;
import hpcomputeing.entities.Stock;

import java.util.ArrayList;
import java.util.List;

/**
 *@ClassName: Test
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/5/31 18:12
 **/
public class Test {
    public static void main(String[] args) {

//        List<String>strings = new ArrayList<>();
//        strings.add("str1");
//        strings.add("str2");
//        strings.add("str3");
//        strings.add("str1");
//        for (String str:strings){
//            switch (str){
//                case "str1":{
//                    int i =0;
//                    System.out.println("str1"+i);
//                    break;}
//                case "str2":
//                    System.out.println("str2");
//                    break;
//                case "str3":
//                    System.out.println("str3");
//                    break;
//            }
//        }

        testCase();
    }
    public  static void testCase(){
        List<Stock> paramList = ReadDataFile.ReadParam("model2");
        for(Stock param :paramList){
            System.out.println(param.getId()+"="+param.getName());
            switch (param.getId()){
                /*这个case有bug*/
                case "股票数量":
                    System.out.println(param.getId()+": march case!");
                    break;
                case "起始日期":
                    System.out.println(param.getId()+": march case!");
                    break;
                case "所需天数":
                    System.out.println(param.getId()+": march case!");
                    break;
                case "聚类族数":
                    System.out.println(param.getId()+": march case!");
                    break;
                case "预测天数":
                    System.out.println(param.getId()+": march case!");
                    break;
                default:
                    System.out.println(param.getId()+": no march case!");
            }
        }
    }
}
