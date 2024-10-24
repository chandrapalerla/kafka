package com.vehicledistance.model;

public class VehicleState {

  private double latitude;
  private double longitude;
  private double totalDistance;

  public VehicleState(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.totalDistance = 0;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public double getTotalDistance() {
    return totalDistance;
  }

  public void addDistance(double distance) {
    this.totalDistance += distance;
  }

  public void updatePosition(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }
}