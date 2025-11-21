package com.example.api.adapter.output.bd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hello")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelloEntity {
    
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "message")
    private String message;
}
