package com.urakhov.dao;

import com.urakhov.domain.Respondent;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

public interface RespondentDAO {
    void setDataSource(DataSource dataSource);
    void addRespondent(String surname, LocalDate birth, String gender, String email, String phone);
   /* void addRespondent(String surname, LocalDate birth, String gender, String phone);
    void addRespondent(String surname, LocalDate birth, String gender, String email);*/
    //void addRespondent(String surname, LocalDate birth, String gender);


    Respondent getRespondentById(int id);
    void updateRespondent(int id, String surname, LocalDate birth, String gender, String email, String phone);
    void removeRespondent(int id);

    List<Respondent> getRespondentList();
}
