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

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
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

    @Test
    public void readDateOrStocksTest() throws Exception {
        List<String> stringList =  ReadDataFile.readDateOrStocks("model2","date.csv");

        List<String> stockList =  ReadDataFile.readDateOrStocks("model2","stocks.csv");

        Assert.assertThat(stringList.size(), allOf( greaterThan(0), lessThan(101) ) );

        Assert.assertThat(stockList.size(), allOf( greaterThan(0), lessThan(1002) ) );


    }
    @Test
    public void readPreAndTrueByLineNumberTest() throws Exception {
        List<String> stringList =  ReadDataFile.readPreAndTrueByLineNumber("model2",5);

        Assert.assertThat(stringList.size(), is(2 ) );


    }

}