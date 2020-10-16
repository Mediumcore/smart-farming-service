package com.sdpm.smart.farming.devicemgr.entity;

import javax.persistence.*;

/**
 * 寻迹路线
 *
 * @author shirukai
 */
@Entity
public class TraceRoute {
    /**
     * 路线ID 唯一主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 路线名称
     */
    private String name;

    private String center;
    @Lob
    @Column(columnDefinition="TEXT")
    private String locations;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }
}
