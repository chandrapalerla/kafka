package com.vehicledistance.model;

public class VehicleDistance {

  private String vehicleId;
  private Double totalDistance;

  public VehicleDistance(String vehicleId, Double totalDistance) {
    this.vehicleId = vehicleId;
    this.totalDistance = totalDistance;
  }

  public String getVehicleId() {
    return vehicleId;
  }

  public void setVehicleId(String vehicleId) {
    this.vehicleId = vehicleId;
  }

  public Double getTotalDistance() {
    return totalDistance;
  }

  public void setTotalDistance(Double totalDistance) {
    this.totalDistance = totalDistance;
  }
}