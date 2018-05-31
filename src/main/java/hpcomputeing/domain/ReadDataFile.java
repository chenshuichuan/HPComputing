package hpcomputeing.domain;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/22
 * Time: 22:22
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hpcomputeing.entities.Cluster;
import hpcomputeing.entities.Stock;
import hpcomputeing.entities.Zuhe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *@ClassName: ReadDataFile
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/5/22 22:22
 **/

public class ReadDataFile {
    private static String dataPath="./data/";
    private static String clusterFile="cluster.txt";
    private static String zuheFile="zuhe.txt";
    private static String stock_listFile="stock_list.json";
    private static String paramFile="param.txt";

    private static String modelFile = "models.txt";
    /*读取cluster.txt的数据
    * 格式：要求数据为utf-8的格式
    * 第0类的股票有以下：
    * 000001,平安银行
    * 000009,中国宝安
    *
    * */
    public static List<Cluster> readClusters(String model){

        List<Cluster> clusterList = new ArrayList<>();

        File file=new File(dataPath+model+"/"+clusterFile);
        BufferedReader reader=null;
        String temp=null;
        int line=1;
        try{
            Cluster cluster = null;
            int clusterId = 0;
            reader=new BufferedReader(new FileReader(file));
            while((temp=reader.readLine())!=null&&temp.length()>0){
//                System.out.println("line"+line+":"+temp);
//                line++;
                //读到新一类
                if (temp.contains("类的股票有以下")){
                    //System.out.println(temp+"非空，存入列表");
                    //非空，则将上一类存入列表
                    if (cluster!=null)clusterList.add(cluster);
                    //新起一类
                    clusterId++;
                    cluster = new Cluster(Integer.toString(clusterId),temp);

                }
                //读到正常股票数据
                else{
                    //System.out.println(temp+"，存入列表");
                    String[] arr= temp.split(",");
                    if (arr.length>2||arr.length<=0)
                        throw new ArrayIndexOutOfBoundsException("cluster.txt数据异常！");
                    cluster.getStockList().add(new Stock(arr[0],arr[1]));
                }
            }
            //非空，则将最后一类存入列表
            if (cluster!=null)clusterList.add(cluster);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(reader!=null){
                try{
                    reader.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return clusterList;
    }

    /*读取zuhe.txt的数据
    * 格式：要求数据为utf-8的格式
    *
    *0,551,创元科技
    * 1,2026,山东威达
    * 2,600208,新湖中宝
    * 3,2375,亚厦股份
    * 4,2361,神剑股份
    *
    * */
    public static List<Zuhe> readZuhe(String model){

        List<Zuhe> zuheList = new ArrayList<>();

        File file=new File(dataPath+model+"/"+zuheFile);
        BufferedReader reader=null;
        String temp=null;

        try{
            Zuhe cluster = null;

            reader=new BufferedReader(new FileReader(file));
            while((temp=reader.readLine())!=null&&temp.length()>0){
                //System.out.println(temp+"，存入列表");
                temp = temp.replace(" ","");
                String[] arr= temp.split(",");
                if (arr.length>3||arr.length<=0)
                    throw new ArrayIndexOutOfBoundsException("zuhe.txt数据异常！");
                int classNum = Integer.parseInt(arr[0])+1;
                cluster = new Zuhe(Integer.toString(classNum),arr[1],arr[2]);
               zuheList.add(cluster);

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(reader!=null){
                try{
                    reader.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return zuheList;
    }

    /*读取stock_list.json的数据
    * 格式：要求数据为utf-8的格式,只有一行
    *{'300135': '宝利国际', '600583': '海油工程', '000595': '宝塔实业'}
    * */
    public static List<Stock> readStockList(String model){
        List<Stock> stockList = new ArrayList<>();
        File file=new File(dataPath+model+"/"+stock_listFile);
        BufferedReader reader=null;
        String temp=null;
        try{
            reader=new BufferedReader(new FileReader(file));
            int count =0;
            while((temp=reader.readLine())!=null&&temp.length()>0){
                System.out.println(temp+"，----读取到");
                count++;
                if (count!=1)
                    throw new ArrayIndexOutOfBoundsException("stock_list.json数据异常！");
                stockList = transJsonToStock(temp.replace(" ",""));
                //allStock
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(reader!=null){
                try{
                    reader.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return stockList;
    }

    private static List<Stock> transJsonToStock(String jsonStr){

        List<Stock> stockList = new ArrayList<>();
        if(JSONObject.parseObject(jsonStr)!=null)
        {
            JSONObject json = JSONObject.parseObject(jsonStr);
            //System.out.println("json = "+json);
            Set<String>stringSet = json.keySet();
            for (String key : stringSet) {
                String value = json.getString(key);
                //System.out.println("key = "+key+",value = "+value);
                stockList.add(new Stock(key,value));
            }
        }
        return stockList;
    }
    /*读取models.txt的数据
        * 格式：要求数据为utf-8的格式
        * model1
        * model2
        * model3
        * */
    public static List<String> ReadModels(){

        File file=new File(dataPath+modelFile);
        BufferedReader reader=null;
        String temp=null;
        List<String> modelList = new ArrayList<>();
        try{
            reader=new BufferedReader(new FileReader(file));
            int count =0;
            while((temp=reader.readLine())!=null&&temp.length()>0){
                System.out.println(temp+"，----读取到");
                modelList.add(temp);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(reader!=null){
                try{
                    reader.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return modelList;
    }


    /*读取param.txt的数据
     * 格式：要求数据为utf-8的格式
     * 股票数量:10
     * 起始日期:2018-05-23
     * 所需天数:10
     * 聚类族数:5
     * 预测天数:10
      * */
    public static List<Stock> ReadParam(String model){

        File file=new File(dataPath+model+"/"+paramFile);
        BufferedReader reader=null;
        String temp=null;
        List<Stock> paramList = new ArrayList<>();
        paramList.add(new Stock("测试id","测试值"));
        try{
            reader=new BufferedReader(new FileReader(file));
            int count =0;
            while((temp=reader.readLine())!=null&&temp.length()>0){
                //System.out.println(temp+"，----读取到");
                String[] arr=temp.split(":");
                if (arr.length>2||arr.length<=0)
                    throw new ArrayIndexOutOfBoundsException("param.txt数据异常！");
                paramList.add(new Stock(arr[0],arr[1]));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(reader!=null){
                try{
                    reader.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return paramList;
    }


    /*读取data.csv或者stocks.csv的数据
   * 格式：要求数据为utf-8的格式
   *stocks.csv
   *0,创元科技
   * 1,山东威达
   * 2,新湖中宝
   * 3,亚厦股份
   * 4,神剑股份
   *date.csv
   * 0,2017/12/18
   * 1,2017/12/19
   * 2,2017/12/20
   * 3,2017/12/21
   * 4,2017/12/22
   * 5,2017/12/25
   * */
    public static List<String> readDateOrStocks(String model,String fileName){

        List<String> stringList = new ArrayList<>();

        File file=new File(dataPath+model+"/"+fileName);
        BufferedReader reader=null;
        String temp=null;

        try{

            reader=new BufferedReader(new FileReader(file));
            while((temp=reader.readLine())!=null&&temp.length()>0){
                //System.out.println(temp+"，存入列表");
                String[] arr= temp.split(",");
                if (arr.length>2||arr.length<=0)
                    throw new ArrayIndexOutOfBoundsException("readDateOrStocks数据异常！");
                stringList.add(arr[1].replace(" ",""));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(reader!=null){
                try{
                    reader.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        System.out.println("读取到"+stringList.size()+"条记录！");
        return stringList;
    }

    /* 根据给定的行号（从0行开始算起）
     * 读取pre.csv或者true.csv的数据
     * 返回两行，两个String对象
     * 格式：要求数据为utf-8的格式
     *
     *
     * */
    public static List<String> readPreAndTrueByLineNumber(String model,int lineNumber){

        List<String> stringList = new ArrayList<>();

        File prefile=new File(dataPath+model+"/"+"pre.csv");
        BufferedReader pre_reader=null;
        File truefile=new File(dataPath+model+"/"+"true.csv");
        BufferedReader true_reader=null;
        String preStr=null;
        String trueStr=null;
        try{
            pre_reader=new BufferedReader(new FileReader(prefile));
            true_reader=new BufferedReader(new FileReader(truefile));

            int lineCount = 0;
            while((preStr=pre_reader.readLine())!=null&&preStr.length()>0&&
                    (trueStr=true_reader.readLine())!=null&&trueStr.length()>0){
                //System.out.println(lineCount+"行，读取到");
                if (lineCount == lineNumber){
                    System.out.println(lineCount+"行读取到preStr="+preStr);
                    System.out.println(lineCount+"行读取到trueStr="+trueStr);

                    stringList.add(preStr);
                    stringList.add(trueStr);
                    break;
                }
                lineCount++;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(pre_reader!=null){
                try{
                    pre_reader.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(true_reader!=null){
                try{
                    true_reader.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        System.out.println("读取到"+stringList.size()+"条记录！");
        return stringList;
    }
}
