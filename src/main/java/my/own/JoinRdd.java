package my.own;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JoinRdd {
    public static void main(String[] args) throws URISyntaxException {
        SparkConf conf = new SparkConf().setAppName("RDD Join").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<AccountFactRow> accountDataRdd = loadAccountFact(sc);
        JavaRDD<TimeRow> timeDimensionRdd = loadTimeDimension(sc);
        JavaRDD<TimelineRow> cartesianRdd = accountDataRdd
                                            .cartesian(timeDimensionRdd)
                                            .filter(c -> c._1.getStartMinute() <= c._2.getMinute() && c._1.getEndMinute() > c._2.getMinute())
                                            .map(f -> new TimelineRow(f._1.getAccountId(), f._2.getTime(), f._1.getAccountBalance(), f._1.getEffectiveDate()));

        System.out.println(cartesianRdd.collect());
    }

    private static JavaRDD<TimeRow> loadTimeDimension(JavaSparkContext sc) throws URISyntaxException {
        JavaRDD<String> timelineRdd = sc.textFile(JoinRdd.class
                .getClassLoader()
                .getResource("time.csv")
                .toURI()
                .getPath());
        return timelineRdd.map(s -> {
            String[] elements = s.split(",");
            Integer minute = Integer.valueOf(elements[0]);
            return new TimeRow(minute, elements[1]);
        });
    }

    private static JavaRDD<AccountFactRow> loadAccountFact(JavaSparkContext sc) throws URISyntaxException {
        JavaRDD<String> dataRdd = sc.textFile(JoinRdd.class
                .getClassLoader()
                .getResource("s.csv")
                .toURI()
                .getPath());
        return dataRdd.map(s -> {
            String[] elements = s.split(",");
            return new AccountFactRow(elements[0],
                        Integer.valueOf(elements[1]),
                        Integer.valueOf(elements[2]),
                        Double.valueOf(elements[3]),
                        LocalDate.parse(elements[4], DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        });
    }
}
