package hpcomputeing.tools;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/31
 * Time: 18:12
 */

import hpcomputeing.domain.ReadDataFile;
import hpcomputeing.entities.Stock;

import java.util.*;

/**
 *@ClassName: Test
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/5/31 18:12
 **/
public class Test {
    public static void main(String[] args) {


        testOptiona();
    }
    public  static void testOptiona(){
        /* of */
        //调用工厂方法创建Optional实例
        Optional<String> name = Optional.of("YanWei");
        //传入参数为null，抛出NullPointerException.
        //Optional<String> someNull = Optional.of(null);

        /*opNullable*/
        Optional empty = Optional.ofNullable(null);

        /*isPresent*/
        if (name.isPresent()) {
            System.out.println(name.get());//输出YanWei
        }

        /*get*/
        try {
            System.out.println(empty.get());
        } catch (NoSuchElementException ex) {
            System.err.println(ex.getMessage());
        }

        /*ifPresent*/
        name.ifPresent((value) -> {
            System.out.println("The length of the value is: " + value.length());
        });

        /*orElse*/
        System.out.println(empty.orElse("There is no value present!"));
        System.out.println(name.orElse("There is some value!"));

        /*orElseGet*/
        System.out.println(empty.orElseGet(() -> "Default Value"));
        System.out.println(name.orElseGet(String::new));
        /*orElse*/
        System.out.println(empty.orElse("There is no value present2!"));
        System.out.println(empty.orElseGet(() -> "Value"));
        /*orElseThrow*/
        try {
            empty.orElseThrow(IllegalArgumentException::new);
        } catch (Throwable ex) {
            System.out.println("error:" + ex.getMessage());
        }

        /*map*/
        Optional<String> upperName = name.map((value) -> value.toUpperCase());
        System.out.println(upperName.orElse("No value found"));

        /*flatMap*/
        upperName = name.flatMap((value) -> Optional.of(value.toUpperCase()));
        System.out.println(upperName.get());

        /*filter*/
        List<String> names = Arrays.asList("YanWei","YanTian");
        for(String s:names)
        {
            Optional<String> nameLenLessThan7 = Optional.of(s).filter((value) -> value.length() < 7);
            System.out.println(nameLenLessThan7.orElse("The name is more than 6 characters"));
        }
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
