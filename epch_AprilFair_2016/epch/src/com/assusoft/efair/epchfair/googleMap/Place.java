package com.assusoft.efair.epchfair.googleMap;

public class Place {
	String placeName,vicinity,type,formattedAddress;
	Double lat,lng;
	String id,reference;
	Float rating;
	public Place(){
		super();
	}
	public Place(String placeName, String vicinity, String type,Double lat,Double lng) {
        this.placeName = placeName;
        this.vicinity = vicinity;
        this.type = type;
        this.lat=lat;
        this.lng=lng;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public void setLatitude(Double lat){
    	this.lat=lat;
    }
    
    public Double getLatitude(){
    	return this.lat;
    }
	
    public void setLongitude(Double lng){
    	this.lng=lng;
    }
    
    public Double getLongitude(){
    	return this.lng;
    }
    
    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

}
