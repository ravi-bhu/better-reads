package com.bhushan.betterreads.author;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
@Data
public class Author {
    @Id
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String id;

    private String name;

    @Column("personal_name")
    private String personalName;
}
