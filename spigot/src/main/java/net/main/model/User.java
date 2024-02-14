package net.main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.*;

@Entity
@Table(name = "co_user")
public class User {
    @Id
    @Column(name = "rowid") private int id;
    @Column(name = "time") private int time;
    @Column(name = "user") private String user;
    @Column(name = "uuid") private String uuid;

    public int getId() {return  id;}
    public String getUser() {return user;}
}
