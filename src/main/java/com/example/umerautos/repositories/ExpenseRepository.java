package com.example.umerautos.repositories;

import com.example.umerautos.entities.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expenses, UUID> {
    @Query("SELECT SUM(e.amount) FROM Expenses e" )
    double todayExpense();

    @Query("SELECT SUM(e.amount) FROM Expenses e WHERE e.createdAt BETWEEN :startOfMonth AND :today")
    Double getMonthlyExpense(@Param("startOfMonth") Timestamp startOfMonth, @Param("today") Timestamp today);


}
