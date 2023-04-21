package io.gihub.apilogic.multiclient.models;

import java.util.ArrayList;
import java.util.List;

public class HotelSearchParams {

  private int detailLevel;
  private String currency;
  private HotelSearchCriteriaInput hotelLocation;
  private List<HotelSearchCriteriaInput> hotelRef = new ArrayList<>();
  private DateRangeInput dateRange;
  private List<RoomPax> roomPaxes = new ArrayList<>();
  private List<String> mealPlans = new ArrayList<>();
  private RateRange rateRange;
  private List<Integer> rating = new ArrayList<>();
  private Position position;
  private List<String> hotelProvideIds = new ArrayList<>();
  private boolean nonRefundable;
  private boolean taxDetails;
  private boolean supplementDetails;

  private String context = "b2bDefault";

  public int getDetailLevel() {
    return detailLevel;
  }

  public void setDetailLevel(int detailLevel) {
    this.detailLevel = detailLevel;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public HotelSearchCriteriaInput getHotelLocation() {
    return hotelLocation;
  }

  public void setHotelLocation(HotelSearchCriteriaInput hotelLocation) {
    this.hotelLocation = hotelLocation;
  }

  public List<HotelSearchCriteriaInput> getHotelRef() {
    return hotelRef;
  }

  public void setHotelRef(List<HotelSearchCriteriaInput> hotelRef) {
    this.hotelRef = hotelRef;
  }

  public DateRangeInput getDateRange() {
    return dateRange;
  }

  public void setDateRange(DateRangeInput dateRange) {
    this.dateRange = dateRange;
  }

  public List<RoomPax> getRoomPaxes() {
    return roomPaxes;
  }

  public void setRoomPaxes(List<RoomPax> roomPaxes) {
    this.roomPaxes = roomPaxes;
  }

  public List<String> getMealPlans() {
    return mealPlans;
  }

  public void setMealPlans(List<String> mealPlans) {
    this.mealPlans = mealPlans;
  }

  public RateRange getRateRange() {
    return rateRange;
  }

  public void setRateRange(RateRange rateRange) {
    this.rateRange = rateRange;
  }

  public List<Integer> getRating() {
    return rating;
  }

  public void setRating(List<Integer> rating) {
    this.rating = rating;
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public List<String> getHotelProvideIds() {
    return hotelProvideIds;
  }

  public void setHotelProvideIds(List<String> hotelProvideIds) {
    this.hotelProvideIds = hotelProvideIds;
  }

  public boolean getNonRefundable() {
    return nonRefundable;
  }

  public boolean getTaxDetails() {
    return taxDetails;
  }

  public boolean getSupplementDetails() {
    return supplementDetails;
  }

  public void setNonRefundable(Boolean nonRefundable) {
    this.nonRefundable = nonRefundable;
  }

  public String getContext() {
    return context;
  }

  public static final class HotelSearchCriteriaInput {

    private String type;
    private String value;

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }

  public static final class DateRangeInput {

    private String start;
    private String end;

    public String getStart() {
      return start;
    }

    public void setStart(String start) {
      this.start = start;
    }

    public String getEnd() {
      return end;
    }

    public void setEnd(String end) {
      this.end = end;
    }
  }

  public static final class RoomPax {

    private int rph;
    private List<Pax> travelers = new ArrayList<>();

    public int getRph() {
      return rph;
    }

    public void setRph(int rph) {
      this.rph = rph;
    }

    public List<Pax> getTravelers() {
      return travelers;
    }

    public void setTravelers(List<Pax> travelers) {
      this.travelers = travelers;
    }
  }

  public static final class Pax {

    private String ageCode;
    private int count;
    private int age;

    public String getAgeCode() {
      return ageCode;
    }

    public void setAgeCode(String ageCode) {
      this.ageCode = ageCode;
    }

    public int getCount() {
      return count;
    }

    public void setCount(int count) {
      this.count = count;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }
  }

  public static final class RateRange {

    private int min;
    private int max;

    public int getMin() {
      return min;
    }

    public void setMin(int min) {
      this.min = min;
    }

    public int getMax() {
      return max;
    }

    public void setMax(int max) {
      this.max = max;
    }
  }

  public static final class Position {

    private String lat;
    private String lon;
    private int radius;

    public String getLat() {
      return lat;
    }

    public void setLat(String lat) {
      this.lat = lat;
    }

    public String getLon() {
      return lon;
    }

    public void setLon(String lon) {
      this.lon = lon;
    }

    public int getRadius() {
      return radius;
    }

    public void setRadius(int radius) {
      this.radius = radius;
    }
  }
}
