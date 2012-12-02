package edu.champlain.csi319.findstuff.model;

import android.os.Parcel;
import android.os.Parcelable;


public class SearchCriteria implements Parcelable
{
	private final String TAG = SearchCriteria.class.getName();
	private String keyword = new String();
	private String category = new String();
	private String subCategory = new String();
	private String country = new String();
	private String state = new String();
	private int id;
    /**
     * Default Constructor - does nothing
     */
	
    private SearchCriteria(Parcel in){
        keyword = in.readString();
        category = in.readString();
        subCategory = in.readString();
        country = in.readString();
        state = in.readString();
    }
    public SearchCriteria() {
    	

    }
	public String getTAG() {
		return TAG;
	}
	
	//
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	//
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	//	
    public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	//
	public String getCountry() {
		return country;
	}
	public void setCountryString(String country) {
		this.country = country;
	}

	//
	public String getState() {
		return state;
	}
	public void setStateString(String state) {
		this.state = state;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	//
	// Parcel Implementation
	//
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(keyword);
		dest.writeString(category);
		dest.writeString(subCategory);
		dest.writeString(country);
		dest.writeString(state);
		dest.writeInt(id);		
	}


	public static final SearchCriteria.Creator<SearchCriteria> CREATOR = new SearchCriteria.Creator<SearchCriteria>() {
        public SearchCriteria createFromParcel(Parcel in) {
            return new SearchCriteria(in);
        }

        public SearchCriteria[] newArray(int size) {
            return new SearchCriteria[size];
        }
    };  


    
    
}
