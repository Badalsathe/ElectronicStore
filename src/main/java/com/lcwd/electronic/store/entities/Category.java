package com.lcwd.electronic.store.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "categories")
public class Category {

    @Id
    @Column(name = "Id")
    private  String categoryId;

    @Column(name="category_title", length = 60,nullable = false)
    private String title;

    @Column(name="category_desc", length = 60)
    private String description;

    private String coverImage;


}
