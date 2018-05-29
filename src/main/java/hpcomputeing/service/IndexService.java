package hpcomputeing.service;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/23
 * Time: 11:03
 */

import hpcomputeing.domain.ReadDataFile;
import hpcomputeing.entities.Cluster;
import hpcomputeing.entities.Stock;
import hpcomputeing.entities.Zuhe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *@ClassName: IndexService
 *@Description: 根据解析规则返回相应的数据。
 *@Author: Ricardo
 *@Date: 2018/5/23 11:03
 **/
public interface IndexService {

    public List<String> getModels();

    public List<Stock> getAllStcokByModel(String model);

    public List<Cluster> getClusterByModel(String model);
    public List<Zuhe> getZuheByModel(String model);

    public List<Stock> getParamByModel(String model);

}
