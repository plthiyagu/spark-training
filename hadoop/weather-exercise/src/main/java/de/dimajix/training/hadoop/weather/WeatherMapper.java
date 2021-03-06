package de.dimajix.training.hadoop.weather;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.*;
import java.util.HashMap;


class WeatherMapper extends Mapper<LongWritable,Text,Text,WeatherData> {
    private final HashMap<String,String> countries = new HashMap<String,String>();

    @Override
    public void setup(Context context) {
        File countriesFile = new File("./countries");
        try (BufferedReader br = new BufferedReader(new FileReader(countriesFile))) {
            CSVParser parser = new CSVParser(br, CSVFormat.RFC4180);
            for (CSVRecord record : parser) {
                // USAF code of station
                String usaf = record.get(0);
                // WBAN code of station
                String wban = record.get(1);
                // Country code
                String country = record.get(3);

                String stationCcode = usaf + wban;
                countries.put(stationCcode, country);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cleanup(Context context) {
    }

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
    }
}