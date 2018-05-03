package my.own;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

public class HelloWorld {
    public static void main(String... args) {
        SparkSession spark = new SparkSession.Builder()
                                .appName("HelloWorld")
                                .master("local")
                                .config("spark.sql.warehouse.dir", "file:///C:/Temp")
                                .getOrCreate();

        String readMeFile = "file:///C:/Users/cool_/IdeaProjects/sparkhelloworld/src/main/resources/README.md";

        Dataset<String> readmeData = spark.read().textFile(readMeFile).cache();

        long numAs = readmeData.filter(s -> s.contains("a")).count();
        long numBs = readmeData.filter(s -> s.contains("b")).count();

        System.out.printf("Lines with a: %d, lines with b: %d", numAs, numBs);

        spark.stop();
    }
}
