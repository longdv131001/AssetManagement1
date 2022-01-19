package com.nashtech.rookie.assetmanagement.entity;

public class Enum {
	public enum State {
		Available,Not_Available,Waiting_for_recycling,Recycled,Assigned
	}
	
	public enum Gender{
		MALE,FEMALE
	}
	
	public enum StateAssignment{
		Accepted,Waiting_for_acceptance,Declined
	}
	
	public enum Type{
		Admin,Staff
	}
}
