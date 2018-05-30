package hpcomputeing.service.impl;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/23
 * Time: 11:03
 */

import hpcomputeing.domain.ReadDataFile;
import hpcomputeing.entities.Cluster;
import hpcomputeing.entities.Stock;
import hpcomputeing.entities.Zuhe;
import hpcomputeing.service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@ClassName: IndexServiceImpl
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/5/23 11:03
 **/
@Service
public class IndexServiceImpl implements IndexService{

    private  final  Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public List<String> getModels() {
        return ReadDataFile.ReadModels();
    }

    @Override
    public List<Stock> getAllStcokByModel(String model) {
        return ReadDataFile.readStockList(model);
    }

    @Override
    public List<Cluster> getClusterByModel(String model) {
        return ReadDataFile.readClusters(model);
    }

    @Override
    public List<Zuhe> getZuheByModel(String model) {
        return ReadDataFile.readZuhe(model);
    }

    @Override
    public List<Stock> getParamByModel(String model) {
        return ReadDataFile.ReadParam(model);
    }
    @Override
    public List<String> readDateOrStocks(String model,String fileName){
        return ReadDataFile.readDateOrStocks(model,fileName);
    }
    @Override
    public List<String> getStockDataByModelAndStock(String modelName,int lineNumber){
        return ReadDataFile.readPreAndTrueByLineNumber(modelName,lineNumber);
    }
}
