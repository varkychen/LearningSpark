package my.own;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import scala.Tuple2;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        JavaRDD<String[]> splitMapRdd = dataRdd.map(s -> s.split(","));
        JavaPairRDD<String, Iterable<String[]>> groupByRdd = splitMapRdd.groupBy(s -> s[0]);
        JavaPairRDD<String, Double> flatMapRdd = groupByRdd.flatMapToPair(
                t -> {
                    Double sum = 0d;
                    for (String[] row : t._2()) {
                        sum += Double.valueOf(row[3]);
                    }
                    List<Tuple2<String, Double>> values = new ArrayList<>();
                    values.add(new Tuple2<>(t._1, sum));
                    return values.iterator();
                });

        flatMapRdd.foreach(t -> System.out.printf("Key : %s, Value : %.2f%n", t._1, t._2));

        System.out.println("Success !!!");
    }
}
