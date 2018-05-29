package hpcomputeing.domain;

import hpcomputeing.entities.Cluster;
import hpcomputeing.entities.Stock;
import hpcomputeing.entities.Zuhe;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.NotNull;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/22
 * Time: 23:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReadDataFileTest {

    @Test
    public void readClustersTest() throws Exception {
        List<Cluster>clusterList =  ReadDataFile.readClusters("model1");

        Assert.assertThat(clusterList.size(),
                is(5));
        for (Cluster cluster: clusterList){
            System.out.println(cluster.getId()+","+cluster.getName());
        }
    }
    @Test
    public void readZuheTest() throws Exception {
        List<Zuhe>clusterList =  ReadDataFile.readZuhe("model1");

        Assert.assertThat(clusterList.size(),
                is(5));
        for (Zuhe cluster: clusterList){
            System.out.println(cluster.getId()+","+cluster.getName());
        }
    }

    @Test
    public void readStockListTest() throws Exception {
        List<Stock> stockList =  ReadDataFile.readStockList("model1");

        Assert.assertThat(stockList, notNullValue());

    }

    @Test
    public void readParamTest() throws Exception {
        List<Stock> paramList =  ReadDataFile.ReadParam("model1");

        Assert.assertThat(paramList, notNullValue());

    }

}