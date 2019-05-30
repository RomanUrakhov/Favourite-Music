package com.urakhov.dao;

import com.urakhov.domain.Vote;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

public interface PollDAO {
    void setDataSource(DataSource dataSource);
    void addVote(int id_respondent, int id_song, float assessment);
    Vote getVoteById(int id);
    void removeVote(int id);
    void updateVote(int id, int id_respondent, int id_song);

    List<Vote> getAllVotes();
    List<Vote> getVotesByRespondentId(int id_respondent);
    List<Vote> getVotesBySong(int id_song);
    List<Vote> getVotesByDate(LocalDate date);
    List<Vote> getVotesByGender(String gender);
    List<Vote> getVotesByAge(List<LocalDate> ageInterval);
}
