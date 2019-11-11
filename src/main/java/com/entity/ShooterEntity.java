package com.entity;

import javax.persistence.*;

@Entity
public class ShooterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( nullable = false)
    private long id;
    @Basic
    @Column (nullable = true, unique = true)
    private String shooterId;
    @Basic
    @Column (nullable = true)
    private String name;
    @Basic
    @Column (nullable = true)
    private double standStat;
    @Basic
    @Column (nullable = true)
    private int standShootCount;
    @Basic
    @Column (nullable = true)
    private int standHit;
    @Basic
    @Column (nullable = true)
    double lyingStat;
    @Basic
    @Column (nullable = true)
    private int lyingShootCount;
    @Basic
    @Column (nullable = true)
    private int lyingHit;
    @Basic
    @Column (nullable = true)
    private double generalStat;

    @Basic
    @Column (nullable = true)
    private double xHits;

    @Basic
    @Column (nullable = true)
    private double yHits;

    @Basic
    @Column(nullable = true)
    private int countOfRegHits;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getShooterId() {
        return shooterId;
    }

    public void setShooterId(String shooterId) {
        this.shooterId = shooterId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStandShootCount() {
        return standShootCount;
    }

    public void setStandShootCount(int standShootCount) {
        this.standShootCount = standShootCount;
    }

    public int getStandHit() {
        return standHit;
    }

    public void setStandHit(int standHit) {
        this.standHit = standHit;
    }

    public int getLyingShootCount() {
        return lyingShootCount;
    }

    public void setLyingShootCount(int lyingShootCount) {
        this.lyingShootCount = lyingShootCount;
    }

    public int getLyingHit() {
        return lyingHit;
    }

    public void setLyingHit(int lyingHit) {
        this.lyingHit = lyingHit;
    }

    public double getStandStat() {
        return standStat;
    }

    public void setStandStat(double standStat) {
        this.standStat = standStat;
    }

    public double getLyingStat() {
        return lyingStat;
    }

    public void setLyingStat(double lyingStat) {
        this.lyingStat = lyingStat;
    }

    public double getGeneralStat() {
        return generalStat;
    }

    public void setGeneralStat(double generalStat) {
        this.generalStat = generalStat;
    }

    public double getxHits() {
        return xHits;
    }

    public void setXHits(double xHits) {
        this.xHits = xHits;
    }

    public int getCountOfRegHits() {
        return countOfRegHits;
    }

    public void setCountOfRegHits(int countOfRegHits) {
        this.countOfRegHits = countOfRegHits;
    }

    public double getyHits() {
        return yHits;
    }

    public void setYHits(double yHits) {
        this.yHits = yHits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShooterEntity that = (ShooterEntity) o;
        return shooterId != null ? shooterId.equals(that.shooterId) : that.shooterId == null;

    }

    @Override
    public int hashCode() {
        return shooterId != null ? shooterId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ShooterEntity{" +
                "name='" + name + '\'' +
                ", generalStat=" + generalStat +
                '}';
    }
}
