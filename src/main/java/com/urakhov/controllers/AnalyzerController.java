package com.urakhov.controllers;

import com.urakhov.DatabaseHandler;
import com.urakhov.dao.postgres.RatingDaoImpl;
import com.urakhov.domain.Rating;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class AnalyzerController {

    @FXML
    private BarChart<String, Number> totalRatingChart;
    @FXML
    private CategoryAxis categoryAxis;
    @FXML
    private NumberAxis numberAxis;
    @FXML
    private PieChart genreTotalRatingPieChart;

    private RatingDaoImpl ratingDao;

    @FXML
    void initialize() {
        ratingDao = (RatingDaoImpl) DatabaseHandler.getInstanceForAdminConnection().getBean("ratingDao");
        initBarChart();
        initPieChart();
    }

    private HashMap<String, Float> initMap() {
        HashMap<String, Float> songRatingMap = new HashMap<>();
        for (Rating r : ratingDao.getFirstTenPopularSongs()) {
            String name = r.getName_song();
            Float rating = r.getValue();
            songRatingMap.put(name, rating);
        }
        return songRatingMap;
    }

    private void initBarChart() {

        XYChart.Series<String, Number> totalRating = new XYChart.Series<>();
        HashMap<String, Float> map = initMap();
        TreeMap<String, Float> sortedMap = new TreeMap<>(map);
        for (Map.Entry<String, Float> entry: sortedMap.entrySet()) {
            String songName = entry.getKey();
            Float rating = entry.getValue();
            totalRating.getData().add(new XYChart.Data<>(songName, rating*20));
        }

        totalRatingChart.getData().add(totalRating);
    }

    private void initPieChart() {
        HashMap<String, Float> genreTotalRatingMap = ratingDao.getGenreRatingMap();

        for(Map.Entry<String, Float> entry: genreTotalRatingMap.entrySet()) {
            String genreName = entry.getKey();
            Float rating = entry.getValue();

            PieChart.Data slice = new PieChart.Data(genreName, rating);
            genreTotalRatingPieChart.getData().add(slice);
        }
        //applyCustomColorSequence(FXCollections.observableArrayList(genreTotalRatingPieChart.getData()));
    }

   /* private void applyCustomColorSequence(ObservableList<PieChart.Data> pieChartData) {
        for (PieChart.Data data : pieChartData) {
            data.getNode().setStyle("-fx-pie-color: #FFFF8D;");
        }
    }*/

}
