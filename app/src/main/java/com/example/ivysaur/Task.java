package com.example.ivysaur;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Task extends RealmObject {

    @PrimaryKey
    private long id;

    private Date deadLine;

    private String title;

    private String detail;

}
