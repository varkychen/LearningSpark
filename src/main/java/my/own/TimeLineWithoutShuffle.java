package my.own;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.net.URISyntaxException;

public class TimeLineWithoutShuffle {
    public static void main(String[] args) throws URISyntaxException {
        SparkConf conf = new SparkConf().setAppName("Create time-line without Shuffle").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        String path = TimeLineWithoutShuffle.class.getClassLoader().getResource("account_balance.csv").toURI().getPath();
        JavaRDD<String> accountFactData = sc.textFile(path);

        JavaRDD<String[]> rowData = accountFactData.map(t -> t.split(",")).filter(a -> a[0].equals("1001"));
    }
}
