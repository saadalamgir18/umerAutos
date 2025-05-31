package com.example.umerautos.repositories;

import com.example.umerautos.entities.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expenses, UUID> {
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expenses e WHERE FUNCTION('DATE', e.createdAt) = CURRENT_DATE" )
    int todayExpense();


    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expenses e WHERE e.createdAt BETWEEN :startOfMonth AND :today")
    int getMonthlyExpense(@Param("startOfMonth") Timestamp startOfMonth, @Param("today") Timestamp today);


}
