package com.kerneldc.avwxora2pg.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Metar {

    private String rawText;
    private String stationId;
    private LocalDateTime observationTime;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal tempC;
    private BigDecimal dewpointC;
    private Long windDirDegrees;
    private Long windSpeedKt;
    private Long windGustKt;
    private BigDecimal visibilityStatuteMi;
    private BigDecimal altimInHg;
    private BigDecimal seaLevelPressureMb;
    private String corrected;
    private String auto;
    private String autoStation;
    private String maintenanceIndicatorOn;
    private String noSignal;
    private String lightningSensorOff;
    private String freezingRainSensorOff;
    private String presentWeatherSensorOff;
    private String wxString;
    private String skyCover1;
    private Long cloudBaseFtAgl1;
    private String skyCover2;
    private Long cloudBaseFtAgl2;
    private String skyCover3;
    private Long cloudBaseFtAgl3;
    private String skyCover4;
    private Long cloudBaseFtAgl4;
    private String flightCategory;
    private BigDecimal threeHrPressureTendencyMb;
    private BigDecimal maxtC;
    private BigDecimal mintC;
    private BigDecimal maxt24hrC;
    private BigDecimal mint24hrC;
    private BigDecimal precipIn;
    private BigDecimal pcp3hrIn;
    private BigDecimal pcp6hrIn;
    private BigDecimal pcp24hrIn;
    private BigDecimal snowIn;
    private Long vertVisFt;
    private String metarType;
    private BigDecimal elevationM;}
