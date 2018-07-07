package hpcomputeing.tools;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/9
 * Time: 9:47
 */

import hpcomputeing.entities.Cluster;
import hpcomputeing.entities.PieChartsParam;
import hpcomputeing.entities.Stock;
import hpcomputeing.entities.Zuhe;

import java.util.ArrayList;
import java.util.List;

/**
 *@ClassName: ControllerUtils
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/6/9 9:47
 **/
public class ControllerUtils {

    /*
 * 根据所有聚类，计算各个聚类含有股票的比例，以及将所有聚类转换为Zuhe的形式*/
    public static List<PieChartsParam> calPieChartsParamAndTurnZuhe(List<Cluster> clusterList, List<Zuhe> clusterZuheList){
        if (clusterZuheList == null)throw new NullPointerException ("clusterZuheList 不能为null！");
        int totalNum  = 0;
        List<PieChartsParam> pieChartsParamList = new ArrayList<>();
        for (Cluster cluster:clusterList){
            //计算piechart的参数
            String name ="第"+cluster.getId()+"聚类";
            pieChartsParamList.add(new PieChartsParam(cluster.getStockList().size(),name));
            totalNum += cluster.getStockList().size();

            //转换为Zuhe类别
            for (Stock stock: cluster.getStockList()){
                clusterZuheList.add(new Zuhe(cluster.getId(),stock.getId(),stock.getName()));
            }
        }
        return pieChartsParamList;
    }
}
