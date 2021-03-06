package com.chinaredstar.longyan.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by mdc on 2016/8/3.
 */
@Entity
@Table(name="xiwa_redstar_lottery_location")
public class RedstarLotteryLocation extends BaseEntity {
    private String name;

    private Double latitude;

    private Double longitude;

    private Double distance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
