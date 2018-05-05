package my.own;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.net.URISyntaxException;

public class RddInAction {
    public static void main(String[] args) throws URISyntaxException {
        String path = RddInAction.class.getClassLoader()
                        .getResource("s.csv")
                        .toURI()
                        .getPath();

        SparkConf conf = new SparkConf()
                            .setAppName("RDD in Action")
                            .setMaster("local[*]");

        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> dataRdd = sc.textFile(path);

        PairFunction<String, String, Double> rowSplitFunction = p -> {
            String[] columns = p.split(",");
            return new Tuple2<>(columns[0], Double.valueOf(columns[3]));
        };
        Function2<Double, Double, Double> sumFunction = (u, v) -> u + v;

        JavaPairRDD<String, Double> resultRdd = dataRdd
                                                    .mapToPair(rowSplitFunction)
                                                    .aggregateByKey(0d, sumFunction, sumFunction);

        System.out.println(resultRdd.collectAsMap());
        System.out.println("Success !!!");
    }
}