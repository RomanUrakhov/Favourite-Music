package com.urakhov.dao;

import com.urakhov.domain.Rating;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

public interface RatingDAO {
    void setDataSource(DataSource dataSource);
    void removeRating(int id_category);

    List<Rating> getGeneralRatingList();
    List<Rating> getFirstTenPopularSongs();

    List<Rating> getMenRatingList();
    List<Rating> getWomenRatingList();
    List<Rating> getTenRatingList();
    List<Rating> getSixteenRatingList();
    List<Rating> getTwentytwoRatingList();
    List<Rating> getThirtySixRatingList();

    HashMap<String, Float> getGenreRatingMap();


}
